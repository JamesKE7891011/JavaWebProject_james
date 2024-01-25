package com.example.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.bean.Schedule;
import com.example.bean.Task;

@Repository("scheduledaomysql")
public class ScheduleDaoImplMySQL implements ScheduleDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("taskdaomysql")
	private TaskDao taskDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int addSchedule(Schedule schedule) {
		int scheduleId = -1;
		String sql = "insert into schedule(projectId) values(?)";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		int affectedRows = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1,schedule.getProjectId());
			return ps;
		},keyHolder);
		
		if (keyHolder.getKey() != null) {
			scheduleId = keyHolder.getKey().intValue();
			schedule.setScheduleId(scheduleId);
		}		
		return affectedRows;
	}

	@Transactional
	@Override
	public int removeScheduleByScheduleId(Integer scheduleId) {
	    try {
	        String deleteTaskSql = "delete from task where scheduleId = ?";
	        jdbcTemplate.update(deleteTaskSql, scheduleId);

	        String deleteScheduleSql = "delete from schedule where scheduleId = ?";
	        return jdbcTemplate.update(deleteScheduleSql, scheduleId);
	    } catch (Exception e) {
	        // 如果發生異常，可以在這裡處理，也可以選擇拋出異常以觸發事務回滾
	        throw new RuntimeException("刪除進度表時發生異常", e);
	    }
	}
	
	
	
	@Override
	public int removeScheduleByProjectId(String projectId) {
		try {
	        String deleteTaskSql = "delete from task where scheduleId in (select scheduleId from schedule where projectId = ?)";
	        jdbcTemplate.update(deleteTaskSql,projectId);

	        String deleteScheduleSql = "delete from schedule where projectId = ?";
	        return jdbcTemplate.update(deleteScheduleSql, projectId);
	    } catch (Exception e) {
	        // 如果發生異常，可以在這裡處理，也可以選擇拋出異常以觸發事務回滾
	        throw new RuntimeException("刪除進度表時發生異常", e);
	    }
	}



	RowMapper<Schedule> scheduleMapper = (ResultSet rs, int rowNum) -> {
		Schedule schedule = new Schedule();
		schedule.setScheduleId(rs.getInt("scheduleId"));
		schedule.setProjectId(rs.getString("projectId"));		
		
		//set List<Task>
		List<Task> tasks = taskDao.findTasksByScheduleId(rs.getInt("scheduleId"));
		tasks.forEach(task -> {
			task.calculateTaskDuration();
			task.calculateTaskPercentComplete();
		});
		schedule.setTasks(tasks);
		
		return schedule;		
	};

	@Override
	public List<Schedule> findAllSchedules() {
		String sql = "select scheduleId,projectId from schedule";
		return jdbcTemplate.query(sql, scheduleMapper);
	}

	@Override
	public Optional<Schedule> findScheduleByScheduleId(Integer scheduleId) {
		String sql = "select scheduleId,projectId from schedule where scheduleId = ?";
		try {
			Schedule schedule = jdbcTemplate.queryForObject(sql,scheduleMapper,scheduleId);
			return Optional.ofNullable(schedule);
		} catch (Exception e) {
			return Optional.empty();
		}		
	}

	@Override
	public Optional<Schedule> findSchedulueByProjectId(String projectId) {
		String sql = "select scheduleId,projectId from schedule where projectId = ?";
		try {
			Schedule schedule = jdbcTemplate.queryForObject(sql,scheduleMapper,projectId);
			return Optional.ofNullable(schedule);
		} catch (Exception e) {
			return Optional.empty();
		}		
	}
	
	

}
