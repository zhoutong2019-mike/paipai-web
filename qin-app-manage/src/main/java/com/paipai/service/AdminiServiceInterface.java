package com.paipai.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



//@FeignClient(name = "QIN-APP-USER",fallbackFactory = MyFallBack.class)
public interface AdminiServiceInterface {

	@GetMapping("/paipai/User/findAllUser")
	@ResponseBody
	public Map<String, Object> findAllUser();

	@GetMapping("/paipai/User/updateUserState")
	@ResponseBody
	public String updateUserState(@RequestParam("id")Integer id);

	@GetMapping("/paipai/User/updateUserStateToOne")
	@ResponseBody
	public String updateUserStateToOne(@RequestParam("id")Integer id);
	
	@GetMapping("/paipai/User/findManyUserByName")
	@ResponseBody
	public Map<String, Object> findManyUserByName(@RequestParam("username")String username);

	@GetMapping("/paipai/User/findManyUserByTime")
	@ResponseBody
	public Map<String, Object> findManyUserByTime(@RequestParam("startDay")String startDay,@RequestParam("endDay") String endDay);

	@GetMapping("/paipai/Commodity/findAllCommiditys")
	@ResponseBody
	public Map<String, Object> findAllCommiditys(@RequestParam("pageNum")Integer pageNum,@RequestParam("pageSize") Integer pageSize);

	@GetMapping("/paipai/Record/findBiddingRecord")
	@ResponseBody
	public Map<String, Object> findBiddingRecord(@RequestParam("pId")Integer pId);

	@GetMapping("/paipai/Commodity/changeCommoState")
	@ResponseBody
	public String changeCommoState(@RequestParam("pId")Integer pId);
	
	
	@GetMapping("/paipai/Commodity/findCommoByPushTime")
	@ResponseBody
	public Map<String, Object> findCommoByPushTime(@RequestParam("startDay")String startDay,@RequestParam("endDay") String endDay);
	
	@GetMapping("/paipai/Commodity/findDescription")
	@ResponseBody
	public Map<String, Object> findDescription(@RequestParam("pId")Integer pId);
	
	
	
	
	
	
}

@Component
class MyFallBack implements FallbackFactory<AdminiServiceInterface> {

	@Override
	public AdminiServiceInterface create(Throwable cause) {
		return new AdminiServiceInterface() {
			@Override
			public Map<String, Object> findAllUser() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("list", "error");
				return map;
			}

			@Override
			public String updateUserState(Integer id) {
				return "error";
			}

			@Override
			public String updateUserStateToOne(Integer id) {
				return "error";
			}

			@Override
			public Map<String, Object> findManyUserByName(String username) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("list", "error");
				return map;
			}

			@Override
			public Map<String, Object> findManyUserByTime(String startTime, String endTime) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("list", "error");
				return map;
			}

			@Override
			public Map<String, Object> findAllCommiditys(Integer pageNum, Integer pageSize) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("list", "error");
				return map;
			}

			@Override
			public Map<String, Object> findBiddingRecord(Integer pId) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("list", "error");
				return map;
			}

			@Override
			public String changeCommoState(Integer pId) {
				return "error";
			}

			@Override
			public Map<String, Object> findCommoByPushTime(String startDay, String endDay) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("list", "error");
				return map;
			}

			@Override
			public Map<String, Object> findDescription(Integer pId) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("list", "error");
				return map;
			}

		};
	}

}
