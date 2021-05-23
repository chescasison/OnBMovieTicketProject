<%@ tag language="java" pageEncoding="UTF-8" isELIgnored="false" trimDirectiveWhitespaces="true" body-content="empty" %>
<%@ tag import="java.time.LocalTime" %>
<%@ tag import="java.time.format.DateTimeFormatter" %>
<%@ tag import="java.time.format.FormatStyle" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="pattern" type="java.lang.String" required="true" rtexprvalue="true" description="Format pattern" %>
<%@ attribute name="value" type="java.time.LocalTime" required="false" rtexprvalue="true" description="(optional) A LocalTime value. Defaults to LocalTime.now()." %>
<%@ attribute name="var" type="java.lang.String" required="false" rtexprvalue="false" description="(optional) Output variable" %>
<%
	// Sorry for mixing JSTL with scriptlets, but scriptlets
	// were the fastest way to access static methods. Using
	// Spring's <eval> custom tag is slower due to reflection.
	if (value == null) {
		value = LocalTime.now();
	}
	String formattedValue;
	try {
		FormatStyle formatStyle = FormatStyle.valueOf(pattern);
		// FULL, and LONG, require ZoneId
		formattedValue = DateTimeFormatter.ofLocalizedTime(formatStyle).format(value);
	} catch (IllegalArgumentException ex) {
		formattedValue = value.format(DateTimeFormatter.ofPattern(pattern)); 
	}
%>
<c:choose>
    <c:when test="${not empty var}">
        <% request.setAttribute(var, formattedValue); %>
    </c:when>
    <c:otherwise>
        <% jspContext.setAttribute("formattedValue", formattedValue); %>
		<c:out value="${formattedValue}" />
    </c:otherwise>
</c:choose>
