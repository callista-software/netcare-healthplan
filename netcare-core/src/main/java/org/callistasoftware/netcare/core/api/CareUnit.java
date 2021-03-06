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
package org.callistasoftware.netcare.core.api;

import java.io.Serializable;

import org.callistasoftware.netcare.core.api.impl.CareUnitImpl;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(as=CareUnitImpl.class)
public interface CareUnit extends Serializable {
	
	Long getId();

	/**
	 * Get the hsa id of the care unit
	 * @return
	 */
	String getHsaId();
	
	/**
	 * Get the name of the care unit
	 * @return
	 */
	String getName();
	
	/**
	 * Get the county council
	 * @return
	 */
	CountyCouncil getCountyCouncil();
}
