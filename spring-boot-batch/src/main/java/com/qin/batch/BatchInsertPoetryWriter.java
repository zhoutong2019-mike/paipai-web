package com.qin.batch;

import com.qin.config.BatchConfig;
import com.qin.domain.Poetry;
import com.qin.service.PoetryCopyDataService;


import groovy.util.logging.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Slf4j
public class BatchInsertPoetryWriter implements ItemWriter<Poetry> {
    private static final Logger log = LoggerFactory.getLogger(BatchInsertPoetryWriter.class);
    @Autowired
    private PoetryCopyDataService poetryCopyDataService ;

    @Override
    public void write(List<? extends Poetry> items) throws Exception {

        try {
            log.debug("开始写入数据 ， 批量写入数据量：{}",items.size());
            poetryCopyDataService.batchInsertPoetryData(items);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("批量写数据失败：",e);
        }

    }
}
