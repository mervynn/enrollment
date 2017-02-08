var distantTime = 120;
var checkCode = function(a, b, c) {
	var d;
		d = {
			mobile : $("#mobile").val(),
			smscodee : $("#code").val(),
			ifNeedVerifyMobile : c,
			validateType : a
		}
	jQuery.get("../../sms/verify.shtml?",
					d,
					function(e) {
						if (JSON.parse(e).code == 0) {
							b.submit()
						} else {
							$("#codeError").removeClass("paypw-msg");
							$("#codeError").addClass("paypw-err");
							if (e.returnCode == "-4") {
								$("#codeError")
										.html(
												"\u8be5\u624b\u673a\u53f7\u5df2\u88ab\u9a8c\u8bc1\u8fc7")
							} else {
								if (e.returnCode == "error") {
									$("#codeError")
											.html(
													"\u4f7f\u7528\u4e86\u4e0d\u5141\u8bb8\u7684\u8eab\u4efd\u6821\u9a8c\u65b9\u5f0f")
								} else {
									if (e.returnCode == "lock") {
										$("#codeError")
												.html(
														"\u64cd\u4f5c\u6b21\u6570\u8d85\u8fc7\u4e0a\u9650,\u5df2\u9501\u5b9a")
									} else {
										$("#codeError")
												.html(
														"\u77ed\u4fe1\u9a8c\u8bc1\u7801\u6709\u8bef")
									}
								}
							}
						}
					})
};
var getCode = function(a) {
	var b;
		b = {
			mobile : $("#mobile").val(),
			validateType : a
		}
	jQuery
			.get(
					"../../sms/sendcode.shtml?",
					b,
					function(c) {
						if (JSON.parse(c).code == 0) {
							$("#mobile").attr({
								disabled : "disabled"
							});
							$("#notify").show();
							timer()
						} else {
							$("#btnCodesend").removeClass(
									"btn-type-prohibit tbn-type-mglf0");
							$("#btnCodesend").addClass(
									"btn-type tbn-type-mglf0");
							$("#btnCodesend").removeAttr("disabled");
							$("#mobileError").removeClass("paypw-msg");
							$("#mobileError").addClass("paypw-err");
							$("#mobileError").show();
							if (c.returnCode == "-6") {
								$("#mobileError")
										.html(
												"\u60a8\u7684\u5386\u53f2\u8ba2\u5355\u4e2d\u6ca1\u6709\u8be5\u624b\u673a\u53f7")
							} else {
								if (c.returnCode == "-4") {
									$("#mobileError")
											.html(
													"\u8be5\u624b\u673a\u53f7\u5df2\u88ab\u9a8c\u8bc1\u8fc7")
								} else {
									if (c.returnCode == "-2") {
										$("#mobileError")
												.html(
														"\u53d1\u9001\u77ed\u4fe1\u9a8c\u8bc1\u7801\u592a\u8fc7\u9891\u7e41,\u8bf7\u4e00\u5206\u949f\u540e\u91cd\u65b0\u53d1\u9001")
									} else {
										if (c.returnCode == "-1") {
											$("#mobileError")
													.html(
															"\u624b\u673a\u53f7\u683c\u5f0f\u6709\u8bef")
										} else {
											$("#mobileError")
													.html(
															"\u53d1\u9001\u77ed\u4fe1\u9a8c\u8bc1\u7801\u5931\u8d25,\u8bf7\u4e00\u5206\u949f\u540e\u91cd\u65b0\u53d1\u9001")
										}
									}
								}
							}
						}
					})
};
var timer = function() {
	var a = new Date().getTime() / 1000;
	var b = Math.floor(endTime - a);
	$("#second").text(b);
	if (b > 0) {
		setTimeout("timer()", 1000)
	} else {
		$("#btnCodesend").removeClass("btn-type-prohibit tbn-type-mglf0");
		$("#btnCodesend").addClass("btn-type tbn-type-mglf0");
		$("#btnCodesend").removeAttr("disabled");
		$("#mobile").removeAttr("disabled");
		$("#notify").hide()
	}
};
var validatePassword = function() {
	var c = new RegExp("^[\u0000-\u00FF]+$", "g");
	var b = new RegExp("^[0-9]+$", "g");
	var d = new RegExp("^[A-Za-z]+$", "g");
	var e = new RegExp(
			"^[_-`~!@#$^&*()=|{}':;',\\[\\].<>/?~��@#������&*��������|{}��������������'��������]+$",
			"g");
	var a = $("#password").val();
	if (a.length < 6 || a.length > 20) {
		$("#strength_tip").hide();
		$("#pwdError").removeClass("paypw-msg");
		$("#pwdError").addClass("paypw-err");
		$("#pwdError")
				.html(
						"\u5bc6\u7801\u957f\u5ea6\u4e0d\u6b63\u786e\uff0c\u8bf7\u91cd\u65b0\u8bbe\u7f6e");
		return false
	} else {
		if (!c.test(a)) {
			$("#strength_tip").hide();
			$("#pwdError").removeClass("paypw-msg");
			$("#pwdError").addClass("paypw-err");
			$("#pwdError").html("\u5bc6\u7801\u683c\u5f0f\u4e0d\u6b63\u786e");
			return false
		} else {
			if (a.length < 10) {
				if (b.test(a) || d.test(a) || e.test(a)) {
					$("#strength_tip").hide();
					$("#pwdError").removeClass("paypw-msg");
					$("#pwdError").addClass("paypw-err");
					$("#pwdError")
							.html(
									"\u5bc6\u7801\u592a\u5f31\uff0c\u6709\u88ab\u76d7\u98ce\u9669\uff0c\u8bf7\u91cd\u65b0\u8f93\u5165\u7531\u591a\u79cd\u5b57\u7b26\u7ec4\u6210\u7684\u590d\u6742\u5bc6\u7801\u3002");
					return false
				}
			}
		}
	}
	if (a.length <= 10) {
		$("#strength_tip").show();
		$("#strength").addClass("paypw-err");
		$("#strength").html("\u4e2d\u7ea7")
	} else {
		if (b.test(a) || d.test(a) || e.test(a)) {
			$("#strength_tip").show();
			$("#strength").addClass("paypw-err");
			$("#strength").html("\u4e2d\u7ea7")
		} else {
			$("#strength_tip").show();
			$("#strength").addClass("paypw-err");
			$("#strength").html("\u9ad8\u7ea7")
		}
	}
	return true
};
var chooseUserInputUsername = function(c, b, a, e) {
	var d;
		d = {
			userInput : b,
			userInputType : a,
			username : e
		}
	jQuery.get("/findloginpassword/chooseUserInputUsername.json?", d, function(f) {
		if (JSON.parse(f).code == 0) {
			c.submit()
		} else {
			$("#username_error").show();
			if (f.returnCode == "none") {
				$("#username_error").html(
						"\u7528\u6237\u540d\u4e0d\u5b58\u5728")
			} else {
				$("#username_error").html("\u7cfb\u7edf\u5f02\u5e38")
			}
		}
	})
};
var validateForm = function(a, l) {
	var j = 0;
	var e = true;
	var c = false;
	var k = $("form#" + a + " :input,textArea,select");
	var h = l.errorElClass ? l.errorElClass : "error";
	var b = l.errorShowClass ? l.errorShowClass : "errorLabel";
	var g = l.targetEnumId;
	var i = l.tipClass ? l.tipClass : "tipClass";
	var d = l.showTip;
	var f = l.loop;
	$.each(k, function() {
		var o = $(this);
		if (g) {
			if (g != o.attr("id")) {
				c = true
			} else {
				c = false
			}
		}
		if (!c) {
			var p = o.attr("reg");
			var v = o.attr("matchFor");
			var w = o.attr("matchForTxt");
			var t = o.attr("regValidityTxt");
			var r = o.attr("notBlank");
			var n = o.attr("valueMissingTxt");
			var y = o.attr("requiredCheck");
			var s = o.attr("requiredCheckTxt");
			var m = o.attr("errorLabel");
			var u = $("#" + m);
			var q = o.val();
			var A = o.attr("tip") ? o.attr("tip") : o.attr("regValidityTxt");
			o.focus(function() {
				if (o.attr("name") == $("#password").attr("name")) {
					$("#strength_tip").hide()
				}
				u.html("");
				u.removeClass(b);
				o.removeClass(h);
				if (d) {
					u.addClass(i)
				}
				u.html(A)
			});
			if (y) {
				if (o.is("input:radio,input:checkbox")) {
					if (!o.is(":checked")) {
						commonDo(o, u, s, b, h, d, i);
						if (!f) {
							e = false;
							return
						}
						j++
					}
				}
			}
			if (r) {
				if (q == undefined || q == null || q == "null"
						|| q.trim() == "" || q.trim().length == 0) {
					commonDo(o, u, n, b, h, d, i);
					if (!f) {
						e = false;
						return
					}
					j++
				}
			}
			if (p) {
				var z = new RegExp(p);
				if (!z.test(q)) {
					commonDo(o, u, t, b, h, d, i);
					if (!f) {
						e = false;
						return
					}
					j++
				}
			}
			if (v) {
				var x = $("#" + v);
				if (x && (x.val() != q)) {
					commonDo(o, u, w, b, h, d, i);
					if (!f) {
						e = false;
						return
					}
					j++
				}
			}
		}
	});
	if (f) {
		return !j
	} else {
		return e
	}
};
function commonDo(d, a, g, f, c, e, b) {
	if (a) {
		a.html(g ? g : "");
		a.addClass(f);
		if (e) {
			a.removeClass(b)
		}
	}
	if (c) {
		d.addClass(c)
	}
}
var formTipRender = function(d, a) {
	var b = $("form#" + d + " :input,textArea,select");
	var c = a ? a : "tipClass";
	$.each(b, function() {
		var e = $(this);
		var f = e.attr("tipShowLabel") ? e.attr("tipShowLabel") : e
				.attr("errorLabel");
		var g = e.attr("tip") ? e.attr("tip") : e.attr("regValidityTxt");
		e.focus(function() {
			if (f) {
				var h = $("#" + f);
				if (h) {
					h.removeClass();
					h.addClass(c)
				}
				h.html(g)
			}
		});
		e.blur(function() {
			var h = $("#" + f);
			if (f && h.hasClass(c)) {
				h.html("")
			}
		})
	})
};