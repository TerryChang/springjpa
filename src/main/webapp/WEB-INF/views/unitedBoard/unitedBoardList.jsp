<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="springconfig" uri="/WEB-INF/tlds/springjpa.tld" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title><c:out value="${boardType.boardTypeName}" escapeXml="false"/> 게시판</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<!-- 부트스트랩 -->
	<link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<script src="/js/jquery-1.11.2.min.js"></script>
	<script src="/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		$("#btnRegist").click(function(){
			var serializeForm = $("#listfrm").serialize();
			location.href="/unitedBoard/unitedBoardInsertUpdate.do?" + serializeForm;
		});
		
		$("#searchBtn").click(function(){
			var frm = $("#listfrm")[0];
			if($("#searchWrd1").val() == ""){
				location.href="/unitedBoard/unitedBoardList.do?boardTypeIdx=<c:out value='${boardType.idx}' escapeXml='false'/>";
			}else{
				$("#searchCnd").val($("#searchCnd1").val());
				$("#searchWrd").val($("#searchWrd1").val());
				$("#pageNo").val(1);
				frm.submit();
			}
		});
		
		$("#listfrm").submit(function(event){
			event.preventDefault();
		});
	});
	
	function go_page(page_no){
		$("#pageNo").val(page_no);
		$("#listfrm")[0].submit();
	}
	
	function go_detail(idx){
		var serializeForm = $("#listfrm").serialize();
		location.href="/unitedBoard/unitedBoardInsertUpdate.do?idx="+idx+"&"+serializeForm;
	}
	
	function changeBoard(objSel){
		var frm = $("#listfrm")[0];
		$("#searchCnd2").val($("#searchCnd3").val());
		$("#searchCnd").val($("#searchCnd1").val());
		$("#searchWrd").val($("#searchWrd1").val());
		$("#pageNo").val(1);
		frm.submit();
	}
	</script>
</head>



<body>
	<div class="container">
		<!-- Page Header Start -->
		<div class="page-header">
	 		<h1><c:out value="${boardType.boardTypeName}" escapeXml="false"/> 게시판</h1>
		</div>
		
		<form:form id="listfrm" method="get" cssClass="form-inline" commandName="searchVO" role="form">
		<div class="form-group pull-right">
       		<form:select id="searchCnd1" class="form-control" path="searchCnd1">
       			<form:option value="">전체</form:option>
       			<form:option value="loginId">아이디</form:option>
       			<form:option value="title">제목</form:option>
       			<form:option value="contents">내용</form:option>
       		</form:select>
      		<form:input type="text" class="form-control" id="searchWrd1" path="searchWrd1" />
      		<button id="searchBtn" class="form-control">검색</button>
		</div>
		
		<!-- Page Header End -->
		<!--
		Spring Data에서 제공하는 페이징의 경우에는 1페이지는 0으로 시작한다. 
		물론 페이지 번호를 서버에 전송할때 1페이지를 0이 아닌 1로 보내도 작동할 수 있게끔
		PageableHandlerMethodArgumentResolver bean 을 설정할 수는 있겠으나
		조회된 데이터가 몇 페이지에 속하는 데이터인지를 나타내는 의미에서는 1페이지는 0으로 나타낸다.
		즉 현재 페이지 번호는 1 페이지는 0 페이지로 보여준다.
		  
		result.number : ${result.number}
		result.size : ${result.size}
		result.totalElements : ${result.totalElements}
		 -->
		<c:set var="pageno" value="${result.number + 1}" />
		<c:set var="pagesize" value="${result.size}" />
		<c:set var="page" value="${result.content}" />
		<c:set var="totcnt" value="${result.totalElements}" />
		<table class="table table-hover table-bordered">  
			<thead>
				<tr>
					<th class="col-md-1 text-center">번호</th>
					<th class="col-md-5 text-center">제목</th>
					<th class="col-md-2 text-center">아이디</th>
					<th class="col-md-2 text-center">조회수</th>
					<th class="col-md-2 text-center">등록일시</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>	
					<c:when test="${totcnt == 0}">			
						<tr>
							<td colspan="5" class="text-center">검색된 결과가 없습니다</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:set var="startNo" value="${totcnt-((pageno-1) * pagesize) }" />
						<c:forEach var="item" items="${page}" varStatus="status">
							<tr>
								<td class="text-center">${startNo-status.index}</td>
								<td><a href="#" onclick="go_detail(<c:out value='${item.idx}' escapeXml='false'/>)"><c:out value="${item.title}" escapeXml="false"/></a></td>
								<td><c:out value="${item.memberLoginId}" escapeXml="false"/></td>
								<td><c:out value="${item.viewCnt}" escapeXml="false"/></td>
								<td><spring:eval expression="item.insertDateTime" /></td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		<!-- Pager Start -->
		<springjpa:pagination page_no="${pageno}" total_cnt="${totcnt}" page_size="${pagesize}" page_group_size="10" jsFunction="go_page" zerobased="false"></springjpa:pagination>
		<!-- Page End --> 
		<div class="pull-right">
			<button id="btnRegist" type="button" class="btn btn-default">등록</button>
		</div>
		<form:hidden id="searchCnd" path="searchCnd" />
		<form:hidden id="searchCnd2" path="searchCnd2" />
		<form:hidden id="searchWrd" path="searchWrd" />
		<form:hidden id="pageNo" path="pageNo" />
		<form:hidden id="pageSize" path="pageSize" />
		<input type="hidden" id="boardTypeIdx" name="boardTypeIdx" value="<c:out value='${boardType.idx}' escapeXml='false'/>" />
		</form:form>
	</div>
</body>
</html>