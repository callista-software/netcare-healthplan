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

import java.util.Collection;

import org.callistasoftware.netcare.core.api.UserBaseView;
import org.springframework.security.core.GrantedAuthority;

public abstract class UserBaseViewImpl implements UserBaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Long id;
	protected String name;
	
	public UserBaseViewImpl(final Long id, final String name) {
		this.id = id;
		this.name = name;
	}
	
	@Override
	public abstract Collection<? extends GrantedAuthority> getAuthorities();

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return this.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public abstract boolean isCareGiver();

}