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

<security:authorize access="isAuthenticated()">
    <jstl:set var="colom" value=", "/>
    <security:authentication property="principal.username" var="username"/>
    <security:authentication property="principal" var="logedActor"/>
    <security:authentication property="principal.authorities[0]"
                             var="permiso"/>
    <jstl:set var="rol" value="${fn:toLowerCase(permiso)}"/>
</security:authorize>
<jstl:if test="${pageSize == null}">
    <jstl:set value="5" var="pageSize"/>
</jstl:if>
<jstl:if test="${showroom != null}">
    <jstl:set value="showroom/display.do" var="requestUri"/>
    <jstl:set value="true" var="included"/>
</jstl:if>
<div class="seccion w3-light-grey">
    <legend>
        <spring:message code="label.items"/>
        <jstl:if test="${showroomId != null and !included}">
            <spring:message code="label.of"/>
            <jstl:out value="${showroomName}"/>
        </jstl:if>
    </legend>
    <jstl:if test="${!items.isEmpty()}">
        <jstl:if test="${included}">
            <a href="item/list.do?showroomId=${showroom.id}&showroomName=${showroom.name}"
               class="w3-bar-item w3-button w3-padding w3-xlarge"> <i
                    class="fa fa-diamond fa-fw"></i>  <spring:message
                    code="label.show.all" />
            </a>
        </jstl:if>
            <jstl:if test="${!included}">
                <form:form action="${requestUri}" method="POST">
                <div class="row">
                    <div class="col-50">
                        <spring:message code="label.search" var="placeholder"/>
                        <input name="word" value="${word}" placeholder="&#xf002; ${placeholder}"
                               class="font-awesome">
                    </div>
                    <div class="col-50">

                        <p class="toRight"> <spring:message code="pagination.size"/>
                            <input hidden="true" name="showroomId" value="${showroomId}">
                            <input hidden="true" name="showroomName" value="${showroomName}">
                            <input type="number" name="pageSize" min="1" max="100"
                                   value="${pageSize}">
                            <input type="submit" value=">">
                        </p>

                    </div>

                </div>
            </form:form>
        </jstl:if>
    </jstl:if>
    <div style="overflow-x:auto;">
        <display:table pagesize="${pageSize}"
                       class="flat-table flat-table-1 w3-light-grey" name="items"
                       requestURI="${requestUri}" id="row">
            <jstl:set var="owns"
                      value="${logedActor.id==row.showroom.user.userAccount.id}"/>
            <jstl:if test="${owns}">
                <jstl:set var="url" value="item/user/edit.do?itemId=${row.id}"/>
                <jstl:set var="icono" value="fa fa-edit w3-xlarge"/>
            </jstl:if>
            <jstl:if test="${!owns}">
                <jstl:set var="url" value="item/display.do?itemId=${row.id}"/>
                <jstl:set var="icono" value="fa fa-eye w3-xlarge"/>

            </jstl:if>
            <jstl:if test="${row.available}">
                <jstl:set var="availableIcon" value="fa fa-check-square-o w3-xlarge"/>
            </jstl:if>
            <jstl:if test="${!row.available}">
                <jstl:set var="availableIcon" value="fa fa-square-o w3-xlarge"/>
            </jstl:if>
            <jstl:if test="${!included}">
                <acme:column property="${row.showroom.name}" title="label.showroom" rowUrl="${url}"/>
            </jstl:if>
            <acme:column property="${row.SKU}" title="label.SKU" rowUrl="${url}"/>
            <acme:column property="${row.title}" title="label.name" rowUrl="${url}"/>
            <acme:column property="${row.description}" title="label.description" rowUrl="${url}"/>
            <acme:column property="${row.price}" title="label.price" rowUrl="${url}"/>
            <acme:column property="${row.available}" icon="${availableIcon}" title="label.available" rowUrl="${url}"/>
            <acme:column property="" title="label.none" icon="${icono}" rowUrl="${url}"/>
        </display:table>
    </div>
    <jstl:if test="${showroom!=null and rol eq 'user' and logedActor eq showroom.user.userAccount}">

        <spring:message var="msg" code="msg.save.first"/>
        <jstl:set var="url" value="/item/user/create.do?showroomId=${showroom.id}"></jstl:set>
        <spring:message code="label.new" var="newTitle"/>
        <spring:message code="label.item" var="itemTitle"/>
        <p>
            <i class="fa fa-plus-square w3-text-dark-grey w3-hover-text-light-blue w3-xxlarge toRight w3-padding iButton"
               onclick="showConditionalAlert('${msg}','${showroom.id}','${url}');" title="${newTitle} ${itemTitle}"></i>
        </p>
    </jstl:if>
    <jstl:if test="${showroom==null and rol eq 'user'}">
        <hr>
        <jstl:if test="${userList and rol eq 'user'}">
            <p>
                <a href="item/list.do" >
                    <i class="fa fa fa-filter w3-xxlarge w3-text-green"
                       title="<spring:message code="label.show.all"/>">
                </i> <spring:message code="label.show.all"/></a>
            </p>
        </jstl:if>
        <jstl:if test="${userList==null and rol eq 'user'}">
            <p>
                <a href="item/user/list.do" >
                    <i class="fa fa fa-filter w3-xxlarge w3-text-gray"
                       title="<spring:message code="label.show.mine.only"/>"></i>
                    <spring:message code="label.show.mine.only"/></a>
            </p>
        </jstl:if>
    </jstl:if>
    <br/>
</div>