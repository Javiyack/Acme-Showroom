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
    <jstl:if test="${rol == 'user' || rol == 'responsable'}">
        <jstl:set var="accesscontrol" value="external"/>
    </jstl:if>
    <jstl:if test="${rol == 'technician' || rol == 'manager'}">
        <jstl:set var="accesscontrol" value="internal"/>
    </jstl:if>
</security:authorize>


<jstl:set var="owns"
          value="${logedActor.id == showroom.user.userAccount.id}"/>

<jstl:set var="readonly"
          value="${(display || !owns || !edition) && showroom.id != 0}"/>

<jstl:set value="showroom/list.do" var="backUrl"/>
<jstl:if test="${owns}">
    <jstl:set value="showroom/user/list.do" var="backUrl"/>
</jstl:if>

<div class="form" <jstl:if test='${!readonly}'>id="dropbox" ondragover="return false"
     ondrop="myDrop(event)"</jstl:if>>
    <form:form action="${requestUri}" modelAttribute="showroom">
        <form:hidden path="id"/>
        <form:hidden path="version"/>
        <form:hidden path="user"/>
        <div class="seccion w3-light-grey">
            <div class="row">
                <div class="col-100">
                    <legend>
                        <spring:message code="label.showroom"/>
                    </legend>
                    <div class="row">
                        <div class="col-25">
                            <acme:textbox code="label.name" path="name" readonly="${readonly}"/>
                            <acme:textbox code="label.length" path="length" readonly="${readonly}"/>
                            <acme:textarea code="label.description" path="description" readonly="${readonly}"
                                           css="formTextArea w3-text-black"/>
                            <acme:textarea code="label.pictures" path="pictures"
                                           readonly="${readonly}" css="collection w3-text-black"
                                           id="fotosPath"/>
                        </div>
                        <div class="col-75" >
                            <h2>
                                Gallery
                            </h2>
                            <hr>
                            <!-- Carrusel se fotos  -->
                            <div class="carrusel">
                                <div class="slideshow-container" id="carrusel">
                                    <a class="prev" onclick="plusSlides(-1)">&#10094;</a>
                                    <a class="next" onclick="plusSlides(1)">&#10095;</a>
                                    <jstl:forEach items="${showroom.pictures}" var="picture">
                                        <jstl:set var="count" scope="application" value="${count + 1}"/>
                                        <div class="mySlides">
                                            <a href="${picture}"> <img src="${picture}" class="w3-border w3-card-4 marco iButton"
                                                 style="width: 100%"></a>
                                        </div>
                                    </jstl:forEach>
                                </div>
                            </div>
                            <br>

                            <div id="fotos" style="margin-bottom: 0.2em;">
                                <jstl:set var="count" scope="application" value="${0}"/>
                                <jstl:forEach items="${showroom.pictures}" var="picture">
                                    <jstl:set var="count" scope="application" value="${count + 1}"/>
                                    <img src="${picture}" class="tableImg iButton" onclick="currentSlide(${count})">
                                </jstl:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-100">
                    <jstl:if test="${owns}">
                        <acme:submit name="save" code="label.save"/>
                    </jstl:if>
                    <acme:button text="label.back" url="${backUrl}"/>
                </div>
            </div>
        </div>
    </form:form>
</div>
<jstl:set value="${showroom.id}" var="showroomId"/>

<%@ include file="/views/item/list.jsp" %>