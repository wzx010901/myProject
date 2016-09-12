var locat = (window.location+'').split('/'); 
$(function(){if('tool'== locat[3]){locat =  locat[0]+'//'+locat[2];}else{locat =  locat[0]+'//'+locat[2]+'/'+locat[3];};});

$(top.hangge());

$(top.hangge());


function openMap(){
	 top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="地图";
	 diag.URL =locat+"/tool/mapXY.do";
	 diag.Width = 650;
	 diag.Height = 500;
	 diag.CancelEvent = function(){ //关闭事件
		 document.getElementById("coordinateX").value = diag.innerFrame.contentWindow.document.getElementById('coordinateX').value;
		 document.getElementById("coordinateY").value = diag.innerFrame.contentWindow.document.getElementById('coordinateY').value;
		diag.close();
	 };
	 diag.show();
}

function openMap2(){
	 top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="地图";
	 diag.URL =locat+"/tool/mapXY.do";
	 diag.Width = 650;
	 diag.Height = 500;
	 diag.CancelEvent = function(){ //关闭事件
		 document.getElementById("coordinateX2").value = diag.innerFrame.contentWindow.document.getElementById('coordinateX').value;
		 document.getElementById("coordinateY2").value = diag.innerFrame.contentWindow.document.getElementById('coordinateY').value;
		diag.close();
	 };
	 diag.show();
}

//去后计算
function getDistance(){
	if($("#coordinateY").val()==""){
		$("#coordinateY").tips({
			side:3,
            msg:'不能为空',
            bg:'#AE81FF',
            time:2
        });
		$("#coordinateY").focus();
		return false;
	}
	if($("#coordinateX").val()==""){
		$("#coordinateX").tips({
			side:3,
            msg:'不能为空',
            bg:'#AE81FF',
            time:2
        });
		$("#coordinateX").focus();
		return false;
	}
	if($("#coordinateY2").val()==""){
		$("#coordinateY2").tips({
			side:3,
            msg:'不能为空',
            bg:'#AE81FF',
            time:2
        });
		$("#coordinateY2").focus();
		return false;
	}
	if($("#coordinateX2").val()==""){
		$("#coordinateX2").tips({
			side:3,
            msg:'不能为空',
            bg:'#AE81FF',
            time:2
        });
		$("#coordinateX2").focus();
		return false;
	}
	$.ajax({
		type: "POST",
		url: locat+'/tool/getDistance.do',
    	data: {coordinateX:$("#coordinateX").val(),coordinateY:$("#coordinateY").val(),coordinateX2:$("#coordinateX2").val(),coordinateY2:$("#coordinateY2").val(),tm:new Date().getTime()},
		dataType:'json',
		cache: false,
		success: function(data){
			 if("success" == data.result){
				 if('null' == data.distance || null == data.distance){
					 $("#distance").text("计算失败,参数有误");
				 }else{
					 $("#distance").tips({
							side:1,
				            msg:'计算结果',
				            bg:'#75C117',
				            time:3
				     });
					 $("#distance").val(data.distance);
				 }
			 }else{
				 $("#distance").tips({
						side:3,
			            msg:'计算失败,参数有误',
			            bg:'#FF5080',
			            time:10
			     });
				 return;
			 }
		}
	});
}