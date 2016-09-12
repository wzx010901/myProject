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
								<form action="user/${msg }.do" name="userForm" id="userForm" method="post">
									<input type="hidden" name="userId" id="userId" value="${pd.userId }"/>
									<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report" class="table table-striped table-bordered table-hover">
										<c:if test="${fx != 'head'}">
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">角色:</td>
											<td id="juese">
											<select class="chosen-select form-control" name="roleId" id="roleId" style="vertical-align:top;" style="width:98%;" disabled="disabled">
											<c:forEach items="${roleList}" var="role">
												<option value="${role.roleId }" <c:if test="${role.roleId == pd.roleId }">selected</c:if>>${role.roleName }</option>
											</c:forEach>
											</select>
											</td>
										</tr>
										</c:if>
										<c:if test="${fx == 'head'}">
											<input name="roleId" id="roleId" value="${pd.roleId }" type="hidden" />
										</c:if>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">用户名:</td>
											<td><input type="text" name="username" id="loginname" value="${pd.username }" maxlength="32" title="用户名" style="width:98%;" disabled="disabled"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">编号:</td>
											<td><input type="text" name="number" id="number" value="${pd.number }" maxlength="32" title="编号" onblur="hasN('${pd.username }')" style="width:98%;" disabled="disabled"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">姓名:</td>
											<td><input type="text" name="name" id="name"  value="${pd.name }"  maxlength="32" placeholder="这里输入姓名" title="姓名" style="width:98%;" disabled="disabled"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">手机号:</td>
											<td><input type="number" name="phone" id="phone"  value="${pd.phone }"  maxlength="32" title="手机号" style="width:98%;" disabled="disabled"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">邮箱:</td>
											<td><input type="email" name="email" id="email"  value="${pd.email }" maxlength="32" title="邮箱" onblur="hasE('${pd.username }')" style="width:98%;" disabled="disabled"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">备注:</td>
											<td><input type="text" name="remark" id="remark"value="${pd.remark }"  maxlength="64" title="备注" style="width:98%;" disabled="disabled"/></td>
										</tr>
									</table>
									</div>
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
	
	//判断用户名是否存在
	function hasU(){
		var username = $.trim($("#loginname").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/hasU.do',
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
		var email = $.trim($("#email").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/hasE.do',
	    	data: {email:email,username:username,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#email").tips({
							side:3,
				            msg:'邮箱 '+email+' 已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					 $("#email").val('');
				 }
			}
		});
	}
	
	$(function() {
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