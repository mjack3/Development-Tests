<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>






<form:form action="curricula/auditor/save.do" modelAttribute="curricula">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="workRecords" />
		<form:hidden path="specialities" />
		<form:hidden path="educationsRecords" />
		
	    <form:label path="name"> <spring:message code="curricula.name"/> </form:label>
		<br />
		<form:input path="name"/>
		<form:errors cssClass="error" path="name" /> <br />
		
		
		<br/>
		<spring:message code="acme.save" var="creditcardSaveHeader"/>
		<spring:message code="acme.cancel" var="creditcardCancelHeader"/>
		
		<input type="submit" name="save" class="btn btn-primary" value="${creditcardSaveHeader}" />
		<input onclick="window.location='curricula/auditor/list.do';" type="button" class="btn btn-warning"  name="cancel" value="${creditcardCancelHeader}"/>

	</form:form>