<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<form:form action="creditcard/manager/save.do" modelAttribute="creditCard">

		<form:hidden path="id" />
		<form:hidden path="version" />
		
		
	    <form:label path="number"> <spring:message code="creditcard.number"/> </form:label>
		<br />
		<form:input path="number"/>
		<form:errors cssClass="error" path="number" /> <br />
		
		<form:label path="CVV"> <spring:message code="creditcard.CVV"/> </form:label>
		<br />
		<form:input path="CVV"/>
		<form:errors cssClass="error" path="CVV" /> <br />
		
		<form:label path="month"> <spring:message code="creditcard.month"/> </form:label>
		<br/>
		<form:input path="month"/>
		<form:errors cssClass="error" path="month" /> <br />
		<br />
		
		
		<form:label path="year"> <spring:message code="creditcard.year"/> </form:label>
		<br />
		<form:input path="year"/>
		<form:errors cssClass="error" path="year" /> <br />
		
		
		<br />
		<div class="form-group" style="width: 55%;">
		<label><spring:message code='creditcard.brandName' /></label> 
		<br/>
		<select name="brandName">
			<option value="VISA">VISA</option>
			<option value="MASTERCARD">MASTERCARD</option>
			<option value="DISCOVER">DISCOVER</option>
			<option value="DINNER">DINNER</option>
			<option value="AMEX">AMEX</option>
		</select>
	</div>
		
	<br />
		
		
		<spring:message code="acme.save" var="creditcardSaveHeader"/>
		<spring:message code="acme.cancel" var="creditcardCancelHeader"/>
		
		<acme:submit name="save" code="acme.save"/>
		<acme:cancel url="/welcome/index.do" code="acme.cancel"/>
	</form:form>

		



