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
package org.callistasoftware.netcare.api.rest;

import org.callistasoftware.netcare.core.api.MeasureUnit;
import org.callistasoftware.netcare.core.api.ServiceResult;
import org.callistasoftware.netcare.core.spi.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/units", produces = "application/json")
public class UnitApi extends ApiSupport {

	// No patient data, no pdl logging

	@Autowired
	private UnitService service;

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public ServiceResult<MeasureUnit[]> list() {
		logAccessWithoutPdl("list", "measure units");
		return service.loadUnits();
	}

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ServiceResult<MeasureUnit> create(@RequestBody final MeasureUnit measureUnit) {
		logAccessWithoutPdl("create", "measure unit");
		return service.saveUnit(measureUnit);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ServiceResult<MeasureUnit> update(@PathVariable("id") final Long id,
			@RequestBody final MeasureUnit measureUnit) {
		logAccessWithoutPdl("update", "measure unit");
		return service.saveUnit(measureUnit);
	}
}
