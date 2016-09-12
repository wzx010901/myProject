var locat = (window.location+'').split('/'); 
$(function(){if('head'== locat[3]){locat =  locat[0]+'//'+locat[2];}else{locat =  locat[0]+'//'+locat[2]+'/'+locat[3];};});

$(top.hangge());
//发送
function sendEm(){
	
	if($("#type").val()=="1"){
		$("#content").val(getContentTxt());
	}else{
		$("#content").val(getContent());
	}
	if($("#email").val()==""){
		$("#email").tips({
			side:3,
            msg:'请输入邮箱',
            bg:'#AE81FF',
            time:2
        });
		$("#email").focus();
		return false;
	}
	if($("#title").val()==""){
		$("#title").tips({
			side:3,
            msg:'请输入标题',
            bg:'#AE81FF',
            time:2
        });
		$("#title").focus();
		return false;
	}
	if($("#content").val()==""){
		$("#nr").tips({
			side:1,
            msg:'请输入内容',
            bg:'#AE81FF',
            time:3
        });
		return false;
	}
	
	$("#zhongxin").hide();
	$("#zhongxin2").show();
	
	var email = $("#email").val();
	var type  = $("#type").val();
	var title = $("#title").val();
	var content = $("#content").val();
	var isAll = $("#isAll").val();
	
	var fmsg = "${pd.msg}";
	
	$.ajax({
		type: "POST",
		url: locat+'/head/sendEmail.do?tm='+new Date().getTime(),
    	data: {email:email,type:type,title:title,content:content,isAll:isAll,fmsg:fmsg},
		dataType:'json',
		//beforeSend: validateData,
		cache: false,
		success: function(data){
			 $.each(data.list, function(i, list){
				 if(list.msg == 'ok'){
					 var count = list.count;
					 var ecount = list.ecount;
					 $("#msg").tips({
						side:3,
			            msg:'成功发出'+count+'条,失败'+ecount+'条',
			            bg:'#68B500',
			            time:4
				      });
				 }else{
					 $("#msg").tips({
							side:3,
				            msg:'邮件发送失败,请联系管理员检查邮件服务器配置是否正确!',
				            bg:'#FF0000',
				            time:6
					 });
				 }
				 setTimeout("close()",6000);
				 timer(5);
			 });
		}
	});
	
}

//倒计时
function timer(intDiff){
	window.setInterval(function(){
	$('#second_shows').html('<s></s>'+intDiff+'秒');
	intDiff--;
	}, 1000);
} 

function setType(value){
	$("#type").val(value);
}
function close(){
	top.Dialog.close();
}
function isAll(){
	if(document.getElementsByName('form-field-checkbox')[0].checked){
		$("#isAll").val('yes');
		$("#email").attr("disabled",true);
	}else{
		$("#isAll").val('no');
		$("#email").attr("disabled",false);
	}
}

//编辑邮箱(此方式弃用)
function editEmail(){
   var email = $("#email").val();
   var result = showModalDialog(locat+"/head/editEmail.do?email="+email,"","dialogWidth=600px;dialogHeight=380px;");
   if(result==null || ""==result){
	    $("#email").val("");
   }else{
		$("#email").val(result);
   }
}

//打开编辑邮箱
function dialog_open(){
	$("#emails").val($("#email").val());
	$("#dialog-add").css("display","block");
}
//关闭编辑邮箱
function cancel_pl(){
	$("#dialog-add").css("display","none");
}
//保存编辑邮箱
function saveEmail(){
	$("#email").val($("#emails").val());
	$("#dialog-add").css("display","none");
}

//ueditor纯文本
function getContentTxt() {
    var arr = [];
    arr.push(UE.getEditor('editor').getContentTxt());
    return arr.join("");
}
//ueditor有标签文本
function getContent() {
    var arr = [];
    arr.push(UE.getEditor('editor').getContent());
    return arr.join("");
}

setTimeout("ueditor()",500);
function ueditor(){
	var ue = UE.getEditor('editor');
}