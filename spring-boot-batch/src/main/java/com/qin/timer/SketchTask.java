package com.qin.timer;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SketchTask {

    @Autowired
    private  JobLauncher jobLauncher ;

    @Resource(name = "job")
    private Job job ;

    @Scheduled(cron ="0 */2 * * * ?" )
    public  void startBatch(){

        JobParameters timeparam = new JobParametersBuilder().addLong("timeparam", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(job,timeparam);
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }

    }

}
