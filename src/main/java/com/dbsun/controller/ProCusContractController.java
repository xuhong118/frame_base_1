package com.dbsun.controller;

import com.dbsun.base.BaseController;
import com.dbsun.entity.system.Page;
import com.dbsun.entity.system.PageData;
import com.dbsun.service.ProCusContractService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

//import com.dbsun.core.TokenLimit;

/**
 * 测试
 */
@Api(value = "pro_cus_contract", description = "测试", tags = "测试")
@Controller
@RequestMapping("/pro_cus_contract")
public class ProCusContractController extends BaseController {

	@Autowired
	private ProCusContractService proCusContractService;

	@RequestMapping(value = "/goProCusContractPage", method = RequestMethod.GET)
	@ApiOperation(value = "跳转到测试页面")
	public String goProCusContractPage(){
		return "";
	}

	/**
	* 获取测试列表数据
	* @return
	*/
	@RequestMapping(value = "/getPageProCusContractList", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取测试列表数据", notes = "分页")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType="query", name = "showCount", value = "每页记录数", required = false, dataType = "String"),
			@ApiImplicitParam(paramType="query", name = "currentPage", value = "当前页", required = true, dataType = "String"),
	})
	public JSONObject getPageProCusContractList(){
		PageData pd = this.getPageData();
		Page page = this.getPage();
		page.setPd(pd);
		List<PageData> proCusContractList = null;
		try {
			proCusContractList = proCusContractService.getPageProCusContractList(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return viewReturnPageData(page, proCusContractList);
	}



}
