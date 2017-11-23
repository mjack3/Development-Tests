<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<acme:list requestURI="${requestURI}" list="${bill}" variable="e" sortable="true" hidden_fields="creditCard,raffle,payed" pagesize="6" >
<security:authorize access="hasRole('MANAGER')">
<jstl:choose>

<jstl:when test="${creditCard == null }">
	<spring:message code="mustegisterCredit" />
</jstl:when>

<jstl:when test="${e.payed == null}">
	<a href="bill/manager/pay.do?q=${e.id}"> <spring:message
						code="bill.pay" />
				</a>
</jstl:when>
<jstl:otherwise>
	<spring:message code="bill.paid"/>: <jstl:out value="${e.payed }"/>
</jstl:otherwise>

</jstl:choose>






				

</security:authorize>
<security:authorize access="hasRole('ADMIN')">
<jstl:if test="${e.creditCard==null}">
<spring:message code="bill.unpay"></spring:message>
</jstl:if>
<jstl:if test="${e.creditCard!=null}">
<spring:message code="bill.paid"></spring:message>

</jstl:if>

</security:authorize>	
</acme:list>
