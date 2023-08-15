package com.pxp.springbatch13thaug2023;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class PersonItemReader implements ItemReader<String> {

    private int counter;

    public PersonItemReader(long counter) {
        this.counter = Math.toIntExact(counter);
    }

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (counter < 10) {
            counter ++;
//            if (counter == 8)
//                throw new RuntimeException("Forced failure");
            return "Counter value is " + counter;
        } else {
            return null;
        }
    }
}
