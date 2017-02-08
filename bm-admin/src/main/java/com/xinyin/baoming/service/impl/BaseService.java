package com.xinyin.baoming.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public class BaseService implements Serializable {

    private static final long serialVersionUID = 1293567786956029903L;

    @Autowired
    protected SqlSessionFactoryBean sqlSessionFactory;

    /**
     * 查询分页数据
     * 
     * @param mapperClass
     * @param sqlId
     * @param sqlParameter
     * @param pageIndex
     * @param pageSize
     * @param orderSegment
     * @return
     * @throws Exception
     */
    protected List<?> getPageList(Class<?> mapperClass, String sqlId,
            Object sqlParameter, int pageIndex, int pageSize, String orderSegment) throws Exception {
        SqlSession session = null;
        try {
            SqlSessionFactory sessionFactory = sqlSessionFactory.getObject();
            session = SqlSessionUtils.getSqlSession(sessionFactory);
            if (pageIndex <= 0) {
                pageIndex = 1;
            }
            if (pageSize <= 0) {
                pageSize = 10;
            }
            PageBounds pageBounds = new PageBounds(pageIndex, pageSize,Order.formString(orderSegment));
            List<Object> list = session.selectList(mapperClass.getName() + "." + sqlId,
                    sqlParameter, pageBounds);
            return list;
        } finally {
            session.close();
        }

    }

}