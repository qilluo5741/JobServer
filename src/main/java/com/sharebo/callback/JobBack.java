package com.sharebo.callback;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.sharebo.config.Config;
import com.sharebo.entity.TaskInfo;
import com.sharebo.util.HttpUtil;
import com.sharebo.util.MD5Util;

/**
 * ִ�лص�����
 * @author niewei
 *
 */
public class JobBack implements Job {
	//�ص�ִ�з���
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//�õ���������еĲ���
		TaskInfo task = (TaskInfo) context.getMergedJobDataMap().get("task");
		sendBack(task);
	}

	private void sendBack(TaskInfo task) {
		task.setErrormaxcount(task.getErrormaxcount() - 1);
		// �õ���������ص�
		try {
			//ƴװ�����ַ�Լ�����
			String uri = "jobName=" + task.getJobName() + "&context=" + task.getContext() + "&jobGorupName="
					+ task.getJobGorupName() + "&paramkey=" + MD5Util.GetMD5Code(task.getJobName()+Config.token);
			//���󲢵õ�����ֵ
			String res = HttpUtil.request_post(task.getBackUrl(), uri);
			//�������ֵ���ǡ�SUCCESS�� �͵ȴ�10S�����ظ����󣨴˴���������ʧ�ܾͽ������󣬲����д�����һ��ʧ�����������
			if (!res.trim().equals("SUCCESS")) {
				//�������������������0
				if (task.getErrormaxcount() >= 1) {
					Thread.sleep(10000);
					sendBack(task);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			if (task.getErrormaxcount() >= 1) {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				sendBack(task);
			}
		}
	}

}
