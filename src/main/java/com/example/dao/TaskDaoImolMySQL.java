package com.example.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import javax.swing.tree.RowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.bean.Task;
import com.mysql.cj.protocol.Resultset;

@Repository("taskdaomysql")
public class TaskDaoImolMySQL implements TaskDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int addTask(Task task) {
		int taskId = -1;
		
		String sql = "insert into task(scheduleId,taskName,taskResource,taskStartDate,taskEndDate,taskDependency )values(?,?,?,?,?,?))";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		int affectedRows = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1,task.getScheduleId());
			ps.setString(2, task.getTaskName());
			ps.setString(3,task.getTaskResourse());
			ps.setDate(4, (Date) task.getTaskStartDate());
			ps.setDate(5, (Date) task.getTaskEndDate());
			ps.setInt(6, task.getTaskDependency());			
			return ps;
		},keyHolder);
		
		if(keyHolder.getKey()!= null) {
			taskId = keyHolder.getKey().intValue();
		}
		return taskId;
	}

	@Override
	public int removeTask(Integer taskId) {
		String sql = "delete from task where taskId = ?";
		return jdbcTemplate.update(sql,taskId);
	}

	
	@Override
	public List<Task> findAllTasks() {
		String sql = "select taskId,scheduleId,taskName,taskRsource,taskStartDate,taskEndDate,taskDependency from task";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Task.class));
	}

	@Override
	public List<Task> findTasksByScheduleId(int scheduleId) {
		String sql = "select taskId,scheduleId,taskName,taskRsource,taskStartDate,taskEndDate,taskDependency from task where scheduleId = ?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Task.class),scheduleId);
		}

	@Override
	public Optional<Task> findTaskByTaskId(Integer taskId) {
		String sql = "select taskId,scheduleId,taskName,taskRsource,taskStartDate,taskEndDate,taskDependency from task where taskId = ?";
		try {
			Task task = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Task.class), taskId);
			return Optional.ofNullable(task);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}	
	}
	
	

}
