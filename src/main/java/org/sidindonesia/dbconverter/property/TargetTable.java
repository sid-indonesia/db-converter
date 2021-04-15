package org.sidindonesia.dbconverter.property;

import java.util.Set;

import lombok.Data;

@Data
public class TargetTable {
	private String name;
	private String sourceTableName;
	private Set<TargetColumn> columns;

	@Data
	private static class TargetColumn {
		private String name;
		private String type;
		private String sourceColumnType;
		private String sourceColumnName;
		private String jsonPath;
	}
}
