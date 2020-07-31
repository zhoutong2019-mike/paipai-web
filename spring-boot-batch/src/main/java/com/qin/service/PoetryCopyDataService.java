package com.qin.service;

import com.qin.domain.Poetry;

import java.util.List;

public interface PoetryCopyDataService {


    boolean batchInsertPoetryData(List<? extends Poetry> items);
}
