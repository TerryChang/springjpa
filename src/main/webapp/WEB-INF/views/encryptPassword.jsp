<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="springconfig" uri="/WEB-INF/tlds/springjpa.tld" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>패스워드 암호화</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<sec:csrfMetaTags /><!-- Spring Security Meta Tag 추가 -->
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
	
	// ajax 작업시 캐쉬를 사용하지 않도록 한다
	$.ajaxSetup({ 
		cache: false
	});
	
	$(document).ready(function(){
		$("#btnPassword").click(function(){
			var validateResult = validate();
			if(validateResult){
				$("#encryptPasswordFrm")[0].submit();
			}
		});
	});
	
	function validate(){
		var result = true;
		var password = $("#password").val();
		if($.trim(password) == ""){
			alert("패스워드를 입력해주세요");
			$("#password").focus();
			result = false;
		}
		return result;
	}
	</script>
</head>
<body>
	<div class="container">
		<div class="page-header">
			<h1>패스워드 암호화</h1>
		</div>
		<form id="encryptPasswordFrm" method="post" action="<c:url value='/encryptPassword.do'/>">
		<div class="form-group">
			<label for="name" class="col-xs-2 col-lg-2 control-label">패스워드</label>
			<div class="col-xs-10 col-lg-10">
				<input type="text" id="password" name="password" class="form-control" placeholder="암호화 할 패스워드 문자열을 입력하세요" value="${password}" />
			</div>
		</div>
		
		<c:if test="${not empty passwordResult}">
		<div class="form-group">
			<label for="name" class="col-xs-2 col-lg-2 control-label">암호화 된 패스워드</label>
			<div class="col-xs-10 col-lg-10">
				<font color="red">${passwordResult}</font>
			</div>
		</div>
    	</c:if>
		<div class="form-group text-center">
			<div class="col-xs-offset-4 col-xs-4 col-cs-offset-4 col-lg-offset-4 col-lg-4 col-lg-offset-4">
				<button id="btnPassword" type="button" class="btn btn-default">패스워드 변환</button>
			</div>
		</div>
		<sec:csrfInput /><!-- Spring Security Input Tag 추가 -->
		</form>
	</div>
</body>
</html>