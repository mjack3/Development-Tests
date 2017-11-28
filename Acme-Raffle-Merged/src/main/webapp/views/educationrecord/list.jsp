<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<acme:list requestURI="${requestURI}" list="${educationrecord}" variable="e" pagesize="12">

<security:authorize access="hasRole('AUDITOR')">
<a href="educationrecord/auditor/edit.do?q=${e.id}"><spring:message code="acme.edit"></spring:message></a>
</security:authorize>
</acme:list>







