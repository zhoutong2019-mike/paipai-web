package com.qin.controller;

import com.qin.repository.PoetryMapper;
import com.qin.service.PoetryService;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qin.utils.BaseController;

import javax.annotation.Resource;

@Controller
public class HomeController extends BaseController {

	@Resource
	private PoetryService poetryService ;

	@Resource
	private PoetryMapper poetryMapper ;

	@Resource
	private JobLauncher jobLauncher ;

	@Resource(name = "job")
	private Job job ;// 注入指定的对象

	@RequestMapping("/")
	public String goHome() {
		return "view/home";
	}

	@RequestMapping("/start_batch")
	@ResponseBody
	public String startBatch() {
		String taskName = "task-1" ;//相当于任务名

		try {
			JobParameters jobParameters = new JobParametersBuilder().addString("task",taskName).toJobParameters();
			JobExecution run = jobLauncher.run(job, jobParameters);
		} catch (JobExecutionAlreadyRunningException e) {
			e.printStackTrace();
		} catch (JobRestartException e) {
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			e.printStackTrace();
		}

		return "success";
	}


}
