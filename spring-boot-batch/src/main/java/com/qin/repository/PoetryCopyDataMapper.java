package com.qin.repository;

import com.qin.domain.Poetry;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PoetryCopyDataMapper extends Mapper<Poetry> {


    Integer batchInsertPoetryData(List<? extends Poetry> items);
}
