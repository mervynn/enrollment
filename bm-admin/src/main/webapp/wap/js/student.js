// 项目根目录
var projectRootPath = getRootPath();
$(function(){
	// 下拉框初始化
	// 初始化当前选择内容(新增时、默认第一个)
	$(".new-select").each(function(){
		$(this).parent().find("div").html($(this).find("option:selected").text());
	});
	// 初始化 选择事件
	$(".new-select").change(function(){
		$(this).parent().find("div").html($(this).find("option:selected").text());
	});
	
	// 根据证件类型判断是否需要输入出生日期
	$("#cardType").change(function(){
		var selectedVal = $(this).find("option:selected").val();
		if(selectedVal == 0){
			$("#birthDateDiv").hide();
		}else{
			$("#birthDateDiv").show();
		}
	});
	
	// 保存资料按钮初校验始化
	$("#address_submit").on("click",checkAndSubmit);
	// 校验和提交
	function checkAndSubmit(){
		// 防止重复提交
		$("#address_submit").unbind("click");
		var resultVal = true;
		$("input[type='text']").each(function(){
			var val = $(this).val();
			if (val == null || val.trim() == "") {
				resultVal = false;
				$(this).parent().parent().find(".new-txt-err").text($(this).parent().parent().find(".new-txt2").text().replace("*","")  +"不能为空");
			}else{
				$(this).parent().parent().find(".new-txt-err").text("");
			}
		});
		// 图片校验
		if($(".img_wrap").attr("src") == ""){
			resultVal = false;
			$(".img_wrap").parent().find(".new-txt-err").text("您必须要设置一个头像哦");
		}else{
			$(".img_wrap").parent().find(".new-txt-err").text("");
		}
		
		// 出生日期校验
		if($("#cardType").val() == 1){
			var birthDate = $("#birthDate").val();
			if (birthDate == null || birthDate.trim() == "") {
				$("#birthDate").parent().parent().find(".new-txt-err").text("出生日期不能为空");
				resultVal = false;
			} else {
				$("#birthDate").parent().parent().find(".new-txt-err").text("");
			}
		}
		
		// 证件号码校验
		var idcardNo = $("#idcardNo").val();
		if (idcardNo == null || idcardNo.trim() == "") {
			$("#idcardNo").parent().parent().find(".new-txt-err").text("证件号码不能为空");
			resultVal = false;
		} else {
			$.post(projectRootPath+"/wap/user/student/validbeforesave.shtml",$("form").serialize(),function(msg){
				if(msg != ""){
					$("#idcardNo").parent().parent().find(".new-txt-err").text(msg);
					resultVal = false;
				}else{
					// 身份证号码格式
					var idcardObj = $("input[name='idcardNo']");
					if($(idcardObj).val() != ''){
						if($("#cardType").val() == 0){
							if (!isIdCardNo($(idcardObj).val())) {
								$(idcardObj).parent().parent().find(".new-txt-err").text("请输入合法的身份证号");
								resultVal = false;
							} else {
								$(idcardObj).parent().parent().find(".new-txt-err").text("");
							}
						}else{
							$(idcardObj).parent().parent().find(".new-txt-err").text("");
						}
					}
				}
				if (resultVal) {
					$("form").submit();
				}else{
					$("#address_submit").on("click",checkAndSubmit);
				}
			});
		}
		// 校验失败时，恢复按钮功能
		if(!resultVal){
			$("#address_submit").on("click",checkAndSubmit);
		}
	}
	
	// 失去焦点校验初始化
	$("input[type='text']").blur(function(){
		var val = $(this).val();
		if (val == null || val.trim() == "") {
			$(this).parent().parent().find(".new-txt-err").text($(this).parent().parent().find(".new-txt2").text().replace("*","")  +"不能为空");
			return false;
		} else {
			$(this).parent().parent().find(".new-txt-err").text("");
			return true;
		}
	});
	
	// 身份证号码校验
	$("input[name='idcardNo']").blur(function(){
		var thisVal = $(this).val();
		if(thisVal != ''){
			if($("#cardType").val() == 0){
				if (!isIdCardNo(thisVal)) {
					$(this).parent().parent().find(".new-txt-err").text("请输入合法的身份证号");
					return false;
				} else {
					$(this).parent().parent().find(".new-txt-err").text("");
					// 根据身份证号码，计算出生日期
					$("#birthDate").val(thisVal.substring(6, 10) + "-" + thisVal.substring(10, 12) + "-" + thisVal.substring(12, 14));
					return true;
				}
			}else{
				$(this).parent().parent().find(".new-txt-err").text("");
			}
		}else{
			$(this).parent().parent().find(".new-txt-err").text("证件号码不能为空");
		}
	});
	
	// 出生日期校验
	$("input[name='birthDate']").blur(function(){
		var thisVal = $(this).val();
		if (thisVal == null || thisVal.trim() == "") {
			$(this).parent().parent().find(".new-txt-err").text("出生日期不能为空");
			return false;
		} else {
			$(this).parent().parent().find(".new-txt-err").text("");
			return true;
		}
	});
})

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
