package org.sidindonesia.dbconverter.service;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.sidindonesia.dbconverter.IntegrationTest;
import org.sidindonesia.dbconverter.property.SourceDatabaseProperties;
import org.sidindonesia.dbconverter.property.SourceTable;
import org.springframework.beans.factory.annotation.Autowired;

class SourceDatabaseServiceTest extends IntegrationTest {
	@Autowired
	private SourceDatabaseService sourceDatabaseService;

	@Autowired
	private SourceDatabaseProperties sourceDatabaseProperties;

	@Test
	void testLoadAll() throws Exception {
		Map<String, List<Map<String, Object>>> allResultSet = sourceDatabaseService.loadAll();

		assertThat(allResultSet).isNotEmpty().containsOnlyKeys(
			sourceDatabaseProperties.getTables().stream().map(SourceTable::getDestinationTableName).collect(toList()));
	}
}
