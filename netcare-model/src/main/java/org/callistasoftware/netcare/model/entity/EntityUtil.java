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
 package org.callistasoftware.netcare.model.entity;

import java.util.Calendar;

/**
 * Utility functions.
 * 
 * @author Peter
 */
public class EntityUtil {
	
	/**
	 * Returns a non null object.
	 * 
	 * @param o the object.
	 * @return the object if not null, otherwise an exception is raised.
	 * @throws IllegalArgumentException
	 */
	public static <T> T notNull(T o) {
		if (o == null) {
			throw new IllegalArgumentException("Invalid value: null");
		}
		return o;
	}
	
	/**
	 * Truncates time to zero, keeps date.
	 * 
	 * @param cal the calendar to truncate.
	 * @return the altered  calendar.
	 */
	public static Calendar floor(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	/**
	 * Sets time to 23:59:59:999, at en of day, keeps date.
	 * 
	 * @param cal the calendar to set to end of day.
	 * @return the altered calendar.
	 */
	public static Calendar ceil(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal;
	}

}