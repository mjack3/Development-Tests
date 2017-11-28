<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('ADMIN')">
<acme:list requestURI="mana/administrator/list.do" list="${manager}" pagesize="12" variable="e" hidden_fields="isDebtor,raffles,CreditCard,socialIdentities,userAccount">
	
	<td>
	
	<jstl:choose>
		<jstl:when test= "${e.userAccount.banned==false}">
		<a href="mana/administrator/banned.do?q=${e.id}"> <spring:message
						code='manager.admin.banned' /></a>
		</jstl:when>
		<jstl:otherwise>
		<a href="mana/administrator/desbanned.do?q=${e.id}"> <spring:message
						code='manager.admin.disbanned' /></a>
		</jstl:otherwise>
	</jstl:choose>
	
		
	</td>


</acme:list>
</security:authorize>