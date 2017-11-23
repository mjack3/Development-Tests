<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<spring:message code="creditcard.brandName" />: <jstl:out value="${creditCard.brandName }"/> <br/>
<spring:message code="creditcard.number" />: <jstl:out value="${creditCard.number }"/> <br/>
<spring:message code="creditcard.CVV" />: <jstl:out value="${creditCard.CVV }"/> <br/>
<spring:message code="creditcard.month" />: <jstl:out value="${creditCard.month }"/> <br/>
<spring:message code="creditcard.year" />: <jstl:out value="${creditCard.year }"/> <br/>
		

<acme:cancel url="/welcome/index.do" code="acme.cancel"/>

