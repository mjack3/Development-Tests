<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<jstl:forEach var="e" items="${report}">


<table class="table">
    
     <tr>
      <td> <jstl:out value="${e.moment}"></jstl:out>: <spring:message code="audit.report"></spring:message> 
      <jstl:out value="${e.raffle.title}"></jstl:out> </td>
     </tr>
    
   </table>
  </td>
 </tr>
 <tr>
 
 </jstl:forEach>