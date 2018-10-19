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
          value="${logedActor.id == item.showroom.user.userAccount.id}"/>

<jstl:set var="readonly"
          value="${(display || !owns || !edition) && item.id != 0}"/>

<jstl:set value="showroom/list.do" var="backUrl"/>
<jstl:if test="${owns}">
    <jstl:set value="showroom/user/edit.do?showroomId=${item.showroom.id}" var="backUrl"/>
</jstl:if>
<div class="seccion w3-light-grey" <jstl:if test='${!readonly}'>id="dropbox" ondragover="return false"
     ondrop="myDrop(event)"</jstl:if>>
    <form:form action="${requestUri}" modelAttribute="item">

        <form:hidden path="id"/>
        <form:hidden path="version"/>
        <form:hidden path="showroom"/>
        <div class="row">
            <div class="col-100">
                <legend><spring:message code="label.showroom"/>: <jstl:out value="${item.showroom.name}"/>
                </legend>
            </div>
        </div>
        <div class="row">
            <div class="col-50">
                <div class="row">
                    <div class="col-50">
                        <acme:textbox code="label.name" path="name"
                                      readonly="${readonly}"/>
                    </div>
                    <div class="col-25">
                        <acme:textbox code="label.length" path="length"
                                      readonly="${readonly}"/>
                    </div>
                    <div class="col-25">
                        <form:label path="difficulty">
                            <spring:message code="label.difficult"/>
                        </form:label>
                        <select id="difficulty" name="difficulty" class="w3-text-black"
                                <jstl:if test='${readonly}'>disabled</jstl:if>>

                            <jstl:forEach items="${difficultyLevels}" var="item">
                                <option value="${item}" id="${item}"
                                        <jstl:if test='${item==item.difficulty}'>selected</jstl:if> >
                                    <spring:message code="difficulty.${item}"/>
                                </option>

                            </jstl:forEach>
                        </select>
                    </div>
                </div>
                <div class="row">
                    <div class="col-50">

                        <acme:textbox code="label.origin" path="origin"
                                      readonly="${readonly}"/>
                    </div>
                    <div class="col-50">
                        <acme:textbox code="label.destination" path="destination"
                                      readonly="${readonly}"/>

                    </div>

                </div>

            </div>
            <div class="col-50">
                <acme:textarea code="label.description" path="description"
                               readonly="${readonly}" css="formTextArea w3-text-black"/>
            </div>
        </div>
        <div class="row">
            <div class="col-100">
                <acme:textarea path="pictures" code="label.pictures" css="formTextArea collection w3-text-black"
                               id="fotosPath" readonly="${readonly}"/>
                <br/>
                <br/>
                <div id="fotos" style="margin-bottom: 0.2em;">
                    <jstl:set var="count" scope="application" value="${0}"/>
                    <jstl:forEach items="${item.pictures}" var="picture">
                        <jstl:set var="count" scope="application" value="${count + 1}"/>
                        <img src="${picture}" class="tableImg iButton" onclick="currentSlide(${count})">
                    </jstl:forEach>
                </div>


                <!-- Carrusel se fotos  -->
                <div class="carrusel">
                    <div class="slideshow-container" id="carrusel">
                        <a class="prev" onclick="plusSlides(-1)">&#10094;</a>
                        <a class="next" onclick="plusSlides(1)">&#10095;</a>
                        <jstl:forEach items="${item.pictures}" var="picture">
                            <jstl:set var="count" scope="application" value="${count + 1}"/>
                            <div class="mySlides">
                                <a href="${picture}"><img src="${picture}" class="w3-border w3-card-4 marco iButton" style="width: 100%">
                                </a>
                            </div>
                        </jstl:forEach>
                    </div>
                </div>
                <br>
                <jstl:set var="count" scope="application" value="${0}"/>
                <div style="text-align: center" id="punto">
                    <jstl:forEach items="${item.pictures}" var="picture">
                        <jstl:set var="count" scope="application" value="${count + 1}"/>
                        <span class="dot" onclick="currentSlide(${count})"></span>
                    </jstl:forEach>
                </div>
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