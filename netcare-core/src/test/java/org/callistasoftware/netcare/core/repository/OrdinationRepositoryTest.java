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
package org.callistasoftware.netcare.core.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.callistasoftware.netcare.core.entity.ActivityDefinitionEntity;
import org.callistasoftware.netcare.core.entity.ActivityTypeEntity;
import org.callistasoftware.netcare.core.entity.CareGiverEntity;
import org.callistasoftware.netcare.core.entity.DurationUnit;
import org.callistasoftware.netcare.core.entity.Frequency;
import org.callistasoftware.netcare.core.entity.FrequencyDay;
import org.callistasoftware.netcare.core.entity.FrequencyTime;
import org.callistasoftware.netcare.core.entity.MeasureUnit;
import org.callistasoftware.netcare.core.entity.OrdinationEntity;
import org.callistasoftware.netcare.core.entity.PatientEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:/netcare-config.xml")
public class OrdinationRepositoryTest {

	@Autowired
	private OrdinationRepository repo;
	@Autowired
	private ActivityDefinitionRepository actRepo;
	@Autowired
	private ActivityTypeRepository typeRepo;
	@Autowired
	private CareGiverRepository cgRepo;
	@Autowired
	private PatientRepository patientRepo;
	
	ActivityDefinitionEntity createActivityDefinition() {
		Frequency freq = new Frequency();
		freq.getFrequencyDay().addDay(FrequencyDay.MON);
		freq.getFrequencyDay().addDay(FrequencyDay.FRI);
		FrequencyTime fval = new FrequencyTime();
		fval.setHour(10);
		fval.setMinute(0);
		freq.getTimes().add(fval);
		
		final CareGiverEntity cg = CareGiverEntity.newEntity("Doctor Hook", "12345-67");
		cgRepo.save(cg);
		cgRepo.flush();
		
		final PatientEntity patient = PatientEntity.newEntity("Peter", "123456", cg);
		patientRepo.save(patient);
		patientRepo.flush();

		final ActivityTypeEntity type = ActivityTypeEntity.newEntity("test", MeasureUnit.KILOMETERS);
		typeRepo.save(type);
		typeRepo.flush();

		ActivityDefinitionEntity entity = ActivityDefinitionEntity.newEntity(patient, type, freq);
		
		return entity;
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testInsertFind() throws Exception {
		
		final OrdinationEntity e1 = OrdinationEntity.newEntity("Hälsoplan B", new Date(), 20, DurationUnit.WEEK);
		
		ActivityDefinitionEntity ad =  createActivityDefinition();
		e1.getActivityDefinitions().add(ad);
		
		actRepo.save(ad);

		repo.save(e1);
		repo.flush();
		
		final List<OrdinationEntity> all = repo.findAll();
		assertNotNull(all);
		assertEquals(1, all.size());
		OrdinationEntity e2 = all.get(0);
		assertEquals("Hälsoplan B", e2.getName());
		assertEquals(DurationUnit.WEEK, e2.getDurationUnit());
		assertEquals(20, e2.getDuration());
		Calendar c = Calendar.getInstance();
		c.setTime(e1.getStartDate());
		c.add(Calendar.WEEK_OF_YEAR, e1.getDuration());
		assertEquals(c.getTime(), e2.getEndDate());
		
		assertEquals(1, e2.getActivityDefinitions().size());
	}
}