<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:if test="${modelName == null}">
	<jstl:set value="files" var="modelName" />
</jstl:if>

<fieldset>
	<legend>
		<spring:message code="subSection.data.files" />
	</legend>
	<jstl:if test="${requestUri != null && pageSize != null}">
		<form:form action="${requestUri}" method="GET">
			<spring:message code="pagination.size" />
			<input hidden="true" name="word" value="${word}">
			<input type="number" name="pageSize" min="1" max="100"
				value="${pageSize}">
			<input type="submit" value=">">
		</form:form>
		<display:table pagesize="${pageSize}" class="displaytag"
			name="${modelName}" requestURI="${requestUri}" id="row">			
			<%@ include file="/views/file/columns.jsp" %> 
		</display:table>
	</jstl:if>
	<jstl:if test="${requestUri == null || pageSize == null}">
		<display:table class="displaytag" name="${modelName}" id="row">
			<%@ include file="/views/file/columns.jsp" %> 
		</display:table>
	</jstl:if>
	<jstl:if test="${allowed}">
		<acme:button text="file.create"
			url="${url}"
			css="formButton toLeft" />
	</jstl:if>
</fieldset>



