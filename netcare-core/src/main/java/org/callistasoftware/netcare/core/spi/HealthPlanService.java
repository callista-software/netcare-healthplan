/**
 * Copyright (C) 2011,2012 Landstinget i Joenkoepings laen <http://www.lj.se/minhalsoplan>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.callistasoftware.netcare.core.spi;

import java.util.Date;

import org.callistasoftware.netcare.core.api.ActivityComment;
import org.callistasoftware.netcare.core.api.ActivityDefinition;
import org.callistasoftware.netcare.core.api.CareActorBaseView;
import org.callistasoftware.netcare.core.api.CareUnit;
import org.callistasoftware.netcare.core.api.HealthPlan;
import org.callistasoftware.netcare.core.api.PatientBaseView;
import org.callistasoftware.netcare.core.api.PatientEvent;
import org.callistasoftware.netcare.core.api.ScheduledActivity;
import org.callistasoftware.netcare.core.api.ServiceResult;
import org.callistasoftware.netcare.core.api.UserBaseView;
import org.callistasoftware.netcare.core.api.statistics.HealthPlanStatistics;
import org.callistasoftware.netcare.model.entity.ActivityDefinitionEntity;

/**
 * Defines the ordination service that will provide
 * all required functionality for dealing with
 * ordinations
 * 
 * @author Marcus Krantz [marcus.krantz@callistaenterprise.se]
 *
 */
public interface HealthPlanService {
	
	/**
	 * Update the reminder on this activity definition
	 * @param reminderOn True, reminder is on. False otherwise
	 * @return
	 */
	ServiceResult<ActivityDefinition> updateReminder(final Long id, final boolean reminderOn);
	
	/**
	 * Load a scheduled activity
	 * @param activity
	 * @return
	 */
	ServiceResult<ScheduledActivity> loadScheduledActivity(final Long activity);

	/**
	 * Load ordinations for a patient
	 * @param patient
	 * @return
	 */
	ServiceResult<HealthPlan[]> loadHealthPlansForPatient(final Long patient);
	
	/**
	 * Creates a new ordination.
	 * 
	 * @param ordination
	 * @param creator
	 * @param patientId
	 * @return
	 */
	ServiceResult<HealthPlan> createNewHealthPlan(final HealthPlan ordination, final CareActorBaseView creator, final Long patientId);

    /**
     * Inactivates a health plan.
     * @param healthPlan
     * @return
     */
    ServiceResult<HealthPlan> inactivateHealthPlan(Long healthPlan, boolean sysUser);

    /**
     * Activates a health plan.
     * @param healthPlan
     * @return
     */
    ServiceResult<HealthPlan> activateHealthPlan(Long healthPlan, boolean sysUser);


	/**
	 * Load a specific ordination
	 * @param healthPlanId
	 * @return
	 */
	ServiceResult<HealthPlan> loadHealthPlan(final Long healthPlanId);
	
	/**
	 * Adds an activity definition to an existing ordination specified by its id
	 * @param dto the activity definition.
	 * 
	 * @return the result.
	 */
	ServiceResult<ActivityDefinition> addActvitiyToHealthPlan(final ActivityDefinition dto);
	
	/**
	 * Load an activity definition
	 * @param definitionId
	 * @return
	 */
	ServiceResult<ActivityDefinition> loadDefinition(final Long definitionId);
	
	/**
	 * Updates an existing activity definition
	 * @param dto
	 * @return
	 */
	ServiceResult<ActivityDefinition> updateActivity(final ActivityDefinition dto);
	
	/**
	 * Inactivates an activity definition. All scheduled activities that not yet has been reported will
	 * be deleted from the system.
	 * @param activityDefinitionId
	 * @return
	 */
	ServiceResult<ActivityDefinition> inactivateActivity(final Long activityDefinitionId);

    /**
     * Activates an activity definition.
     * @param activityDefinitionId
     * @return
     */
    ServiceResult<ActivityDefinition> activateActivity(Long activityDefinitionId);

	/**
	 * Load all activities for a specific health plan
	 * @param healthPlanId
	 * @return
	 */
	ServiceResult<ActivityDefinition[]> loadActivitiesForHealthPlan(final Long healthPlanId);
	
	/**
	 * Comment a performed activity
	 * @param activityId
	 * @param comment
	 * @return
	 */
	ServiceResult<ScheduledActivity> commentOnPerformedActivity(final Long activityId, final String comment);
	
	/**
	 * Like an activity.
	 * @param activityId
	 * @param like
	 * @return
	 */
	ServiceResult<ScheduledActivity> likePerformedActivity(final Long activityId, boolean like);

	/**
	 * Mark an activity as read.
	 * @param activityId
	 * @param hasBeenRead
	 * @return
	 */
	ServiceResult<ScheduledActivity> markPerformedActivityAsRead(final Long activityId, boolean hasBeenRead);

	/**
	 * Reply to a comment
	 * @param comment
	 * @return
	 */
	ServiceResult<ActivityComment> replyToComment(final Long comment, final String reply);
	
	/**
	 * Get comments on reported activities for the currently logged in patient
	 * @return
	 */
	ServiceResult<ActivityComment[]> loadCommentsForPatient();
	
	/**
	 * Load replies for the currently logged in care giver
	 * @return
	 */
	ServiceResult<ActivityComment[]> loadRepliesForCareActor();
	
	/**
	 * Delete a comment with the specified id
	 * @param commentId
	 * @return
	 */
	ServiceResult<ActivityComment> deleteComment(final Long commentId);

	/**
	 * 
	 * @param commentId
	 * @return
	 */
	ServiceResult<ActivityComment> hideComment(final Long commentId, boolean isAdmin);

	/**
	 * Load the latest reported activities for all patients that
	 * has health plans belonging to the caller's care unit
	 * @return
	 */
	ServiceResult<ScheduledActivity[]> loadLatestReportedForAllPatients(final CareUnit careUnit, final Date start, final Date end);
	
	/**
	 * Filter out a list of reported activities with supplied params.
	 * @param careUnit the actual care unit
	 * @param personnummer what person
	 * @param start date from
	 * @param end date to
	 * @return the list of reported activities
	 */
	ServiceResult<ScheduledActivity[]> filterReportedActivities(CareUnit careUnit, String personnummer, Date start,
			Date end);

	/**
	 * Returns actual activity definitions.
	 * 
	 * @return the result with actual activity definitions.
	 */
	ServiceResult<ActivityDefinition[]> getPlannedActivitiesForPatient(final Long patientId, final boolean onlyOngoing);

	/**
	 * Get reported activities for a certain activity definition within a specific time interval
	 * @param healthPlanId
	 * @return
	 */
	ServiceResult<ScheduledActivity[]> getScheduledActivitiesForHealthPlan(final Long healthPlanId);
	
	/**
	 * Get statistical information from a health plan
	 * @param healthPlanId
	 * @return
	 */
	ServiceResult<HealthPlanStatistics> getStatisticsForHealthPlan(final Long healthPlanId);
	
	/**
	 * Schedules activities.
	 * 
	 * @param activityDefinition the activity defintion.
	 */
	void scheduleActivities(ActivityDefinitionEntity activityDefinition);

	/**
	 * Performs a renewal, adding another iteration.
	 * 
	 * @param healthPlanId the health plan id.
	 * @param stop, indicates if renewal shall be terminated.
	 * 
	 * @return the result.
	 * 
	 */
	ServiceResult<HealthPlan> healthPlanRenewal(Long healthPlanId, boolean stop);

}
