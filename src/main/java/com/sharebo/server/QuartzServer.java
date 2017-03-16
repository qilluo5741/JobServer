package com.sharebo.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.core.jmx.JobDetailSupport;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sharebo.entity.JobGroupInfo;

@Component
public class QuartzServer {
	@Autowired
	private Scheduler scheduler;
	private static String JOB_GROUP_NAME = "JOB_GROUP_";

	/**
	 * �������
	 * 
	 * @param jobName
	 *            ��������
	 * @param job
	 *            �������� ��Ҫ�̳�Job
	 * @param context
	 *            ����������Ի�ȡ��������
	 *            ͨ��context.getMergedJobDataMap().getString("context"); ��ȡ
	 * @param seconds
	 *            �����
	 * @return 0 ��ӳɹ� 1�������Ѿ����� 2������쳣
	 */
	public int addJob(String jobName, Class<? extends Job> job, Object task, int seconds, String jobGorupName,
			Date date) {
		try {
			// �ж������Ƿ����
			JobKey jobKey = JobKey.jobKey(jobName, jobGorupName);
			if (scheduler.checkExists(jobKey)) {
				return 1;// �����Ѿ�����
			}
			// ����һ��JobDetailʵ����ָ��SimpleJob
			Map<String, Object> JobDetailmap = new HashMap<String, Object>();
			JobDetailmap.put("name", jobName);// ������������
			JobDetailmap.put("group", jobGorupName);// ����������
			JobDetailmap.put("jobClass", job.getCanonicalName());// ָ��ִ����
			JobDetail jobDetail = JobDetailSupport.newJobDetail(JobDetailmap);
			// �����������
			jobDetail.getJobDataMap().put("task", task);// �����������
			// ͨ��SimpleTrigger������ȹ�������������ÿ2������һ�Σ�������100�� �ȡ�������
			SimpleTriggerImpl simpleTrigger = new SimpleTriggerImpl();
			simpleTrigger.setName(jobName);
			simpleTrigger.setGroup(JOB_GROUP_NAME);
			// ʲôʱ��ʼִ��
			simpleTrigger.setStartTime(new Date(date.getTime() + 1000 * seconds));
			// ���ʱ��
			simpleTrigger.setRepeatInterval(1000 * seconds);
			// ���ִ�д��� Ĭ��ִ��һ��
			simpleTrigger.setRepeatCount(0);
			// ͨ��SchedulerFactory��ȡһ��������ʵ��
			scheduler.scheduleJob(jobDetail, simpleTrigger);// ע�Ტ���е���
			scheduler.start();// �ݵ�������
			return 0;// ��ӳɹ�
		} catch (Exception e) {
			e.printStackTrace();
			return 2;// �����쳣
		}
	}

	/**
	 * �ر��������
	 * 
	 * @param jobName
	 *            ��������
	 * @return 0 �رճɹ� 1�� �ر�ʧ�� 2�������쳣
	 */
	public int closeJob(String jobName, String jobGorupName) {
		// �ر��������
		try {
			JobKey jobKey = JobKey.jobKey(jobName, jobGorupName);
			return scheduler.deleteJob(jobKey) == true ? 0 : 1;
		} catch (SchedulerException e) {
			// e.printStackTrace();
			return 2;
		}
	}
	/**
	 * ͨ��������ѯȫ��ʣ�������
	 * @param jobGroupName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<JobGroupInfo> monitoringJob(String jobGroupName) {
		// ��֤ǩ��
		try {
			List<JobGroupInfo> list = new ArrayList<JobGroupInfo>();
			for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(jobGroupName == null ? "" : jobGroupName))) {
				String jobName = jobKey.getName();
				String jobGroup = jobKey.getGroup();
				// get job's trigger
				List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
				Date nextFireTime = triggers.get(0).getNextFireTime(); // ��һ��ִ��ʱ�䡢
				JobGroupInfo gri = new JobGroupInfo(jobName, jobGroup, nextFireTime.getTime());
				list.add(gri);
			}
			return list;
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �����ݿ����ҵ��Ѿ����ڵ�job�������¿�������
	 */
	public void resumeJob() {
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}