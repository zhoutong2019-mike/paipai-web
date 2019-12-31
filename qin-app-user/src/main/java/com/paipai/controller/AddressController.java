package com.paipai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.paipai.service.AddressService;
import com.paipai.utils.BaseController;

@Controller
@RequestMapping("/paipai/Address")
public class AddressController extends BaseController {
	@Autowired
	private AddressService addressService;
	public void getAddressService() {
		System.out.println(addressService);
	}

}