<%--

    Copyright (C) 2011,2012 Callista Enterprise AB <info@callistaenterprise.se>

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
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="mvk" uri="http://www.callistasoftware.org/mvk/tags"%>

<%@ taglib prefix="mvk" uri="http://www.callistasoftware.org/mvk/tags"%>
<%@ taglib prefix="netcare" uri="http://www.callistasoftware.org/netcare/tags"%>

<%@ taglib prefix="hp" tagdir="/WEB-INF/tags"%>

<hp:view>
	<hp:viewHeader>
		<script type="text/javascript">
			$(function() {
				var util = new NC.Util();
				var support = new NC.Support();
				
				/*
				 * Are we a patient or are a care giver
				 */
				var isPatient = util.isPatient('<c:out value="${sessionScope.currentPatient}" />');
				
				/*
				 * Disable the weekdays as well as its
				 * add icon
				 */
				var disableWeekdays = function() {
					$('input[name $= "TimeField"]').attr('disabled', 'disabled');
					$('input[name $= "TimeField"]').next().hide();
				};
				
				/*
				 * Method that will completely reset the
				 * new schedule activity form
				 */ 
				var resetForm = function() {
					NC.log("Disabling weekdays");
					disableWeekdays();
					
					NC.log("Remove added times");
					var rootId = 'div[id$=AddedTimes]';
					$(rootId + ' div').remove();
					$(rootId).hide();
				};
				
				/*
				 * Disable time input field as well as "add" icon next to it
				 */
				disableWeekdays();
				
				/*
				 * If the user clicks a day checkbox, enable that time field
				 */
				var onDayClick = function(day, enable) {
					var value = day + 'TimeField';
					var elem = $('input[name="'+ value +'"]');
					var icon = elem.next();
					
					if (enable == "checked") {
						// Enable add time field
						elem.removeAttr('disabled');
						elem.focus();
						
						// Show icon
						icon.show();
					} else {
						elem.prop('disabled', 'disabled');
						icon.hide();
					}
				};
				 
				$('input[name="day"]').click(function(event){
					onDayClick($(this).val(), $(this).attr('checked'));
				});
				
				/*
				 * When the user presses enter or on the add icon, get the value of the
				 * time field and print it together with a delete icon
				 */
				var addTime = function(timeField, text) {
					
					if (timeField.val().length != 5) {
						timeField.parent().parent().removeClass('success').addClass('error');
						return;
					} else {
						timeField.parent().parent().removeClass('error').addClass('success');
					}
					
					// Find added times div
					var inputName = timeField.attr('name');
					
					// Rip off the 'TimeField' to get the day
					var day = inputName.slice(0, inputName.length - 9);
					
					// Add time
					var elem = $('#' + day + 'AddedTimes');
					
					var addedTimeContainer = $('<div>').css('padding-right', '10px').css('float', 'left');
					var addedTime = $('<span>' + text + '</span>').css('margin-right', '3px');
					var removeTime = util.createIcon('trash', 16, function() {
						NC.log("Delete time");
						addedTimeContainer.remove();
						
						var timeCount = $('#' + day + 'AddedTimes span').size();
						NC.log("Times count: " + timeCount);
						
						// Hide if no more times
						if (timeCount == 0) {
							NC.log("No more times, hide container");
							elem.hide();
							
							timeField.parent().parent().removeClass('error success');
							
							$('input[value="' + day + '"]').click();
							onDayClick(day, "unchecked");
						}
					});
					
					addedTimeContainer.append(addedTime);
					addedTimeContainer.append(removeTime);
					
					elem.append(addedTimeContainer);
					elem.show();
					
					timeField.val('');
					timeField.focus();
				};
				
				/*
				 * Bind all time fields as validated time fields.
				 * The function we pass is executed when the user presses enter
				 * in the field
				 */
				$.each($('input[name$="TimeField"]'), function(index, value) {
					util.validateTimeField($(value), function(text) {
						addTime($(value), text);
					});
				});
				
				/*
				 * Add the time when user presses the image icon
				 */
				$('img[src*="add"]').click(function(event) {
					NC.log("Icon clicked");
					var timeField = $(this).parent().children('input[name$="TimeField"]');
					addTime(timeField, timeField.val());
				});
				
				/*
				 * Start by loading existing activites
				 * and fill the table
				 */
				var healthPlan = <c:out value="${requestScope.result.data.id}" />;
				var sd = '<c:out value="${requestScope.result.data.startDate}" />';
				var hp = new NC.HealthPlan();
				
				/*
				 * Set the default start date to health plans
				 * start date
				 */
				NC.log('Setting start date to: ' + sd);
				$('input[name="startDate"]').datepicker('setDate', sd);
				
				hp.listActivities(healthPlan, 'activitiesTable', isPatient);
				
				
				var addMeasureValueInput = function(id, rowCol, label, value) {
					
					var input = $('<input>').attr('type', 'number').attr('step', '1').attr('name', id).attr('id', id).addClass('input-medium');
					util.validateNumericField(input, 6);
					var div = util.createInputField(label, '(' + value.unit.value + ')', input);
					
					rowCol.append( $('<div>').addClass('span5').append(div) );
				};
				
				var types = NC.ActivityTypes();
				var activityTypes = new Array();
				
				/*
				 * Fill activity types in drop down
				 */ 
				var cUnit = '<c:out value="${requestScope.result.data.careUnit.hsaId}" />';
				types.load(cUnit, function(data) {
					$.each(data.data, function(i, v) {
						activityTypes.push(v);
						$('select[name="activityType"]').append(
							$('<option>').attr('value', v.id).html(v.name)		
						);	
					});
					
					/* Trigger change when page loads */
					$('select[name="activityType"]').change();
				});
				
				/*
				 * When user selects an activity type
				 */
				var selectActivityType = function(id, itemData) {
					$('input[name="activityTypeId"]').attr('value', id);
					$('#measureValues').remove();
					
					var minValue = '';
					var maxValue = '';
					var value = '';
					var title = '';
					new NC.Support().loadCaptions('result', ['targetMinValue', 'targetMaxValue', 'value', 'title'], function(data) {
						minValue = data.targetMinValue;
						maxValue = data.targetMaxValue;
						value = data.value;
					});
					
					/*
					 * Loop through all measure values
					 */
					var data = itemData;
					NC.log("Selected data is: " + data);
					
					var fieldset = $('<fieldset>').attr('id', 'measureValues');
					fieldset.append(
						$('<legend>').html(title)
					);
					
					$.each(data.measureValues, function(i, v) {
						NC.log("Processing " + v.name);
						
						var row = $('<div>').addClass('row-fluid').css('background', '#FDF5D9');
						
						row.append(
							$('<div>').addClass('span2').append(
								$('<div>').addClass('clearfix').append(
									$('<label>').html('&nbsp')
								).append(
									$('<span>').css('vertical-align', 'middle').append($('<strong>').html(v.name)) 
								)
							)
						);
						
						if (v.valueType.code == "INTERVAL") {
							addMeasureValueInput(v.id + '-1', row, minValue, v);
							addMeasureValueInput(v.id + '-2', row, maxValue, v);
						} else if (v.valueType.code == "SINGLE_VALUE") {
							addMeasureValueInput(v.id + '-1', row, value, v);
						} else {
							throw new Error("Unsupported value type");
						}
						
						fieldset.append(row);
						
					});
					
					$('#activityFieldset').append(fieldset);
					
					/*
					 * Move focus to the first measure value
					 */
					$('#measureValues input').get(0).focus();
				};
				
				/*
				 * The user selected an activity type. Update to reflect measure values etc
				 */
				$('select').change(function(e) {
					var value = $('select[name="activityType"] option:selected').attr('value');
					var obj = new Object();
					$.each(activityTypes, function(i, v) {
						if (v.id == value) {
							obj = v;
						}
					});
					
					selectActivityType(value, obj);
				});
				
				/*
				 * Bind the form submission and package what is going
				 * to be sent to the server as a JSON object
				 */
				$('#activityForm :submit').click(function(event) {
					NC.log("Form submission...");
					event.preventDefault();
					
					var activityType = $('select[name="activityType"] option:selected').prop('value');
					var goal = $('#activityForm input[name="activityGoal"]').val();
					var startDate = $('input[name="startDate"]').val();
					var activityRepeat = $('input[name="activityRepeat"]').val();
					
					var dayTimes = new Array();
					$.each($('#activityForm input[name="day"]:checked'), function(index, value) {
						var dayTime = new Object();
						dayTime.times = new Array();
						dayTime.day = $(value).attr('value');
						
						NC.log("Processing: " + dayTime.day);
						
						// Pick all times, if not times exist for a day. Just skip
						if ($('#' + dayTime.day + 'AddedTimes span').size() != 0) {
							$.each($('#' + dayTime.day + 'AddedTimes span'), function(index, value) {
								NC.log($(value).html());
								dayTime.times[index] = $(value).html();
							});
							
							dayTimes.push(dayTime);	
						}
					});
					
					var at = new Object();
					at.id = activityType;
					
					var measureValues = new Array();
					var processed = 0;
					$.each($('#measureValues input'), function(i, v) {
						var attr = $(v).attr('id');
						var id = attr.split("-")[0];
						
						NC.log("Checking " + id + " with " + processed + " to determine whether to process...");
						if (id != processed) {
							var measure = new Object();
							measure.measurementType = new Object();
							measure.measurementType.id = id;
							
							var value1 = $(v).val();
							var value2 = $('#measureValues input[id="'+ id +'-2"]').val();
							if (typeof value2 === 'undefined') {
								measure.target = value1;
							} else {
								measure.minTarget = value1;
								measure.maxTarget = value2;
							} 
							
							measureValues.push(measure);
							processed = id;
						}
					});
					
					
					var activity = new Object();
					activity.goalValues = measureValues;
					activity.type = at;
					activity.startDate = startDate;
					activity.dayTimes = dayTimes;
					activity.activityRepeat = activityRepeat;
					
					if (!isPatient) {
						activity.publicDefinition = true;
					} else {
						if ($('#publicDefinition').attr('checked') == "checked") {
							activity.publicDefinition = true;
						} else {
							activity.publicDefinition = false;
						}
					}
					
					var hp = new NC.HealthPlan();
					hp.addActivity(healthPlan, activity, function(data) {
						NC.log("Success callback is executing...");
						NC.log("Resetting form");
						
						$('button:reset').click();
						
						resetForm();
						
						hp.listActivities(healthPlan, 'activitiesTable', isPatient)
						
					});
					
					$('#activityForm').hide();
					
				});
				
				$(':reset').click(function(e) {
					resetForm();
				});
				
				$('#showActivityForm').click(function(event) {
					NC.log("Displaying new activity form");
					$('#activityForm').toggle();
					
					if (isPatient != true) {
						$('#publicDefinitionContainer').hide();
					}
				});
				
				$('#activityForm').hide();
				
				/*
				 * Destroy update-goal-value modal
				 * on close
				 */
				$('#update-goal-values').on('hidden', function() {
					$(this).find('.btn-info').unbind('click');
					$(this).find('h3').html('<spring:message code="activity.update" />');
					$(this).find('.modal-body').empty();
				});
			});
		</script>
	</hp:viewHeader>
	<hp:viewBody title="Resultat">
		<c:set var="healthPlanName" value="${requestScope.result.data.name}" scope="page"/>
		<spring:message code="activity.new" var="title" scope="page" />
	
		<h2><c:out value="${healthPlanName}" /> : <spring:message code="activity.title" /></h2>
		<p>
			<span class="label label-info"><spring:message code="information" /></span>
			<spring:message code="activity.desc" arguments="${healthPlanName},${title}" />
		</p>
		
		<p style="text-align: right; padding-right: 20px">
			<a id="showActivityForm" class="btn addButton"><c:out value="${title}" /></a>
		</p>
		
		<form id="activityForm" action="#" method="post">
			<fieldset id="activityFieldset">
				<legend><spring:message code="activity.form.nameAndGoal" /></legend>
				<netcare:row>
					<netcare:col span="6">
						<spring:message code="activity.form.type" var="what" scope="page" />
						<netcare:field name="activityType" label="${what}">
							<select name="activityType" class="input-medium"></select>
							<sec:authorize access="hasRole('CARE_ACTOR')">
								<p><a href="<c:url value="/netcare/admin/activitytypes" />"><spring:message code="activity.form.addType" /></a></p>
							</sec:authorize>
						</netcare:field>
					</netcare:col>
				</netcare:row>
			</fieldset>
			
			<fieldset id="scheduleFieldset">
				<netcare:row>
					<netcare:col span="6">
						<spring:message code="startDate" var="start" scope="page" />
						<netcare:field containerId="startDate" name="startDate" label="${start}">
							<netcare:dateInput name="startDate" />
						</netcare:field>
					</netcare:col>
					<netcare:col span="6">
						<spring:message code="repeatSchedule" var="repeat" scope="page"/>
						<netcare:field name="activityRepeat" label="${repeat}">
							<input name="activityRepeat" type="number" value="1"/>
							<span><spring:message code="week" /></span>
						</netcare:field>
					</netcare:col>
				</netcare:row>
			</fieldset>
			
			
			<spring:message code="activity.form.time" var="addTime" scope="page" />
			
			<fieldset>
				<legend><spring:message code="activity.form.specifyTimes" /></legend>

				<hp:timeContainer name="monday" />
				<hp:timeContainer name="tuesday" />
				<hp:timeContainer name="wednesday" />
				<hp:timeContainer name="thursday" />
				<hp:timeContainer name="friday" />
				<hp:timeContainer name="saturday" />
				<hp:timeContainer name="sunday" />
				
			</fieldset>
			
			<netcare:row id="publicDefinitionContainer">
				<netcare:col span="6">
					<spring:message code="activity.form.permission" var="permission" scope="page" />
					<netcare:field name="publicDefinition" label="${permission}">
						<input type="checkbox" name="publicDefinition" value="true" checked="checked"/>
					</netcare:field>
				</netcare:col>
			</netcare:row>
			
			<div class="form-actions">
				<button type="submit" class="btn info"><spring:message code="activity.form.submit" /></button>
				<button type="reset" class="btn"><spring:message code="clear" /></button>
			</div>
		
		</form>
		
		<div id="activityContainer">
			<netcare:block-message type="info" style="display:none">
				<spring:message code="activity.none" />
			</netcare:block-message>
			<netcare:table id="activitiesTable">
				<thead>
					<tr>
						<th><spring:message code="activity.type" /></th>
						<th><spring:message code="activity.category" /></th>
						<th><spring:message code="activity.start" /></th>
						<th><spring:message code="activity.frequency" /></th>
						<th>&nbsp;</th>
					</tr>
				</thead>
				<tbody></tbody>
			</netcare:table>
		</div>
		
		<netcare:modal titleCode="activity.update" confirmCode="label.update" id="update-goal-values">
			<p>
				<span class="label label-info"><spring:message code="label.information" /></span>
				<spring:message code="activity.update.desc" />
			</p>
		</netcare:modal>
	</hp:viewBody>
</hp:view>