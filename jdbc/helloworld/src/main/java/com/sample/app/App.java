package com.sample.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sample.app.entity.Employee;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class App {
	private static final Logger log = LoggerFactory.getLogger(App.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public static void main(String args[]) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	public CommandLineRunner demo() {
		return (args) -> {
			log.info("Creating tables");

			jdbcTemplate.execute("DROP TABLE employees IF EXISTS");
			jdbcTemplate
					.execute("CREATE TABLE employees(" + "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");

			jdbcTemplate
					.execute("INSERT INTO employees(id, first_name, last_name) VALUES (1, 'Rama Krishna', 'Gurram')");

			System.out.println("Printing Employees\n");

			List<Employee> emps = jdbcTemplate.query("SELECT id, first_name, last_name FROM employees", (rs,
					rowNum) -> new Employee(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name")));
			emps.forEach(customer -> System.out.println(customer));

		};
	}

}
