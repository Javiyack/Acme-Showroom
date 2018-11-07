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

<div class="seccion w3-light-grey">
    <legend>
        <spring:message code="dashboard.administrator"/>
    </legend>
    <!-- Dashboard C1 Showrooms Per User-->
    <h3>
        <spring:message code="dashboard.best.selling.services"/>
    </h3>
    <spring:message code="dashboard.administrator" var="tituloC1"/>
    <jstl:set var="dataC1" value="["/>
    <jstl:forEach items="${showroomsPerUser}" var="data">
        <spring:message code="label.${data.key}" var="label"/>
        <jstl:set var="dataC1" value="${dataC1}{y: ${data.value}, label: '${label}'},"/>
    </jstl:forEach>
    <jstl:set var="dataC1" value="${dataC1}]"/>
    <jstl:out value="${dataC1}"/>
    <div id="ShowroomsPerUser" style="height: 370px; width: 100%;" class="w3-border w3-card-4 marco"></div>
</div>

<script>
    window.onload = function () {

        var chart = new CanvasJS.Chart("ShowroomsPerUser", {
            animationEnabled: true,
            theme: "dark2", // "light1", "light2", "dark1", "dark2"
            title: {
                text: "${tituloC1}"
            },
            data: [{
                type: "column",
                startAngle: 270,
                yValueFormatString: "##0.00\"\"",
                indexLabel: "{label} {y}",
                dataPoints: ${dataC1}
            }]
        });
        chart.render();
    }
</script>

