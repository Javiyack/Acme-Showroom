<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<security:authorize access="isAuthenticated()">
    <jstl:set var="colom" value=", "/>
    <security:authentication property="principal.username" var="username"/>
    <security:authentication property="principal" var="logedActor"/>
    <security:authentication property="principal.authorities[0]"
                             var="permiso"/>
    <jstl:set var="rol" value="${fn:toLowerCase(permiso)}"/>

</security:authorize>


<jstl:set var="owns"
          value="${logedActor.id == item.showroom.user.userAccount.id}"/>

<jstl:set var="readonly"
          value="${(display || !owns || !edition) && item.id != 0}"/>

<jstl:set value="showroom/list.do" var="backUrl"/>
<jstl:if test="${owns}">
    <jstl:set var="backUrl" value="showroom/user/edit.do?showroomId=${item.showroom.id}"/>
</jstl:if>

<div class="seccion w3-light-grey" <jstl:if test='${!readonly}'>id="dropbox" ondragover="return false"
     ondrop="myDrop(event)"</jstl:if>>
    <form:form action="${requestUri}" modelAttribute="item">

        <form:hidden path="id"/>
        <form:hidden path="version"/>
        <form:hidden path="showroom"/>
        <div class="row">
            <div class="col-100">
                <a href="" class="iButton">Acme-Showroom/</a>
                <a href="showroom/list.do" class="iButton"><spring:message code="label.showrooms"/>/</a>
                <a href="showroom/display.do?showroomId=${item.showroom.id}" class="iButton">
                    <jstl:out value="${item.showroom.name}/"/></a>
            </div>
        </div>
        <hr>
        <div class="row">
            <div class="col-100">
                <legend>
                    <spring:message code="label.item"/>: <jstl:out value="${item.title}"/>
                    <jstl:if test="${item.id!=0}">
                    <security:authorize access="isAuthenticated()">
                        <a href="comment/actor/create.do?objectId=${item.id}"><i
                                class="fa fa-commenting-o font-awesome w3-xxlarge toRight"></i></a>
                        <a href="comment/actor/list.do?objectId=${item.id}"><i
                                class="fa fa-comments-o font-awesome w3-xxlarge toRight w3-margin-right"></i></a>
                    </security:authorize>
                </jstl:if>
                </legend>
            </div>
        </div>
        <div class="row">
            <div class="col-40">
                <div class="row">
                    <div class="col-50">
                        <acme:textbox code="label.title" path="title"
                                      readonly="${readonly}"/>
                    </div>
                    <div class="col-50"><acme:textbox code="label.price" path="price"
                                                      readonly="${readonly}" pattern="\\d{1,7}.{0,1}\\d{0,2}"
                                                      placeholder="0.00"
                                                      title="'####0.00'" icon="fa fa-eur"/></div>
                </div>
                <div class="row">
                    <div class="col-50">
                        <acme:textbox code="label.SKU" path="SKU" readonly="true"/></div>
                    <div class="col-50"><br>
                        <acme:checkBox value="${item.available}" code="label.available"
                                       path="available" readonly="${readonly}" css="w3-check"/>
                    </div>

                </div>


            </div>
            <div class="col-60">
                <acme:textarea code="label.description" path="description"
                               readonly="${readonly}" css="formTextArea w3-text-black"/>
            </div>
        </div>

        <div class="row">
            <div class="col-100">

                <hr>
                <jstl:if test="${!readonly}">
                    <acme:submit name="save" code="label.save"
                                 css="formButton toLeft"/>
                    <jstl:if test="${item.id!=0 and !hasRequests}">
                        <acme:submit name="delete" code="label.delete"
                                     css="formButton toLeft"/>
                    </jstl:if>
                </jstl:if>
                <jstl:if test="${item.id!=0}">
                    <acme:button text="label.user" url="actor/display.do?actorId=${item.showroom.user.id}"/>
                    <acme:button text="label.showroom" url="showroom/display.do?showroomId=${item.showroom.id}"/>
                    <security:authorize access="hasRole('USER')">
                        <jstl:if test="${item.available and !owns}">
                            <spring:message code="label.request" var="title"/>
                            <acme:button text="label.request" url="request/user/create.do?itemId=${item.id}"
                                         icon="fa fa fa-shopping-cart font-awesome" title="${title}"/>
                        </jstl:if>
                    </security:authorize>

                </jstl:if>
            </div>
        </div>


    </form:form>

</div>

<%@ include file="/views/comment/list.jsp" %>