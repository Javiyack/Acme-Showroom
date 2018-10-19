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
        pageEncoding="ISO-8859-1" %>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message code="msg.delete.confirmation" var="deleteConfirmation"/>
<div class="seccion w3-light-grey ">
    <legend>
        <spring:message code="label.users"/>
    </legend>
    <div style="overflow-x:auto;">
        <jstl:if test="${!actors.isEmpty()}">
            <form:form action="${requestUri}" method="GET">
                <spring:message code="pagination.size"/>
                <input hidden="true" name="word" value="${word}">
                <input hidden="true" name="actorId" value="${showroom.id}">
                <input type="number" name="pageSize" min="1" max="100"
                       value="${pageSize}">
                <input type="submit" value=">">
            </form:form>
        </jstl:if>

        <display:table pagesize="${pageSize}"
                       class="flat-table flat-table-1 w3-light-grey" name="actors"
                       requestURI="${requestUri}" id="row">
            <jstl:set var="activateUrl" value="actor/activate.do?actorId=${row.id}&pageSize=${pageSize}"/>
            <jstl:set var="url" value="actor/display.do?actorId=${row.id}"/>
            <acme:column property="${row.userAccount.username}" title="actor.username" sortable="true" rowUrl="${url}"/>
            <acme:column property="${row.surname}, ${row.name}" title="label.name" sortable="true" rowUrl="${url}"/>
            <acme:column property="${row.registrationMoment}" title="label.date" sortable="true" rowUrl="${url}"
                         format="moment.format"/>
            <security:authorize access="hasRole('ADMINISTRATOR')">
                <jstl:if test="${!row.userAccount.active}">
                    <acme:column property=" " title="label.activation" sortable="true"
                                 rowUrl="${activateUrl}"
                                 icon="fa fa-toggle-off w3-xlarge w3-text-grey w3-opacity"/>
                </jstl:if>
                <jstl:if test="${row.userAccount.active}">
                    <acme:column property=" " title="label.activation" sortable="true"
                                 rowUrl="${activateUrl}"
                                 icon="fa fa-toggle-on w3-xlarge w3-text-green"/>
                </jstl:if>
            </security:authorize>
        </display:table>
    </div>
</div>
<br/>
<acme:button url="/"
             text="label.back" css="formButton toLeft w3-padding"/>

