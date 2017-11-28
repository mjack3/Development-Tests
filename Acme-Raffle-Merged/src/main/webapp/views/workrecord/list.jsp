<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div class="btn-group btn-group-xs" role="group" aria-label="label">
		<security:authorize access="hasRole('AUDITOR')">
			<button
				onclick="javascript:location.href='workrecord/auditor/auditregister/view.do'"
				type="button" class="btn btn-default">
				<spring:message code="audit.report" />
			</button>
			</security:authorize>
			<security:authorize access="hasRole('ADMIN')">
			<button
				onclick="javascript:location.href='curricula/workrecord/auditor/auditregister/view.do'"
				type="button" class="btn btn-default">
				<spring:message code="audit.report" />
			</button>
			</security:authorize>
		</div>

<acme:list requestURI="${requestURI}" list="${workrecord}" variable="e" pagesize="12">

<security:authorize access="hasRole('AUDITOR')">
<a href="workrecord/auditor/edit.do?q=${e.id}"><spring:message code="acme.edit"></spring:message></a>
</security:authorize>
</acme:list>







