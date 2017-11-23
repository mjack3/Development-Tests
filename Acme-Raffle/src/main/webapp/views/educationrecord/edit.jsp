<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<form:form action="educationrecord/auditor/saveEdit.do" modelAttribute="educationrecord">

		<form:hidden path="id" />
		<form:hidden path="version" />
		
		<div class="form-group" style="width: 55%;">
		
	    <form:label path="title"> <spring:message code="workrecord.title"/> </form:label>
		<br />
		<form:input path="title" class="form-control" />
		<form:errors cssClass="error" path="title" /> <br />
		
		<form:label path="description"> <spring:message code="workrecord.description"/> </form:label>
		<br />
		<form:input path="description" class="form-control"/>
		<form:errors cssClass="error" path="description" /> <br />
		
		<form:label path="startDate" > <spring:message code="workrecord.startDate"/> </form:label>
		<br/>
		<form:input path="startDate"  placeholder="dd/MM/yyyy" class="form-control"/>
		<form:errors cssClass="error" path="startDate" /> <br />
		<br />
		
		
		<form:label path="endDate"> <spring:message code="workrecord.endDate"/> </form:label>
		<br />
		<form:input path="endDate" placeholder="dd/MM/yyyy" class="form-control"/>
		<form:errors cssClass="error" path="endDate" /> <br />
		
		
	
		
		<spring:message code="acme.save" var="workrecordSave"/>
		<spring:message code="acme.cancel" var="workrecordCancel"/>
		
		<input type="submit" name="save" value="${workrecordSave}" class="btn btn-primary" />
		<input onclick="window.location='educationrecord/auditor/list.do';" type="button" name="cancel" class="btn btn-warning" value="${workrecordCancel}"/>
</div>
	</form:form>