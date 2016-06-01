<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="springconfig" uri="/WEB-INF/tlds/springjpa.tld" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>로그인 화면</title>
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
	$(document).ready(function(){
		$("#btnLogin").click(function(){
			var validateResult = validate();
			if(validateResult){
				$("#loginfrm")[0].submit();
			}
		});
	});
	
	function validate(){
		var loginId = $("#loginId").val();
		var loginPwd = $("#loginPwd").val();
		var result = true;
		if($.trim(loginId) == ""){
			alert("로그인 ID를 입력해주세요");
			$("#loginId").focus();
			result = false;
		}else if($.trim(loginPwd) == ""){
			alert("패스워드를 입력해주세요");
			$("#loginPwd").focus();
			result = false;
		}
		return result;
	}
	</script>
</head>
<body>
	<div class="container">
		<div class="page-header">
			<h1>로그인 화면</h1>
		</div>
		<form id="loginfrm" method="post" action="<c:url value='/login.do'/>">
		<div class="form-group">
			<label for="name" class="col-xs-2 col-lg-2 control-label">로그인 ID</label>
			<div class="col-xs-10 col-lg-10">
				<input type="text" id="loginId" name="loginId" class="form-control" placeholder="로그인 ID" />
			</div>
		</div>
			
		<div class="form-group">
			<label for="password" class="col-xs-2 col-lg-2 control-label">패스워드</label>
			<div class="col-xs-10 col-lg-10">
				<input type="password" id="loginPwd" name="loginPwd" class="form-control" />
			</div>
		</div>
		
		<div class="form-group text-center">
			<div class="col-xs-offset-4 col-xs-4 col-cs-offset-4 col-lg-offset-4 col-lg-4 col-lg-offset-4">
				<button id="btnLogin" type="button" class="btn btn-default">로그인</button>
			</div>
		</div>
		</form>
	</div>
</body>
</html>