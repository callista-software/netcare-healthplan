/**
 * Copyright (C) 2011 Callista Enterprise AB <info@callistaenterprise.se>
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

import org.callistasoftware.netcare.core.api.CareGiverBaseView;
import org.callistasoftware.netcare.core.api.Ordination;
import org.callistasoftware.netcare.core.entity.OrdinationEntity;

/**
 * Implementation of an ordination
 * 
 * @author Marcus Krantz [marcus.krantz@callistaenterprise.se]
 *
 */
public class OrdinationImpl implements Ordination {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String startDate;
	private String endDate;
	private int duration;
	private String durationUnit;
	
	private CareGiverBaseView issuedBy;
	
	public static OrdinationImpl newFromEntity(final OrdinationEntity entity) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		final OrdinationImpl dto = new OrdinationImpl();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setDuration(entity.getDuration());
		dto.setDurationUnit(entity.getDurationUnit().getCode());
		dto.setStartDate(sdf.format(entity.getStartDate()));
		
		final CareGiverBaseViewImpl cg = new CareGiverBaseViewImpl(entity.getIssuedBy().getId(), entity.getIssuedBy().getName());
		cg.setHsaId(entity.getIssuedBy().getHsaId());
		
		dto.setIssuedBy(cg);
		
		return dto;
	}

	@Override
	public Long getId() {
		return this.id;
	}
	
	public void setId(final Long id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String getStartDate() {
		return this.startDate;
	}
	
	public void setStartDate(final String startDate) {
		this.startDate = startDate;
	}

	@Override
	public String getEndDate() {
		return this.endDate;
	}

	@Override
	public CareGiverBaseView getIssuedBy() {
		return this.issuedBy;
	}
	
	public void setIssuedBy(final CareGiverBaseView careGiver) {
		this.issuedBy = careGiver;
	}

	@Override
	public int getDuration() {
		return this.duration;
	}
	
	public void setDuration(final int duration) {
		this.duration = duration;
	}

	@Override
	public String getDurationUnit() {
		return this.durationUnit;
	}
	
	public void setDurationUnit(final String durationUnit) {
		this.durationUnit = durationUnit;
	}

}