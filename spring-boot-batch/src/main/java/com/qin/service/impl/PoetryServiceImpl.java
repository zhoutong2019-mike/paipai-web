package com.qin.service.impl;

import com.qin.domain.Poetry;
import com.qin.repository.PoetryMapper;
import com.qin.service.PoetryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class PoetryServiceImpl implements PoetryService {

    @Resource
    private PoetryMapper poetryMapper ;
    @Override
    public List<Poetry> findPoetryListByIds() {
  /*      if (ids==null ||ids.length==0) {
            return new ArrayList<Poetry>();
        }*/
        List<Poetry> poetries = poetryMapper.queryPoetryByIds();
        if (poetries !=null){
            return poetries ;
        }
        return new ArrayList<Poetry>();
    }
}
