/**
 * Copyright (C) 2011,2012 Callista Enterprise AB <info@callistaenterprise.se>
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

import org.callistasoftware.netcare.core.api.CareActorBaseView;
import org.callistasoftware.netcare.core.api.CareUnit;
import org.callistasoftware.netcare.model.entity.CareActorEntity;
import org.springframework.security.core.GrantedAuthority;

/**
 * Implementation of a care giver base view
 * @author Marcus Krantz [marcus.krantz@callistaenterprise.se]
 *
 */
public class CareActorBaseViewImpl extends UserBaseViewImpl implements CareActorBaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String hsaId;
	
	private CareUnit careUnit;
	
	public CareActorBaseViewImpl() {
		super(null, null, null);
	}
	
	public CareActorBaseViewImpl(final Long id, final String name, final String surname) {
		super(id, name, surname);
	}
	
	public static CareActorBaseView newFromEntity(final CareActorEntity entity) {
		final CareActorBaseViewImpl ca = new CareActorBaseViewImpl(entity.getId(), entity.getFirstName(), entity.getSurName());
		ca.setHsaId(entity.getHsaId());
		ca.setCareUnit(CareUnitImpl.newFromEntity(entity.getCareUnit()));

		return ca;
	}
	
	@Override
	public String getHsaId() {
		return this.hsaId;
	}
	
	public void setHsaId(final String hsaId) {
		this.hsaId = hsaId;
	}

	@Override
	public CareUnit getCareUnit() {
		return careUnit;
	}

	public void setCareUnit(CareUnit careUnit) {
		this.careUnit = careUnit;
	}

	@Override
	public boolean isCareActor() {
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Role.getCareActorRoleSet();
	}

	@Override
	public String getPassword() {
		return null;
		//throw new UnsupportedOperationException("Care givers should never use basic authentication. This method is most likely called from the mobile authentication manager");
	}

	@Override
	public String getUsername() {
		return this.getHsaId();
	}
}