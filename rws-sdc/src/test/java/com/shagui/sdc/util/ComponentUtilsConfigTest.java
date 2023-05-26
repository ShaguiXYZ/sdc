package com.shagui.sdc.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentHistoricalCoverageModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentPropertyModel;
import com.shagui.sdc.model.SquadModel;
import com.shagui.sdc.model.pk.ComponentAnalysisPk;
import com.shagui.sdc.model.pk.ComponentHistoricalCoveragePk;
import com.shagui.sdc.repository.ComponentAnalysisRepository;
import com.shagui.sdc.repository.ComponentHistoricalCoverageRepository;
import com.shagui.sdc.repository.ComponentPropertyRepository;
import com.shagui.sdc.repository.ComponentRepository;
import com.shagui.sdc.repository.SquadRepository;
import com.shagui.sdc.test.utils.RwsTestUtils;
import com.shagui.sdc.util.jpa.JpaCommonRepository;

class ComponentUtilsConfigTest {

	@Mock
	private ComponentRepository componentRepository;

	@Mock
	private ComponentAnalysisRepository componentAnalysisRepository;

	@Mock
	private ComponentPropertyRepository componentPropertyRepository;

	@Mock
	private ComponentHistoricalCoverageRepository historicalCoverageComponentRepository;

	@Mock
	private SquadRepository squadRepository;

	@Test
	void componentRepositoryTest() {
		JpaCommonRepository<ComponentRepository, ComponentModel, Integer> result = RwsTestUtils.componentUtilsConfig()
				.componentRepository();
		assertNotNull(result);
	}

	@Test
	void componentAnalysisRepositoryTest() {
		JpaCommonRepository<ComponentAnalysisRepository, ComponentAnalysisModel, ComponentAnalysisPk> result = RwsTestUtils
				.componentUtilsConfig().componentAnalysisRepository();
		assertNotNull(result);
	}

	@Test
	void componentPropertyRepositoryTest() {
		JpaCommonRepository<ComponentPropertyRepository, ComponentPropertyModel, Integer> result = RwsTestUtils
				.componentUtilsConfig().componentPropertyRepository();
		assertNotNull(result);
	}

	@Test
	void historicalCoverageComponentRepositoryTest() {
		JpaCommonRepository<ComponentHistoricalCoverageRepository, ComponentHistoricalCoverageModel, ComponentHistoricalCoveragePk> result = RwsTestUtils
				.componentUtilsConfig().historicalCoverageComponentRepository();
		assertNotNull(result);
	}

	@Test
	void squadRepositoryTest() {
		JpaCommonRepository<SquadRepository, SquadModel, Integer> result = RwsTestUtils.componentUtilsConfig()
				.squadRepository();
		assertNotNull(result);
	}

}
