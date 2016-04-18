<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="springjpa" uri="/WEB-INF/tlds/springjpa.tld" %>


<%@ page session="false" %>
<%
    /*Logger logger = LoggerFactory.getLogger(this.getClass());*/
    // response.setHeader("Cache-Control","no-store");   
    response.setHeader("Cache-Control","no-cache");
    response.setHeader("Pragma","no-cache");   
    response.setDateHeader("Expires",0);   
    if (request.getProtocol().equals("HTTP/1.1")) 
            response.setHeader("Cache-Control", "no-cache"); 
%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="imgpath" value="${pageContext.request.contextPath}/resources/images/"/> 