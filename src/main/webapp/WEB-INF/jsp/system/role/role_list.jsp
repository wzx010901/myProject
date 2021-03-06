﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
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
							<table style="margin-top: 8px;">
								<tr height="35">
									<c:if test="${jurisdiction.add == 1 }">
									<td style="width:69px;"><a href="javascript:addRole(0);" class="btn btn-sm btn-success">新增组</a></td>
									</c:if>
										<c:choose>
										<c:when test="${not empty roleList}">
										<c:forEach items="${roleList}" var="role" varStatus="vs">
											<td style="width:100px;" class="center" <c:choose><c:when test="${pd.roleId == role.roleId}">bgcolor="#FFC926" onMouseOut="javascript:this.bgColor='#FFC926';"</c:when><c:otherwise>bgcolor="#E5E5E5" onMouseOut="javascript:this.bgColor='#E5E5E5';"</c:otherwise></c:choose>  onMouseMove="javascript:this.bgColor='#FFC926';" >
												<a href="role.do?roleId=${role.roleId }" style="text-decoration:none; display:block;"><i class="menu-icon fa fa-users"></i><font color="#666666">${role.roleName }</font></a>
											</td>
											<td style="width:5px;"></td>
										</c:forEach>
										</c:when>
										<c:otherwise>
											<tr>
											<td colspan="100">没有相关数据</td>
											</tr>
										</c:otherwise>
										</c:choose>
									<td></td>
								</tr>
							</table>
							
							<table>
								<tr height="7px;"><td colspan="100"></td></tr>
								<tr>
								<td><font color="#808080">本组：</font></td>
								<td>
								<c:if test="${jurisdiction.edit == 1 }">
								<a class="btn btn-mini btn-info" onclick="editRole('${pd.roleId }');">修改组名称<i class="icon-arrow-right  icon-on-right"></i></a>
								</c:if>
									<c:choose>
										<c:when test="${pd.roleId == '99'}">
										</c:when>
										<c:otherwise>
										<c:if test="${jurisdiction.edit == 1 }">
										<a class="btn btn-mini btn-purple" onclick="editRights('${pd.roleId }');"><i class="icon-pencil"></i>组菜单权限</a>
										</c:if>
										</c:otherwise>
									</c:choose>
									<c:choose> 
										<c:when test="${pd.roleId == '1' or pd.roleId == '2'}">
										</c:when>
										<c:otherwise>
										 <c:if test="${jurisdiction.del == 1 }">
										 <a class='btn btn-mini btn-danger' title="删除" onclick="delRole('${pd.roleId }','z','${pd.roleName }');"><i class='ace-icon fa fa-trash-o bigger-130'></i></a>
										 </c:if>
										</c:otherwise>
									</c:choose>
								</td>
								</tr>
							</table>
							
							<table id="dynamic-table" class="table table-striped table-bordered table-hover" style="margin-top:7px;">
								<thead>
								<tr>
									<th class="center" style="width: 50px;">序号</th>
									<th class='center'>角色</th>
									<c:if test="${jurisdiction.edit == 1 }">
									<th class="center">增</th>
									<th class="center">删</th>
									<th class="center">改</th>
									<th class="center">查</th>
									</c:if>
									<th style="width:155px;"  class="center">操作</th>
								</tr>
								</thead>
								<c:choose>
									<c:when test="${not empty roleListZ}">
										<c:if test="${jurisdiction.find == 1 }">
										<c:forEach items="${roleListZ}" var="var" varStatus="vs">
										
										<tr>
										<td class='center' style="width:30px;">${vs.index+1}</td>
										<td id="roleNameTd${var.roleId }">${var.roleName }</td>
										<c:if test="${jurisdiction.edit == 1 }">
										<td style="width:30px;"><a onclick="roleButton('${var.roleId }','addJurisdiction');" class="btn btn-warning btn-mini" title="分配新增权限"><i class="ace-icon fa fa-wrench bigger-110 icon-only"></i></a></td>
										<td style="width:30px;"><a onclick="roleButton('${var.roleId }','delJurisdiction');" class="btn btn-warning btn-mini" title="分配删除权限"><i class="ace-icon fa fa-wrench bigger-110 icon-only"></i></a></td>
										<td style="width:30px;"><a onclick="roleButton('${var.roleId }','editJurisdiction');" class="btn btn-warning btn-mini" title="分配修改权限"><i class="ace-icon fa fa-wrench bigger-110 icon-only"></i></a></td>
										<td style="width:30px;"><a onclick="roleButton('${var.roleId }','chaJurisdiction');" class="btn btn-warning btn-mini" title="分配查看权限"><i class="ace-icon fa fa-wrench bigger-110 icon-only"></i></a></td>
										</c:if>
										<td style="width:155px;">
										<c:if test="${jurisdiction.edit != 1 && jurisdiction.del != 1 }">
										<div style="width:100%;" class="center">
										<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
										</div>
										</c:if>
										<c:if test="${jurisdiction.edit == 1 }">
										<a class="btn btn-mini btn-purple" onclick="editRights('${var.roleId }');"><i class="icon-pencil"></i>菜单权限</a>
										<a class='btn btn-mini btn-info' title="编辑" onclick="editRole('${var.roleId }');"><i class='ace-icon fa fa-pencil-square-o bigger-130'></i></a>
										</c:if>
										<c:choose> 
											<c:when test="${var.roleId == '2' or var.roleId == '1'}">
											</c:when>
											<c:otherwise>
											 <c:if test="${jurisdiction.del == 1 }">
											 <a class='btn btn-mini btn-danger' title="删除" onclick="delRole('${var.roleId }','c','${var.roleName }');"><i class='ace-icon fa fa-trash-o bigger-130'></i></a>
											 </c:if>
											</c:otherwise>
										</c:choose>
										</td>
										</tr>
										</c:forEach>
										</c:if>
										<c:if test="${jurisdiction.find == 0 }">
											<tr>
												<td colspan="100" class="center">您无权查看</td>
											</tr>
										</c:if>
									</c:when>
									<c:otherwise>
										<tr>
										<td colspan="100" class="center" >没有相关数据</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</table>
							<div>
							<c:if test="${jurisdiction.add == 1 }">
								&nbsp;&nbsp;<a class="btn btn-sm btn-success" onclick="addRole('${pd.roleId }');">新增角色</a>
							</c:if>
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


		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<script type="text/javascript">
		$(top.hangge());
		
		//新增组
		function addRole(pid){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>role/toAdd.do?parentId='+pid;
			 diag.Width = 222;
			 diag.Height = 100;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					top.jzts();
					setTimeout("self.location.reload()",100);
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//修改
		function editRole(roleId){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>role/toEdit.do?roleId='+roleId;
			 diag.Width = 222;
			 diag.Height = 100;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					top.jzts();
					setTimeout("self.location.reload()",100);
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function delRole(roleId,msg,roleName){
			bootbox.confirm("确定要删除["+roleName+"]吗?", function(result) {
				if(result) {
					var url = "<%=basePath%>role/delete.do?roleId="+roleId+"&guid="+new Date().getTime();
					top.jzts();
					$.get(url,function(data){
						if("success" == data.result){
							if(msg == 'c'){
								top.jzts();
								document.location.reload();
							}else{
								top.jzts();
								window.location.href="role.do";
							}
							
						}else if("false" == data.result){
							top.hangge();
							bootbox.dialog({
								message: "<span class='bigger-110'>删除失败，请先删除下级角色!</span>",
								buttons: 			
								{
									"button" :
									{
										"label" : "确定",
										"className" : "btn-sm btn-success"
									}
								}
							});
						}else if("false2" == data.result){
							top.hangge();
							bootbox.dialog({
								message: "<span class='bigger-110'>删除失败，此角色已被使用!</span>",
								buttons: 			
								{
									"button" :
									{
										"label" : "确定",
										"className" : "btn-sm btn-success"
									}
								}
							});
						}
					});
				}
			});
		}
		
		//菜单权限
		function editRights(roleId){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag = true;
			 diag.Title = "菜单权限";
			 diag.URL = '<%=basePath%>role/menuJurisdiction.do?roleId='+roleId;
			 diag.Width = 320;
			 diag.Height = 450;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		
		//按钮权限(增删改查)
		function roleButton(roleId,msg){
			top.jzts();
			if(msg == 'addJurisdiction'){
				var Title = "授权新增权限(此角色下打勾的菜单拥有新增权限)";
			}else if(msg == 'delJurisdiction'){
				Title = "授权删除权限(此角色下打勾的菜单拥有删除权限)";
			}else if(msg == 'editJurisdiction'){
				Title = "授权修改权限(此角色下打勾的菜单拥有修改权限)";
			}else if(msg == 'findJurisdiction'){
				Title = "授权查看权限(此角色下打勾的菜单拥有查看权限)";
			}
			 var diag = new top.Dialog();
			 diag.Drag = true;
			 diag.Title = Title;
			 diag.URL = '<%=basePath%>role/b4Button.do?roleId='+roleId+'&msg='+msg;
			 diag.Width = 330;
			 diag.Height = 450;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
	</script>


</body>
</html>