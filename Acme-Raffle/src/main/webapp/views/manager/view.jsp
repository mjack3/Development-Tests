<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<h1><spring:message code="manager.banned"></spring:message></h1>
<img src ="http://www.fiscal-impuestos.com/sites/fiscal-impuestos.com/files/deudores.jpg"/>
<input onclick="window.location='/Acme-Raffle/welcome/index.do'" type="button" class="btn btn-warning" value="<spring:message code="manager.welcome" />">