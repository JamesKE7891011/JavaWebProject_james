package com.example.controller;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

import com.example.bean.Project;
import com.example.bean.Schedule;
import com.example.bean.Task;
import com.example.dao.ProjectDao;
import com.example.dao.ScheduleDao;
import com.example.dao.TaskDao;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
	
	@Autowired
	@Qualifier("scheduledaomysql")
	private ScheduleDao scheduleDao;
	
	@Autowired
	@Qualifier("taskdaomysql")
	private TaskDao taskDao;
	
	@Autowired
	@Qualifier("projectdaomysql")
	private ProjectDao projectDao;
	
	@GetMapping
	public String getSchedule(Model model) {
		
		// 1.schedules
		List<Schedule> schedules = scheduleDao.findAllSchedules();
		model.addAttribute("schedules", schedules);
		
		// 2.tasks
		List<Task> tasks =taskDao.findAllTasks();
		model.addAttribute("tasks",tasks);
		
		// 3.projects
		List<Project> projects = projectDao.findAllProjects();
		model.addAttribute("projects",projects);
		
		return "/backend/Schedule";
	}
	
	@GetMapping(value = "/findschedule/{projectId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Schedule findScheduleByScheduleId(@PathVariable("projectId") String projectId){
		return scheduleDao.findSchedulueByProjectId(projectId).orElse(null);
	}
	
	// 新增Schedule
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/addschedule", method = RequestMethod.POST, produces = "text/plain;charest=utf-8")
	public String addschedule(@RequestParam(name = "projectId") String projectId,
	                          @RequestParam(name = "taskName") String taskName,
	                          @RequestParam(name = "taskResource") String taskResource,
	                          @RequestParam(name = "taskStartDate") Date taskStartDate,
	                          @RequestParam(name = "taskEndDate") Date taskEndDate,
	                          @RequestParam(name = "taskDependency") Integer taskDependency,
	                          HttpSession session, Model model) throws Exception {

	    // 創建一個新的 Task 物件
	    Task task = new Task();
	    task.setTaskName(taskName);
	    task.setTaskResource(taskResource);
	    task.setTaskStartDate(taskStartDate);
	    task.setTaskEndDate(taskEndDate);
	    task.setTaskDependency(taskDependency);

	    // 設置其他 Task 的屬性，如果有需要的話

	    // 設定 Task 到 Schedule 中
	    Schedule schedule = new Schedule();
	    schedule.setProjectId(projectId);
	    schedule.setTasks(Collections.singletonList(task));

	    // 呼叫 addTask 方法新增 Task
	    int taskId = taskDao.addTask(task);

	    // 若成功新增 Task，則設定 Schedule 的 ID 並新增 Schedule
	    if (taskId >= 1) {
	        scheduleDao.addSchedule(schedule);
	        return "redirect:/mvc/schedule"; // 返回成功的視圖名稱
	    } else {
	        // 處理新增 Task 失敗的情況，這裡可以添加錯誤處理邏輯
	        model.addAttribute("errorMessage", "任務新增失敗，請通知管理員");
	        return "/backend/Schedule";
	    }

	}
	
	//刪除Schedule
	@GetMapping("/deleteschedule/{sceduleId}")
	@ResponseBody
	public String deleteSchedule(@PathVariable("scheduleId") Integer scheduleId,Model model) {
		try {
			int rowcount = scheduleDao.removeScheduleByScheduleId(scheduleId);
			if(rowcount > 0) {
				return "redirect:/mvc/schedule";
			}else {
				model.addAttribute("errorMessage","進度表刪除失敗請通知管理員");
				return "backend/Schedule";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage","進度表刪除時發生異常請通知管理員");
			return "backend/Schedule";
		}
	}
	
	//刪除Task
	@GetMapping("/deletetask/{taskId}")
	@ResponseBody
	public String deleteTask(@PathVariable("taskId") Integer taskId,Model model) {
		try {
			int rowcount = taskDao.removeTask(taskId);
			if(rowcount > 0) {
				return "redirect:/mvc/schedule";
			}else {
				model.addAttribute("errorMessage","任務刪除失敗請通知管理員");
				return "backend/Schedule";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage","任務刪除時發生異常請通知管理員");
			return "backend/Schedule";
		}
	}
}



