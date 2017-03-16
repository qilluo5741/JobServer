package com.sharebo.controller;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sharebo.config.Config;
import com.sharebo.entity.TaskInfo;
import com.sharebo.server.QuartzServer;
import com.sharebo.util.MD5Util;

@RestController
public class SysController {
	@Autowired
	QuartzServer server;

	// 添加一个倒计时任务
	@RequestMapping("addJob")
	public Object add(String jobName, String backUrl, Integer seconds, Integer errormaxcount,
			String context, String jobGorupName, String token,Long startDate) {
		// 验证签名
		if (!MD5Util.GetMD5Code(jobName + Config.token).equals(token)) {
			return "val token fasle!";
		}
		// 得到请求定时时间
		errormaxcount = errormaxcount == null ? 1 :errormaxcount;// 回调请求失败的次数
		TaskInfo t = new TaskInfo(backUrl, jobName, seconds, context, errormaxcount, jobGorupName);
		return server.addJob(jobName, com.sharebo.callback.JobBack.class, t, seconds, jobGorupName,new Date(startDate));
	}

	// 删除一个倒计时任务
	@RequestMapping("removeJob")
	public Object remove(String jobName, String jobGorupName,String token) {
		// 验证签名
		if (!MD5Util.GetMD5Code(jobName + Config.token).equals(token)) {
			return "val token fasle!";
		}
		return server.closeJob(jobName, jobGorupName);
	}

	// 通过组名查询
	@RequestMapping("monitoringJob")
	public Object monitoringJob(String jobGroupName) {
		return server.monitoringJob(jobGroupName);
	}

	/*
	 * //从数据库加载还未执行的任务（spring容器初始化的时候会自动加载）
	 * 
	 * @RequestMapping("resume") public Object resume(String name) {
	 * server.resumeJob(); return "OK"; }
	 */
}
