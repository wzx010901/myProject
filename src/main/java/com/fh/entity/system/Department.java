package com.fh.entity.system;

import java.util.List;

/**
 * 
 * 类名称：组织机构 类描述：
 * 
 * @author wangzhengxing 作者单位： 联系方式： 修改时间：2015年12月16日
 * @version 2.0
 */
public class Department {

	private String name; // 名称
	private String nameEn; // 英文名称
	private String encoding; // 编码
	private String parentId; // 上级ID
	private String headman; // 负责人
	private String tel; // 电话
	private String functions; // 部门职能
	private String remark; // 备注
	private String address; // 地址
	private String departmentId; // 主键
	private String target;
	private Department department;
	private List<Department> subDepartment;
	private boolean hasDepartment = false;
	private String treeUrl;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
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
	 * @param nameEn
	 *            the nameEn to set
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
	 * @param encoding
	 *            the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the headman
	 */
	public String getHeadman() {
		return headman;
	}

	/**
	 * @param headman
	 *            the headman to set
	 */
	public void setHeadman(String headman) {
		this.headman = headman;
	}

	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel
	 *            the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the functions
	 */
	public String getFunctions() {
		return functions;
	}

	/**
	 * @param functions
	 *            the functions to set
	 */
	public void setFunctions(String functions) {
		this.functions = functions;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the departmentId
	 */
	public String getDepartmentId() {
		return departmentId;
	}

	/**
	 * @param departmentId
	 *            the departmentId to set
	 */
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target
	 *            the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * @return the department
	 */
	public Department getDepartment() {
		return department;
	}

	/**
	 * @param department
	 *            the department to set
	 */
	public void setDepartment(Department department) {
		this.department = department;
	}

	/**
	 * @return the subDepartment
	 */
	public List<Department> getSubDepartment() {
		return subDepartment;
	}

	/**
	 * @param subDepartment
	 *            the subDepartment to set
	 */
	public void setSubDepartment(List<Department> subDepartment) {
		this.subDepartment = subDepartment;
	}

	/**
	 * @return the hasDepartment
	 */
	public boolean isHasDepartment() {
		return hasDepartment;
	}

	/**
	 * @param hasDepartment
	 *            the hasDepartment to set
	 */
	public void setHasDepartment(boolean hasDepartment) {
		this.hasDepartment = hasDepartment;
	}

	/**
	 * @return the treeUrl
	 */
	public String getTreeUrl() {
		return treeUrl;
	}

	/**
	 * @param treeUrl
	 *            the treeUrl to set
	 */
	public void setTreeUrl(String treeUrl) {
		this.treeUrl = treeUrl;
	}

}