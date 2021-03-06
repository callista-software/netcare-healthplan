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
package org.callistasoftware.netcare.core.api.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.callistasoftware.netcare.core.api.Alarm;
import org.callistasoftware.netcare.core.api.CareActorBaseView;
import org.callistasoftware.netcare.core.api.Option;
import org.callistasoftware.netcare.core.api.Patient;
import org.callistasoftware.netcare.model.entity.AlarmEntity;

public class AlarmImpl implements Alarm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String careUnitHsaId;
	private Patient patient;
	private String createdTime;
	private String resolvedTime;
	private CareActorBaseView resolvedBy;
	private Option cause;
	private String info;
	private Long entityReferenceId;
	private String healtPlanName;

	public static Alarm[] newFromEntities(final List<AlarmEntity> entities, final Locale l) {
		final Alarm[] dtos = new Alarm[entities.size()];
		for (int i = 0; i < entities.size(); i++) {
			dtos[i] = AlarmImpl.newFromEntity(entities.get(i), l);
		}

		return dtos;
	}

	public static Alarm newFromEntity(final AlarmEntity entity, final Locale l) {

		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		final AlarmImpl alarm = new AlarmImpl();
		alarm.setCareUnitHsaId(entity.getCareUnitHsaId());
		alarm.setCause(new Option(entity.getCause().name(), l));
		alarm.setCreatedTime(sdf.format(entity.getCreatedTime()));

		if (entity.getResolvedTime() != null) {
			alarm.setResolvedTime(sdf.format(entity.getResolvedTime()));
		}

		alarm.setEntityReferenceId(entity.getRefEntityId());
		alarm.setId(entity.getId());
		alarm.setPatient(PatientImpl.newFromEntity(entity.getPatient()));
		alarm.setInfo(entity.getInfo());
		// This in not ideal but in 2 cases of 3 the info equals the Healt Plan
		// Name, felt overkill to create one more attribute
		if (entity.getInfo() != null) {
			int index = entity.getInfo().indexOf("#");
			if (index > 0) {
				alarm.setHealtPlanName(entity.getInfo().substring(0, index));
			} else {
				alarm.setHealtPlanName(entity.getInfo());
			}
		}
		if (entity.getResolvedBy() != null) {
			alarm.setResolvedBy(CareActorBaseViewImpl.newFromEntity(entity.getResolvedBy()));
		}
		return alarm;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCareUnitHsaId(String careUnitHsaId) {
		this.careUnitHsaId = careUnitHsaId;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public void setResolvedTime(String resolvedTime) {
		this.resolvedTime = resolvedTime;
	}

	public void setResolvedBy(CareActorBaseView resolvedBy) {
		this.resolvedBy = resolvedBy;
	}

	public void setCause(Option cause) {
		this.cause = cause;
	}

	public void setEntityReferenceId(Long entityReferenceId) {
		this.entityReferenceId = entityReferenceId;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public String getCareUnitHsaId() {
		return this.careUnitHsaId;
	}

	@Override
	public Patient getPatient() {
		return this.patient;
	}

	@Override
	public String getCreatedTime() {
		return this.createdTime;
	}

	@Override
	public String getResolvedTime() {
		return this.resolvedTime;
	}

	@Override
	public CareActorBaseView getResolvedBy() {
		return this.resolvedBy;
	}

	@Override
	public Option getCause() {
		return this.cause;
	}

	@Override
	public Long getEntityReferenceId() {
		return this.entityReferenceId;
	}

	void setInfo(String info) {
		this.info = info;
	}

	@Override
	public String getInfo() {
		return info;
	}

	public String getHealtPlanName() {
		return healtPlanName;
	}

	public void setHealtPlanName(String healtPlanName) {
		this.healtPlanName = healtPlanName;
	}

}
