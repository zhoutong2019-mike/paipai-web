package com.qin.batch;

import com.qin.domain.Poetry;
import com.qin.service.PoetryService;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

public class BatchPoetryReader implements ItemReader<Poetry> {

    private  final Iterator<Poetry> iterator ;

    @Resource
    private PoetryService poetryService ;

    public BatchPoetryReader(List<Poetry> data) {
        this.iterator = data.iterator();
    }

    @Override
    public Poetry read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (iterator.hasNext()) {
            return this.iterator.next();
        } else {
            return null;
        }
    }

}
