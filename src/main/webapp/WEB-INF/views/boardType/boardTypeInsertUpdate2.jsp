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
	$(document).ready(function(){
		// ajax 작업시 캐쉬를 사용하지 않도록 한다
		$.ajaxSetup({ 
			cache: false
		});
		$("#btnRegist").click(function(){
			// $("#regfrm").submit();
			$("#regfrm").submit();
		});
		
		$("#btnReset").click(function(){
			$("#regfrm").reset();
		});
		
		$("#btnList").click(function(){
			location.href="/boardType/boardTypeList.do";
		});
		
		/*
		$("#regfrm").submit(function(event){
			event.preventDefault();
		});
		*/
	});
	</script>
</head>
<body>
	<div class="container">
		<div class="page-header">
	 		<h1>게시판 종류 등록2</h1>
		</div>
		<form:form id="regfrm" commandName="boardType" method="post" cssClass="form-horizontal" role="form">
		<div class="form-group">
			<label for="name" class="col-xs-2 col-lg-2 control-label">게시판 이름</label>
			<div class="col-xs-8 col-lg-8">
				<form:input path="boardTypeName" id="boardTypeName" cssClass="form-control"  placeholder="이름" />
			</div>
			<div class="col-xs-2 col-lg-2">
				<form:errors path="boardTypeName" />
			</div>
		</div>
		<div class="form-group">
			<label for="url" class="col-xs-2 col-lg-2 control-label">URL</label>
			<div class="col-xs-8 col-lg-8">
				<form:input path="url" id="url" cssClass="form-control"  placeholder="URL" />
			</div>
			<div class="col-xs-2 col-lg-2">
				<form:errors path="url" />
			</div>
		</div>
		<div class="form-group text-center">
			<div class="col-xs-offset-4 col-xs-4 col-cs-offset-4 col-lg-offset-4 col-lg-4 col-lg-offset-4">
				<button id="btnRegist" type="button" class="btn btn-default">등록</button>
				<button id="btnReset" type="button" class="btn btn-default">재작성</button>
				<button id="btnList" type="button" class="btn btn-default">목록</button>
			</div>
		</div>
		</form:form>
	</div>
</body>
</html>