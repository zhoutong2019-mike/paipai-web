package com.paipai.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.paipai.dao.CommodityMapper;
import com.paipai.entity.Commodity;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

public class MikeUtil {
	//封装方法1  通过类型分页查找
	public static Map<String, Object> findAllByTypeAndPage(CommodityMapper commodityMapper,Integer pageNum, Integer pageSize, int type) {
		Map<String, Object> map = new HashMap<String, Object>();
		Example exa = new Example(Commodity.class);
		Criteria cri = exa.createCriteria();
		cri.andCondition("type=", type);
		int count = commodityMapper.selectCountByExample(exa);
		int pagecount = (int) Math.ceil(count * 1.0 / pageSize);
		PageHelper.startPage(pageNum, pageSize);
		List<Commodity> list = commodityMapper.selectByExample(exa);
		Collections.shuffle(list);//打乱顺序
		map.put("list", list);
		map.put("pagecount", pagecount);
		
		return map;
	}
	//2.验证字符串非空
	public static boolean notNullOrEmpty(String str) {
		if(str==null||"".equals(str))
			return false;
		return true;
	}

}
