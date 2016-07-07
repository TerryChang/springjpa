<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="springconfig" uri="/WEB-INF/tlds/springjpa.tld" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>회원 등록/수정</title>
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
	
	$(document).ready(function(){
		var checkLoginId = false;
		
		// ajax 작업시 캐쉬를 사용하지 않도록 한다
		$.ajaxSetup({ 
			cache: false
		});
		$("#btnRegist").click(function(){
			// $("#regfrm").submit();
			processjob(true);
		});
		
		$("#btnUpdate").click(function(){
			// $("#regfrm").submit();
			processjob(false);
		});
		
		$("#btnReset").click(function(){
			$("#regfrm")[0].reset();
		});
		
		$("#btnDelete").click(function(){
			var idx = $("#idx").val();
			
			var headers = {};
			headers["X-Ajax-Call"] = "true";
			headers[csrfHeader] = csrfToken;
			
			$.ajax({
	        	url : "/member/memberDelete.do",
	        	headers : headers,
	        	type : "POST",
	        	// data : JSON.stringify({"idx" : idx}),
	        	data : {"idx" : idx},
	        	// contentType: "application/json",	// contentType으로 지정하면 Request Body로 전달되기 때문에 Spring에서 받을때 다르게 접근해야 한다
	        	dataType : "json",
	        	success:function(data, textStatus, jqXHR){
	        		alert("<spring:message code='deleteOK' />");
	        		location.href="/member/memberList.do";
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
		
		<c:if test="${member.idx eq null}">
		$("#loginId").change(function(){
			checkLoginId = false;	
		});
		
		$("#existchkbtn").click(function(){
			var loginId = $("#loginId").val();
			
			var headers = {};
			headers["X-Ajax-Call"] = "true";
			headers[csrfHeader] = csrfToken;
			
			$.ajax({
	        	url : "/member/checkLoginId.do",
	        	headers : headers,
	        	type : "POST",
	        	data : {"loginId" : loginId},
	        	// contentType: "application/json",	// contentType으로 지정하면 Request Body로 전달되기 때문에 Spring에서 받을때 다르게 접근해야 한다
	        	dataType : "json",
	        	beforeSend : function(xhr){
	        		var validationResult = true;
	        		if($.trim(loginId) == ""){
						alert("<spring:message code='NotBlank.memberVO.loginId' />");
						$("#loginId").focus();
						validationResult = false;
	        		}else if((loginId.length < 8) || (loginId.length > 16)){
						var alertMsg = "<spring:message code='Size.memberVO.loginId' />";
						alertMsg = alertMsg.replace("\{2\}", "8");
						alertMsg = alertMsg.replace("\{1\}", "16");
						alert(alertMsg);
						$("#loginId").focus();
						validationResult = false;
	        		}
	        		return validationResult;
					
	        	},
	        	success:function(data, textStatus, jqXHR){
	        		if(data.result == "OK"){
	        			alert("입력된 아이디는 사용가능합니다");
	        			checkLoginId = true;
	        		}else{
	        			alert("입력된 아이디는 사용할 수 없습니다");
	        			checkLoginId = false;
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
		</c:if>
		
		$("#btnList").click(function(){
			// location.href="/member/memberList.do";
			$("#listfrm")[0].submit();
		});
		
		$("#regfrm").submit(function(event){
			event.preventDefault();
		});
	});
	
	function processjob(blInsert){
		
		var loginId = $("#loginId").val();
		var password = $("#password").val();
		var cfpassword = $("#cfpassword").val();
		var name = $("#name").val();
		var email = $("#email").val();
		
		var headers = {};
		headers["X-Ajax-Call"] = "true";
		headers[csrfHeader] = csrfToken;
		
		var sendData = null;
		if(blInsert){
			sendData = JSON.stringify({"loginId" : loginId, "password" : password, "name" : name, "email" : email});
		}else{
			var idx = $("#idx").val();
			sendData = JSON.stringify({"idx" : idx, "loginId" : loginId, "password" : password, "name" : name, "email" : email});
		}
		
		$.ajax({
        	url : "/member/memberInsertUpdate.do",
        	headers : headers,
        	type : "POST",
        	// data : JSON.stringify({"idx" : idx, "boardTypeName" : boardTypeName, "url" : url}),
        	data : sendData,
        	// data : formSerialize,
        	contentType: "application/json",	// contentType으로 지정하면 Request Body로 전달되기 때문에 Spring에서 받을때 다르게 접근해야 한다
        	// processData: false,
        	dataType : "json",
        	beforeSend : function(xhr){
        		var validationResult = true;
        		var pwdpattern = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]+$/;
        		if($.trim(loginId) == ""){
					alert("<spring:message code='NotBlank.memberVO.loginId' />");
					$("#loginId").focus();
					validationResult = false;
        		}else if((loginId.length < 8) || (loginId.length > 16)){
					var alertMsg = "<spring:message code='Size.memberVO.loginId' />";
					alertMsg = alertMsg.replace("\{2\}", "8");
					alertMsg = alertMsg.replace("\{1\}", "16");
					alert(alertMsg);
					$("#loginId").focus();
					validationResult = false;
        		}else if(!checkLoginId){
        			alert("중복체크 버튼을 클릭해서 로그인 아이디 중복체크를 해주세요");
        			validationResult = false;
        		}else if((password.length < 8) || (password.length > 20)){
					var alertMsg = "<spring:message code='Size.memberVO.password' />";
					alertMsg = alertMsg.replace("\{2\}", "8");
					alertMsg = alertMsg.replace("\{1\}", "20");
					alert(alertMsg);
					$("#password").focus();
					validationResult = false;
        		}else if(password != cfpassword){
					var alertMsg = "패스워드와 패스워드 확인 입력값이 다릅니다";
					alert(alertMsg);
					$("#password").focus();
					validationResult = false;
        		}else if(!/^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]+$/.test(password)){
        			var alertMsg = "<spring:message code='Pattern.memberVO.password' />";
        			alert(alertMsg);
        			$("#password").focus();
					validationResult = false;
				}else if($.trim(name) == ""){
					alert("<spring:message code='NotBlank.memberVO.name' />");
					$("#name").focus();
					validationResult = false;
				}else if((name.length < 2) || (name.length > 10)){
					var alertMsg = "<spring:message code='Size.memberVO.name' />";
					alertMsg = alertMsg.replace("\{2\}", "2");
					alertMsg = alertMsg.replace("\{1\}", "10");
					alert(alertMsg);
					$("#name").focus();
					validationResult = false;
				}else if($.trim(email) == ""){
					alert("<spring:message code='NotBlank.memberVO.email' />");
					$("#email").focus();
					validationResult = false;
				}else if((email.length < 10) || (email.length > 100)){
					var alertMsg = "<spring:message code='Size.memberVO.email' />";
					alertMsg = alertMsg.replace("\{2\}", "10");
					alertMsg = alertMsg.replace("\{1\}", "100");
					alert(alertMsg);
					$("#email").focus();
					validationResult = false;
				}
        		return validationResult;
				
        	},
        	success:function(data, textStatus, jqXHR){
        		if(blInsert){
        			alert("<spring:message code='registOK' />");
        			location.href="/member/memberList.do";
        		}else{
        			alert("<spring:message code='updateOK' />");
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
	}
	</script>
</head>
<body>
	<!-- 
	member.idx : ${member.idx}
	member.loginId : ${member.loginId}
	member.password : ${member.password}
	 -->
	<div class="container">
		<div class="page-header">
			<c:choose>
				<c:when test="${member.idx eq null}">
					<h1>회원 등록</h1>
				</c:when>
				<c:otherwise>
					<h1>회원 수정</h1>
				</c:otherwise>
			</c:choose>
		</div>
		<form:form id="regfrm" commandName="member" method="post" cssClass="form-horizontal" role="form" action="/member/memberInsertUpdate.do">
		<c:choose>
			<c:when test="${member.idx eq null}">
				<div class="form-group">
					<label for="name" class="col-xs-2 col-lg-2 control-label">로그인 ID</label>
					<div class="col-xs-8 col-lg-8">
						<form:input id="loginId" path="loginId" cssClass="form-control" placeholder="로그인 ID" />
					</div>
					<div class="col-xs-2 col-lg-2">
						<button id="existchkbtn" class="btn btn-default">중복확인</button>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="form-group">
					<label for="name" class="col-xs-2 col-lg-2 control-label">로그인 ID</label>
					<div class="col-xs-10 col-lg-10">
						<form:label path="loginId" cssClass="control-label"><c:out value="${member.loginId}" escapeXml="false"/></form:label>
						<form:hidden id="loginId" path="loginId" />
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		<div class="form-group">
			<label for="password" class="col-xs-2 col-lg-2 control-label">패스워드</label>
			<div class="col-xs-10 col-lg-10">
				<form:password id="password" path="password" cssClass="form-control" showPassword="true" />
			</div>
		</div>
		<div class="form-group">
			<label for="cfpassword" class="col-xs-2 col-lg-2 control-label">패스워드 확인</label>
			<div class="col-xs-10 col-lg-10">
				<input type="password" id="cfpassword" name="cfpassword" class="form-control" />
			</div>
		</div>
		<div class="form-group">
			<label for="name" class="col-xs-2 col-lg-2 control-label">이름</label>
			<div class="col-xs-10 col-lg-10">
				<form:input id="name" path="name" cssClass="form-control" placeholder="이름" />
			</div>
		</div>
		<div class="form-group">
			<label for="email" class="col-xs-2 col-lg-2 control-label">이메일</label>
			<div class="col-xs-10 col-lg-10">
				<form:input id="email" path="email" cssClass="form-control" placeholder="이메일" />
			</div>
		</div>
		<div class="form-group text-center">
			<div class="col-xs-offset-4 col-xs-4 col-cs-offset-4 col-lg-offset-4 col-lg-4 col-lg-offset-4">
				<c:choose>
					<c:when test="${member.idx eq null}">
						<button id="btnRegist" type="button" class="btn btn-default">등록</button>
					</c:when>
					<c:otherwise>
						<button id="btnUpdate" type="button" class="btn btn-default">수정</button>
						<button id="btnDelete" type="button" class="btn btn-default">삭제</button>
					</c:otherwise>
				</c:choose>
				<button id="btnReset" type="button" class="btn btn-default">재작성</button>
				<button id="btnList" type="button" class="btn btn-default">목록</button>
			</div>
		</div>
		<form:hidden id="idx" path="idx" />
		</form:form>
		<form:form id="listfrm" commandName="searchVO" method="get" cssClass="form-horizontal" role="form" action="/member/memberList.do">
			<form:hidden id="searchCnd1" path="searchCnd1" />
			<form:hidden id="searchWrd1" path="searchWrd1" />
			<form:hidden id="searchCnd" path="searchCnd" />
			<form:hidden id="searchWrd" path="searchWrd" />
			<form:hidden id="pageNo" path="pageNo" />
			<form:hidden id="pageSize" path="pageSize" />
		</form:form>
	</div>
</body>
</html>