package com.example.controller;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

import com.example.bean.AddTask;
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
		
		List<Project> projects = projectDao.findAllProjects();
		model.addAttribute("projects", projects);

		return "/backend/Schedule";
	}

	
	@GetMapping(value = "/findschedule/{projectId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Schedule findScheduleByScheduleId(@PathVariable("projectId") String projectId){
		
		return scheduleDao.findSchedulueByProjectId(projectId).orElse(null);
	}
	
	// 新增Schedule
	@Transactional(propagation = Propagation.REQUIRED)
	@PostMapping(value = "/addTask", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Map<String,String> addTask(@RequestBody AddTask addTask,HttpSession session, Model model) throws Exception {

	    // scheduleId
		Optional<Schedule> scheduleOpt = scheduleDao.findScheduleByScheduleId(addTask.getScheduleId());
		if(scheduleOpt.isEmpty()) {
		    Schedule schedule = new Schedule();
		    schedule.setProjectId(addTask.getProjectId());
		    scheduleDao.addSchedule(schedule); 
		    addTask.setScheduleId(schedule.getScheduleId());
		}
		
		// 新增的任務的關係檢查
		List<Task> tasks = taskDao.findTasksByScheduleId(addTask.getScheduleId());
//		boolean isValidDependency = false;
//		if(tasks.size()==0) {
//			isValidDependency = StringUtils.isEmpty(addTask.getTaskDependency());
//		} else {
//			isValidDependency = tasks.stream()
//                    .map(Task::getTaskId)
//                    .map(String::valueOf)
//                    .anyMatch(taskId -> taskId.equals(addTask.getTaskDependency()));
//		}
		Map<String, String> results = new LinkedHashMap<>();
//		
//		if( !isValidDependency) {
//			results.put("message", "新增失敗:依賴關係錯誤");
//			return results;
//		}
		
		// 創建一個新的 Task 物件
	    Task task = new Task();
	    task.setScheduleId(addTask.getScheduleId());
	    task.setTaskName(addTask.getTaskName());
	    task.setTaskResource(addTask.getTaskResource());
	    task.setTaskStartDate(addTask.getTaskStartDate());
	    task.setTaskEndDate(addTask.getTaskEndDate());
	    task.setTaskDependency(addTask.getTaskDependency());

	    // 呼叫 addTask 方法新增 Task
	    int affectedRows = taskDao.addTask(task);

	    if (affectedRows >0 ) {
	    	results.put("message", "任務新增成功");
	    } else {
	    	results.put("message", "新增失敗");
	    }
	    return results;
	}
	
	//刪除Schedule
	@GetMapping("/deleteschedule/{scheduleId}")
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
	@GetMapping(value = "/deletetask/{taskId}")
	public String deleteTask(@PathVariable("taskId") Integer taskId, Model model) {
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



