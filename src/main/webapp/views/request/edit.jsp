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
                              readonly="${true}"/>
            </div>
            <div class="col-50">
                <acme:textbox code="label.status" path="status"
                              readonly="true"/>
            </div>
        </div>
        <hr>
        <div class="row">
            <div class="col-50">
                <h3>Billing Address</h3>
                <label for="fname"><i class="fa fa-user"></i> Name</label>
                <input type="text" id="fname" name="firstname" placeholder="John M." value="${request.user.name}" readonly>
                <label for="fname"><i class="fa fa-user"></i> Surname</label>
                <input type="text" id="fname" name="surname" placeholder="Doe" value="${request.user.surname}" readonly>
                <label for="email"><i class="fa fa-envelope"></i> Email</label>
                <input type="text" id="email" name="email" placeholder="john@example.com" value="${request.user.email}" readonly>
                <label for="adr"><i class="fa fa-address-card-o"></i> Address</label>
                <input type="text" id="adr" name="address" placeholder="542 W. 15th Street"
                       value="${request.user.address}" readonly>
                <label for="phone"><i class="fa fa-phone"></i> Phone</label>
                <input type="text" id="phone" name="phone" placeholder="666666666" value="${request.user.phone}" readonly>

            </div>
            <div class="col-50">
                <h3>Payment</h3>
                <label for="fname">Accepted Cards</label>
                <div class="icons-container">
                    <i class="fa fa-cc-visa" style="color:navy;"></i>
                    <i class="fa fa-cc-amex" style="color:blue;"></i>
                    <i class="fa fa-cc-mastercard" style="color:red;"></i>
                    <i class="fa fa-cc-discover" style="color:orange;"></i>
                </div>
                <label for="cname">Brand Name</label>
                <input type="text" id="cname" name="creditCard.brandName" placeholder="John More Doe" path="creditCard.brandName"
                       <jstl:if test="${!creditCardNeeded}">readonly</jstl:if> value="${request.creditCard.brandName}" >
                <form:errors path="creditCard.brandName" cssClass="error" /><br>
                <label for="cname">Name on Card</label>
                <input type="text" id="cname" name="creditCard.holderName" value="${request.creditCard.holderName}"
                       placeholder="John More Doe" <jstl:if test="${!creditCardNeeded}">readonly</jstl:if>>
                <form:errors path="creditCard.holderName" cssClass="errorCC" /><br>
                <label for="ccnum">Credit card number</label>
                <input type="text" id="ccnum" name="creditCard.cardNumber" value="${request.creditCard.cardNumber}" placeholder="1111-2222-3333-4444" <jstl:if test="${!creditCardNeeded}">readonly</jstl:if>>
                <form:errors path="creditCard.cardNumber" cssClass="errorCC" /><br>
                <div class="row">
                    <div class="col-33">
                        <label for="expmonth">Exp Month</label>
                        <input type="text" id="expmonth" name="creditCard.expirationMonth" value="${request.creditCard.expirationMonth}" placeholder="September" <jstl:if test="${!creditCardNeeded}">readonly</jstl:if>">
                        <form:errors path="creditCard.expirationMonth" cssClass="errorCC" /><br>
                    </div>
                    <div class="col-33">
                        <label for="expyear">Exp Year</label>
                        <input type="text" id="expyear" name="creditCard.expirationYear" value="${request.creditCard.expirationYear}" placeholder="2018" <jstl:if test="${!creditCardNeeded}">readonly</jstl:if>>
                        <form:errors path="creditCard.expirationYear" cssClass="errorCC" /><br>
                    </div>
                    <div class="col-33">
                        <label for="cvv">CVV</label>
                        <input type="text" id="cvv" name="creditCard.CVV" value="${request.creditCard.CVV}" placeholder="352" <jstl:if test="${!creditCardNeeded}">readonly</jstl:if>>
                        <form:errors path="creditCard.CVV" cssClass="errorCC" /><br>
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