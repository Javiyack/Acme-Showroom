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
        <spring:message code="label.items"/>
    </legend>

            <jstl:if test="${pageSize == null}">
                <jstl:set value="20" var="pageSize"/>
            </jstl:if>
            <jstl:if test="${showroom != null}">
                <jstl:set value="showroom/user/edit.do" var="requestUri"/>
            </jstl:if>
            <jstl:if test="${!items.isEmpty()}">

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
                               class="flat-table flat-table-1 w3-light-grey" name="items"
                               requestURI="${requestUri}" id="row">
                    <jstl:set var="owns"
                              value="${logedActor.id==row.showroom.user.userAccount.id}"/>
                    <jstl:if test="${owns}">
                        <spring:message var="dificultad" code="difficulty.${row.difficulty}"/>
                        <jstl:set var="url" value="item/user/edit.do?itemId=${row.id}"/>
                        <jstl:set var="icono" value="fa fa-edit w3-xlarge"/>
                    </jstl:if>
                    <jstl:if test="${!owns}">
                        <spring:message var="dificultad" code="difficulty.${row.difficulty}"/>
                        <jstl:set var="url" value="item/user/edit.do?itemId=${row.id}"/>
                        <jstl:set var="icono" value="fa fa-eye w3-xlarge"/>
                    </jstl:if>

                    <acme:column property="${row.name}" title="label.name" rowUrl="${url}"/>
                    <acme:column property="${row.description}" title="label.description" rowUrl="${url}"/>
                    <acme:column property="${dificultad}" title="label.difficult" rowUrl="${url}"/>
                    <acme:column property="${row.origin}" title="label.origin" rowUrl="${url}"/>
                    <acme:column property="${row.destination}" title="label.destination" rowUrl="${url}"/>
                    <acme:column property="${row.length}" title="label.length" rowUrl="${url}"/>
                    <acme:column property="" title="label.none" icon="${icono}" rowUrl="${url}"/>

                </display:table>
            </div>
            <jstl:if test="${showroom==null}">
                <acme:backButton text="label.back" css="formButton toLeft"/>
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