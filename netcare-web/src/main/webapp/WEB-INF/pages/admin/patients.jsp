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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ taglib prefix="mvk" uri="http://www.callistasoftware.org/mvk/tags"%>
<%@ taglib prefix="netcare" uri="http://www.callistasoftware.org/netcare/tags"%>

<%@ taglib prefix="hp" tagdir="/WEB-INF/tags"%>

<hp:view>
	<hp:viewHeader>
		<hp:templates />
		<script type="text/javascript">
			$(function() {
				
				var currentPatientId = "<c:out value='${sessionScope.currentPatient.id}' />";
				
				var params = {	
				};
				
				NC_MODULE.PATIENTS.init(params);
				NC_MODULE.PATIENT_FORM.init(params);
				NC_MODULE.PATIENT_SEARCH.init();
			});
		</script>
	</hp:viewHeader>
	<hp:viewBody title="Patienter" plain="true">
	
		<form id="pickPatientForm" action="#" method="post">
			<netcare:modal id="modal-from-dom" confirmCode="admin.menu.patient.pickFromSearch" titleCode="admin.menu.patient.search">
				<spring:message code="admin.menu.patient.searchValue" var="pick" scope="page"/>
				<netcare:field name="pickPatient" label="${pick}">
					<input name="pickPatient" class="xlarge nc-autocomplete" size="30" type="text" />
					<input name="selectedPatient" type="hidden" />
				</netcare:field>
			</netcare:modal>
		</form>
	
		<button id="showCreatePatient" class="btn" style="margin-top: 20px; margin-bottom: 20px;">
			<spring:message code="admin.patients.new" />
		</button>
		
		<a href="#modal-from-dom" role="button" data-toggle="modal" class="btn" style="margin-top: 20px; margin-bottom: 20px">
			<spring:message code="admin.menu.patient.search" />
		</a>
		
		<mvk:sheet id="patientSheet" style="display: none;">
		<form id="patientForm">
			<netcare:row>
				<netcare:col span="6">
					<spring:message code="patient.firstName" var="name" scope="page"/>
					<netcare:field containerId="firstNameContainer" name="firstName" label="${name}">
						<input type="text" id="firstName" name="firstName" class="required" />
					</netcare:field>
				</netcare:col>
				<netcare:col span="6">
					<spring:message code="patient.surName" var="surName" scope="page"/>
					<netcare:field containerId="surNameContainer" name="surName" label="${surName}">
						<input type="text" id="surName" name="surName" class="required" />
					</netcare:field>
				</netcare:col>
			</netcare:row>
			
			<netcare:row>
				<netcare:col span="6">
					<spring:message code="patient.crn" var="cnr" scope="page" />
					<netcare:field containerId="civicRegistrationNumberContainer" name="crn" label="${cnr}">
						<input type="text" id="civicRegistrationNumber" name="civicRegistrationNumber" placeholder="<spring:message code="pattern.crn" />" class="personnummer"/>
						<br />
						<span class="help-block"><small>Exempel: 191212121212</small></span>
					</netcare:field>
				</netcare:col>
				<netcare:col span="6">
					<spring:message code="patient.phoneNumber" var="phoneNumber" scope="page" />
					<netcare:field containerId="phoneNumberContainer" name="phoneNumber" label="${phoneNumber}">
						<input type="text" id="phoneNumber" name="phoneNumber" />
					</netcare:field>
				</netcare:col>
			</netcare:row>
			
			<div class="form-actions">
				<input type="submit" class="btn btn-info" value="<spring:message code="create" />" />
			</div>
			
		</form>
		</mvk:sheet>
	
		<div id="patients">
			<h3 class="title"><spring:message code="admin.patients.list" /></h3>
			<mvk:touch-list id="patientList">
			</mvk:touch-list>
		</div>
	</hp:viewBody>
</hp:view>
