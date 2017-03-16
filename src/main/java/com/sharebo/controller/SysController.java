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

	// ���һ������ʱ����
	@RequestMapping("addJob")
	public Object add(String jobName, String backUrl, Integer seconds, Integer errormaxcount,
			String context, String jobGorupName, String token,Long startDate) {
		// ��֤ǩ��
		if (!MD5Util.GetMD5Code(jobName + Config.token).equals(token)) {
			return "val token fasle!";
		}
		// �õ�����ʱʱ��
		errormaxcount = errormaxcount == null ? 1 :errormaxcount;// �ص�����ʧ�ܵĴ���
		TaskInfo t = new TaskInfo(backUrl, jobName, seconds, context, errormaxcount, jobGorupName);
		return server.addJob(jobName, com.sharebo.callback.JobBack.class, t, seconds, jobGorupName,new Date(startDate));
	}

	// ɾ��һ������ʱ����
	@RequestMapping("removeJob")
	public Object remove(String jobName, String jobGorupName,String token) {
		// ��֤ǩ��
		if (!MD5Util.GetMD5Code(jobName + Config.token).equals(token)) {
			return "val token fasle!";
		}
		return server.closeJob(jobName, jobGorupName);
	}

	// ͨ��������ѯ
	@RequestMapping("monitoringJob")
	public Object monitoringJob(String jobGroupName) {
		return server.monitoringJob(jobGroupName);
	}

	/*
	 * //�����ݿ���ػ�δִ�е�����spring������ʼ����ʱ����Զ����أ�
	 * 
	 * @RequestMapping("resume") public Object resume(String name) {
	 * server.resumeJob(); return "OK"; }
	 */
}
