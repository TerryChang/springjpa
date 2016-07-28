<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="springconfig" uri="/WEB-INF/tlds/springjpa.tld" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>
		<c:choose>
			<c:when test="${unitedBoard.idx eq null}">
				${boardType.boardTypeName} 등록
			</c:when>
			<c:otherwise>
				${boardType.boardTypeName} 수정
			</c:otherwise>
		</c:choose>
	</title>
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
			var headers = {};
			headers["X-Ajax-Call"] = "true";
			headers[csrfHeader] = csrfToken;
			
			var idx = $("#idx").val();
			$.ajax({
	        	url : "/unitedBoard/unitedBoardDelete.do",
	        	headers : headers,
	        	type : "POST",
	        	// data : JSON.stringify({"idx" : idx}),
	        	data : {"idx" : idx},
	        	// contentType: "application/json",	// contentType으로 지정하면 Request Body로 전달되기 때문에 Spring에서 받을때 다르게 접근해야 한다
	        	dataType : "json",
	        	success:function(data, textStatus, jqXHR){
	        		alert("<spring:message code='deleteOK' />");
	        		location.href="/unitedBoard/unitedBoardList.do?boardTypeIdx=<c:out value='${boardType.idx}' escapeXml='false' />";
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
		
		$("#btnList").click(function(){
			// location.href="/member/memberList.do";
			$("#listfrm")[0].submit();
		});
		
		$("#regfrm").submit(function(event){
			event.preventDefault();
		});
	});
	
	function processjob(blInsert){
		var headers = {};
		headers["X-Ajax-Call"] = "true";
		headers[csrfHeader] = csrfToken;
		
		var boardTypeIdx = $("#boardTypeIdx").val();
		var memberIdx = $("#memberIdx").val();
		var title = $("#title").val();
		var contents = $("#contents").val();
		
		var sendData = null;
		if(blInsert){
			sendData = JSON.stringify({"boardTypeIdx" : boardTypeIdx, "memberIdx" : memberIdx, "title" : title, "contents" : contents});
		}else{
			var idx = $("#idx").val();
			sendData = JSON.stringify({"idx" : idx, "boardTypeIdx" : boardTypeIdx, "memberIdx" : memberIdx, "title" : title, "contents" : contents});
		}
		
		$.ajax({
        	url : "/unitedBoard/unitedBoardInsertUpdate.do",
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
        		
        		if($.trim(title) == ""){
					alert("<spring:message code='NotBlank.unitedBoardVO.title' />");
					$("#title").focus();
					validationResult = false;
        		}else if((title.length < 3) || (title.length > 255)){
					var alertMsg = "<spring:message code='Size.unitedBoardVO.title' />";
					alertMsg = alertMsg.replace("\{2\}", "3");
					alertMsg = alertMsg.replace("\{1\}", "255");
					alert(alertMsg);
					$("#title").focus();
					validationResult = false;
        		}else if($.trim(contents) == ""){
						alert("<spring:message code='NotBlank.unitedBoardVO.contents' />");
						$("#contents").focus();
						validationResult = false;
        		}else if(contents.length < 10){
					var alertMsg = "<spring:message code='Size.unitedBoardVO.contents' />";
					alertMsg = alertMsg.replace("\{2\}", "10");
					alert(alertMsg);
					$("#contents").focus();
					validationResult = false;
        		}

        		return validationResult;
				
        	},
        	success:function(data, textStatus, jqXHR){
        		if(blInsert){
        			alert("<spring:message code='registOK' />");
        			location.href="/unitedBoard/unitedBoardList.do?boardTypeIdx=<c:out value='${boardType.idx}' escapeXml='false' />";
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
	<div class="container">
		<div class="page-header">
			<c:choose>
				<c:when test="${unitedBoard.idx eq null}">
					<h1><c:out value="${boardType.boardTypeName}" escapeXml="false" /> 게시판 등록</h1>
				</c:when>
				<c:otherwise>
					<h1><c:out value="${boardType.boardTypeName}" escapeXml="false" /> 게시판 수정</h1>
				</c:otherwise>
			</c:choose>
		</div>
		<form:form id="regfrm" commandName="unitedBoard" method="post" cssClass="form-horizontal" role="form" action="/member/memberInsertUpdate.do">
		<div class="form-group">
			<label for="name" class="col-xs-2 col-lg-2 control-label">작성자</label>
			<div class="col-xs-10 col-lg-10">
				<form:input id="memberLoginId" path="memberLoginId" cssClass="form-control" placeholder="로그인 아이디" />
			</div>
		</div>
		<div class="form-group">
			<label for="title" class="col-xs-2 col-lg-2 control-label">제목</label>
			<div class="col-xs-10 col-lg-10">
				<form:input id="title" path="title" cssClass="form-control" placeholder="제목" />
			</div>
		</div>
		<div class="form-group">
			<label for="content" class="col-xs-2 col-lg-2 control-label">내용</label>
			<div class="col-xs-10 col-lg-10">
				<form:textarea id="contents" path="contents" cssClass="form-control" rows="8" cols="80" placeholder="내용" />
			</div>
		</div>
		<div class="form-group text-center">
			<div class="col-xs-offset-4 col-xs-4 col-cs-offset-4 col-lg-offset-4 col-lg-4 col-lg-offset-4">
				<c:choose>
					<c:when test="${unitedBoard.idx eq null}">
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
		<form:hidden id="memberIdx" path="memberIdx" />
		<form:hidden id="idx" path="idx" />
		</form:form>
		<form:form id="listfrm" commandName="searchVO" method="get" cssClass="form-horizontal" role="form" action="/unitedBoard/unitedBoardList.do">
			<form:hidden id="searchCnd2" path="searchCnd2" />
			<form:hidden id="searchCnd3" path="searchCnd3" />
			<form:hidden id="searchCnd1" path="searchCnd1" />
			<form:hidden id="searchWrd1" path="searchWrd1" />
			<form:hidden id="searchCnd" path="searchCnd" />
			<form:hidden id="searchWrd" path="searchWrd" />
			<form:hidden id="pageNo" path="pageNo" />
			<form:hidden id="pageSize" path="pageSize" />
			<input type="hidden" id="boardTypeIdx" name="boardTypeIdx" value="<c:out value='${boardType.idx}' escapeXml='false'/>" />
		</form:form>
	</div>
</body>
</html>