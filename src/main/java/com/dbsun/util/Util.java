package com.dbsun.util;

import com.dbsun.entity.system.PageData;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Util {

	/**
	 * 对微信菜单数据进行组装
	 * */
	public static JSONArray lstWechatTreeToJson(List<PageData> inlst){
		
		JSONArray jsonArray = new JSONArray();
		List<XMGLTaskDTO > lst = new ArrayList();
		//先转换数据
		for(int i=0;i<inlst.size();i++){
			PageData menu = inlst.get(i);
			XMGLTaskDTO dto = new XMGLTaskDTO();
			dto.setId(menu.get("ID").toString());
			dto.setText(menu.getString("NAME"));
			dto.setHref(menu.getString("URL"));//存放父ID
			dto.setPid(menu.getString("PARENT_ID"));//存放父ID
			dto.setType(menu.getString("TYPE"));//type
			dto.setWx_key(menu.getString("WX_KEY"));//KEY
			lst.add(dto);
		}     
		for(XMGLTaskDTO node1 : lst){//taskDTOList 是数据库获取的List列表数据或者来自其他数据源的List
			int checked = 0;//用户计量每个父节点下得子节点选中数量
			JSONObject json = new JSONObject();
            boolean mark = false;  
            json.put("nodeId", node1.getId());
            json.put("text", node1.getText());
            json.put("href", node1.getHref());
            json.put("pid", node1.getPid());
            json.put("key", node1.getWx_key());
            json.put("type", node1.getType());
            json.put("allid",node1.getId());
            JSONArray jsnode2 = null;
            for(XMGLTaskDTO node2 : lst){
                if(node1.getId().equals(node2.getPid())){  //主数据主ID等于从数据父ID
                    mark = true;
                    //转换子节点数据
                    JSONObject json2 = new JSONObject();
                    json2.put("nodeId", node2.getId());
                    json2.put("text", node2.getText());
                    json2.put("href", node2.getHref());
                    json2.put("pid", node2.getPid());
                    json2.put("allid",node2.getId());
                    json2.put("key", node2.getWx_key());
                    json2.put("type", node2.getType());
                    if(jsnode2 == null){
                    	jsnode2 = new JSONArray();
                    }
                    jsnode2.add(json2);
                }
            }
            if(mark){
            	json.put("allid",node1.getId());
            	json.put("nodes",jsnode2);
            	jsonArray.add(json);
//                jsonArray.add(node1);
            }else if(node1.getPid().equals("0")){//表示没有子节点的父节点页需要显示

            	jsonArray.add(json);
            }
		}
		return jsonArray;
	}
	/**
	 * 按照微信菜单进行同步前的json组装
	 * */
	public static JSONArray wechatMenuLstToLst(List<PageData> inlst){
		
		JSONArray jsonArray = new JSONArray();
		List<XMGLTaskDTO > lst = new ArrayList();
		//先转换数据
		for(int i=0;i<inlst.size();i++){
			PageData menu = inlst.get(i);
			XMGLTaskDTO dto = new XMGLTaskDTO();
			dto.setId(menu.get("ID").toString());
			dto.setText(menu.getString("NAME"));//显示名称
			dto.setHref(menu.getString("URL"));//存放url
			dto.setPid(menu.getString("PARENT_ID"));//存放父ID
			dto.setType(menu.getString("TYPE"));//type
			dto.setWx_key(menu.getString("WX_KEY"));//KEY
			lst.add(dto);
		}     
		for(XMGLTaskDTO node1 : lst){//taskDTOList 是数据库获取的List列表数据或者来自其他数据源的List
			int checked = 0;//用户计量每个父节点下得子节点选中数量
			JSONObject json = new JSONObject();
            boolean mark = false;  
            //json.put("type", node1.getId());
            json.put("name", node1.getText());
            JSONArray jsnode2 = null;
            for(XMGLTaskDTO node2 : lst){
                if(node1.getId().equals(node2.getPid())){  //主数据主ID等于从数据父ID
                    mark = true;
                    //转换子节点数据
                    JSONObject json2 = new JSONObject();
                    json2.put("type", node2.getType());
                    json2.put("name", node2.getText());
                    if(node2.getType().equals("view")){//根据type来确定填充url还是key
                    	json2.put("url",node2.getHref());
                    }
                    if(node2.getType().equals("click")){//根据type来确定填充url还是key
                    	json2.put("key",node2.getWx_key());
                    }
                    if(jsnode2 == null){
                    	jsnode2 = new JSONArray();
                    }
                    jsnode2.add(json2);
                }
            }
            if(mark){
            	
            	json.put("sub_button",jsnode2);
            	jsonArray.add(json);
//                jsonArray.add(node1);
            }else if(node1.getPid().equals("0")){//表示没有子节点的父节点页需要显示
            	jsonArray.add(json);
            }
		}
		return jsonArray;
	}
	
	/**
	 * 对权限菜单进行重新组装
	 * */
	public static List<PageData> yqLstToLst(List<PageData> lst){
		
		List<PageData> yqLst = new ArrayList<PageData>();
		String[] str =new String[lst.size()];
		List<PageData> inlst = lst;
		for(int i=0;i<inlst.size();i++){
			PageData pData = inlst.get(i);
            boolean mark = false;  
            List<PageData> yqSubLst = new ArrayList<PageData>();
            String pdw = pData.getString("year") +pData.getString("month");//
            for(int j=0;j<inlst.size();j++){
            	PageData psubData =  inlst.get(j);
            	
            	String psubdw = psubData.getString("year") +psubData.getString("month");
                if(pdw.equals(psubdw)){  //年月相等说明属于同一类数据
                    mark = true;
                    yqSubLst.add(psubData);
                }
            }
            	int y = i+1;
            	boolean addbol = true;
	            for(int s=0;s<y;s++){//if(!pdw.equals("")){//遍历当前操作日期跟存储的日期进行遍历是否有重复有责不添加
	            	if(Tools.isObjEmpty(str[s])){
	            		break;
	            	}else if(str[s].equals(pdw)){
	            		addbol = false;
	                	break;
	            	}
	            }
	            if(addbol){
	            	PageData pmarkData = new PageData();
            		pmarkData.put("YWREWARD001",pData.get("YWREWARD001"));
        			pmarkData.put("year",pData.get("year"));
        			pmarkData.put("month",pData.get("month"));
        			pmarkData.put("day",pData.get("day"));
        			pmarkData.put("YWU004",pData.get("YWU004"));
                	pmarkData.put("subLst",yqSubLst);
                	yqLst.add(pmarkData);
	            }
            str[i] = pdw;//年月执行一次存储一次
		}
		
		
		return yqLst;
	}

	private static boolean isNotEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
				|| (codePoint == 0xD)
				|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
				|| ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
				|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}

	/** 
	* 过滤emoji 或者 其他非文字类型的字符 
	* @param source 
	* @return 
	*/ 
	public static String filterEmoji(String source) {
		int len = source.length();
		StringBuilder buf = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (isNotEmojiCharacter(codePoint)) {
				buf.append(codePoint);
			} else {

				buf.append("*");

			}
		}
		return buf.toString();
	}


	public static   JSONArray treeMenuList(List<PageData> inlst, String parentId) {//
		JSONArray childDept = new JSONArray();
		String menuId;
		String pid;
		String DEPT_NAME;
		for (PageData object : inlst) {
			PageData jsonMenu = new PageData();
			menuId = object.getString("DEPT_ID");
			pid = object.getString("DEPT_PID");
			DEPT_NAME = object.getString("DEPT_NAME");
			jsonMenu.put("value",menuId);
			jsonMenu.put("label",DEPT_NAME);
			if (parentId.equals(pid)) {
				JSONArray c_node = treeMenuList(inlst, menuId);
				if(c_node.size()!=0){
					jsonMenu.put("children", c_node);
				}
				childDept.add(jsonMenu);
			}
		}
		return childDept;
	}
	/**
	 * 返回包含当前角色的部门信息
	 * */
	public static   JSONArray treeDeptAndMeList(List<PageData> inlst, String parentId) {//
		JSONArray childDept = new JSONArray();
		String DeptId;
		String pid;
		String DEPT_NAME;
		String DEPT_LAYERORDER;

		for (PageData object : inlst) {
			PageData jsonMenu = new PageData();
			DeptId = object.getString("DEPT_ID");
			pid = object.getString("DEPT_PID");
			DEPT_NAME = object.getString("DEPT_NAME");
			DEPT_LAYERORDER = object.getString("DEPT_LAYERORDER");
			jsonMenu.put("value",DeptId);
			jsonMenu.put("label",DEPT_NAME);
			jsonMenu.put("DEPT_LAYERORDER",DEPT_LAYERORDER);
			if (parentId.equals(pid)) {
				JSONArray c_node = treeDeptAndMeList(inlst, DeptId);
				if(c_node.size()!=0){
					jsonMenu.put("children", c_node);
				}
				childDept.add(jsonMenu);
			}
		}
		return childDept;
	}

	/**
	 * 返回包含当前角色的部门信息（列表页面查询专用）
	 * */
	public static   JSONArray searchTreeDeptAndMeList(List<PageData> inlst, String parentId) {//
		JSONArray childDept = new JSONArray();
		String DeptId;
		String pid;
		String DEPT_NAME;
		String DEPT_LAYERORDER;
		for (PageData object : inlst) {
			PageData jsonMenu = new PageData();
			DeptId = object.getString("DEPT_ID");
			pid = object.getString("DEPT_PID");
			DEPT_NAME = object.getString("DEPT_NAME");
			DEPT_LAYERORDER = object.getString("DEPT_LAYERORDER");
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("DEPT_ID",DeptId);
			jsonObj.put("DEPT_LAYERORDER",DEPT_LAYERORDER);
			jsonMenu.put("value",jsonObj);
			jsonMenu.put("label",DEPT_NAME);
			if (parentId.equals(pid)) {
				JSONArray c_node = searchTreeDeptAndMeList(inlst, DeptId);
				if(c_node.size()!=0){
					jsonMenu.put("children", c_node);
				}
				childDept.add(jsonMenu);
			}
		}
		return childDept;
	}
	//组装产品类型数据(级联选择)
	public static   JSONArray productList(List<PageData> inlst, String parentId) {//
		JSONArray childDept = new JSONArray();
		String id;
		String pid;
		String NAME;
		for (PageData object : inlst) {
			PageData jsonMenu = new PageData();
			id = object.getString("YBCT001");
			pid = object.getString("YBCT007");
			NAME = object.getString("YBCT002");
			jsonMenu.put("value",id);
			jsonMenu.put("label",NAME);
			if (parentId.equals(pid)) {
				JSONArray c_node = productList(inlst, id);
				if(c_node.size()!=0){
					jsonMenu.put("children", c_node);
				}
				childDept.add(jsonMenu);
			}
		}
		return childDept;
	}
	//组装客户标签数据
	public static   JSONArray labelList(List<PageData> inlst, String parentId) {//
		JSONArray childDept = new JSONArray();
		String id;
		String pid;
		String NAME;
		for (PageData object : inlst) {
			PageData jsonMenu = new PageData();
			id = object.getString("YBC_LAB001");
			pid = object.getString("YBC_LAB003");
			NAME = object.getString("YBC_LAB002");
			jsonMenu.put("value",id);
			jsonMenu.put("label",NAME);
			if (parentId.equals(pid)) {
				JSONArray c_node = labelList(inlst, id);
				if(c_node.size()!=0){
					jsonMenu.put("options", c_node);
				}
				childDept.add(jsonMenu);
			}
		}
		return childDept;
	}

	//组装产品类型数据(树形选择)
	public static   JSONArray productTreeList(List<PageData> inlst, String parentId) {//
		JSONArray childDept = new JSONArray();
		String id;
		String pid;
		String NAME;
		String YBCT004_ID;
		String YBCT004_NAME;
		for (PageData object : inlst) {
			PageData jsonMenu = new PageData();
			id = object.getString("YBCT001");
			pid = object.getString("YBCT007");
			NAME = object.getString("YBCT002");
			jsonMenu.put("id",id);
			jsonMenu.put("value",id);
			jsonMenu.put("pid",pid);
			jsonMenu.put("label",NAME);
			if(!Tools.isObjEmpty(object.get("YBCT004_ID"))){
				YBCT004_ID = object.getString("YBCT004_ID");//负责人ID
				YBCT004_NAME = object.getString("NAME");//负责人姓名
				jsonMenu.put("YBCT004_ID",YBCT004_ID);
				if(!Tools.isObjEmpty(object.get("NAME"))){
					jsonMenu.put("YBCT004_NAME",YBCT004_NAME);
				}else{
					jsonMenu.put("YBCT004_NAME","empty");
				}
			}else{
				jsonMenu.put("YBCT004_ID","empty");
				jsonMenu.put("YBCT004_NAME","empty");
			}
			
			if (parentId.equals(pid)) {
				JSONArray c_node = productTreeList(inlst, id);
				if(c_node.size()!=0){
					jsonMenu.put("children", c_node);
				}
				childDept.add(jsonMenu);
			}
		}
		return childDept;
	}
	//组装部门数据(树形选择)
	public static   JSONArray deptTreeList(List<PageData> inlst, String parentId) {//
		JSONArray childDept = new JSONArray();
		String id;
		String pid;
		String NAME;
		for (PageData object : inlst) {
			PageData jsonMenu = new PageData();
			id = object.getString("DEPT_ID");
			pid = object.getString("DEPT_PID");
			NAME = object.getString("DEPT_NAME");
			jsonMenu.put("id",id);
			jsonMenu.put("value",id);
			jsonMenu.put("pid",pid);
			jsonMenu.put("label",NAME);
			if (parentId.equals(pid)) {
				JSONArray c_node = deptTreeList(inlst, id);
				if(c_node.size()!=0){
					jsonMenu.put("children", c_node);
				}
				childDept.add(jsonMenu);
			}
		}
		return childDept;
	}

	//组装部门数据(树形选择)
	public static JSONArray getSeatObj(List<PageData> userLst, JSONArray jsaryGroup, String groupId) {//
		JSONArray returnAry = new JSONArray();//返回拼接好的坐席组成员状态数据
		for(PageData pd: userLst){//遍历所有用户数据 跟传入的坐席组数据进行比对 获取状态
			//遍历坐席组
			for(Object jsono:jsaryGroup) {//遍历所有的坐席组(默认需要传入指定坐席组，并返回单个坐席组的数据进行遍历)
				JSONObject jo = JSONObject.fromObject(jsono);
				if(groupId.equals(jo.getString("id"))){//相等则表明组信息匹配成功可以进行成员匹配
					JSONArray jsonArray = jo.getJSONArray("chlidren");//获取当前匹配坐席组下的所有成员
					for(Object jsonSeatUserObj:jsonArray){//
						JSONObject jsonSeatUser = JSONObject.fromObject(jsonSeatUserObj);
						//获取当前用户
						if(pd.getString("NUMBER").equals(jsonSeatUser.getString("staffId"))){//判定当前编号跟遍历的编号配对上后就需要返回当前组装后的参数
							JSONObject jsonUser = new JSONObject();
							jsonUser.put("name",pd.getString("NAME"));//姓名
							jsonUser.put("state",jsonSeatUser.getString("state"));//坐席状态
							jsonUser.put("staffId",jsonSeatUser.getString("staffId"));//坐席号8001...
							returnAry.add(jsonUser);
							break;
							//每个用户匹配成功后退出当前条件
						}
					}

				}
			}
			//添加不存在的用户

		}
		return returnAry;
	}

}
