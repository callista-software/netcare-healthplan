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
package org.callistasoftware.netcare.core.repository;

import static org.junit.Assert.assertEquals;

import org.callistasoftware.netcare.core.support.TestSupport;
import org.callistasoftware.netcare.model.entity.AlarmCause;
import org.callistasoftware.netcare.model.entity.AlarmEntity;
import org.callistasoftware.netcare.model.entity.CareGiverEntity;
import org.callistasoftware.netcare.model.entity.CareUnitEntity;
import org.callistasoftware.netcare.model.entity.PatientEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

public class AlarmRepositoryTest extends TestSupport {

	@Autowired
	private AlarmRepository repo;	
	@Autowired
	private CareGiverRepository cgRepo;
	@Autowired
	private PatientRepository patientRepo;
	@Autowired
	private CareUnitRepository cuRepo;

	@Test
	@Transactional
	@Rollback(true)
	public void findByReportedTimeIsNotNull() {
		final PatientEntity patient = PatientEntity.newEntity("Peter", "123456");
		patientRepo.save(patient);
		final CareUnitEntity cu = CareUnitEntity.newEntity("cu");
		cuRepo.save(cu);
		final CareGiverEntity cg = CareGiverEntity.newEntity("Doctor Hook", "12345-67", cu);
		cgRepo.save(cg);
		
		AlarmEntity e = AlarmEntity.newEntity(AlarmCause.PLAN_EXPIRES, patient, "hsa-123", 42L);
		
		e = repo.save(e);
		
		assertEquals(1, repo.findByResolvedTimeIsNullAndCareUnitHsaIdLike("hsa-123", new Sort(Sort.Direction.DESC, "createdTime")).size());

		e.resolve(cg);
		
		e = repo.save(e);
		repo.flush();
		
		assertEquals(0, repo.findByResolvedTimeIsNullAndCareUnitHsaIdLike("hsa-123", new Sort(Sort.Direction.DESC, "createdTime")).size());
	}
}