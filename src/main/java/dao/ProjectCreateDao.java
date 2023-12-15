package dao;

import java.util.Date;
import java.util.List;

import bean.ProjectCreate;

public interface ProjectCreateDao {
		// 新增專案：
		int addProjectCreate(ProjectCreate projectcreate);
		
		// 取消專案
		int cancelProjectCreate(String projectid);
		
		// 查看專案
		List<ProjectCreate> findAllProjectCreates();
		
		// 修改預約人
		int updateProjectCreate(String projectid, String projectname,String projectcontent,String projectowner,String projectmember,Date projectstart,Date projectend);
}
