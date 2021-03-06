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
package org.callistasoftware.netcare.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="nc_patient")
@PrimaryKeyJoinColumn(name="id")
public class PatientEntity extends UserEntity implements PermissionRestrictedEntity {

	@Column(name="civic_reg_number", length=16, nullable=false, unique=true)
	private String civicRegistrationNumber;
	
	@Column(name="password")
	private String password;
	
	@Column(name="phone_number")
	private String phoneNumber;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="forPatient", cascade=CascadeType.REMOVE, orphanRemoval=true)
	private List<HealthPlanEntity> healthPlans;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="patient", cascade=CascadeType.REMOVE, orphanRemoval=true)
	private List<AlarmEntity> alarms;
	
	public static PatientEntity newEntity(final String firstName, final String surName, final String civicRegistrationNumber) {
		return new PatientEntity(firstName, surName, civicRegistrationNumber);
	}
	
	PatientEntity() {
		super();
		this.setHealthPlans(new ArrayList<HealthPlanEntity>());
	}
	
	PatientEntity(final String name, final String surName, final String civicRegistrationNumber) {
		super(name, surName);
		this.setCivicRegistrationNumber(civicRegistrationNumber);
	}

	public String getCivicRegistrationNumber() {
		return civicRegistrationNumber;
	}

	void setCivicRegistrationNumber(String civicRegistrationNumber) {
		String crn = EntityUtil.notNull(civicRegistrationNumber);
		// clean dash from number (if any).
		this.civicRegistrationNumber = EntityUtil.formatCrn(crn);
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(final String password) {
		this.password = password;
	}

	public List<HealthPlanEntity> getHealthPlans() {
		return healthPlans;
	}
	
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	
	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	void setHealthPlans(List<HealthPlanEntity> healthPlans) {
		this.healthPlans = healthPlans;
	}
	
	public List<AlarmEntity> getAlarms() {
		return this.alarms;
	}
	
	void setAlarms(final List<AlarmEntity> alarms) {
		this.alarms = alarms;
	}
	
	public boolean isPushEnbaled() {
		return this.getProperties().containsKey("apnsRegistrationId") || this.getProperties().containsKey("c2dmRegistrationId");
	}
	
	public boolean isGcmUser() {
		return this.getProperties().containsKey("c2dmRegistrationId");
	}
	
	public boolean isApnsUser() {
		return this.getProperties().containsKey("apnsRegistrationId");
	}

	@Override
	public boolean isCareActor() {
		return false;
	}

	@Override
	public boolean isReadAllowed(UserEntity user) {
		return this.isReadAllowed(user);
	}

	@Override
	public boolean isWriteAllowed(UserEntity user) {
		if (user.isCareActor()) {
			final CareActorEntity ca = (CareActorEntity) user;
			final List<HealthPlanEntity> hps = this.getHealthPlans();
			
			for (final HealthPlanEntity ent : hps) {
				if (ent.getCareUnit().getId().equals(ca.getCareUnit().getId())) {
					return true;
				}
			}
		}
		
		return user.getId().equals(this.getId());
	}

	@Override
	public String getUsername() {
		return this.getCivicRegistrationNumber();
	}
}
