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

<%@ taglib prefix="netcare" tagdir="/WEB-INF/tags" %>

<netcare:page>
	<netcare:header>
		<script type="text/javascript">
		
			NC.log("Load google visualization package...");
			google.load('visualization', '1.0', {'packages' : ['corechart']});
			NC.log("done.");
		
			$(function() {
				var drawOverview = function() {
					
					NC.log("Drawing overview...");
					
					var captions = null;
					new NC.Support().loadCaptions('result', ['activityType', 'numberOfActivities', 'date', 'reportedValue', 'targetValue'], function(data) {
						NC.log("Load captions returned success...");
						captions = data;
					});
					
					NC.log("Captions are: " + captions);
					
					var hp = new NC.HealthPlan();
					var statisticsCallback = function(data) {
						var arr = new Array();
						
						$.each(data.data.activities, function(index, value) {
							var item = new Array();
							item[0] = value.name;
							item[1] = value.count;
							
							arr.push(item);
						});
						
						var dataOverview = new google.visualization.DataTable();
						dataOverview.addColumn('string',  captions.activityType);
						dataOverview.addColumn('number', captions.numberOfActivities);
						
						NC.log("Add rows: " + arr);
						dataOverview.addRows(arr);
						
						var options = {'width' : 600, 'height' : 300};
					
						var chart = new google.visualization.PieChart(document.getElementById('pieChart'));
						chart.draw(dataOverview, options);
						
						$('#pieChart').show();
						
						$.each(data.data.reportedActivities, function(index, value) {
							NC.log("Processing " + value.name + " ...");
							var chartData = new google.visualization.DataTable();
							chartData.addColumn('string', captions.date);
							chartData.addColumn('number', captions.targetValue);
							chartData.addColumn('number', captions.reportedValue);
							chartData.addColumn({type:'string', role:'tooltip'});
							
							var items = new Array();
							$.each(value.reportedValues, function(index, val) {
								var arr = new Array();
								
								if (val.newWeek || index == 0 || value.reportedValues.length -1 == index) {
									arr[0] = val.label;
								} else {
									arr[0] = null;
								}
								
								arr[1] = val.targetValue;
								arr[2] = val.reportedValue;
								arr[3] = captions.reportedValue + ': ' + val.reportedValue + '\n"' + val.note + '"';
								
								items.push(arr);
							});
							
							NC.log("Adding rows: " + items);
							chartData.addRows(items);
							
							var chartDiv = $('<div>', { id: 'activity-' + index}).addClass('shadow-box');
							$('#activityCharts').append('<br />').append(chartDiv);
							
							var opts = {
									width: 600,
									height: 300,
									title: value.name
							}
							
							var chart = new google.visualization.LineChart(document.getElementById('activity-' + index));
							chart.draw(chartData, opts);
						
							$('#pieChart').show();
							$('#activityCharts').show();
						});
					};
					
					var healthPlanId = "<c:out value="${param.healthPlan}" />";
					NC.log("Health plan parameter resolved to: " + healthPlanId);
					if (healthPlanId == "")  {
						hp.list(<sec:authentication property="principal.id" />, function(data) {
							$.each(data.data, function(index, value) {
								healthPlanId = value.id;
								hp.loadStatistics(healthPlanId, statisticsCallback);
							});
						});	
					} else {
						hp.loadStatistics(healthPlanId, statisticsCallback);	
					}
				};
								
				google.setOnLoadCallback(drawOverview);
			});
		</script>
	</netcare:header>
	<netcare:body>
		<netcare:content>
			<h2><spring:message code="myStatistics" /></h2>
			<p>
				<span class="label notice">Information</span>
				Nedan visas hur din hälsoplan är fördelad. Din hälsoplan innehåller aktiviteter och diagrammet visar hur stor del
				dessa aktiviteter utgör av hälsoplanen.
			</p>
			<div id="pieChart" style="display: none;" class="shadow-box"></div><br />
			
			<h2>Rapporterade Resultat</h2>
			<p>
				<span class="label notice">Information</span>
				Diagrammen nedan visar dina rapporterade resultat i förhållande till ditt uppsatta målvärde per aktivitet.
			</p>
			<div id="activityCharts" style="display: none;"></div>
		</netcare:content>
		
	</netcare:body>	
</netcare:page>