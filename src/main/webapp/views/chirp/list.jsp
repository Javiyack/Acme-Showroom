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

<jsp:useBean id="date" class="java.util.Date"/>

<security:authorize access="isAuthenticated()">
    <jstl:set var="colom" value=", "/>
    <security:authentication property="principal.username" var="username"/>
    <security:authentication property="principal" var="logedActor"/>
    <security:authentication property="principal.authorities[0]"
                             var="permiso"/>
    <jstl:set var="rol" value="${fn:toLowerCase(permiso)}/"/>
</security:authorize>
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
            <input hidden="true" name="showroomId" value="${showroom.id}">
            <input type="number" name="pageSize" min="1" max="100"
                   value="${pageSize}">
            <input type="submit" value=">">
        </form:form>
    </jstl:if>
    <div style="overflow-x:auto;">

        <display:table pagesize="${pageSize}"
                       class="flat-table flat-table-1 w3-light-grey" name="chirps"
                       requestURI="${requestUri}" id="row">

            <jstl:set var="url" value="chirp/user/display.do?chirpId=${row.id}"/>
            <jstl:set var="icono" value="fa fa-eye w3-xlarge"/>


            <acme:column property="${row.actor.userAccount.username}" title="label.user" rowUrl="${url}"/>
            <acme:column property="${row.title}" title="label.title" rowUrl="${url}"/>
            <acme:column property="${row.description}" title="label.description" rowUrl="${url}"/>
            <acme:column property="${row.moment}" title="label.moment" rowUrl="${url}"/>
            <acme:column property="" title="label.none" icon="${icono}" rowUrl="${url}"/>

        </display:table>
    </div>
    <jstl:if test="${showroom==null}">
        <acme:backButton text="label.back" css="formButton toLeft"/>
        <acme:button text="label.new" url="/chirp/user/create.do"/>
    </jstl:if>
    <jstl:if test="${showroom!=null}">
        <spring:message var="msg" code="msg.save.first"/>
        <jstl:set var="url"
                  value="/item/user/create.do?showroomId=${showroom.id}"></jstl:set>
        <p>
            <i class="fa fa-plus-square w3-xlarge"
               onclick="showConditionalAlert('${msg}','${showroom.id}','${url}');"></i>
        </p>

    </jstl:if>
    <br/>
</div>