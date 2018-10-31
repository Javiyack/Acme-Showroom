<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
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
    <security:authentication property="principal" var="logedActor"/>
    <security:authentication property="principal.authorities[0]"
                             var="permiso"/>
    <jstl:set var="rol" value="${fn:toLowerCase(permiso)}"/>
</security:authorize>
<jstl:if test="${pageSize == null}">
    <jstl:set value="20" var="pageSize"/>
</jstl:if>
<jstl:if test="${actorForm != null}">
    <jstl:set value="actor/display.do" var="requestUri"/>
</jstl:if>
<div class="seccion w3-light-grey">
    <legend>
        <spring:message code="label.showrooms"/>
    </legend>

    <jstl:if test="${!showrooms.isEmpty()}">
        <form:form action="${requestUri}" method="POST">

            <div class="row">
                <div class="col-75">
                    <spring:message code="pagination.size"/>
                    <input type="number" name="pageSize" min="1" max="100"
                           value="${pageSize}">
                    <input type="submit" value=">">
                </div>
                <div class="col-25">
                    <spring:message code="label.search" var="placeholder"/>
                    <input name="word" value="${word}" placeholder="&#xf002; ${placeholder}"
                           class="font-awesome toRight">
                </div>

            </div>
        </form:form>
    </jstl:if>
    <div class="row">
        <div class="col-100">
            <div style="overflow-x:auto;">
                <display:table pagesize="${pageSize}"
                               class="flat-table flat-table-1 w3-light-grey" name="showrooms"
                               requestURI="${requestUri}" id="row">
                    <jstl:set var="owns" value="${logedActor.id == row.user.userAccount.id}"/>
                    <jstl:set value="showroom/display.do?showroomId=${row.id}" var="rowUrl"/>
                    <jstl:if test="${owns}">
                        <jstl:set value="showroom/user/edit.do?showroomId=${row.id}" var="rowUrl"/>
                    </jstl:if>
                    <acme:column property="${row.name}" title="label.name" sortable="true" rowUrl="${rowUrl}"/>
                    <acme:column property="${row.description}" title="label.description" sortable="true"
                                 rowUrl="${rowUrl}"/>
                    <jstl:if test="${owns}">
                        <acme:column property=" " title="label.none" sortable="true"
                                     icon="fa fa-edit w3-xlarge" rowUrl="${rowUrl}"/>
                    </jstl:if>
                    <jstl:if test="${!owns}">
                        <acme:column property=" " title="label.none" sortable="true"
                                     icon="fa fa-eye w3-xlarge" rowUrl="${rowUrl}"/>
                    </jstl:if>

                </display:table>
            </div>
        </div>
    </div>

    <jstl:if test="${logedActor != null}">
        <jstl:if test="${rol == 'user'}">
            <p>
                <i class="w3-bar-item fa fa fa-edit w3-xxxlarge w3-text-dark-grey w3-hover-text-light-blue iButton"
                   onclick="relativeRedir('showroom/user/create.do');"></i>
            </p>
        </jstl:if>
    </jstl:if>


</div>