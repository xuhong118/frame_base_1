package com.dbsun.service;

import com.dbsun.entity.system.Page;
import com.dbsun.entity.system.PageData;
import com.dbsun.mapper.ProCusContractMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 */
@Service
public class ProCusContractService extends BaseService {

	@Autowired
	private ProCusContractMapper proCusContractMapper;

	public List<PageData> getPageProCusContractList(Page page){
		return proCusContractMapper.getPageProCusContractList(page);
	}


}
