// 项目根目录
var projectRootPath = getRootPath();

// 默认初始化(PJAX && Page Load EVENT)
$(document).ready(function(){
	// 上传组件初始化
	demo_h5_upload_ops.init();
	/**
	 * 禁用[backspace]键回退历史页面
	 */
	document.getElementsByTagName("body")[0].onkeydown =function(){
		//获取事件对象
		var elem = event.relatedTarget || event.srcElement || event.target ||event.currentTarget; 
		if(event.keyCode==8){//判断按键为backSpace键
		
			//获取按键按下时光标做指向的element
			var elem = event.srcElement || event.currentTarget; 
			
			//判断是否需要阻止按下键盘的事件默认传递
			var name = elem.nodeName;
			
			if(name!='INPUT' && name!='TEXTAREA'){
				return _stopIt(event);
			}
			var type_e = elem.type.toUpperCase();
			if(name=='INPUT' && (type_e!='TEXT' && type_e!='TEXTAREA' && type_e!='PASSWORD' && type_e!='FILE' && type_e!='SEARCH')){
					return _stopIt(event);
			}
			if(name=='INPUT' && (elem.readOnly==true || elem.disabled ==true)){
					return _stopIt(event);
			}
		}
	}
		
	/**
	 * 全局的AJAX访问，处理AJAX清求时会话超时和越权请求
	 */
	$.ajaxSetup({
		contentType : "application/x-www-form-urlencoded;charset=utf-8", 
		complete : function(XMLHttpRequest, textStatus) { 
			var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus"); // 通过XMLHttpRequest取得响应头，sessionstatus， 
			if (sessionstatus == "timeout") {
				// 如果超时就处理 ，指定要跳转的页面 
				window.location.replace("/login.shtml"); 
				// 越权访问跳转
			} else if(sessionstatus == "unauthorized"){
				window.location.replace("/403.jsp"); 
			}
		}
	});
	
	// 按钮初始化
	btnInit();
	validInit();
	var pageFlag = $("#pageFlag").val();
	// 支付报表
	if(pageFlag == "report"){
		var param = JSON.stringify({
			"username":$("#username").val(),
			"password":$("#password").val(),
			"flag":$("#flag").val()
		});
		$("#hidExtra").val(param);
		initTableSorter("", param);
	// 菜单CRUD
	}else if(pageFlag == "menu"){
		initMenu();
	}else if(pageFlag == "orderDetail"){
		initTable("", $("#hidExtra").val());
	}else{
		initTableSorter();
	}
	
	// SESSION中设定PJAX请求标致
	$("a[data-pjax]").click(function(){
		// XMLHttpRequest
		var xhr = new XMLHttpRequest();
	    xhr.open("post", projectRootPath+"/base/pjax.shtml", false);
	    xhr.send(null);
	});
});

/**
 * 通知 Web 浏览器不要执行与事件关联的默认动作
 * @param e
 * @returns {Boolean}
 */
function _stopIt(e){
	if(e.returnValue){
		e.returnValue = false ;
	}
	if(e.preventDefault ){
		e.preventDefault();
	}				

	return false;
}

/**
 * reLoadJs
 * @param src
 */
function reLoadJs(src){
    var script = document.createElement( 'script' );
    script.className = 're-execute';
    script.type = 'text/javascript';
    script.src = projectRootPath + src;
    $('script[src="' + projectRootPath + src + '"]').remove();
    $('body').append(script);
}

/**
 * reMoveJs
 * @param src
 */
function reMoveJs(src){
	$('script[src="' + projectRootPath + src + '"]').remove();
}

/**
 * 空值格式化
 * @param obj
 * @returns
 */
function emptyFormat(obj){
	if(obj==null){
		return "";
	}
	return obj;
}

/**
 * 指定元素内的所有元素重置
 * 
 * @param obj
 */
function Reset(obj){
	$(obj).validate().resetForm();
	$(obj).find("select").removeClass("text-danger");
	$(obj).find("textarea").removeClass("text-danger");
	$(obj).find("input").removeClass("text-danger");
	$(obj).find("select").val("");
	$(obj).find("textarea").val("");
	$(obj).find("input").val("");
	$(obj).find("img").attr("src", "");
	$("div[id^='divP']").hide(); // 新闻新增模态窗新增时候要清空图片链接div
}

/**
 * 刷新效果
 */
function pageLoading(){
	if($("#wait").length < 1){
		$("#panel-tablesorter").append(
			"<div id='wait' class='modal fade' tabindex='-1' >" +
			"<div class='modal-dialog' style='margin-top:25%;'>" +
			"<div class='panel-loader'><div class='loader-container'><div class='loader-spinner'></div></div></div>" +
		"</div></div>");
	}
	$("#wait").modal({backdrop: "static", keyboard: false});
	$("#wait").modal("show");
}

/**
 * 若干秒后去除刷新效果(暂定1秒)
 */
function removePageLoading(){
	$("#wait").modal("hide");
}

//显示上传进度条
function showProcessBar(){
	if($("#editModal input[type='file']").val() != ""){
		$("#uploadProcessModal").modal({backdrop: "static", keyboard: false});
		$("#uploadProcessModal").modal("show");
		processIncrease(0);
	}
}

// 进度条增长
function processIncrease(obj){
	obj++;
	if(obj < 75){
		setTimeout(function(){processIncrease(obj);},100);
	}else if(obj >= 75 && obj < 85){
		setTimeout(function(){processIncrease(obj);},500);
	}else if(obj >= 85 && obj < 99){
		setTimeout(function(){processIncrease(obj);},100);
	}
	$("#processValue").html(obj+"<sup>%</sup>");
	$(".progress-bar").attr("style","width:"+obj + "%");
}

/**
 * POST方式异步提交表单
 * @param url
 * @param param
 * @param successFuc
 */
function AjaxPost(url,param,successFuc){
	pageLoading();
	$.ajax({
			type:"POST",
			contentType : "application/x-www-form-urlencoded;charset=utf-8",
			url:url,
			data:param, 
			dataType:"json", 
			success:function(msg){
				removePageLoading();
				successFuc(msg);
			},
			error:function (xhr,statusText,error){ 
				removePageLoading();
				bootbox.alert("Ajax异常:"+statusText);
			}
	});
}

/**
 * GET方式异步提交表单
 * @param url
 * @param param
 * @param successFuc
 */
function AjaxGet(url,param,successFuc){
	pageLoading();
	$.ajax({
		type:"GET",
		contentType : "application/x-www-form-urlencoded;charset=utf-8",
		url:url,
		data:param,
		dataType:"json",
		success:function(msg){
			removePageLoading();
			successFuc(msg);
		},
		error:function (xhr,statusText,error){
			removePageLoading();
			bootbox.alert("Ajax异常:"+statusText);
		}
	});
}

// Html5 不刷新提交表单(支持上传文件)
function UpladFileForm(url, formId, callBackFunc) {
	// 上传进度条方法存在则执行之(有文件标签的页面默认为上传界面)
	if($("#uploadFile").length > 0){
		if($("#uploadProcessModal").length < 1){
			$("#panel-tablesorter").append("<div id='uploadProcessModal' class='modal fade' tabindex='-1'>" +
				"<div class='modal-dialog'>" +
				"<div class='modal-content'>" +
				"<div class='modal-body bg-inverse' style='border-radius: 4px;'>" +
				"<p class='text-lg'><span class='pull-right' id='processValue'>0<sup>%</sup></span><i class='fa fa-cloud-upload'></i> 上传中...</p>" +
				"<div class='progress progress-lg'>" +
				"<div class='progress-bar progress-bar-inverse' role='progressbar' aria-valuenow='0'" +
				"aria-valuemin='0' aria-valuemax='100' style='width: 0%'>" +
				"<span class='sr-only'>0% Complete (inverse)</span>" +
				"</div>" +
				"</div>" +
				"</div><!--/panel-body-->" +
				"</div>" +
				"</div>" +
			"</div>");
		}
		showProcessBar();
	}
	pageLoading();
	var formobj = document.getElementById(formId);
    // Html5 FormData 对象(低版本浏览器不支持)
    var form = new FormData(formobj);
    // XMLHttpRequest 对象
    var xhr = new XMLHttpRequest();
    xhr.open("post", url, true);
    xhr.onload = callBackFunc;
    xhr.send(form);
}

/******↓****** CRUD界面提取公共方法开始 *********↓*********/
/**
 * 行选择颜色(#13A89E) 该全局变量用于各页面行选择判断
 */
var colorRgb = "rgb(19, 168, 158)";
/**
 * 页面地址
 */
var globleUrl;
/**
 * 页面名称
 */
var title;
/**
 * 初始化数据列表
 * @param url 请求url
 * @param ref 刷新按钮触发标识
 * @param extra 额外参数
 */
function initTableSorter(ref,extra){
	$("table.tablesorter").tablesorter().tablesorterPager({ // 默认按第一列升序
        container: $("tfoot"),
        ajaxUrl: globleUrl+"data.shtml?page={page+1}&size={size}&{sortList:col}&{filterList:fcol}&extra="+extra + "&random="+Math.random(),
        ajaxObject: {
        	dataType: 'json'
        },
        ajaxProcessing: function(data){
			if(ref==1){
				removePageLoading();
			}
        	return data;
        }
	});
}

/**
 * 刷新数据列表
 */
function refresh(extra){
	pageLoading();
	initTableSorter(1,extra);
}

// 全选
function chooseAll(){
	var selNum = 0; // 被选择数量
	if($("#btnChooseAll").text().trim() == "全选"){
		$("table.tablesorter tbody tr").each(function(){
			$(this).find("td").css("background-color", "#13A89E");
			if($(this).find("td").length > 0){
				selNum++;
			}
		});
		$("#btnChooseAll").html("全不选 <i class='fa fa-check'></i>");
	}else{
		$("table.tablesorter tbody tr td").css("background-color", "");
		$("#btnChooseAll").html("全选 <i class='fa fa-check'></i>");
	}
	if(selNum == 1){
		$("#btnEdit").attr("disabled", false);
		$("#btnAuth").attr("disabled", false);
		$("button[name='btnSingleSel']").attr("disabled", false);
		$("#btnDel").attr("disabled", false);
		$("#btnOnSell").attr("disabled", false);
		$("#btnStopSell").attr("disabled", false);
		$("#btnImport").attr("disabled", false);
		$("#btnRecommend").attr("disabled", false);
	}else if(selNum > 1){
		$("#btnEdit").attr("disabled", true);
		$("#btnAuth").attr("disabled", true);
		$("button[name='btnSingleSel']").attr("disabled", true);
		$("#btnDel").attr("disabled", false);
		$("#btnOnSell").attr("disabled", false);
		$("#btnStopSell").attr("disabled", false);
		$("#btnImport").attr("disabled", false);
		$("#btnRecommend").attr("disabled", false);
	}else{
		$("#btnDel").attr("disabled", true);
		$("#btnAuth").attr("disabled", true);
		$("#btnEdit").attr("disabled", true);
		$("button[name='btnSingleSel']").attr("disabled", true);
		$("#btnOnSell").attr("disabled", true);
		$("#btnStopSell").attr("disabled", true);
		$("#btnImport").attr("disabled", true);
		$("#btnRecommend").attr("disabled", true);
	}
}

// 行选择
function doSelection(obj) {
	var selRow = $(obj).parent().find("td");
	var allRows = $(obj).parent().parent().find("tr");
	// 合计行排除
	if("合计" == $(obj).parent().find("td:eq(0)").text()){
		return false;
	}
	if ($(obj).css("background-color") != colorRgb) {
		selRow.css("background-color", "#13A89E");
	} else {
		selRow.css("background-color", "");
	}
	var selNum = 0; // 被选择数量
	$(allRows).each(function(){
		if($(this).find("td:eq(0)").css("background-color") == colorRgb){
			selNum++;
		}
	});
	if(selNum == 1){
		$("#btnEdit").attr("disabled", false);
		$("#btnAuth").attr("disabled", false);
		$("button[name='btnSingleSel']").attr("disabled", false);
		$("#btnDel").attr("disabled", false);
		$("#btnOnSell").attr("disabled", false);
		$("#btnStopSell").attr("disabled", false);
		$("#btnImport").attr("disabled", false);
		$("#btnRecommend").attr("disabled", false);
	}else if(selNum > 1){
		$("#btnEdit").attr("disabled", true);
		$("#btnAuth").attr("disabled", true);
		$("button[name='btnSingleSel']").attr("disabled", true);
	}else{
		$("#btnDel").attr("disabled", true);
		$("#btnAuth").attr("disabled", true);
		$("#btnEdit").attr("disabled", true);
		$("button[name='btnSingleSel']").attr("disabled", true);
		$("#btnOnSell").attr("disabled", true);
		$("#btnStopSell").attr("disabled", true);
		$("#btnImport").attr("disabled", true);
		$("#btnRecommend").attr("disabled", true);
	}
}

// 按钮初始化
function btnInit(){
	$("#btnDel").attr("disabled", true);
	$("#btnEdit").attr("disabled", true);
	$("#btnAuth").attr("disabled", true);
	$("button[name='btnSingleSel']").attr("disabled", true);
	$("#btnOnSell").attr("disabled", true);
	$("#btnStopSell").attr("disabled", true);
	$("#btnImport").attr("disabled", true);
	$("#btnChooseAll").html("全选 <i class='fa fa-check'></i>");
	$("#btnRecommend").attr("disabled", true);
}

// 校验初始化
function validInit(){
	$(".modal").validate();
}

// 进入添加模式窗
function accessAddModule(){
	Reset($("#editModal"));
	// 设定窗口标题
	$("#editModal h4 strong").text("添加" + title);
	// 设定保存事件
	$("#editModal button.btn-success").off("click");
	$("#editModal button.btn-success").on("click",function(){doExcute(1)});
	// 预处理方法存在则执行预处理
	if(typeof(preAdd)!="undefined"){
		preAdd();
	}
}

//进入编辑模式窗
function accessEditModule(obj){
	Reset($("#editModal"));
	// 设定窗口标题
	$("#editModal h4 strong").text("修改" + title);
	// 设定保存事件
	$("#editModal button.btn-success").off("click");
	$("#editModal button.btn-success").on("click",doExcute);
	var url;
	// 双主键编辑(暂时为系统常量管理专用)
	if(obj == "1"){
		var code = "";
		var sort = "";
		$("table.tablesorter tbody tr").each(function(){
			if($(this).find("td:eq(0)").css("background-color") == colorRgb){
				code = $(this).children().eq(0).text();
				sort = $(this).children().eq(1).text();
				return false;//退出循环
			}
		});
		url = globleUrl+"data/code/"+ code + "/sort/" + sort + ".shtml?random="+Math.random();
	// 单主键编辑
	}else{
		var id = "";
		$("table.tablesorter tbody tr").each(function(){
			if($(this).find("td:eq(0)").css("background-color") == colorRgb){
				id = $(this).children().eq(0).text();
				return false;//退出循环
			}
		});
		url = globleUrl+"data/"+ id +".shtml?random="+Math.random();
	}
	AjaxGet(url,{},editInitCallBack);
}

// 执行删除
function doDel(){
	bootbox.confirm("是否删除选中记录？",function(sure){
		if(sure){
			var idArr = delDate();
			AjaxPost(globleUrl+"delete.shtml",{"id":JSON.stringify(idArr)},function(msg){
				initTableSorter(globleUrl+"data.shtml");
				btnInit();
				// 修正tablesorter插件删除尾页全部数据时不能重置的问题
				bootbox.alert(msg,function(){
					var allRows = $("table.tablesorter tbody").find("tr");
					$(allRows).each(function(){
						if($(this).find("td:eq(0)").css("background-color") == colorRgb){
							$("table.tablesorter").trigger("update");
							return false;
						}
					});
				});
			}); 
		}
	});
}

// 执行数据变更(新增或修改)
function doExcute(flag){
	var validFlag = $("#editModal").valid(); // jquery校验(如required等前端校验)结果
	// server校验不存在时
	if((typeof(serverValid)=="undefined" && validFlag)){
		validSuccess(flag);
	// server校验存在时执行之
	}else if(typeof(serverValid)!="undefined"){
		serverValid(validFlag,validSuccess,flag);
	}
}

// 校验通过通过回调函数
function validSuccess(flag){
	bootbox.confirm(flag==1?"确认添加？":"确认保存？",function(sure){
		if(sure){
			// 提交前数据准备方法有则执行
			if(typeof(preSubmit)!="undefined"){
				preSubmit();
			}
			UpladFileForm(globleUrl+(flag==1?"save.shtml":"modify.shtml"),"editModal",function(e){
				initTableSorter(globleUrl + "data.shtml");
				btnInit();
				$("#editModal").modal("hide");
				removePageLoading();
				if($("#uploadProcessModal").length > 0){
					$("#uploadProcessModal").modal("hide");
				}
				if (this.status == 200) {
					// 字符串中解析出json并提示
					bootbox.alert(this.responseText);
			    }
			});
		}
	});
}

/******↑****** CRUD界面提取公共方法结束 *********↑*********/

// 取得项目跟路径(适配NGINX和各种域跳转的环境)
function getRootPath(){
  // 获取当前网址，如： http://localhost:8088/test/test.jsp
    var curPath=window.document.location.href;
    // 获取主机地址之后的目录，如： test/test.jsp
    var pathName=window.document.location.pathname;
    var pos=curPath.indexOf(pathName);
    // 获取主机地址，如： http://localhost:8088
    var localhostPaht=curPath.substring(0,pos);
    // 80端口请求时(服务器要默认端口项目)
    if(!window.location.port){
    	// 1.含有项目路径
    	if(curPath.indexOf(localhostPaht+"") != -1){
    		return localhostPaht+"";
    	// 2.不含项目路径
    	}else{
    		return localhostPaht;
    	}
    // 非80端口请求时
    }else{
    	// 1.含有项目路径
    	if(curPath.indexOf(localhostPaht+"") != -1){
    		return (localhostPaht + "");
    	// 2.不含项目路径
    	}else{
    		return localhostPaht;
    	}
    }
}