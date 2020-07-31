package com.qin.config;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.qin.batch.BatchInsertPoetryWriter;
import com.qin.batch.BatchPoetryProcesser;
import com.qin.batch.BatchPoetryReader;
import com.qin.domain.Poetry;
import com.qin.listener.JobListener;
import com.qin.service.PoetryService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;

import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
public class BatchConfig {
    private static final Logger log = LoggerFactory.getLogger(BatchConfig.class);
    @Autowired
    private JobBuilderFactory jobBuilderFactory;//用于构建JOB
    @Autowired
    private StepBuilderFactory stepBuilderFactory;//用于构建Step

    @Autowired
    private SqlSessionFactory sqlSessionFactory;//注入实例化Factory 访问数据

    @Qualifier("batchPoetryProcesser")
    @Autowired
    private ItemProcessor itemProcessor ;

    @Autowired
    private BatchInsertPoetryWriter batchInsertPoetryWriter ;

    @Autowired
    private PoetryService poetryService ;

    @Resource
    private JobListener jobListener ;////简单的JOB listener

    private static final String JOB = "job";

    private static final String STEP = "step";

    @Bean(name = JOB)
    public Job job() {

        return jobBuilderFactory.get(JOB).incrementer(new RunIdIncrementer()).start(step())//第一个step
                //.next(xxxstep()).next(xxxstep).
                .listener(jobListener).build();
    }

    @Bean(name = STEP)
    public Step step() {

        return stepBuilderFactory.get(STEP).<Poetry, Poetry>chunk(1024)// <输入,输出> 。chunk通俗的讲类似于SQL的commit; 这里表示处理(processor)100条后写入(writer)一次。

                .faultTolerant().retryLimit(3).retry(Exception.class).skipLimit(100).skip(Exception.class)//捕捉到异常就重试,重试100次还是异常,JOB就停止并标志失败
                .reader(itemReader())//指定itemReader
                .processor(processor())  //指定processor
                .writer(batchInsertPoetryWriter)//指定itemWriter
                .build();
    }

    @Bean
    public  BatchPoetryReader itemReader(){
  /*      ArrayList<Object> list = Lists.newArrayList();
        for (int i=1 ; i<=100 ;i++){
            list.add(i);
        }*/
      /*  log.debug("读取到数据一共：{}",list.size());*/
     /*   Integer [] ids = new Integer[list.size()];
        Integer[] idsNew = list.toArray(ids);*/
        List<Poetry> poetryList = poetryService.findPoetryListByIds();

        return  new BatchPoetryReader(poetryList);
    }

    @Bean
    public BatchInsertPoetryWriter itemWriter(){
        return  new BatchInsertPoetryWriter();
    }

    @Bean
    public BatchPoetryProcesser processor(){
        return new BatchPoetryProcesser();
    }


/*
    @Bean(name = "itemReader")
    public ItemReader itemReader() {
        long startTime = System.currentTimeMillis();
        log.info("开始读取数据...");
        //读取数据 ： mybaits
        MyBatisCursorItemReader<Poetry> reader = new MyBatisCursorItemReader<>();
        //构建查询接口参数
        Map<String, Object> map = Maps.newHashMap();
        ArrayList<Object> list = Lists.newArrayList();
        for (int i=1 ; i<=100 ;i++){
            list.add(i);
        }
        Integer [] ids = new Integer[list.size()];
        Integer[] idsNew = list.toArray(ids);
        map.put("ids", idsNew);
        reader.setQueryId("com.qin.repository.PoetryMapper.queryPoetryByIds");//注入接口的包路径
        reader.setSqlSessionFactory(sqlSessionFactory);//注入数据访问层对象
        reader.setParameterValues(map);//注入参数
        log.info("读取数据完毕,耗时:{}ms",System.currentTimeMillis()-startTime);
        return reader;
    }
*/


    /*//写入逻辑
    @Bean(name = "itemWriter")
    FlatFileItemWriter<Poetry> itemWriter() {

        log.info("开始写入数据...");
        MyBatisBatchItemWriter<Poetry> itemWriter = new MyBatisBatchItemWriter<>();
        itemWriter.setSqlSessionFactory(sqlSessionFactory);
        itemWriter.setAssertUpdates(true);
        itemWriter.setStatementId("com.qin.repository.PoetryCopyDataMapper.batchInsertPoetryData");
        itemWriter.setSqlSessionTemplate( new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH));
        itemWriter.afterPropertiesSet();
        itemWriter.write();
        return null ;
*/





       /* FlatFileItemWriter<Poetry> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource("D:\\poetry.txt"));//指定写入的txt文件
        writer.setLineAggregator(new LineAggregator<Poetry>() {
            @Override
            public String aggregate(Poetry poetry) {
                ObjectMapper mapper = new ObjectMapper();
                String str = null;

                try {
                    str = mapper.writeValueAsString(poetry);
                    log.info("数据写入成功!!!");
                } catch (IOException e) {
                    e.printStackTrace();
                }


                return str;
            }
        });

        return writer;*/
   // }
}
