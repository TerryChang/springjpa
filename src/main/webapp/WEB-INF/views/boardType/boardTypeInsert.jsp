<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="springconfig" uri="/WEB-INF/tlds/springjpa.tld" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>게시판 종류 등록</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<!-- 부트스트랩 -->
	<link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<style>
		.col-xs-offset-5, .col-xs-2, .col-cs-offset-5, .col-lg-offset-5, .col-lg-2, .col-lg-offset-5 {
  			border: 0px solid red;
		}
    </style>
	<script src="/js/jquery-1.11.2.min.js"></script>
	<script src="/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript">
	$(document).ready(function () {
		$("#btnReg").click(function(){
			if(validate()){
				// $("#regfrm").submit();
				var gidx = $("#gidx").val();
				var pidx = $("#pidx").val();
				var title = $("#title").val();
				var content = $("#content").val();
				
				$.ajax({
		        	url : "/notmember/insertBoard.do",
		        	data : {gidx : gidx, pidx : pidx, title : title, content : content},
		        	dataType : "json",
		        	type : "POST",
		        	success:function(data, textStatus, jqXHR){
		        		var result = data.result;
		        		if(result.code == "0000"){
		    	    		alert("등록되었습니다");
		    	    		location.href="getBoardList.do";
		    	    	}else{
		    	    		if(result.code == "4000"){
		    	    			var fieldValidateMap = result.fieldValidateMap;
		    	    			$.each(fieldValidateMap, function(key, value){
		    	    				if(key == "title"){
		    	    					$("#errorTitle").html(value);
		    	    				}else if(key == "content"){
		    	    					$("#errorContent").html(value);
		    	    				}else{
		    	    					$("#errorMessage").html(value);
		    	    				}
		    	    			});
		    	    		}else{
		    	    			var message = result.message;
		    	    			alert(message);
		    	    		}
		    	    	}
		        	},
		        	error:function(jqXHR, textStatus, errorThrown){
		        		alert("등록 오류입니다");
		        	}
		        	
		        });
			}	
		});
	});
	function validate(){
		
		if($("#name").val() == ""){
			alert("제목을 입력해주세요");
			$("#title").focus();
			return false;
		}
		
		if(!utf8Check($("#title").val(), "제목이 지정된 크기인 ", 100)){
			$("#title").focus();
			return false;
		}
		
		if($("#content").val() == ""){
			alert("내용을 입력해주세요");
			$("#content").focus();
			return false;
		}
		
		return true;
	}
	
	function utf8Check(val, msg, limitsize){
		var result = true;
		
	   	var byte=0;
		var one_char="";
		for(var x=0; x<val.length; x++){
			one_char=val.charAt(x);
		    //utf-8이므로 3자를 더함, euc-kr이면 2를 더함
		    if(escape(one_char).length > 4)
		    	byte=byte+3;
		    else
		        byte++;
		}
		
		if(byte > limitsize){
			// alert(msg + " ( " + byte + "/" + limitsize + " )");
			alert(msg + limitsize + " byte보다 큽니다(현재 크기 : " + byte + " byte)");
			result = false;
		}else{
			result = true;
		}
		
		return result;
	}
	</script>
</head>
<body>
	<div class="container">
		<div class="page-header">
	 		<h1>게시판 종류 등록</h1>
		</div>
		<form:form id="regfrm" method="post" cssClass="form-horizontal" role="form">
		<div class="form-group">
			<label for="name" class="col-xs-2 col-lg-2 control-label">게시판 이름</label>
			<div class="col-xs-10 col-lg-10">
				<input id="name" name="name" type="text" class="form-control" placeholder="이름">
			</div>
		</div>
		<div class="form-group">
			<label for="url" class="col-xs-2 col-lg-2 control-label">URL</label>
			<div class="col-xs-10 col-lg-10">
				<input id="url" name="url" type="text" class="form-control" placeholder="URL">
			</div>
		</div>
		<div class="form-group text-center">
			<div class="col-xs-offset-5 col-xs-2 col-cs-offset-5 col-lg-offset-5 col-lg-2 col-lg-offset-5">
				<button id="btnRegist" type="button" class="btn btn-default">등록</button>
				<button id="btnReset" type="reset" class="btn btn-default">재작성</button>
			</div>
		</div>
		</form:form>
	</div>
</body>
</html>