<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<form:form action="mana/creditcard/save.do" modelAttribute="creditCard">


		<form:hidden path="id" />
		<form:hidden path="version" />
		
		
<acme:inputText code="creditCard.number" path="number"/>
<acme:inputText code="creditCard.cvv" path="CVV"/>
<acme:inputText code="creditCard.month" path="month"/>
<acme:inputText code="creditCard.year" path="year"/>
<label><spring:message code='creditCard.brandname' /></label>
		<select name="brandname">
			<option value="VISA">VISA</option>
			<option value="MASTERCARD">MASTERCARD</option>
			<option value="DISCOVER">DISCOVER</option>
			<option value="DINNERS">DINNERS</option>
			<option value="AMEX">AMEX</option>
		</select>


		<spring:message code="actor.save" var="actorSaveHeader"/>
		<spring:message code="actor.cancel" var="actorCancelHeader"/>
		<input type="submit" class="btn btn-primary" name="save" value="${actorSaveHeader}" />
		<input onclick="window.history.back()" class="btn btn-warning" type="button" name="cancel" value="${actorCancelHeader}"/>
		



</form:form>