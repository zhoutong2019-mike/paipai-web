package com.paipai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.paipai.dao.AddressMapper;

@Transactional
@Service
public class AddressService {
	@Autowired
	private AddressMapper addressMapper;

	public AddressMapper getAddressMapper() {
		return addressMapper;
	}

}