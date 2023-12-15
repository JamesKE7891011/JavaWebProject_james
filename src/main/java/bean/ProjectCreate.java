package bean;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class ProjectCreate {
	private String projectid;
	private String projectname;
	private String projectcontent;
	private String projectowner;
	private String projectmember;
	private Date projectstart;
	private Date projectend;
	
	public ProjectCreate() {
		
	}
	
	public ProjectCreate(String projectid, String projectname, String projectcontent, String projectowner,
			String projectmember, Date projectstart, Date projectend) {
		this.projectid = projectid;
		this.projectname = projectname;
		this.projectcontent = projectcontent;
		this.projectowner = projectowner;
		this.projectmember = projectmember;
		this.projectstart = projectstart;
		this.projectend = projectend;
	}

	public String getProjectid() {
		return projectid;
	}

	public String getProjectname() {
		return projectname;
	}

	public String getProjectcontent() {
		return projectcontent;
	}

	public String getProjectowner() {
		return projectowner;
	}

	public String getProjectmember() {
		return projectmember;
	}

	public Date getProjectstart() {
		return projectstart;
	}

	public Date getProjectend() {
		return projectend;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public void setProjectcontent(String projectcontent) {
		this.projectcontent = projectcontent;
	}

	public void setProjectowner(String projectowner) {
		this.projectowner = projectowner;
	}

	public void setProjectmember(String projectmember) {
		this.projectmember = projectmember;
	}

	public void setProjectstart(Date projectstart) {
		this.projectstart = projectstart;
	}

	public void setProjectend(Date projectend) {
		this.projectend = projectend;
	}

	@Override
	public String toString() {
		return "ProjectCreateData [projectid=" + projectid + ", projectname=" + projectname + ", projectcontent="
				+ projectcontent + ", projectowner=" + projectowner + ", projectmember=" + projectmember
				+ ", projectstart=" + projectstart + ", projectend=" + projectend + "]";
	}
		
	
}
