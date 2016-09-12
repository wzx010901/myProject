var locat = (window.location+'').split('/'); 
$(function(){if('fhsms'== locat[3]){locat =  locat[0]+'//'+locat[2];}else{locat =  locat[0]+'//'+locat[2]+'/'+locat[3];};});

$(top.hangge());
//发送
function sendFhsms(){
	
	if($("#type").val()=="1"){
		$("#content").val(getContentTxt());
	}else{
		$("#content").val(getContent());
	}
	if($("#username").val()==""){
		$("#username").tips({
			side:3,
            msg:'请输入用户名',
            bg:'#AE81FF',
            time:2
        });
		$("#username").focus();
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
	var username = $("#username").val();
	var content = $("#content").val();
	$.ajax({
		type: "POST",
		url: locat+'/fhsms/save.do?tm='+new Date().getTime(),
    	data: {username:username,content:content},
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
			            time:3
				      });
					 top.fhsmsmsg(username); //websocket即时通讯去通知收信人有站内信接收 ，fhsmsmsg()函数 editUserH()在 WebRoot\static\js\myjs\head.js
				 }else{
					 $("#msg").tips({
							side:3,
				            msg:'发送失败,请联系管理员!',
				            bg:'#FF0000',
				            time:6
					 });
				 }
				 setTimeout("close()",3000);
				 timer(2);
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
	$("#TYPE").val(value);
}
function close(){
	top.Dialog.close();
}

//打开查看
function dialog_open(){
	$("#usernames").val($("#username").val());
	$("#dialog-add").css("display","block");
}
//关闭查看
function cancel_pl(){
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