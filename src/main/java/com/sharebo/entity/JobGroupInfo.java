package com.sharebo.entity;

public class JobGroupInfo {
	private String jobName;//��������
	private String jobGroupName;//������
	private Long nextFireTime;//�´�ִ��ʱ��
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroupName() {
		return jobGroupName;
	}
	public void setJobGroupName(String jobGroupName) {
		this.jobGroupName = jobGroupName;
	}
	public Long getNextFireTime() {
		return nextFireTime;
	}
	public void setNextFireTime(Long nextFireTime) {
		this.nextFireTime = nextFireTime;
	}
	public JobGroupInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public JobGroupInfo(String jobName, String jobGroupName, Long nextFireTime) {
		super();
		this.jobName = jobName;
		this.jobGroupName = jobGroupName;
		this.nextFireTime = nextFireTime;
	}
	
}
