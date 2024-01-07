package com.example.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.bean.IssueClass;

@Repository("issueclassdaomysql")
public class IssueClassDaoImplMySQL implements IssueClassDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<IssueClass> findAllIssueClass() {
		String sql = "select issueClassId,issueClassName from issueclass";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(IssueClass.class));
	}

	@Override
	public Optional<IssueClass> findIssueClassById(String issueClassId) {	
		try {
			String sql = "select issueClassId,issueClassName from issueclass where issueClassId = ?";
			IssueClass issueClass = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(IssueClass.class), issueClassId);
			return Optional.ofNullable(issueClass);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
		
	}
	
}
