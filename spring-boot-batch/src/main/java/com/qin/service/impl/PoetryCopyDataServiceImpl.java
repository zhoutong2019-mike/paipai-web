package com.qin.service.impl;

import com.qin.domain.Poetry;
import com.qin.repository.PoetryCopyDataMapper;
import com.qin.service.PoetryCopyDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class PoetryCopyDataServiceImpl implements PoetryCopyDataService {



    @Resource
    private PoetryCopyDataMapper poetryCopyDataMapper ;

    @Override
    public boolean batchInsertPoetryData(List<? extends Poetry> items) {
        if(items == null || items.size()==0)
            return false ;
        Integer lines = poetryCopyDataMapper.batchInsertPoetryData(items);



        return false;
    }
}
