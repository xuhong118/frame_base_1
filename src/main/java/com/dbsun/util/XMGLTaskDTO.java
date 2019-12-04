package com.dbsun.util;

import java.util.List;

public class XMGLTaskDTO {
	  String text;  
	  String href;  
	  String id;
	  String pid;
	  String type;
	  String wx_key;
	  List<XMGLTaskDTO> nodes;
	  
	public String getWx_key() {
		return wx_key;
	}
	public void setWx_key(String wx_key) {
		this.wx_key = wx_key;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<XMGLTaskDTO> getNodes() {
		return nodes;
	}
	public void setNodes(List<XMGLTaskDTO> nodes) {
		this.nodes = nodes;
	}
	
	
	  
}
