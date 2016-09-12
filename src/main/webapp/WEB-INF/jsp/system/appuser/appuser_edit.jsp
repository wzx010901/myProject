<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
</head>
<body class="no-skin">
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
						<form action="happuser/${msg }.do" name="userForm" id="userForm" method="post">
							<input type="hidden" name="userId" id="userId" value="${pd.userId }"/>
							<div id="zhongxin" style="padding-top: 13px;">
							<table id="table_report" class="table table-striped table-bordered table-hover">
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">角色:</td>
									<td id="js">
									<select class="chosen-select form-control" name="roleId" id="roleId" data-placeholder="请选择等级" style="vertical-align:top;"  title="级别" style="width:98%;" >
									<option value=""></option>
									<c:forEach items="${roleList}" var="role">
										<option value="${role.roleId }" <c:if test="${role.roleId == pd.roleId }">selected</c:if>>${role.roleName }</option>
									</c:forEach>
									</select>
									</td>
									<td style="width:79px;text-align: right;padding-top: 13px;">开通日期:</td>
									<td><input class="span10 date-picker" name="startTime" id="startTime" value="${pd.startTime}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="开通日期"  title="开通日期" style="width:98%;" /></td>
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">用户名:</td>
									<td><input type="text" name="username" id="loginname" value="${pd.username }" placeholder="这里输入用户名" title="用户名" style="width:98%;" /></td>
									<td style="width:79px;text-align: right;padding-top: 13px;">到期日期:</td>
									<td><input class="span10 date-picker" name="endTime" id="endTime" value="${pd.endTime}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="到期日期" title="到期日期" style="width:98%;" /></td>
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">编号:</td>
									<td><input type="text" name="number" id="number" value="${pd.number }" maxlength="32" placeholder="这里输入编号" title="编号" onblur="hasN('${pd.username }')" style="width:98%;" /></td>
									<td style="width:79px;text-align: right;padding-top: 13px;">邮箱:</td>
									<td><input type="email" name="email" id="email"  value="${pd.email }" maxlength="32" placeholder="这里输入邮箱" title="邮箱" onblur="hasE('${pd.username }')" style="width:98%;" /></td>
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">密码:</td>
									<td><input type="password" name="password" id="password"  placeholder="输入密码"  title="密码" style="width:98%;" /></td>
									<td style="width:79px;text-align: right;padding-top: 13px;">手机号:</td>
									<td><input type="tel" name="phone" id="phone" value="${pd.phone }" placeholder="这里输入手机号" title="手机号" style="width:98%;" /></td>
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">确认密码:</td>
									<td><input type="password" name="chkpwd" id="chkpwd"  placeholder="确认密码"  title="确认密码" style="width:98%;" /></td>
									<td style="width:79px;text-align: right;padding-top: 13px;">身份证号:</td>
									<td><input type="text" name="sfid" id="sfid" value="${pd.sfid }" placeholder="这里输入身份证号" title="身份证号" style="width:98%;" /></td>
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">姓名:</td>
									<td><input type="text" name="name" id="name"  value="${pd.name }" placeholder="这里输入姓名" title="姓名" style="width:98%;" /></td>
									<td style="width:79px;text-align: right;padding-top: 13px;">开通年限:</td>
									<td><input type="number" name="years" id="years" class="input_txt" value="${pd.years }" placeholder="开通年限(请输入数字)" title="开通年限" style="width:98%;" /></td>
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">备注:</td>
									<td><input type="text" name="remark" id="remark"value="${pd.remark }" placeholder="这里输入备注" title="备注" style="width:98%;" /></td>
									<td style="width:79px;text-align: right;padding-top: 13px;">状态:</td>
									<td>
										<select name="status" title="状态">
										<option value="1" <c:if test="${pd.status == '1' }">selected</c:if> >正常</option>
										<option value="0" <c:if test="${pd.status == '0' }">selected</c:if> >冻结</option>
										</select>
									</td>
								</tr>
								<tr>
									<td class="center" colspan="6">
										<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
										<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
									</td>
								</tr>
							</table>
							</div>
							<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
						</form>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->
	</div>
	<!-- /.main-container -->
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
</body>						
<script type="text/javascript">
	$(top.hangge());
	
	$(document).ready(function(){
		if($("#userId").val()!=""){
			$("#loginname").attr("readonly","readonly");
			$("#loginname").css("color","gray");
		}
	});
	
	function ismail(mail){
		return(new RegExp(/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/).test(mail));
	}

	//保存
	function save(){
		if($("#roleId").val()==""){
			$("#js").tips({
				side:3,
	            msg:'选择角色',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#roleId").focus();
			return false;
		}
		if($("#loginname").val()=="" || $("#loginname").val()=="此用户名已存在!"){
			$("#loginname").tips({
				side:3,
	            msg:'输入用户名',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#loginname").focus();
			$("#loginname").val('');
			$("#loginname").css("background-color","white");
			return false;
		}else{
			$("#loginname").val(jQuery.trim($('#loginname').val()));
		}
		if($("#number").val()==""){
			$("#number").tips({
				side:3,
	            msg:'输入编号',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#number").focus();
			return false;
		}else{
			$("#number").val($.trim($("#number").val()));
		}	
		if($("#email").val()==""){
			
			$("#email").tips({
				side:3,
	            msg:'输入邮箱',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#email").focus();
			return false;
		}else if(!ismail($("#email").val())){
			$("#email").tips({
				side:3,
	            msg:'邮箱格式不正确',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#email").focus();
			return false;
		}
		if($("#userId").val()=="" && $("#password").val()==""){
			$("#password").tips({
				side:3,
	            msg:'输入密码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#password").focus();
			return false;
		}
		if($("#password").val()!=$("#chkpwd").val()){
			$("#chkpwd").tips({
				side:3,
	            msg:'两次密码不相同',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#chkpwd").focus();
			return false;
		}
		if($("#name").val()==""){
			$("#name").tips({
				side:3,
	            msg:'输入姓名',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#name").focus();
			return false;
		}
		if($("#startTime").val()!= "" && $("#endTime").val() != ""){
			var t1 = $("#startTime").val();
			var t2 = $("#endTime").val();
			t1 = Number(t1.replace('-', '').replace('-', ''));
			t2 = Number(t2.replace('-', '').replace('-', ''));
			if(t1-t2>0){
				
				$("#endTime").tips({
					side:3,
		            msg:'到期日期必须大于开通日期',
		            bg:'#AE81FF',
		            time:3
		        });
				
				return false;
			}
		}
		if($("#years").val()==""){
			$("#years").val(0);
		}else if(isNaN(Number($("#years").val()))){
			
			$("#years").tips({
				side:3,
	            msg:'输入数字',
	            bg:'#AE81FF',
	            time:3
	        });
			
			$("#years").focus();
			$("#years").val(0);
			return false;
		}
		if($("#userId").val()==""){
			hasU();
		}else{
			$("#userForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
	}
	
	//判断用户名是否存在
	function hasU(){
		var username = $("#loginname").val();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>happuser/hasU.do',
	    	data: {username:username,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.result){
					$("#userForm").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
				 }else{
					$("#loginname").css("background-color","#D16E6C");
					setTimeout("$('#loginname').val('此用户名已存在!')",500);
				 }
			}
		});
	}
	
	//判断邮箱是否存在
	function hasE(username){
		var email = $("#email").val();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>happuser/hasE.do',
	    	data: {email:email,username:username,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#email").tips({
							side:3,
				            msg:'邮箱'+email+'已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					$('#email').val('');
				 }
			}
		});
	}
	
	//判断编码是否存在
	function hasN(username){
		var number = $("#number").val();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>happuser/hasN.do',
	    	data: {number:number,username:username,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#number").tips({
							side:3,
				            msg:'编号'+number+'已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					 $('#number').val('');
				 }
			}
		});
	}
	
	$(function() {
		//日期框
		$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		//下拉框
		if(!ace.vars['touch']) {
			$('.chosen-select').chosen({allow_single_deselect:true}); 
			$(window)
			.off('resize.chosen')
			.on('resize.chosen', function() {
				$('.chosen-select').each(function() {
					 var $this = $(this);
					 $this.next().css({'width': $this.parent().width()});
				});
			}).trigger('resize.chosen');
			$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
				if(event_name != 'sidebar_collapsed') return;
				$('.chosen-select').each(function() {
					 var $this = $(this);
					 $this.next().css({'width': $this.parent().width()});
				});
			});
			$('#chosen-multiple-style .btn').on('click', function(e){
				var target = $(this).find('input[type=radio]');
				var which = parseInt(target.val());
				if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
				 else $('#form-field-select-4').removeClass('tag-input-style');
			});
		}
	});
</script>
</html>