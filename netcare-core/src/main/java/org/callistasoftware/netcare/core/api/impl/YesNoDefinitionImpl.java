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

import org.callistasoftware.netcare.core.api.ActivityItemValuesDefinition;
import org.callistasoftware.netcare.core.api.YesNoDefinition;
import org.callistasoftware.netcare.model.entity.YesNoDefinitionEntity;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class YesNoDefinitionImpl extends ActivityItemValuesDefinitionImpl implements YesNoDefinition {

	public static ActivityItemValuesDefinition newFromEntity(YesNoDefinitionEntity entity) {
		YesNoDefinitionImpl yesNoDefinition = new YesNoDefinitionImpl();
		yesNoDefinition.setId(entity.getId());
		yesNoDefinition.setActivityItemType(ActivityItemTypeImpl.newFromEntity(entity.getActivityItemType()));
		yesNoDefinition.setActive(entity.isActive());
		return yesNoDefinition;
	}
}
