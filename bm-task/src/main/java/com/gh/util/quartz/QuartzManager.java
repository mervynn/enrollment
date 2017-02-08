package com.gh.util.quartz;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.TriggerKey;
import org.quartz.impl.StdScheduler;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;

import com.gh.model.vo.SysConfigQuartz;
import com.gh.service.ISysConfigQuartzService;

/**
 * 基于Quartz和Spring Bean工厂的计划任务管理类
 * 
 * @author HeMingwei
 *
 */
public class QuartzManager implements BeanFactoryAware {
	private Logger log = Logger.getLogger(QuartzManager.class);
	private StdScheduler scheduler;
	private BeanFactory beanFactory = null;
	
	@Autowired
	ISysConfigQuartzService sysConfigQuartzService;

	@SuppressWarnings("unused")
	private void reScheduleJob() throws Exception, ParseException {
		// 通过查询数据库里计划任务来配置计划任务
		SysConfigQuartz d = new SysConfigQuartz();
		List<SysConfigQuartz> quartzList = sysConfigQuartzService.selectConfigQuartzList(d);
		if (quartzList != null && quartzList.size() > 0) {
			for (SysConfigQuartz tbcq : quartzList) {
				configQuatrz(tbcq);
			}
		}
	}

	/**
	 * 配置计划任务
	 * 
	 * @param tbcq
	 * @return
	 */
	public boolean configQuatrz(SysConfigQuartz tbcq) {
		boolean result = false;
		try {
			TriggerKey tk = new TriggerKey(tbcq.getTriggerName(), Scheduler.DEFAULT_GROUP);
			// 运行时可通过动态注入的scheduler得到trigger
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(tk);
			// 如果计划任务已存在则调用修改方法
			if (trigger != null) {
				change(tbcq, trigger);
			} else {
				// 如果计划任务不存在并且数据库里的任务状态为可用时,则创建计划任务
				if (tbcq.getState().equals("1")) {
					this.createCronTriggerBean(tbcq);
				}
			}
			result = true;
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 更新/删除计划任务
	 * 
	 * @param tbcq
	 * @param trigger
	 * @throws Exception
	 */
	public void change(SysConfigQuartz tbcq, CronTrigger trigger)
			throws Exception {
		// 如果任务为可用
		if (tbcq.getState().equals("1")) {
			// 判断从DB中取得的任务时间和现在的quartz线程中的任务时间是否相等
			// 如果相等，则表示用户并没有重新设定数据库中的任务时间，这种情况不需要重新rescheduleJob
			if(!tbcq.getCronExpression().equalsIgnoreCase(trigger.getCronExpression())){
				JobDetail job = createNewJobDetail(tbcq);
				CronTrigger newTrigger = createCronTrigger(tbcq, job);
				scheduler.addJob(job, true); // 将Job添加到管理类
				scheduler.rescheduleJob(trigger.getKey(), newTrigger);
				log.info(new Date() + ": 更新" + tbcq.getTriggerName() + "计划任务");
			}
		} else {
			// 不可用
			scheduler.pauseTrigger(trigger.getKey());// 停止触发器
			scheduler.unscheduleJob(trigger.getKey());// 移除触发器
			scheduler.deleteJob(trigger.getJobKey());// 删除任务
			log.info(new Date() + ": 删除" + tbcq.getTriggerName() + "计划任务");

		}

	}

	/**
	 * 创建/添加计划任务
	 * 
	 * @param tbcq
	 *            计划任务配置对象
	 * @throws Exception
	 */
	public void createCronTriggerBean(SysConfigQuartz tbcq) throws Exception {
		JobDetail job = createNewJobDetail(tbcq);
		CronTrigger triger = createCronTrigger(tbcq, job);
		scheduler.scheduleJob(job, triger);// 注入到管理类
		log.info(new Date() + ": 新建" + tbcq.getTriggerName() + "计划任务");
	}
	
	/**
	 * 创建一个Job
	 * 
	 * @param tbcq
	 * @return
	 * @throws Exception
	 */
	private JobDetail createNewJobDetail(SysConfigQuartz tbcq) throws Exception{
		// 新建一个基于Spring的管理Job类
		MethodInvokingJobDetailFactoryBean mjdfb = new MethodInvokingJobDetailFactoryBean();
		mjdfb.setName(tbcq.getJobDetailName());// 设置Job名称
		// 如果定义的任务类为Spring的定义的Bean则调用 getBean方法
		if (tbcq.getIsSpringBean().equals("1")) {
			mjdfb.setTargetObject(beanFactory.getBean(tbcq.getTargetObject()));// 设置任务类
		} else {
			// 否则直接new对象
			mjdfb.setTargetObject(Class.forName(tbcq.getTargetObject()).newInstance());// 设置任务类
		}

		mjdfb.setTargetMethod(tbcq.getMethodName());// 设置任务方法
		mjdfb.setConcurrent(tbcq.getConcurrent().equals("0") ? false : true); // 设置是否并发启动任务
		mjdfb.setName(tbcq.getJobDetailName());
		mjdfb.afterPropertiesSet();// 将管理Job类提交到计划管理类
		// 将Spring的管理Job类转为Quartz管理Job类
		JobDetail jobDetail = mjdfb.getObject();
		return jobDetail;
	}
	
	/**
	 * 创建一个基于Spring的时间类
	 * 
	 * @param tbcq
	 * @return
	 * @throws Exception
	 */
	private CronTrigger createCronTrigger(SysConfigQuartz tbcq, 
			JobDetail jobDetail) throws Exception{
		CronTriggerFactoryBean c = new CronTriggerFactoryBean();
		c.setCronExpression(tbcq.getCronExpression());// 设置时间表达式
		c.setName(tbcq.getTriggerName());// 设置名称
		c.setJobDetail(jobDetail);// 注入Job
		c.afterPropertiesSet();
		return c.getObject();
	}

	public StdScheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(StdScheduler scheduler) {
		this.scheduler = scheduler;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
   
}
