package com.qin.repository;

import com.qin.domain.Poetry;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface PoetryMapper extends Mapper<Poetry> {


     List<Poetry> queryPoetryByIds() ;

     @Select("select * from poetry where id = #{id}")
     Poetry queryPoetryById(Map<String,Integer> map) ;


}
