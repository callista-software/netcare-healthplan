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


<h3><spring:message code="admin.menu.title" /></h3>
<ul>
	<li>
		<a href="<spring:url value="/netcare/nation-admin/units" />">
			<spring:message code="admin.menu.units" />
		</a>
	</li>
	<li>
		<netcare:image name="new-category" size="16" />
		<a href="<spring:url value="/netcare/nation-admin/categories" />">
			<spring:message code="admin.menu.categories" />
		</a>
	</li>
	<li>
		<a href="<c:url value="/netcare/nation-admin/careunits" />">Vårdenheter</a>
	</li>
</ul>
