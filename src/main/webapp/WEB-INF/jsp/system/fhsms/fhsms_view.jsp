<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
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
					
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="padding-top: 13px;">
								<c:if test="${pd.type != '2' }">
								发信人：${pd.toUsername}&nbsp;&nbsp;
								收信人：${pd.fromUsername}&nbsp;&nbsp;
								</c:if>
								<c:if test="${pd.type == '2' }">
								发信人：${pd.fromUsername}&nbsp;&nbsp;
								收信人：${pd.toUsername}&nbsp;&nbsp;
								</c:if>
								发信时间：${pd.sendTime}
							</tr>
							<tr>
								<td>${pd.content}</td>
							</tr>
						</table>
						</div>
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


	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			if($("#content").val()==""){
				$("#content").tips({
					side:3,
		            msg:'请输入内容',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#content").focus();
			return false;
			}
			if($("#toUsername").val()==""){
				$("#toUsername").tips({
					side:3,
		            msg:'请输入收信人',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#toUsername").focus();
			return false;
			}
			if($("#fromUsername").val()==""){
				$("#fromUsername").tips({
					side:3,
		            msg:'请输入发信人',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#fromUsername").focus();
			return false;
			}
			if($("#sendTime").val()==""){
				$("#sendTime").tips({
					side:3,
		            msg:'请输入发信时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#sendTime").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>