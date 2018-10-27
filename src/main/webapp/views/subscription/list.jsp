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
        <spring:message code="label.subscriptions"/>
    </legend>
    <div style="overflow-x:auto;">
        <jstl:if test="${!subscriptions.isEmpty()}">
            <form:form action="${requestUri}" method="GET">
                <spring:message code="pagination.size"/>
                <input hidden="true" name="word" value="${word}">
                <input hidden="true" name="actorId" value="${showroom.id}">
                <input type="number" name="pageSize" min="1" max="100"
                       value="${pageSize}">
                <input type="submit" value=">">
            </form:form>
        </jstl:if>
        <legend>
            <spring:message code="label.actors"/>
        </legend>

        <display:table pagesize="${pageSize}"
                       class="flat-table flat-table-1 w3-light-grey" name="subscribedActors"
                       requestURI="${requestUri}" id="row">
            <jstl:set var="url" value="chirp/actor/list.do?subscriptionId=${row.id}"/>
            <acme:column property="${row.userAccount.username}" title="actor.username" sortable="true" rowUrl="${url}"/>
            <acme:column property="${row.surname}, ${row.name}" title="label.name" sortable="true" rowUrl="${url}"/>

            <acme:column property=" " title="label.chirp.subscription"
                         rowUrl="subscription/actor/subcribe.do?actorId=${row.id}"
                         icon="fa fa-check w3-xlarge w3-text-green"/>
        </display:table>
        <legend>
            <spring:message code="label.topics"/>
        </legend>
        <display:table pagesize="${pageSize}"
                       class="flat-table flat-table-1 w3-light-grey" name="topicSubscriptions"
                       requestURI="${requestUri}" id="row2">
            <jstl:set var="url" value="chirp/actor/list.do?subscriptionId=${row2.id}"/>
            <acme:column property="${row2.topic.name}" title="label.topic" sortable="true" rowUrl="${url}"/>

            <acme:column property=" " title="label.chirp.subscription"
                         rowUrl="subscription/actor/topic/subcribe.do?topicId=${row2.id}"
                         icon="fa fa-check w3-xlarge w3-text-green"/>
        </display:table>
    </div>
</div>
<br/>
<acme:button url="/"
             text="label.back" css="formButton toLeft w3-padding"/>

