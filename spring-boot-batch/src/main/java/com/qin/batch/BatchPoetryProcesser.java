package com.qin.batch;

import com.qin.domain.Poetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component("batchPoetryProcesser")
public class BatchPoetryProcesser implements ItemProcessor<Poetry,Poetry> {

    private static final Logger log = LoggerFactory.getLogger(BatchPoetryProcesser.class);
    @Override
    public Poetry process(Poetry item) throws Exception {

        item.setAuthor(item.getAuthor()+"ZHT");
        log.debug("处理数据：{}",item.getAuthor());

        return item;
    }
}
