package com.sharebo.callback;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.sharebo.config.Config;
import com.sharebo.entity.TaskInfo;
import com.sharebo.util.HttpUtil;
import com.sharebo.util.MD5Util;

/**
 * 执行回调任务
 * @author niewei
 *
 */
public class JobBack implements Job {
	//回调执行方法
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//得到添加任务中的参数
		TaskInfo task = (TaskInfo) context.getMergedJobDataMap().get("task");
		sendBack(task);
	}

	private void sendBack(TaskInfo task) {
		task.setErrormaxcount(task.getErrormaxcount() - 1);
		// 得到参数请求回调
		try {
			//拼装请求地址以及参数
			String uri = "jobName=" + task.getJobName() + "&context=" + task.getContext() + "&jobGorupName="
					+ task.getJobGorupName() + "&paramkey=" + MD5Util.GetMD5Code(task.getJobName()+Config.token);
			//请求并得到返回值
			String res = HttpUtil.request_post(task.getBackUrl(), uri);
			//如果返回值不是“SUCCESS” 就等待10S进行重复请求（此处避免请求失败就结束请求，参数中传递了一个失败请求次数）
			if (!res.trim().equals("SUCCESS")) {
				//如果请求错误次数还大于0
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
