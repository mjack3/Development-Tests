<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('AUDITOR')">
<div class="btn-group btn-group-xs" role="group" aria-label="label">
		<jstl:if test="${auditor.curricula!=null}">
			<button
				onclick="javascript:location.href='educationrecord/auditor/create.do'"
				type="button" class="btn btn-default">
				<spring:message code="curricula.add.educationRecord" />
			</button>
		

		<button
			onclick="javascript:location.href='workrecord/auditor/create.do'"
			type="button" class="btn btn-default">
			<spring:message code="curricula.add.workrecord" />
		</button>

		<button
			onclick="javascript:location.href='speciality/auditor/create.do'"
			type="button" class="btn btn-default">
			<spring:message code="curricula.add.speciality" />
		</button>

		
		
		<br /><br />
		
		</jstl:if>

	</div>


</security:authorize>
<security:authorize access="hasRole('AUDITOR')">
<acme:list requestURI="${requestURI}"  list="${curricula}" variable="e"
entityUrl="{educationsRecords:educationrecord/auditor/list.do,workRecords:workrecord/auditor/list.do,specialities:speciality/auditor/list.do}"

 >

<a href="curricula/auditor/edit.do?q=${e.id}"><spring:message code="acme.edit"></spring:message></a>

</acme:list>
<jstl:if test="${auditor.curricula==null}">
<spring:message code="curricula.generate" var="curriculaSaveHeader"/>
<input onclick="window.location='curricula/auditor/generate.do';" type="submit" name="generate" value="${curriculaSaveHeader}" />
</jstl:if>
</security:authorize>



<security:authorize access="hasRole('ADMIN')">
<acme:list requestURI="${requestURI}"  list="${curricula}" variable="e"
entityUrl="{educationsRecords:curricula/educationrecord/auditor/administrator/list.do,workRecords:curricula/workrecord/auditor/administrator/list.do,specialities:curricula/speciality/auditor/administrator/list.do}"

 ></acme:list>
</security:authorize>

