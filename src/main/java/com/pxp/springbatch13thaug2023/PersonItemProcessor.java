package com.pxp.springbatch13thaug2023;

import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {
    @Override
    public Person process(Person item) throws Exception {
//        if (item.getFirstName().equalsIgnoreCase("William"))
//            throw new RuntimeException("Throwing exception forcefully");
        item.setFirstName(item.getFirstName().toUpperCase());
        item.setLastName(item.getLastName().toUpperCase());
        return item;
    }
}
