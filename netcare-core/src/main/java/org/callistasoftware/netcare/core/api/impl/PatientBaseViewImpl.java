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

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.callistasoftware.netcare.core.api.PatientBaseView;
import org.callistasoftware.netcare.model.entity.PatientEntity;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;

/**
 * Implementation of a patient base view
 * 
 * @author Marcus Krantz [marcus.krantz@callistaenterprise.se]
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class PatientBaseViewImpl extends UserBaseViewImpl implements PatientBaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String password;
	private String civicRegistrationNumber;

	public PatientBaseViewImpl() {
		super(null, null, null);
	}
	
	PatientBaseViewImpl(final Long id, final String name, final String surName, final String civicRegistrationNumber) {
		super(id, name, surName);
		this.setCivicRegistrationNumber(civicRegistrationNumber);
	}
	
	PatientBaseViewImpl(final PatientEntity entity) {
		super(entity.getId(), entity.getFirstName(), entity.getSurName());
		this.setCivicRegistrationNumber(entity.getCivicRegistrationNumber());
		this.setPassword(entity.getPassword());
	}
	
	public static PatientBaseView newFromEntity(final PatientEntity entity) {
		return new PatientBaseViewImpl(entity);
	}
	
	public static PatientBaseView[] newFromEntities(final List<PatientEntity> entities) {
		final PatientBaseView[] dtos = new PatientBaseViewImpl[entities.size()];
		for (int i = 0; i < entities.size(); i++) {
			dtos[i] = PatientBaseViewImpl.newFromEntity(entities.get(i));
		}
		
		return dtos;
	}
	
	@Override
	public boolean isCareActor() {
		return false;
	}

	@Override
	public String getCivicRegistrationNumber() {
		return this.civicRegistrationNumber;
	}
	
	public void setCivicRegistrationNumber(final String civicRegistrationNumber) {
		this.civicRegistrationNumber = civicRegistrationNumber;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new GrantedAuthority() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public String getAuthority() {
				return "PATIENT";
			}
		});
	}

	@Override
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(final String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return this.getCivicRegistrationNumber();
	}
}
