package com.javalab.calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

@SpringBootTest
class BoardApplicationTests {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	void testDatabaseConnectionAndQuery() {
		// SQL query to fetch user accounts
		String sql = "SELECT * FROM member"; // 테이블 이름을 실제 데이터베이스에 맞게 수정하세요.

		// Execute the query and get the result as a list of maps
		List<Map<String, Object>> accounts = jdbcTemplate.queryForList(sql);

		// Print out the results
		for (Map<String, Object> account : accounts) {
			System.out.println(account);
		}
	}
}