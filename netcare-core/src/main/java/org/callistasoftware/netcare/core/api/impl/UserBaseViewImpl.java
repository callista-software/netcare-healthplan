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

import org.callistasoftware.netcare.core.api.UserBaseView;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;

@JsonIgnoreProperties(ignoreUnknown=true)
public abstract class UserBaseViewImpl implements UserBaseView {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Long id;
	protected String firstName;
	protected String surName;
	
	//
	public UserBaseViewImpl() {}
	
	//
	public UserBaseViewImpl(final Long id, final String name, final String surName) {
		this.id = id;
		this.firstName = name;
		this.surName = surName;
	}
	
	@JsonIgnore
	@Override
	public abstract Collection<? extends GrantedAuthority> getAuthorities();

	@JsonIgnore
	@Override
	public abstract String getPassword();

	@Override
	public abstract String getUsername();

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public String getFirstName() {
		return this.firstName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFirstName(String name) {
		this.firstName = name;
	}
	
	@Override
	public String getSurName() {
		return this.surName;
	}
	
	public void setSurName(final String surName) {
		this.surName = surName;
	}
	
	@Override
	public String getName() {
		return this.getFirstName() + " " + this.getSurName();
	}

	@Override
	public abstract boolean isCareActor();

}
