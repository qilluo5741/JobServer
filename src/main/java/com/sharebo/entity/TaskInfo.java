package com.sharebo.entity;

import java.io.Serializable;

public class TaskInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String backUrl;//任务回调地址
	private String jobName;//任务名字
	private Integer seconds;//计时时间
	private Object context;//其他内容
	private Integer errormaxcount;//失败请求的次数
	private String jobGorupName;//任务组名字
	
	public String getJobGorupName() {
		return jobGorupName;
	}
	public void setJobGorupName(String jobGorupName) {
		this.jobGorupName = jobGorupName;
	}
	public Integer getErrormaxcount() {
		return errormaxcount;
	}
	public void setErrormaxcount(Integer errormaxcount) {
		this.errormaxcount = errormaxcount;
	}
	public String getBackUrl() {
		return backUrl;
	}
	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public Integer getSeconds() {
		return seconds;
	}
	public void setSeconds(Integer seconds) {
		this.seconds = seconds;
	}
	public Object getContext() {
		return context;
	}
	public void setContext(Object context) {
		this.context = context;
	}
	public TaskInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TaskInfo(String backUrl, String jobName, Integer seconds,
			Object context, Integer errormaxcount, String jobGorupName) {
		super();
		this.backUrl = backUrl;
		this.jobName = jobName;
		this.seconds = seconds;
		this.context = context;
		this.errormaxcount = errormaxcount;
		this.jobGorupName = jobGorupName;
	}
	@Override
	public String toString() {
		return "TaskInfo [backUrl=" + backUrl + ", jobName=" + jobName
				+ ", seconds=" + seconds + ", context=" + context
				+ ", errormaxcount=" + errormaxcount + ", jobGorupName="
				+ jobGorupName + "]";
	}
	
	
}
