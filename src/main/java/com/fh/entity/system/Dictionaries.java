package com.fh.entity.system;

import java.util.List;

/**
 * 
* 类名称：数据字典
* 类描述： 
* @author wangzhengxing
* 作者单位： 
* 联系方式：
* 修改时间：2015年12月16日
* @version 2.0
 */
public class Dictionaries {

	private String name;			//名称
	private String nameEn;			//英文名称
	private String encoding;			//编码
	private String orderBy;		//排序	
	private String parentId;		//上级ID
	private String remark;				//备注
	private String tbsName;			//关联表
	private String dictionariesId;	//主键
	private String target;
	private Dictionaries dict;
	private List<Dictionaries> subDict;
	private boolean hasDict = false;
	private String treeUrl;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the nameEn
	 */
	public String getNameEn() {
		return nameEn;
	}
	/**
	 * @param nameEn the nameEn to set
	 */
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	/**
	 * @return the encoding
	 */
	public String getEncoding() {
		return encoding;
	}
	/**
	 * @param encoding the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	/**
	 * @return the orderBy
	 */
	public String getOrderBy() {
		return orderBy;
	}
	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the tbsName
	 */
	public String getTbsName() {
		return tbsName;
	}
	/**
	 * @param tbsName the tbsName to set
	 */
	public void setTbsName(String tbsName) {
		this.tbsName = tbsName;
	}
	/**
	 * @return the dictionariesId
	 */
	public String getDictionariesId() {
		return dictionariesId;
	}
	/**
	 * @param dictionariesId the dictionariesId to set
	 */
	public void setDictionariesId(String dictionariesId) {
		this.dictionariesId = dictionariesId;
	}
	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}
	/**
	 * @param target the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}
	/**
	 * @return the dict
	 */
	public Dictionaries getDict() {
		return dict;
	}
	/**
	 * @param dict the dict to set
	 */
	public void setDict(Dictionaries dict) {
		this.dict = dict;
	}
	/**
	 * @return the subDict
	 */
	public List<Dictionaries> getSubDict() {
		return subDict;
	}
	/**
	 * @param subDict the subDict to set
	 */
	public void setSubDict(List<Dictionaries> subDict) {
		this.subDict = subDict;
	}
	/**
	 * @return the hasDict
	 */
	public boolean isHasDict() {
		return hasDict;
	}
	/**
	 * @param hasDict the hasDict to set
	 */
	public void setHasDict(boolean hasDict) {
		this.hasDict = hasDict;
	}
	/**
	 * @return the treeUrl
	 */
	public String getTreeUrl() {
		return treeUrl;
	}
	/**
	 * @param treeUrl the treeUrl to set
	 */
	public void setTreeUrl(String treeUrl) {
		this.treeUrl = treeUrl;
	}
	
	
	
}
