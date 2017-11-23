<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<h3><spring:message code="bill.process"></spring:message></h3>
<iframe src="http://free.timeanddate.com/countdown/i5y7f7l3/cf101/cm0/cu4/ct2/cs0/ca0/cr0/ss0/cac000/cpc000/pcd8873c/tcfff/fs100/szw320/szh135/tac000/tpc000/mac000/mpc000/iso${date}T00:00:00" 
allowTransparency="true" frameborder="0" width="320" height="135"></iframe>


<jstl:if test="${diff>=1}">
<form:form action="administrator/bill/generateSave.do" modelAttribute="bill">
<form:hidden path="id"/>
<form:hidden path="version"/>
<form:hidden path="moment"/>
<form:hidden path="ticket"/>
<form:hidden path="raffle"/>


<form:label path="money"> <spring:message code="bill.money"/> </form:label>
		<br />
		<form:input path="money" value="99.95"/>
		<form:errors cssClass="error" path="money" /> <br />
		
		
<spring:message code="bill.generate" var="generate"/>		
<input type="submit" name="save" value="${generate}" />

</form:form>
</jstl:if>