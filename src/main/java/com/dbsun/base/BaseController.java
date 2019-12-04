package com.dbsun.base;

import com.dbsun.config.AllConfig;
import com.dbsun.entity.system.Page;
import com.dbsun.entity.system.PageData;
import com.dbsun.init.Config;
import com.dbsun.util.*;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;

@Controller
public class BaseController {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 6357869213649815390L;
	// 全局session
	@Autowired
	protected HttpSession session;

	// 全局request
	@Autowired
	protected HttpServletRequest request;
	// 全局请求路径项目名
	protected String content;
	
	//redis存储数据库封装方法
	@Autowired
	public  RedisUtil redisutil;

	// 404 not found 页面跳转
	protected final String NOT_FOUND = "/404";

	private static final Logger log = LoggerFactory
			.getLogger(BaseController.class);

	/**
	 * 通用返回json分页数据
	 * */
	public JSONObject viewReturnPageData(Page page, List<PageData> pageLst) {
		JSONObject json = new JSONObject();// 返回数据必须包含这个格式
		json.put("total", page.getTotalResult());
		json.put("rows", pageLst);
		return json;
	}

	/**
	 * 获取request
	 * 
	 * @return HttpServletRequest实例
	 */
	public HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
	}

	/**
	 * 获取session
	 * 
	 * @return HttpSession实例
	 */
	public HttpSession getSession() {
		return getRequest().getSession();
	}

	/**
	 * 根据名称获取参数值
	 * 
	 * @param name
	 *            参数名称
	 * @return 字符串参数值
	 */
	protected String getParameter(String name) {
		return request.getParameter(name);
	}

	/**
	 * 获取所有参数值
	 * 
	 * @return 参数值
	 */
	protected JSONObject getParameters() {
		HttpServletRequest request = this.request;
		JSONObject results = JSONObject.fromObject("{}");
		try {
			results = JSONObject.fromObject(request.getParameterMap());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}


	/**
	 * 得到分页列表的信息
	 */
	public Page getPage() {
		String currentResult = request.getParameter("offset");//管理分页编码
		String currentPage = request.getParameter("currentPage");//业务分页编码
		Page page = new Page();
		if(!Tools.isObjEmpty(request.getParameter("showCount"))){//未传入每页显示条数的,默认10条一页
			page.setShowCount(Integer.parseInt(request.getParameter("showCount")));
		}
		//两种分页标签 第一种是offset 适用于管理功能的分页 currentPage 适用于业务功能的分页
		if(Tools.isObjEmpty(currentPage)){
			//管理功能分页
			if(Tools.isObjEmpty(currentResult)){
				page.setCurrentResult(0);// 分页开始位置
			}else {
				if(Tools.isObjEmpty(currentResult)){
					page.setCurrentResult(0);// 分页开始位置
				}else {
					page.setCurrentResult(Integer.parseInt(currentResult));// 分页开始位置
				}
			}
		}else{
			//业务功能分页
			int startLimt = (Integer.parseInt(currentPage)-1) * page.getShowCount(); //当前页数
			page.setCurrentResult(startLimt);// 分页开始位置
		}

		return page;
	}

	/**
	 * 得到PageData
	 */
	public PageData getPageData() {
		return new PageData(this.getRequest());
	}
	
	/**
	 * 获取IP
	 * @return
	 */
	public String getIp(){
		HttpServletRequest request = this.getRequest();
		String ip = "";
		if (request.getHeader("x-forwarded-for") == null) {
			ip = request.getRemoteAddr();
		} else {
			ip = request.getHeader("x-forwarded-for");
		}
		return ip;
	}
	
	/**
	 * 上传生成文件
	 * 
	 * @return String 上传后文件可访问路径
	 * */
	public String uploadAndWriteImg(MultipartFile file){
		try {
			String 	path = UploadUtil.uploadFile(file, "ybu014");
			path = Config.getStaticFilesAccessURL(path);
			return path;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 上传生成文件
	 *
	 * @return String 上传后文件可访问路径
	 * */
	public String uploadAndWriteImg(MultipartFile file,String uploadPath){
		try {
			String 	path = UploadUtil.uploadFile(file, "ybu014",uploadPath);
//			path = Config.getStaticFilesAccessURL(path);
			return path;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取当前登录的用户
	 * @return
	 * 		当前用户的Pd
	 */
	public PageData getUserPd(){
		PageData pd = (PageData)request.getSession().getAttribute(AllConfig.SESSION_KEY);
		return pd;
	}
	
	/**
	 * 获取操作成功返回的json
	 * @return
	 */
	public JSONObject getSucJson(){
		JSONObject json = new JSONObject();
		json.put("msg", "200");
		json.put("result", "success");
		return json;
	}
	
	
	/**
	 * 获取操作失败返回的json
	 * @return
	 */
	public JSONObject getFalJson(){
		JSONObject json = new JSONObject();
		json.put("msg", "500");
		json.put("result", "filed");
		return json;
	}
	/**
	 * 将json对象中包含的null和JSONNull属性修改成""
	 * @param jsonObj
	 */
	public JSONObject filterNull(JSONObject jsonObj){
		Iterator<String> it = jsonObj.keys();
		Object obj = null;
		String key = null;
		while (it.hasNext()) {
			key = it.next();
			obj = jsonObj.get(key);
			if(obj instanceof JSONObject){
				filterNull((JSONObject)obj);
			}
			/*if(obj instanceof JSONArray){
				JSONArray objArr = (JSONArray) obj;
				for(int i=0; i<objArr.size(); i++){
					filterNull(objArr.getJSONObject(i));
				}
			}*/
			if(obj == null || obj instanceof JSONNull){
				jsonObj.put(key, "");
			}
		}
		return jsonObj;
	}

	/**
	 * 获取请求成功的List
	 * @return
	 */
	public JSONObject getSucJson(Object obj){
		JSONObject json = new JSONObject();
		JSONObject jobj = new JSONObject();
		json.put("msg", "200");
		//如果对象为空，实例化一个空的对象，否则返回的json不会带result
		if(Tools.isObjEmpty(obj))obj = new Object();
		json.put("result", obj);
		jobj = filterNull(json);
		return jobj;
	}

	/**
	 * 添加或者修改返回的json
	 * @param ret
	 * @return
	 */
	public JSONObject getAddOrUpdJson(int ret){
		if(ret >= 1)return getSucJson();
		else{
			return getFalJson();
		}
	}
	
	/**
	 * 得到32位的uuid
	 * @return
	 */
	public String get32UUID(){
		return UuidUtil.get32UUID();
	}
	
	/**
	 * 将当前登录用户（USER_ID、DEPT_LAYERORDER、POSITION）信息放入到pd中
	 * @param pd
	 * 			将包含用户信息的pd返回调用者
	 * @return
	 */
	public PageData putUserPd(PageData pd){
		PageData su = this.getUserPd();
		pd.put("USER_ID", su.get("USER_ID"));
		pd.put("DEPT_LAYERORDER", su.get("DEPT_LAYERORDER"));
		pd.put("POSITION", su.get("POSITION"));
		return pd;
	}
	
	/**
	 * 将目标用户的(USER_ID、DEPT_LAYERORDER、POSITION)值赋值到返回的pd
	 * @param pd
	 * @param userPd
	 * @return
	 */
	public PageData putUserPd(PageData pd, PageData userPd){
		pd.put("USER_ID", userPd.get("USER_ID"));
		pd.put("DEPT_LAYERORDER", userPd.get("DEPT_LAYERORDER"));
		pd.put("POSITION", userPd.get("POSITION"));
		return pd;
	}
	
	/**
	 * 将USER_ID添加到PD -- 只为pd添加userId
	 * @param pd
	 * @return
	 */
	public PageData putUserIdPd(PageData pd){
		PageData su = this.getUserPd();
		pd.put("USER_ID", su.get("USER_ID"));
		return pd;
	}
	
	/**
	 * 将USER_ID添加到PD -- 分页使用
	 * @param page
	 * @param pd
	 * @return
	 */
	public Page putUserIdPd(Page page, PageData pd){
		PageData su = this.getUserPd();
		pd.put("USER_ID", su.get("USER_ID"));
		page.setPd(pd);
		return page;
	}
	
	/**
	 * 将用户的DEPT_LAYERORDER、POSITION添加到PD再放入Page -- 分页使用
	 * @param page
	 * @param pd
	 * @return
	 */
	public Page putUserPd(Page page, PageData pd){
		PageData su = this.getUserPd();
		pd.put("DEPT_LAYERORDER", su.get("DEPT_LAYERORDER"));
		pd.put("POSITION", su.get("POSITION"));
		page.setPd(pd);
		return page;
	}
	
	/**
	 * 将用户的USER_ID、DEPT_LAYERORDER、POSITION添加到PD再放入Page -- 分页使用
	 * @param page
	 * @param pd
	 * @return
	 */
	public Page putUserIdAdDeLAdPosPd(Page page, PageData pd){
		PageData userPd = this.getUserPd();
		pd.put("USER_ID", userPd.get("USER_ID"));
		pd.put("DEPT_LAYERORDER", userPd.get("DEPT_LAYERORDER"));
		pd.put("POSITION", userPd.get("POSITION"));
		page.setPd(pd);
		return page;
	}
	
	/**
	 * 上传文件获取路径
	 * @param file
	 * @return
	 */
	@SuppressWarnings("unused")
	public JSONObject uploadPhoto(MultipartFile file) {
		JSONObject json = new JSONObject();
		if (file.isEmpty()) {
			return this.getFalJson();
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        String path = uploadAndWriteImg(file);
        //成功，方便调用者的判断
        json.put("msg", 200);
        json.put("fileName", fileName);
        json.put("path", path);
        return json;
	}

	/**
	 * 上传文件获取路径
	 * @param file
	 * @return
	 */
	@SuppressWarnings("unused")
	public JSONObject uploadPhoto(MultipartFile file,String uploadPath) {
		JSONObject json = new JSONObject();
		if (file.isEmpty()) {
			return this.getFalJson();
		}
		// 获取文件名
		String fileName = file.getOriginalFilename();
		// 获取文件的后缀名
		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		String path = uploadAndWriteImg(file,uploadPath);
		//成功，方便调用者的判断
		json.put("msg", 200);
		json.put("fileName", fileName);
		json.put("path", path);
		return json;
	}
	
}
