package org.sidindonesia.dbconverter.service;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import org.sidindonesia.dbconverter.property.SourceDatabaseProperties;
import org.sidindonesia.dbconverter.property.Table;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SourceDatabaseService {
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final SourceDatabaseProperties sourceDatabaseProperties;

	public Map<String, List<Map<String, Optional<Object>>>> loadAll() {
		Set<Table> requiredTables = sourceDatabaseProperties.getTables();

		return requiredTables.stream().flatMap(table -> {

			List<Map<String, Optional<Object>>> resultSet = namedParameterJdbcTemplate.query(table.getQuery(),
				(rs, rowNum) -> IntStream.rangeClosed(1, rs.getMetaData().getColumnCount()).mapToObj(i -> {
					Map<String, Optional<Object>> map = new HashMap<>();
					try {
						String columnName = rs.getMetaData().getColumnName(i);
						map.put(columnName, ofNullable(rs.getObject(columnName)));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return map;
				}).filter(map -> !map.isEmpty()).flatMap(map -> map.entrySet().stream())
					.collect(toMap(Entry::getKey, Entry::getValue)));

			Map<String, List<Map<String, Optional<Object>>>> map = new HashMap<>();
			map.put(table.getName(), resultSet);
			return map.entrySet().stream();
		}).collect(toMap(Entry::getKey, Entry::getValue));
	}
}
