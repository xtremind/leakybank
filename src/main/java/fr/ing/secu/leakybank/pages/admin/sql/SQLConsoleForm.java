package fr.ing.secu.leakybank.pages.admin.sql;

import java.util.List;

import javax.validation.constraints.NotNull;

public class SQLConsoleForm {
	
	@NotNull(message = "SQL Query is required")
	private String sqlQuery;
	
	private List<String> columnNames;
	
	private List<List<String>> resultSet;

	public String getSqlQuery() {
		return sqlQuery;
	}

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

	public List<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	public List<List<String>> getResultSet() {
		return resultSet;
	}

	public void setResultSet(List<List<String>> resultSet) {
		this.resultSet = resultSet;
	}

}
