<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<h1><spring:message code="user.winners"></spring:message></h1>
 <jstl:forEach var="e" items="${user}">
<table class="table">
    <jstl:if test="${!e.prizes.isEmpty()}">
     <tr>
      <td> <jstl:out value="${e.name}">:</jstl:out>: <spring:message code="user.prize"/> <jstl:out value="${e.prizes}"></jstl:out>
       </td>
     </tr>
     </jstl:if>
   </table>
 
 <tr>

 </jstl:forEach>
 
 <h1><spring:message code="user.75"></spring:message></h1>
 <jstl:forEach var="e" items="${user075}">
<table class="table">
    
     <tr>
      <td> <jstl:out value="${e.name}"></jstl:out>
       </td>
     </tr>
    
   </table>
 
 <tr>

 </jstl:forEach>
  