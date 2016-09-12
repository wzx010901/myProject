<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<%@ include file="../../system/index/top.jsp"%>
		
<script type="text/javascript">
	
	
	//保存
	function save(){
			if($("#keyword").val()==""){
			$("#keyword").tips({
				side:3,
	            msg:'请输入关键词',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#keyword").focus();
			return false;
		}
		if($("#remark").val()==""){
			$("#remark").tips({
				side:3,
	            msg:'请输入备注',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#remark").focus();
			return false;
		}
		if($("#title1").val()==""){
			$("#title1").tips({
				side:3,
	            msg:'请输入标题1',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#title1").focus();
			return false;
		}
		if($("#description1").val()==""){
			$("#description1").tips({
				side:3,
	            msg:'请输入描述1',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#description1").focus();
			return false;
		}
		if($("#imgurl1").val()==""){
			$("#imgurl1").tips({
				side:3,
	            msg:'请输入图片地址1',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#imgurl1").focus();
			return false;
		}
		if($("#tourl1").val()==""){
			$("#tourl1").tips({
				side:3,
	            msg:'请输入超链接1',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#tourl1").focus();
			return false;
		}

		if($("#status").val()==""){
			$("#form-field-radio1").tips({
				side:3,
	            msg:'请输入状态',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#status").focus();
			return false;
		}
		hasK();
	}
	
	//判断关键词是否存在
	function hasK(){
		var keyword = $("#keyword").val();
		var imgmsgId = "${pd.imgmsgId}";
		$.ajax({
			type: "POST",
			url: '<%=basePath%>textmsg/hasK.do',
	    	data: {keyword:keyword,imgmsgId:imgmsgId,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.result){
					$("#Form").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
				 }else{
					$("#keyword").tips({
						side:3,
			            msg:'此关键词已存在(全局)!',
			            bg:'#AE81FF',
			            time:3
			        });
					$("#keyword").focus();
					return false;
				 }
			}
		});
	}
	
	function setType(value){
		$("#status").val(value);
	}
</script>
	</head>
<body>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">


					<form action="imgmsg/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="imgmsgId" id="imgmsgId" value="${pd.imgmsgId}"/>
						<input type="hidden" name="status" id="status" value="${pd.status}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover" style="width:100%;">
							<tr>
								<td style="width:70px;text-align: right;padding-top: 13px;">关键词:</td>
								<td><input style="width:95%;" type="text" name="keyword" id="keyword" value="${pd.keyword}" maxlength="200" placeholder="这里输入关键词" title="关键词"/></td>
								<td style="width:70px;text-align: right;padding-top: 13px;">备注:</td>
								<td><input style="width:95%;" type="text" name="remark" id="remark" value="${pd.remark}" maxlength="200" placeholder="这里输入备注" title="备注"/></td>
							</tr>
						</table>
						<table id="table_report" class="table table-striped table-bordered table-hover" style="width:100%;">
							<tr>
								<td>
									<ul class="unstyled spaced2">
										<li class="text-warning orange"><i class="icon-star blue"></i>
											第一行必录, 总共可以添加8条图文, 只录入第一条是显示单条图文, 录入其它则显示多条图文
										</li>
									</ul>
								</td>
							</tr>
						</table>
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td><input type="text" name="title1" id="title1" value="${pd.title1}" maxlength="200" placeholder="这里输入标题1" title="标题1"/></td>
								<td><input type="text" name="description1" id="description1" value="${pd.description1}" maxlength="200" placeholder="这里输入描述1" title="描述1"/></td>
								<td><input type="text" name="imgurl1" id="imgurl1" value="${pd.imgurl1}" maxlength="200" placeholder="这里输入图片地址(完整路径)1" title="图片地址1"/></td>
								<td><input type="text" name="tourl1" id="tourl1" value="${pd.tourl1}" maxlength="200" placeholder="这里输入超链接1" title="超链接1"/></td>
							</tr>
							<tr>
								<td><input type="text" name="title2" id="title2" value="${pd.title2}" maxlength="200" placeholder="这里输入标题2" title="标题2"/></td>
								<td><input type="text" name="description2" id="description2" value="${pd.description2}" maxlength="200" placeholder="这里输入描述2" title="描述2"/></td>
								<td><input type="text" name="imgurl2" id="imgurl2" value="${pd.imgurl2}" maxlength="200" placeholder="这里输入图片地址(完整路径)2" title="图片地址2"/></td>
								<td><input type="text" name="tourl2" id="tourl2" value="${pd.tourl2}" maxlength="200" placeholder="这里输入超链接2" title="超链接2"/></td>
							</tr>
							<tr>
								<td><input type="text" name="title3" id="title3" value="${pd.title3}" maxlength="200" placeholder="这里输入标题3" title="标题3"/></td>
								<td><input type="text" name="description3" id="DESCRIPTION3" value="${pd.description3}" maxlength="200" placeholder="这里输入描述3" title="描述3"/></td>
								<td><input type="text" name="imgurl3" id="imgurl3" value="${pd.imgurl3}" maxlength="200" placeholder="这里输入图片地址(完整路径)3" title="图片地址3"/></td>
								<td><input type="text" name="tourl3" id="tourl3" value="${pd.tourl3}" maxlength="200" placeholder="这里输入超链接3" title="超链接3"/></td>
							</tr>
							<tr>
								<td><input type="text" name="title4" id="title4" value="${pd.title4}" maxlength="200" placeholder="这里输入标题4" title="标题4"/></td>
								<td><input type="text" name="description4" id="description4" value="${pd.description4}" maxlength="200" placeholder="这里输入描述4" title="描述4"/></td>
								<td><input type="text" name="imgurl4" id="imgurl4" value="${pd.imgurl4}" maxlength="200" placeholder="这里输入图片地址(完整路径)4" title="图片地址4"/></td>
								<td><input type="text" name="tourl4" id="tourl4" value="${pd.tourl4}" maxlength="200" placeholder="这里输入超链接4" title="超链接4"/></td>
							</tr>
							<tr>
								<td><input type="text" name="title5" id="title5" value="${pd.title5}" maxlength="200" placeholder="这里输入标题5" title="标题5"/></td>
								<td><input type="text" name="description5" id="description5" value="${pd.description5}" maxlength="200" placeholder="这里输入描述5" title="描述5"/></td>
								<td><input type="text" name="imgurl5" id="imgurl5" value="${pd.imgurl5}" maxlength="200" placeholder="这里输入图片地址(完整路径)5" title="图片地址5"/></td>
								<td><input type="text" name="tourl5" id="tourl5" value="${pd.tourl5}" maxlength="200" placeholder="这里输入超链接5" title="超链接5"/></td>
							</tr>
							<tr>
								<td><input type="text" name="title6" id="title6" value="${pd.title6}" maxlength="200" placeholder="这里输入标题6" title="标题6"/></td>
								<td><input type="text" name="description6" id="description6" value="${pd.description6}" maxlength="200" placeholder="这里输入描述6" title="描述6"/></td>
								<td><input type="text" name="imgurl6" id="imgurl6" value="${pd.imgurl6}" maxlength="200" placeholder="这里输入图片地址(完整路径)6" title="图片地址6"/></td>
								<td><input type="text" name="tourl6" id="tourl6" value="${pd.tourl6}" maxlength="200" placeholder="这里输入超链接6" title="超链接6"/></td>
							</tr>
							<tr>
								<td><input type="text" name="title7" id="title7" value="${pd.title7}" maxlength="200" placeholder="这里输入标题7" title="标题7"/></td>
								<td><input type="text" name="description7" id="description7" value="${pd.description7}" maxlength="200" placeholder="这里输入描述7" title="描述7"/></td>
								<td><input type="text" name="imgurl7" id="imgurl7" value="${pd.imgurl7}" maxlength="200" placeholder="这里输入图片地址(完整路径)7" title="图片地址7"/></td>
								<td><input type="text" name="tourl7" id="tourl7" value="${pd.tourl7}" maxlength="200" placeholder="这里输入超链接7" title="超链接7"/></td>
							</tr>
							<tr>
								<td><input type="text" name="title8" id="title8" value="${pd.title8}" maxlength="200" placeholder="这里输入标题8" title="标题8"/></td>
								<td><input type="text" name="description8" id="description8" value="${pd.description8}" maxlength="200" placeholder="这里输入描述8" title="描述8"/></td>
								<td><input type="text" name="imgurl8" id="imgurl8" value="${pd.imgurl8}" maxlength="200" placeholder="这里输入图片地址(完整路径)8" title="图片地址8"/></td>
								<td><input type="text" name="tourl8" id="tourl8" value="${pd.tourl8}" maxlength="200" placeholder="这里输入超链接8" title="超链接8"/></td>
							</tr>
						</table>
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:70px;text-align: right;padding-top: 13px;">状态:</td>
								<td>
									<label style="float:left;padding-left: 12px;"><input class="ace" name="form-field-radio" id="form-field-radio1" onclick="setType('1');" <c:if test="${pd.status == '1' }">checked="checked"</c:if> type="radio" value="icon-edit"><span class="lbl">有效</span></label>
									<label style="float:left;padding-left: 5px;"><input class="ace" name="form-field-radio" id="form-field-radio2" onclick="setType('2');" <c:if test="${pd.status == '2' }">checked="checked"</c:if> type="radio" value="icon-edit"><span class="lbl">无效</span></label>
								</td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
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
</div>
<!-- 页面底部js¨ -->
<%@ include file="../../system/index/foot.jsp"%>
<!-- ace scripts -->
<script src="static/ace/js/ace/ace.js"></script>
<!-- ace scripts -->
<script src="static/ace/js/ace/ace.js"></script>
<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>	
	<script type="text/javascript">
	$(top.hangge());
	</script>
</body>
</html>