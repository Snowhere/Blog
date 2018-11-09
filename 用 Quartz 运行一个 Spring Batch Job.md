# 用 Quartz 运行一个 Spring Batch Job
# Run a Spring Batch Job With Quartz
[原文链接](https://dzone.com/articles/spring-batch-with-quartz)

Hey, folks. In this tutorial, we will see how a Spring Batch job runs using a Quartz scheduler. If you are not sure about the basics of Spring Batch, you can visit my tutorial here. 

大家好。在这篇文章中，我们将看到如何使用 Quartz scheduler 来运行一个 Spring Batch job。如果你没有使用 Spring Batch 的基础，可以看这篇[文章](https://dzone.com/articles/spring-batch) 

Now, as we know, Spring Batch jobs are used whenever we want to run any business-specific code or run/generate any reports at any particular time/day. There are two ways to implement jobs: tasklet  and chunks . In this tutorial, I will create a simple job using a tasklet, which will print a  logger. The basic idea here is what all configurations are required to make this job run. We will use Spring Boot to bootstrap our application.

正如我们所知，如果我们可以使用 Spring Batch jobs 在特定的时间或日期运行特定的业务代码或生成记录。有两种方式实现 jobs（任务）：`tasklet` 和 `chunks`。在这篇文章中，我将使用 `tasklet` 创建一个简单的 job（任务），来打印 `logger`（日志）。主要目的是展示任务运行所需要的所有配置。我们用 Spring Boot 来引导启动我们的工程应用。

We require below two dependencies in pom.xml for having Spring Batch and Quartz in our application.

为了把 Spring Batch 和 Quartz 引入工程，我们需要在 pom.xml 文件添加下面两个依赖。

```
<!-- https://mvnrepository.com/artifact/org.springframework.batch/spring-batch-core -->
<dependency>
    <groupId>org.springframework.batch</groupId>
    <artifactId>spring-batch-core</artifactId>
    <version>4.0.1.RELEASE</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.quartz-scheduler/quartz -->
<dependency>
    <groupId>org.quartz-scheduler</groupId>
    <artifactId>quartz</artifactId>
    <version>2.3.0</version>
</dependency>
```

Now, let's see what all configurations we require in our code to run the job.

现在让我们看看任务运行所需要的全部配置。

1. BatchConfiguration.java:
```
package com.category.batch.configurations;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.configuration.support.AutomaticJobRegistrar;
import org.springframework.batch.core.configuration.support.DefaultJobLoader;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
@Configuration
@EnableBatchProcessing
@Import({
    BatchJobsDetailedConfiguration.class
})
public class BatchConfiguration {
    @Bean
    public JobRegistry jobRegistry() {
        return new MapJobRegistry();
    }
    @Bean
    public ResourcelessTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
    }
    @Bean
    public JobRepository jobRepository(ResourcelessTransactionManager transactionManager) throws Exception {
        MapJobRepositoryFactoryBean mapJobRepositoryFactoryBean = new MapJobRepositoryFactoryBean(transactionManager);
        mapJobRepositoryFactoryBean.setTransactionManager(transactionManager);
        return mapJobRepositoryFactoryBean.getObject();
    }
    @Bean
    public JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
        SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
        simpleJobLauncher.setJobRepository(jobRepository);
        simpleJobLauncher.afterPropertiesSet();
        return simpleJobLauncher;
    }
    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry());
        return jobRegistryBeanPostProcessor;
    }
}
```

Let's go one-by-one:

让我们来一个一个看：

* @Configuration: This specifies that this class will contain beans and will be instantiated at load time.
* @EnableBatchProcessing: This enables the Spring Batch features and provides a base configuration for setting up batch jobs.
* @Import({BatchJobsDetailedConfiguration.class}): This will import some other configurations required, which we will see later.
* JobRegistry: This interface is used to register the jobs.
* ResourcelessTransactionManager: This class is used when you want to run the job using any database persistence.
* JobRepository: This contains all the metadata of the job, which returns a  MapJobRepositoryFactoryBean used for non-persistent DAO implementations.
* JobLauncher: This is used to launch a job, requires jobRepository as a dependency.
* JobRegistryBeanPostProcessor: This is used to register a job in the jobRegistry, which returns the  jobRegistry.

* `@Configuration`：指明这个类将包含 bean 并且加载时实例化。
* `@EnableBatchProcessing`：开启 Spring Batch 特性，为批处理任务提供一个基础配置。
* `@Import({BatchJobsDetailedConfiguration.class})`：引入其他所需配置，后面我们会提到。
* `JobRegistry`：注册任务的接口。
* `ResourcelessTransactionManager`：当运行涉及数据库的任务时需要这个类。
* `JobRepository`：存放任务的所有信息，返回一个非固定的DAO实现——`MapJobRepositoryFactoryBean`
* `JobLauncher`：启动任务，需要依赖 jobRepository 
* `JobRegistryBeanPostProcessor`：在 `jobRegistry` 中注册任务，返回 `jobRegistry`。

Let's go to the imported class now.

我们来看看引入的类。

2. BatchJobsDetailedConfiguration.java:
```
package com.category.batch.configurations;
import java.util.HashMap;
import java.util.Map;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.configuration.support.GenericApplicationContextFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import com.category.batch.job.JobLauncherDetails;
import com.category.batch.reports.config.ReportsConfig;
@Configuration
public class BatchJobsDetailedConfiguration {
    @Autowired
    private JobLauncher jobLauncher;
    @Bean(name = "reportsDetailContext")
    public ApplicationContextFactory getApplicationContext() {
        return new GenericApplicationContextFactory(ReportsConfig.class);
    }
    @Bean(name = "reportsDetailJob")
    public JobDetailFactoryBean jobDetailFactoryBean() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(JobLauncherDetails.class);
        jobDetailFactoryBean.setDurability(true);
        Map < String, Object > map = new HashMap < > ();
        map.put("jobLauncher", jobLauncher);
        map.put("jobName", ReportsConfig.jobName);
        jobDetailFactoryBean.setJobDataAsMap(map);
        return jobDetailFactoryBean;
    }
    @Bean(name = "reportsCronJob")
    public CronTriggerFactoryBean cronTriggerFactoryBean() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(jobDetailFactoryBean().getObject());
        cronTriggerFactoryBean.setCronExpression("0 0/1 * 1/1 * ? *");
        return cronTriggerFactoryBean;
    }
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobRegistry jobRegistry) throws NoSuchJobException {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(cronTriggerFactoryBean().getObject());
        schedulerFactoryBean.setAutoStartup(true);
        Map < String, Object > map = new HashMap < > ();
        map.put("jobLauncher", jobLauncher);
        map.put("jobLocator", jobRegistry);
        schedulerFactoryBean.setSchedulerContextAsMap(map);
        return schedulerFactoryBean;
    }
}
```

Let's dig into this:

* ApplicationContextFactory: This interface is primarily useful when creating a new ApplicationContext per execution of a job. It's better to create a sepearte applicationContext for each job.
* JobDetailFactoryBean: This is used to create a Quartz job detail instance. This class will set a job class, which we will see later. It creates a map that will define and set the job name using a class and joblauncher.
* CronTriggerFactoryBean: This is used to create a Quartz cron trigger instance. This will set the jobDetail created earlier and then the cron expression when this job will run. You can set the cron expressions as per your need. Cron expressions can be calculated from http://cronmaker.com.
* SchedulerFactoryBean: This is used to create a Quartz scheduler instance and allows for the registration of JobDetails , Calendars , and Triggers, automatically starting the scheduler on initialization and shutting it down on destruction.

* `ApplicationContextFactory`：为任务每次执行创建一个新的 `ApplicationContext`。每个任务最好创建单独的 `applicationContext`。
* `JobDetailFactoryBean`：创建一个 Quzrtz job detail（任务详情）实例。后面我们会看到它将设置一个任务类。它创建了一个 `map` 来定义并设置任务名称和 `joblauncher`（启动器）
* `CronTriggerFactoryBean`：用于创建一个 Quzrtz `cron` 触发器实例。它设置了前边创建的 `jobDetail` 和一个表示任务何时运行的 `cron` 表达式。你可以根据需要设置 `cron` 表达式，可以在 [http://cronmaker.com](http://cronmaker.com) 生成 `cron` 表达式。
* `SchedulerFactoryBean`：用来创建一个 Quartz scheduler（调度器）实例，允许注册 `JobDetails`、`Calendars` 和 `Triggers`。类初始化时自动运行调度器，类销毁时自动关闭调度器。

Let's check out the JobLauncherDetails class:

让我们看一下 `JobLauncherDetails` 类：

3. JobLauncherDetails.java:
```
package com.category.batch.job;
import java.util.Map;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.QuartzJobBean;
public class JobLauncherDetails extends QuartzJobBean {
    static final String JOB_NAME = "jobName";
    public void setJobLocator(JobLocator jobLocator) {
        this.jobLocator = jobLocator;
    }
    public void setJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }
    private JobLocator jobLocator;
    private JobLauncher jobLauncher;
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
        try {
            Map < String, Object > jobDataMap = jobExecutionContext.getMergedJobDataMap();
            String jobName = (String) jobDataMap.get(JOB_NAME);
            jobLauncher.run(jobLocator.getJob(jobName), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
            |
            JobParametersInvalidException e) {
            e.printStackTrace();
        } catch (NoSuchJobException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
```

This class has an overridden executeInternal method of the QuartzJobBean class, which takes the jobdetails from the map, which were already set before some of the jobParameters , and then executes the jobLauncher.run() to run the job as seen in the code.

这个类重写了 `QuartzJobBean` 类的 `executeInternal` 方法，从 

Lets visit the ReportsConfig class.

4.ReportsConfig.java:
```
package com.category.batch.reports.config;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.DuplicateJobException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.ReferenceJobFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.category.batch.reports.tasklet.ReportTasklet;
@Configuration
public class ReportsConfig {
    @Autowired
    private JobRegistry jobRegistry;
    public final static String jobName = "ReportsJob1";
    public JobBuilderFactory getJobBuilderFactory() {
        return jobBuilderFactory;
    }
    public void setJobBuilderFactory(JobBuilderFactory jobBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
    }
    @Autowired
    private Tasklet taskletstep;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Bean
    public ReportTasklet reportTasklet() {
        return new ReportTasklet();
    }
    @Bean
    public Job job() throws DuplicateJobException {
        Job job = getJobBuilderFactory().get(jobName).start(getStep()).build();
        return job;
    }
    @Bean
    public Step getStep() {
        return stepBuilderFactory.get("step").tasklet(reportTasklet()).build();
    }
}
```

The main purpose of the class is having configurations related to each job. You will have a separate config for each job as this. As you can see, we create the tasklet here, which we will see later. Also, we define and return the Job, step using a JobBuilderFactory,  and StepBuilderFactory. These factories will automatically set the JobRepository for you.

Let's go to the ReportTasklet, which is our job to be run.

5. ReportTasklet.java:
```
package com.category.batch.reports.tasklet;
import java.util.logging.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Configuration;
@Configuration
public class ReportTasklet implements Tasklet {
    private static final Logger logger = Logger.getLogger(ReportTasklet.class.getName());
    @Override
    public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) {
        try {
            logger.info("Report's Job is running. Add your business logic here.........");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RepeatStatus.FINISHED;
    }
}
```

This class has a execute method that will be ran when the job is ran through the jobLauncher.run() from the JobLauncherDetails class. You can define your business logic that needs to be executed here.

We will need some configuration in application.properties as below:

6.  application.properties
```
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
spring.batch.job.enabled=false
```

The first property is required to disable the datasource— only for testing purposes and is not required in production.

The second property is when before the server starts, the job is run. To avoaid this, we require this property.

Now, finally, let's go to the application class. This should be self-explanatory.

7. BatchApplication.java:
```
package com.category.batch;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class BatchApplication {
public static void main(String[] args) {
SpringApplication.run(BatchApplication.class, args);
}
}
```

Enough Configurations! Let's run this application and see the output. We have set the cron to 1 minute. After 1 minute, the job will be run.
```
2018-09-14 11:04:18.648 INFO 7008 --- [ost-startStop-1] org.quartz.impl.StdSchedulerFactory : Quartz scheduler 'schedulerFactoryBean' initialized from an externally provided properties instance.
2018-09-14 11:04:18.648 INFO 7008 --- [ost-startStop-1] org.quartz.impl.StdSchedulerFactory : Quartz scheduler version: 2.3.0
2018-09-14 11:04:18.653 INFO 7008 --- [ost-startStop-1] org.quartz.core.QuartzScheduler : JobFactory set to: org.springframework.scheduling.quartz.AdaptableJobFactory@b29e7d6
2018-09-14 11:04:19.578 INFO 7008 --- [ost-startStop-1] o.s.w.s.handler.SimpleUrlHandlerMapping : Mapped URL path [/**/favicon.ico] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2018-09-14 11:04:20.986 INFO 7008 --- [ost-startStop-1] s.w.s.m.m.a.RequestMappingHandlerAdapter : Looking for @ControllerAdvice: org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext@74a6e16d: startup date [Fri Sep 14 11:04:12 IST 2018]; root of context hierarchy
2018-09-14 11:04:21.264 INFO 7008 --- [ost-startStop-1] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/error]}" onto public org.springframework.http.ResponseEntity<java.util.Map<java.lang.String, java.lang.Object>> org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController.error(javax.servlet.http.HttpServletRequest)
2018-09-14 11:04:21.268 INFO 7008 --- [ost-startStop-1] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/error],produces=[text/html]}" onto public org.springframework.web.servlet.ModelAndView org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController.errorHtml(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse)
2018-09-14 11:04:21.356 INFO 7008 --- [ost-startStop-1] o.s.w.s.handler.SimpleUrlHandlerMapping : Mapped URL path [/webjars/**] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2018-09-14 11:04:21.356 INFO 7008 --- [ost-startStop-1] o.s.w.s.handler.SimpleUrlHandlerMapping : Mapped URL path [/**] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2018-09-14 11:04:22.526 INFO 7008 --- [ost-startStop-1] o.s.j.e.a.AnnotationMBeanExporter : Registering beans for JMX exposure on startup
2018-09-14 11:04:22.555 INFO 7008 --- [ost-startStop-1] o.s.c.support.DefaultLifecycleProcessor : Starting beans in phase 2147483647
2018-09-14 11:04:22.556 INFO 7008 --- [ost-startStop-1] o.s.s.quartz.SchedulerFactoryBean : Starting Quartz Scheduler now
2018-09-14 11:04:22.556 INFO 7008 --- [ost-startStop-1] org.quartz.core.QuartzScheduler : Scheduler schedulerFactoryBean_$_NON_CLUSTERED started.
2018-09-14 11:04:22.578 INFO 7008 --- [ost-startStop-1] com.category.batch.ServletInitializer : Started ServletInitializer in 17.386 seconds (JVM running for 26.206)
2018-09-14 11:04:23.395 INFO 7008 --- [ main] org.apache.coyote.ajp.AjpNioProtocol : Starting ProtocolHandler ["ajp-nio-8009"]
2018-09-14 11:04:23.399 INFO 7008 --- [ main] org.apache.catalina.startup.Catalina : Server startup in 24866 ms
2018-09-14 11:05:02.889 INFO 7008 --- [ryBean_Worker-1] o.s.b.c.l.support.SimpleJobLauncher : Job: [SimpleJob: [name=ReportsJob1]] launched with the following parameters: [{time=1536903301155}]
2018-09-14 11:05:03.262 INFO 7008 --- [ryBean_Worker-1] o.s.batch.core.job.SimpleStepHandler : Executing step: [step]
2018-09-14 11:05:03.503 INFO 7008 --- [ryBean_Worker-1] c.c.batch.reports.tasklet.ReportTasklet : Report's Job is running. Add your business logic here.........
2018-09-14 11:05:03.524 INFO 7008 --- [ryBean_Worker-1] o.s.b.c.l.support.SimpleJobLauncher : Job: [SimpleJob: [name=ReportsJob1]] completed with the following parameters: [{time=1536903301155}] and the following status: [COMPLETED]
```

The bold lines indicate your job ran and completed successfully. That's all for this tutorial. Please comment if you would like to add anything. Happy learning!