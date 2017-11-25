<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${actionParam }" modelAttribute="configuration">


<form:hidden path="id"/>
<form:hidden path="version"/>
<form:hidden path="historic"/>

	<acme:textbox2 code="configuration.month" path="month"/>
	<acme:textbox2 code="configuration.year" path="year"/>



 
 	<legend ></legend>
 	<acme:textbox readonly="true" code="configuration.fee" path="fee"/>
 
 



<acme:submit name="save" code="configuration.save"/>
<acme:cancel url="/welcome/index.do" code="acme.cancel"/>

</form:form>