<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="springconfig" uri="/WEB-INF/tlds/springjpa.tld" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>회원 목록</title>
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
			location.href="/member/memberInsertUpdate.do";
		});
		
		$("#searchBtn").click(function(){
			var frm = $("#listfrm")[0];
			if($("#searchWrd1").val() == ""){
				location.href="/member/memberList.do";
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
	</script>
</head>



<body>
	<div class="container">
		<!-- Page Header Start -->
		<div class="page-header">
	 		<h1>회원 목록</h1>
		</div>
		
		<form:form id="listfrm" method="get" cssClass="form-inline" commandName="searchVO" role="form">
		
		<div class="form-group pull-right">
       		<form:select id="searchCnd1" class="form-control" path="searchCnd1">
       			<form:option value="0">전체</form:option>
       			<form:option value="1">아이디</form:option>
       			<form:option value="2">이름</form:option>
       		</form:select>
      		<form:input type="text" class="form-control" id="searchWrd1" path="searchWrd1" />
      		<button id="searchBtn" class="form-control">검색</button>
		</div>
		
		<!-- Page Header End -->
		<!-- 
		result.number : ${result.number}
		result.size : ${result.size}
		result.totalElements : ${result.totalElements}
		 -->
		<c:set var="pageno" value="${result.number}" />
		<c:set var="pagesize" value="${result.size}" />
		<c:set var="page" value="${result.content}" />
		<c:set var="totcnt" value="${result.totalElements}" />
		<table class="table table-hover table-bordered">  
			<thead>
				<tr>
					<th class="col-md-1 text-center">번호</th>
					<th class="col-md-3 text-center">아이디</th>
					<th class="col-md-6 text-center">이름</th>
					<th class="col-md-2 text-center">등록일시</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>	
					<c:when test="${totcnt == 0}">			
						<tr>
							<td colspan="4" class="text-center">검색된 결과가 없습니다</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:set var="startNo" value="${totcnt-((pageno) * pagesize) }" />
						<c:forEach var="item" items="${page}" varStatus="status">
							<tr>
								<td class="text-center">${startNo-status.index}</td>
								<td><a href="/member/memberInsertUpdate.do?idx=<c:out value='${item.idx}' escapeXml='false'/>"><c:out value="${item.loginId}" escapeXml="false"/></a></td>
								<td><c:out value="${item.name}" escapeXml="false"/></td>
								<td><spring:eval expression="item.insertUpdateDT.insertDateTime" /></td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		<!-- Pager Start -->
		<springjpa:pagination page_no="${pageno}" total_cnt="${totcnt}" page_size="${pagesize}" page_group_size="10" jsFunction="go_page" zerobased="true"></springjpa:pagination>
		<!-- Page End --> 
		<div class="pull-right">
			<button id="btnRegist" type="button" class="btn btn-default">등록</button>
		</div>
		<form:hidden id="searchCnd" path="searchCnd" value="" />
		<form:hidden id="searchWrd" path="searchWrd" value="" />
		<form:hidden id="pageNo" path="pageNo" value="1" />
		<form:hidden id="pageSize" path="pageSize" value="10" />
		</form:form>
	</div>
</body>
</html>