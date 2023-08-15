package com.pxp.springbatch13thaug2023;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

public class PersonItemWriter implements ItemWriter<Person> {
    @Override
    public void write(Chunk<? extends Person> chunk) throws Exception {
        chunk.getItems()
                .stream()
                .forEach(System.out::println);
    }
}
