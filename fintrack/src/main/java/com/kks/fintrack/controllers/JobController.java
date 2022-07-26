package com.kks.fintrack.controllers;

import java.time.Instant;
import java.time.ZoneId;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/job")
public class JobController {

  @Autowired
  JobLauncher jobLauncher;

  @Autowired
  @Qualifier("historicalDataFetcherAsyncJob")
  Job historicalDataFetcherAsyncJob;

  @Autowired
  @Qualifier("recentDataFetcherAsyncJob")
  Job recentDataFetcherAsyncJob;

  @GetMapping("/historicaldatafetcher")
  public String historicalDataFetcher() throws Exception {

    JobParameters jobParameters = new JobParametersBuilder()
        .addString("source", Instant.now().atZone(ZoneId.of("Asia/Kolkata")).toString()).toJobParameters();
    jobLauncher.run(historicalDataFetcherAsyncJob, jobParameters);
    return "Success";
  }

  @GetMapping("/recentdatafetcher")
  public String recentDataFetcherString() throws Exception {
    JobParameters jobParameters = new JobParametersBuilder()
        .addString("source", Instant.now().atZone(ZoneId.of("Asia/Kolkata")).toString()).toJobParameters();
    jobLauncher.run(recentDataFetcherAsyncJob, jobParameters);
    return "Success";
  }

}
