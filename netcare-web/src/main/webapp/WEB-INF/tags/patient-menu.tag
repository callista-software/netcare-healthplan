<%--

    Copyright (C) 2011,2012 Landstinget i Joenkoepings laen <http://www.lj.se/minhalsoplan>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program. If not, see <http://www.gnu.org/licenses/>.

--%>
<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ taglib prefix="netcare" uri="http://www.callistasoftware.org/netcare/tags"%>

<c:url value="/netcare/user/home" var="userHome" scope="page" />

<ul id="permanent">
	<li>
		<a href="<spring:url value="/netcare/home" />">
			<span class="icon start"></span>
			<span class="iconLabel">Min hälsoplan</span>
		</a>
	</li>
	<li>
		<a class="profile" href="<spring:url value="/netcare/user/profile" />">
			<span class="icon profile"></span>
			<span class="iconLabel"><spring:message code="phome.profile" /></span>
		</a>
	</li>
	<li>
		<a href="<spring:url value="/netcare/user/report" />">
			<span class="icon report"></span>
			<span class="iconLabel"><spring:message code="patient.menu.report" /></span>
		</a>
	</li>
	<li>
		<a href="<spring:url value="/netcare/shared/select-results" />">
			<span class="icon results"></span>
			<span class="iconLabel"><spring:message code="patient.menu.results" /></span>
		</a>
	</li>
</ul>
