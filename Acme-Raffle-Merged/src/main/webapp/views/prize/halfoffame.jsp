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

<table class="table">
   	<jstl:forEach items="${user}" var="a">
   	
     <tr>
      <td> <jstl:out value="${a.name}">:</jstl:out>
     </tr>
   </jstl:forEach>

   </table>
 



 
 <h1><spring:message code="user.75"></spring:message></h1>
 <table class="table">
   	<jstl:forEach items="${user075}" var="a">
   	
     <tr>
      <td> <jstl:out value="${a.name}">:</jstl:out>
       </td>
     </tr>
   </jstl:forEach>

   </table>
 
 