package com.paipai.service;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.paipai.entity.Commodity;
import com.paipai.utils.MikeUtil;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.pagehelper.PageHelper;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.huaban.analysis.jieba.SegToken;
import com.paipai.dao.CommodityMapper;

@Transactional
@Service
public class CommodityService {

	@Autowired
	private CommodityMapper commodityMapper;

	@Autowired
	private StringRedisTemplate strRedTpl;

	@Autowired
	private JiebaSegmenter jSegmenter;

	// 1.通过关键字查询商品
	public Map<String, Object> findAllByKw(String keyword, Integer pageNum, Integer pageSize) {
		SetOperations<String, String> ops = strRedTpl.opsForSet();
		StringBuilder builder = new StringBuilder();
		// 1.对关键字进行分词
		String ids = "";
		if (!MikeUtil.notNullOrEmpty(keyword)) {
			ids = "";
		} else {
			List<SegToken> process = jSegmenter.process(keyword, SegMode.SEARCH);
			for (SegToken segToken : process) {
				Set<String> set = ops.members(segToken.word);
				for (String id : set) {
					builder.append(id).append(',');
				}
			}
			if (builder.length() < 1) {
				ids = "-1";
			} else {
				ids = builder.substring(0, builder.length() - 1);
			}
		}
		System.out.println("ids=" + ids);
		Map<String, Object> map = new HashMap<String, Object>();
		// 1.商品总数量
		int counts = commodityMapper.selectCountByIds(ids);
		// 2. 总页数pagecount
		int pagecount = (int) Math.ceil(counts * 1.0 / pageSize);

		// 3.以下量方法捆绑在一起,并且1方法放在前面
		PageHelper.startPage(pageNum, pageSize);
		List<Commodity> list = commodityMapper.selectByIds(ids);
		// List<Commodity> list = commodityMapper.selectByKw(keyword);// 3.查询商品集合

		Collections.shuffle(list);// 打乱顺序
		map.put("list", list);
		map.put("pagecount", pagecount == 0 ? 1 : pagecount);
		return map;

	}

	// 2.通过类型查询商品 类型为1 奢侈品 类型2 artwork 类型3 ceramics 类型4 hetian
	public Map<String, Object> findAllByType(Integer type, Integer pageNum, Integer pageSize) {
		return MikeUtil.findAllByTypeAndPage(commodityMapper, pageNum, pageSize, type);
	}

	// 3.通过id查询商品
	public Commodity findCommByPId(Integer pId) {
		return commodityMapper.selectByPrimaryKey(pId);
	}

	// 4 通过id 查询商品的数量
	public Integer findStateBy(Integer pId) {
		return commodityMapper.selectStateByCId(pId);
	}

	// 5 保存用户上传数据的业务逻辑
	public String conserveMsg(Commodity commodity) {
		if (commodity == null)
			return "信息不能为空 ! ";
		if (!MikeUtil.notNullOrEmpty(commodity.getpName()))
			return "商品名称不能为空 !";
		if (commodity.getFirstPrice() <= 0)
			return "初始价格设置有误 !";
		if (commodity.getMargin() <= 0)
			return "保证金价格设置有误";
		if (commodity.getPriceStep() <= 0)
			return "最小加价设置有误 !";
		if (!MikeUtil.notNullOrEmpty(commodity.getImg()))
			return "请上传图片 !";
		if (!MikeUtil.notNullOrEmpty(commodity.getInfo()))
			return "请上传商品详情介绍  !";
		commodity.setAccessNum(0);
		commodity.setState(0);
		int row = commodityMapper.insert(commodity);
		if (row > 0)
			return "ok";
		return "保存信息失败,请重试 !";
	}

	public String pushThis(Commodity commodity) {
		if (commodity == null)
			return "信息不能为空 ! ";
		if (!MikeUtil.notNullOrEmpty(commodity.getpName()))
			return "商品名称不能为空 !";
		if (commodity.getFirstPrice() <= 0)
			return "初始价格设置有误 !";
		if (commodity.getMargin() <= 0)
			return "保证金价格设置有误";
		if (commodity.getPriceStep() <= 0)
			return "最小加价设置有误 !";
		if (!MikeUtil.notNullOrEmpty(commodity.getImg()))
			return "请上传图片 !";
		if (!MikeUtil.notNullOrEmpty(commodity.getInfo()))
			return "请上传商品详情介绍  !";
		commodity.setAccessNum(0);
		commodity.setState(1);
		commodity.setPublishTime(new Date());
		int row = commodityMapper.insert(commodity);
		if (row > 0)
			return "ok";
		return "保存信息失败,请重试 !";
	}

	public List<Commodity> findCommodityByUname(String username) {
		Example exa = new Example(Commodity.class);
		Criteria cri = exa.createCriteria();
		if (MikeUtil.notNullOrEmpty(username))
			cri.andCondition("seller_name=", username);
		return commodityMapper.selectByExample(exa);
	}

	// 点击发布商品的业务逻辑
	public String pushComm(Integer pId) {
		int row = commodityMapper.updateCommStateAndPushtime(pId);
		if (row > 0) {
			// 开始倒计时 72小时
			// Long totalTimes = 259200L ;
			// timeTask(totalTimes);
			return "ok";
		}
		return "发布失败,请重试 !";
	}

	// 查找对应状态的拍卖品 : 商品状态0表示暂未发布 1表示正在发布 2表示已经结束未成交 3表示已结束已成交
	public List<Commodity> findCommodityBySellerAndState(String username, byte state) {
		Example exa = new Example(Commodity.class);
		Criteria cri = exa.createCriteria();
		if (MikeUtil.notNullOrEmpty(username))
			cri.andCondition("seller_name=", username);
		cri.andCondition("state=", state);
		return commodityMapper.selectByExample(exa);
	}

	// 12.定时器任务
	public int times = 100;

	@Scheduled(fixedDelay = 1000)
	@Async
	public void timeTask() {
		if (times > 0) {

			System.out.println("时间计时任务: 剩余" + times + "秒");
			times--;
		}
	}

	// 13 . 查找所有的商品 集合
	public Map<String, Object> findAllCommodityS(Integer pageNum, Integer pageSize) {
		int count = commodityMapper.selectCount(null);
		Integer ceil = (int) Math.ceil(count * 1.0 / pageSize);
		System.out.println("总页数为: " + ceil);
		PageHelper.startPage(pageNum, pageSize);
		List<Commodity> list = commodityMapper.selectAll();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("pagecount", ceil);
		return map;
	}

	// 14 . 管理员将正在拍卖的商品强制性下架
	public String changeCommoStateByPid(Integer pId) {
		Integer state = commodityMapper.selectStateByCId(pId);
		if (state == 0)
			return "拍品尚未拍卖,无需下架!";
		if (state == 2)
			return "拍品已结束,无需下架 ! ";
		if (state == 3)
			return "拍品已成交,无需下架 ! ";
		if (state == null)
			return "拍品不存在! ";
		byte state1 = 2;
		int row = commodityMapper.updateStateByPid(pId, state1);
		if (row > 0)
			return "ok";
		return "下架失败,请重新提交 ! ";
	}

	// 15.管理员根据
	public Map<String, Object> findCommoByPushTimeServ(String startDay, String endDay) {
		List<Commodity> list = commodityMapper.selectCommoByPubilshTime(startDay, endDay);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		return map;
	}

	// 16.查询所有商品
	public List<Commodity> findAll() {
		return commodityMapper.selectAll();
	}

}