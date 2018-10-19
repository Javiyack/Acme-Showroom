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
          value="${logedActor.id == chirp.user.userAccount.id}"/>

<jstl:set var="readonly"
          value="${(display || !owns || !edition) && chirp.id != 0}"/>

<jstl:set value="chirp/user/list.do" var="backUrl"/>
<jstl:if test="${owns}">
    <jstl:set value="chirp/user/edit.do?chirpId=${chirp.id}" var="backUrl"/>
</jstl:if>
<div class="seccion w3-light-grey" >
    <form:form action="${requestUri}" modelAttribute="chirp">

        <form:hidden path="id"/>
        <form:hidden path="version"/>
        <div class="row">
            <div class="col-100">
                <legend><spring:message code="label.chirp"/>
                </legend>
            </div>
        </div>
        <div class="row">
            <div class="col-25">
                        <acme:textbox code="label.name" path="title"
                                      readonly="${readonly}"/>

                        <acme:textbox code="label.moment" path="moment"
                                      readonly="true" />
                    </div>
            <div class="col-75">
                <acme:textarea code="label.description" path="description"
                               readonly="${readonly}" css="formTextArea w3-text-black"/>
            </div>
        </div>
        <div class="row">
            <div class="col-100">
                <jstl:if test="${!readonly}">
                    <hr>
                    <acme:submit name="save" code="label.save"
                                 css="formButton toLeft"/>
                </jstl:if>
            </div>
        </div>


    </form:form>

</div>