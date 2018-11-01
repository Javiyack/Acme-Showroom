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
    <security:authentication property="principal" var="logedAccount"/>
    <security:authentication property="principal.authorities[0]"
                             var="permiso"/>
    <jstl:set var="rol" value="${fn:toLowerCase(permiso)}"/>
</security:authorize>

<jstl:set var="readonly"
          value="${(display || !owns) && request.id != 0}"/>

<jstl:set var="creditCardNeeded"
          value="${request.item.price>0}"/>


<div class="seccion w3-light-grey">
    <form:form action="${requestUri}" modelAttribute="request">

        <form:hidden path="id"/>
        <form:hidden path="version"/>
        <form:hidden path="item.id"/>
        <form:hidden path="user.id"/>
        <div class="row">
            <div class="col-50">
                <acme:textbox code="label.item" path="item.title"
                              readonly="${true}"/>
            </div>
            <div class="col-25">
                <acme:textbox code="label.price" path="item.price"
                              readonly="true"/>
            </div>
            <div class="col-25">
                <acme:textbox code="label.moment" path="moment"
                              readonly="true" css="flat"/>
            </div>
        </div>
        <div class="row">
            <div class="col-50">
                <acme:textbox code="label.description" path="item.description"
                              readonly="true"/>
            </div>
            <div class="col-50">
                <acme:textbox code="label.status" path="status"
                              readonly="true"/>
            </div>
        </div>
        <hr>
        <div class="row">
            <div class="col-50">
                <h3><spring:message code="label.billing"/></h3>
                <acme:textbox code="label.name" path="user.name" readonly="true" icon="fa fa-user" placeholder=""/>
                <acme:textbox code="label.surname" path="user.surname" readonly="true" icon="fa fa-user"/>
                <acme:textbox code="label.email" path="user.email" readonly="true" icon="fa fa-envelope"/>
                <acme:textbox code="label.address" path="user.address" readonly="true" icon="fa fa-address-card-o"/>
                <acme:textbox code="label.phone" path="user.phone" readonly="true" icon="fa fa-phone"/>

            </div>
            <div class="col-50">
                <div class="icons-container">
                    <h3><spring:message code="label.payment"/>   <i class="fa fa-cc-visa" style="color:navy;"></i>
                        <i class="fa fa-cc-amex" style="color:blue;"></i>
                        <i class="fa fa-cc-mastercard" style="color:red;"></i>
                        <i class="fa fa-cc-discover" style="color:orange;"></i></h3>

                </div>
                <acme:textbox code="label.cc.brandName" path="creditCard.brandName" readonly="${!creditCardNeeded}"/>
                <acme:textbox code="label.cc.holderName" path="creditCard.holderName" readonly="${!creditCardNeeded}"/>
                <acme:textbox code="label.cc.cardNumber" path="creditCard.cardNumber" readonly="${!creditCardNeeded}"
                              placeholder="1111-2222-3333-4444"/>
                <div class="row">
                    <div class="col-33">
                        <acme:textbox code="label.cc.expirationMonth" path="creditCard.expirationMonth"
                                      readonly="${!creditCardNeeded}" placeholder="01-12"/>
                    </div>
                    <div class="col-33">
                        <acme:textbox code="label.cc.expirationYear" path="creditCard.expirationYear"
                                      readonly="${!creditCardNeeded}" placeholder="00-99"/>
                    </div>
                    <div class="col-33">
                        <acme:textbox code="label.cc.CVV" path="creditCard.CVV" readonly="${!creditCardNeeded}"
                                      placeholder="000-999"/>
                    </div>
                </div>
            </div>

        </div>


        <div class="row">
            <div class="col-100">
                <jstl:if test="${!readonly}">
                    <hr>
                    <acme:submit name="save" code="label.save"
                                 css="btn formButton"/>
                </jstl:if>
            </div>

        </div>

    </form:form>

</div>