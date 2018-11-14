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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="seccion w3-light-grey">
    <legend>
        <spring:message code="label.chirps"/>
    </legend>

    <jstl:if test="${pageSize == null}">
        <jstl:set value="20" var="pageSize"/>
    </jstl:if>

        <jstl:if test="${!chirps.isEmpty()}">

            <form:form action="${requestUri}" method="GET">
                <spring:message code="pagination.size"/>
                <input hidden="true" name="word" value="${word}">
                <input hidden="true" name="topic" value="${topic}">
                <input type="number" name="pageSize" min="1" max="100"
                       value="${pageSize}">
                <input type="submit" value=">">
            </form:form>
        </jstl:if>
    
    <div style="overflow-x:auto;">

            <display:table pagesize="${pageSize}"
                           class="flat-table0 flat-table-1 w3-light-grey" name="chirps"
                           requestURI="${requestUri}" id="row3">

                <jstl:set var="url" value="chirp/actor/display.do?chirpId=${row3.id}"/>
                <jstl:set var="icono" value="fa fa-eye w3-xlarge"/>


                <acme:urlColumn value="${row3.actor.userAccount.username}" title="label.user" href="${url}"
                             sortable="true" css="iButton"/>
                <acme:urlColumn value="${row3.topic}" title="label.topic" href="${url}" sortable="true" css="iButton"/>
                <acme:urlColumn value="${row3.title}" title="label.title" href="${url}" css="iButton"/>
                <acme:urlColumn value="${row3.description}" title="label.description" href="${url}" css="iButton"/>
                <spring:message code="moment.pattern" var="intercionalizedPattern"/>
                <fmt:formatDate value="${row3.moment}" pattern="${intercionalizedPattern}" var="intercionalizedMoment"/>
                <acme:urlColumn value="${intercionalizedMoment}" title="label.moment" href="${url}" sortable="true" css="iButton" />
                <acme:urlColumn value="" title="label.none" icon="${icono}" href="${url}" css="iButton"/>

            </display:table>
        </div>

        <acme:button text="label.new" url="/chirp/actor/create.do"/>

        <br/>

</div>