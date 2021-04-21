package org.sidindonesia.dbconverter.service;

import static java.lang.String.format;
import static java.util.stream.Collectors.toMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.sidindonesia.dbconverter.property.SourceDatabaseProperties;
import org.sidindonesia.dbconverter.property.SourceTable;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SourceDatabaseService {
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final SourceDatabaseProperties sourceDatabaseProperties;
	private final ColumnMapRowMapper columnMapRowMapper;

	public Map<String, List<Map<String, Object>>> loadAll() {
		Set<SourceTable> requiredTables = sourceDatabaseProperties.getTables();

		return requiredTables.stream().flatMap(table -> {

			String query = format(table.getQuery(), table.getLastId());
			List<Map<String, Object>> resultList = namedParameterJdbcTemplate.query(query, columnMapRowMapper::mapRow);

			if (!resultList.isEmpty()) {
				Object lastObjectId = resultList.get(resultList.size() - 1).get(table.getIdColumnName());
				table.setLastId(lastObjectId);
			}

			Map<String, List<Map<String, Object>>> map = new HashMap<>();
			map.put(table.getTargetTableName(), resultList);
			return map.entrySet().stream();
		}).collect(toMap(Entry::getKey, Entry::getValue));
	}
}