package org.sidindonesia.dbconverter.service;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.sidindonesia.dbconverter.property.SourceDatabaseProperties;
import org.sidindonesia.dbconverter.property.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SourceDatabaseServiceTest {
	@Autowired
	private SourceDatabaseService sourceDatabaseService;

	@Autowired
	private SourceDatabaseProperties sourceDatabaseProperties;

	@Test
	void testLoadAll() throws Exception {
		Map<String, List<Map<String, Optional<Object>>>> allResultSetJson = sourceDatabaseService.loadAll();

		assertThat(allResultSetJson).isNotEmpty()
			.containsOnlyKeys(sourceDatabaseProperties.getTables().stream().map(Table::getName).collect(toList()));
	}
}
