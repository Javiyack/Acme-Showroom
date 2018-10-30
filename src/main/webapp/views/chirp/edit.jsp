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
          value="${logedActor.id == chirp.actor.userAccount.id}"/>

<jstl:set var="readonly"
          value="${(display || !owns || !edition) && chirp.id != 0}"/>

<jstl:set value="chirp/user/list.do" var="backUrl"/>
<jstl:if test="${owns}">
    <jstl:set value="chirp/user/edit.do?chirpId=${chirp.id}" var="backUrl"/>
</jstl:if>
<div class="seccion w3-light-grey">
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
                <acme:textbox code="label.none" path="moment"
                              readonly="true" css="flat"/>
                <spring:message code='label.title' var="tituloLabel"/>
                <acme:textbox code="label.none" path="title"
                              readonly="${readonly}" placeholder="${tituloLabel}"/>

                <input type="text" placeholder="&#xf02b; Topic" class="font-awesome"
                       onclick="document.getElementById('id01').style.display='block'" path="topic"
                       name="topic" id="topic"/>
                <div id="id01" class="w3-modal w3-animate-opacity">
                    <div class="w3-modal-content w3-card-4">
                        <header class="w3-container">
                            <span onclick="document.getElementById('id01').style.display='none'"
                                  class="w3-button w3-large w3-display-topright">&times;</span>
                            <h2>Modal Header</h2>
                        </header>
                        <div class="w3-container">
                            <br>
                            <p>
                                <input type="text" placeholder="&#xf02b; Topic" class="font-awesome" />
                            </p>
                            <p><acme:select items="${topics}" code="label.topics"
                                            itemLabel="name" path="topic" css="formSelect" id="combo"
                                            onchange="javascript:document.getElementById('id01').style.display='none';setTopic(this, 'topic');"/></p>
                        </div>
                        <footer class="w3-container">
                                <i class="w3-bar-item fa fa-plus-square w3-xlarge toRight w3-padding"
                                   onclick="ajaxTopicCreate(this, '${pageContext.request.contextPath}');document.getElementById('id01').style.display='none';"
                                   onmouseenter="overEffect(this);" onmouseleave="overEffect(this);"></i>
                            <br>
                        </footer>
                    </div>
                </div>
            </div>
            <div class="col-75">
                <spring:message code='label.description' var="descriptionLabel"/>
                <acme:textarea code="label.none" path="description"
                               readonly="${readonly}" css="formTextArea w3-text-black"
                                placeholder="${descriptionLabel}"/>
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