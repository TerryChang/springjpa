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
	
	var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
	var csrfHeader = $("meta[name='_csrf_header']").attr("content");
	var csrfToken = $("meta[name='_csrf']").attr("content");
	
	// ajax 작업시 캐쉬를 사용하지 않도록 한다
	$.ajaxSetup({ 
		cache: false
	});
	
	$(document).ready(function(){
		$("#btnLogin").click(function(){
			var validateResult = validate();
			if(validateResult){
				$("#loginfrm")[0].submit();
			}
		});
		
		$("#btnAjaxLogin").click(function(){
			var loginId = $("#loginId").val();
			var loginPwd = $("#loginPwd").val();
			
			var headers = {};
			headers["X-Ajax-Call"] = "true";
			headers[csrfHeader] = csrfToken;
			
			$.ajax({
	        	url : "/j_spring_security_check",
	        	headers : headers,
	        	type : "POST",
	        	// data : JSON.stringify({"idx" : idx}),
	        	data : {"loginId" : loginId, "loginPwd" : loginPwd},
	        	// contentType: "application/json",	// contentType으로 지정하면 Request Body로 전달되기 때문에 Spring에서 받을때 다르게 접근해야 한다
	        	dataType : "json",
	        	beforeSend : function(xhr){
	        		var validateResult = validate();
	        		return validateResult;
	        	},
	        	success:function(data, textStatus, jqXHR){
	        		var result = data.result;
	        		if(result == "OK"){
	        			var redirectUrl = data.resultMap.redirectUrl;
	        			location.href = redirectUrl;
	        		}else{
	        			var securityexceptionmessage = data.resultMap.securityexceptionmessage;
	        			alert("로그인이 실패했습니다. 다시 시도해주세요\n" + securityexceptionmessage);
	        		}
	        	},
	        	error:function(jqXHR, textStatus, errorThrown){
	        		var alertMsg = "";
	        		if(jqXHR.status == 400){
	        			var responseJSON = jqXHR.responseJSON;
	        			var resultMap = responseJSON.resultMap;
	        			if(responseJSON.job == "Validate"){
	        				$.each(resultMap, function(k, v){
	        					alertMsg += v + "\n";
		        			});
	        				var pattern = /\n$/;
	        				alertMsg = alertMsg.replace(pattern, "");		// 에러 문자열을 결합하면 마지막 행 끝에 개행문자(\n)가 붙기 때문에 이를 제거하기 위해 정규표현식을 이용해서 마지막에 붙은 개행문자를 제거한다
	        				console.log(alertMsg);
		        			alert(alertMsg);
	        			}else if(responseJSON.job == "Ajax"){
	        				var alertMsg = resultMap.message;
	        				alert(alertMsg);
	        			}else{
	        				alert("<spring:message code='errorFail' />");
	        			}
	        		}else{
	        			alert("<spring:message code='errorFail' />");
	        		}
	        	}
	        });
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
		<form id="loginfrm" method="post" action="<c:url value='/j_spring_security_check'/>">
		<div class="form-group">
			<label for="name" class="col-xs-2 col-lg-2 control-label">로그인 ID</label>
			<div class="col-xs-10 col-lg-10">
				<input type="text" id="loginId" name="loginId" class="form-control" placeholder="로그인 ID" value="${loginId}" />
			</div>
		</div>
			
		<div class="form-group">
			<label for="password" class="col-xs-2 col-lg-2 control-label">패스워드</label>
			<div class="col-xs-10 col-lg-10">
				<input type="password" id="loginPwd" name="loginPwd" class="form-control" />
			</div>
		</div>
		
		<c:if test="${not empty securityexceptionmsg}">
		<div class="form-group">
			<div class="col-xs-12 col-lg-12">
				<font color="red">
				로그인이 실패했습니다. 다시 시도해주세요<br/>
				${securityexceptionmsg}
				</font>
			</div>
		</div>
    	</c:if>
		<div class="form-group text-center">
			<div class="col-xs-offset-4 col-xs-4 col-cs-offset-4 col-lg-offset-4 col-lg-4 col-lg-offset-4">
				<button id="btnLogin" type="button" class="btn btn-default">로그인</button>
				<button id="btnAjaxLogin" type="button" class="btn btn-default">Ajax 로그인</button>
			</div>
		</div>
		<sec:csrfInput /><!-- Spring Security Input Tag 추가 -->
		</form>
	</div>
</body>
</html>