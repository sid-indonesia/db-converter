package org.sidindonesia.dbconverter.property;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.sidindonesia.dbconverter.IntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

class DatabasePropertiesTest extends IntegrationTest {

	@Autowired
	private SourceDatabaseProperties sourceDatabaseProperties;

	@Autowired
	private TargetDatabaseProperties targetDatabaseProperties;

	@Test
	void testAssertThatSourceDatabaseTablesIsNotEmpty() throws Exception {
		assertThat(sourceDatabaseProperties.getTables()).isNotEmpty();
	}

	@Test
	void testAssertThatTargetDatabaseTablesIsNotEmpty() throws Exception {
		assertThat(targetDatabaseProperties.getTables()).isNotEmpty();
	}
}
