package com.pxp.springbatch13thaug2023;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Objects;

@Configuration
public class BatchJobConfiguration {

    @Autowired
    private JobRepository jobRepository;

//    @Bean
//    public PersonItemReader personItemReader() {
//        JobInstance jobInstance = jobRepository.getJobInstance("job", new JobParameters());
//        return new PersonItemReader(Objects.isNull(jobInstance) ? 0L : jobRepository.getLastStepExecution(jobInstance, "step").getWriteCount());
//    }

    @Bean
    public FlatFileItemReader<Person> reader() {
        return new FlatFileItemReaderBuilder<Person>()
                .name("personItemReader")
                .resource(new ClassPathResource("sample-data.csv"))
                .delimited()
                .names(new String[]{"id", "firstName", "lastName", "age"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                    setTargetType(Person.class);
                }})
                .build();
    }

    @Bean
    public PersonItemProcessor personItemProcessor() {
        return new PersonItemProcessor();
    }

    @Bean
    public PersonItemWriter personItemWriter() {
        return new PersonItemWriter();
    }

    @Bean
    public PersonItemListner personItemListner() {
        return new PersonItemListner();
    }

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        JobInstance jobInstance = jobRepository.getJobInstance("job", new JobParameters());
        System.out.println("Job instance id: " + (Objects.isNull(jobInstance) ? 0L : jobInstance));
        System.out.println("Write count: " + (Objects.isNull(jobInstance) ? 0L : jobRepository.getLastStepExecution(jobInstance, "step").getWriteCount()));
        return new StepBuilder("step", jobRepository)
                .<Person, Person>chunk(3, transactionManager)
                .reader(reader())
                .processor(personItemProcessor())
                .writer(personItemWriter())
                .listener(personItemListner())
                .build();
    }

    @Bean
    public Job job(JobRepository jobRepository, Step step) {
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addString("JobInstance", jobRepository.getJobInstance("job", new Jo))
        return new JobBuilder("job", jobRepository)
                .start(step)
                .build();
    }
}
