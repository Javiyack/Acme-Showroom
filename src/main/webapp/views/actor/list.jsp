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
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message code="msg.delete.confirmation" var="deleteConfirmation"/>
<jstl:if test="${actors!=null}">
    <div class="seccion w3-light-grey ">

        <legend>
            <spring:message code="${legend}"/>
        </legend>
        <div style="overflow-x: auto;">
            <jstl:if test="${!actors.isEmpty()}">
                <form:form action="${requestUri}" method="GET">
                    <spring:message code="pagination.size"/>
                    <input hidden="true" name="word" value="${word}">
                    <input hidden="true" name="actorId" value="${showroom.id}">
                    <input type="number" name="pageSize" min="1" max="100"
                           value="${pageSize}">
                    <input type="submit" value=">">
                </form:form>
            </jstl:if>
            <display:table pagesize="${pageSize}"
                           class="flat-table flat-table-1 w3-light-grey" name="actors"
                           requestURI="${requestUri}" id="row">
                <jstl:set var="activateUrl"
                          value="actor/administrator/activate.do?actorId=${row.id}&pageSize=${pageSize}"/>
                <jstl:set var="url" value="actor/display.do?actorId=${row.id}"/>
                <acme:column property="${row.userAccount.username}"
                             title="label.user" sortable="true" rowUrl="${url}" css="iButton"/>
                <acme:column property="${row.surname}, ${row.name}"
                             title="label.name" sortable="true" rowUrl="${url}"/>
                <jstl:if test="${legend eq 'label.users'}">
                    <acme:column property="birthdate" title="label.birthdate"
                                 sortable="true" format="date.format"/>
                    <acme:column property="genere" title="label.genere" sortable="true"/>
                </jstl:if>
                <jstl:if
                        test="${legend eq 'label.agents' or legend eq 'label.auditors'}">
                    <acme:column property="company" title="label.company"
                                 sortable="true"/>
                </jstl:if>
                <jstl:if
                        test="${legend eq 'label.all.accounts'}">
                    <acme:column property="userAccount.authorities.iterator.next" title="label.authority"
                                 sortable="true"/>
                </jstl:if>
                <jstl:if test="${!userIsFollowedMap[row]}">
                    <acme:column property=" " title="label.chirp.subscription" sortable="true"
                                 rowUrl="subscription/actor/subcribe.do?actorId=${row.id}"
                                 icon="fa fa-check w3-xlarge w3-text-gray css-uncheck"/>
                </jstl:if>
                <jstl:if test="${userIsFollowedMap[row]}">
                    <acme:column property=" " title="label.chirp.subscription" sortable="true"
                                 rowUrl="subscription/actor/subcribe.do?actorId=${row.id}"
                                 icon="fa fa-check w3-xlarge w3-text-green"/>
                </jstl:if>


            </display:table>
        </div>
    </div>
</jstl:if>
<jstl:if test="${userIsFollowedMap!=null && userIsFollowedMap.size!=0}">
    <spring:message code="date.pattern" var="datePattern"/>

    <div class="seccion w3-light-grey ">
        <legend>
            <spring:message code="label.chirp.subscription"/>
        </legend>
        <div style="overflow-x: auto;">


            <table class="flat-table flat-table-1 w3-light-grey">
                <tr>
                    <th><spring:message code="label.user"/></th>
                    <th><spring:message code="label.name"/></th>
                    <th><spring:message code="label.chirp.subscription"/></th>
                </tr>

                <jstl:forEach items="${userIsFollowedMap}" var="entry">
                    <jstl:if test="${entry.value}">
                        <tr onclick="relativeRedir('actor/display.do?actorId=${entry.key.id}')" class="iButton">
                            <td>${entry.key.userAccount.username}</td>
                            <td>${entry.key.surname},${entry.key.name}</td>
                            <td><a href="subscription/actor/subcribe.do?actorId=${entry.key.id}">
                                <i class="fa fa-check w3-xlarge w3-text-green"></i>
                            </a></td>
                        </tr>
                    </jstl:if>
                </jstl:forEach>
            </table>
        </div>
    </div>
</jstl:if>
<jstl:out value="${userIsFollowerMap}"/>

<security:authorize access="hasRole('USER')">
    <jstl:if test="${followers!=null}">
        <div class="seccion w3-light-grey ">
            <legend>
                <spring:message code="label.followers"/>
            </legend>
            <div style="overflow-x: auto;">
                <jstl:if test="${!followers.isEmpty()}">
                    <form:form action="${requestUri}" method="GET">
                        <spring:message code="pagination.size"/>
                        <input hidden="true" name="word" value="${word}">
                        <input hidden="true" name="actorId" value="${showroom.id}">
                        <input type="number" name="pageSize" min="1" max="100"
                               value="${pageSize}">
                        <input type="submit" value=">">
                    </form:form>
                </jstl:if>
                <display:table pagesize="${pageSize}"
                               class="flat-table flat-table-1 w3-light-grey" name="followers"
                               requestURI="${requestUri}" id="row">
                    <jstl:set var="activateUrl"
                              value="actor/administrator/activate.do?actorId=${row.id}&pageSize=${pageSize}"/>
                    <jstl:set var="url" value="actor/display.do?actorId=${row.id}"/>
                    <acme:column property="${row.userAccount.username}"
                                 title="label.user" sortable="true" rowUrl="${url}"/>
                    <acme:column property="${row.surname}, ${row.name}"
                                 title="label.name" sortable="true" rowUrl="${url}"/>
                    <acme:column property=" " title="label.view" sortable="true"
                                 rowUrl="${url}" icon="fa fa-eye w3-xlarge"/>
                </display:table>

            </div>
        </div>
    </jstl:if>
</security:authorize>


<div class="seccion w3-light-grey">
    <div class="row">
        <div class="col-50">
            <security:authorize access="hasRole('USER')">
                <acme:button url="/subscription/actor/subscribers/list.do" text="label.subscribers"
                             css="formButton toLeft w3-padding"/>
                <acme:button url="/subscription/actor/list.do" text="label.subscriptions"
                             css="formButton toLeft w3-padding"/>
            </security:authorize>
            <acme:backButton text="actor.back" css="formButton toLeft w3-padding"/>
        </div>
    </div>
</div>
