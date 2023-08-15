package com.pxp.springbatch13thaug2023;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class PersonItemListner implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println(stepExecution.getExitStatus());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if (stepExecution.getExitStatus().getExitCode().equalsIgnoreCase("FAILED")){
            stepExecution.setExitStatus(new ExitStatus("This was a forced failure", "We made the job to fail"));
        }
        return stepExecution.getExitStatus();
    }
}
