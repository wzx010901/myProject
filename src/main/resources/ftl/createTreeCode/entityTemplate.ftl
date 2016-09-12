package com.fh.entity.${packageName};

import java.util.List;

/** 
 * 说明：${title} 实体类
 * 创建人：ZX Q149156999
 * 创建时间：${nowDate?string("yyyy-MM-dd")}
 */
public class ${objectNameFirstUpper}{ 
	
	private String ${objectNameFirstLower}Id;	//主键
	private String name;					//名称
	private String parentId;				//父类ID
	private String target;
	private ${objectNameFirstUpper} ${objectNameFirstLower};
	private List<${objectNameFirstUpper}> sub${objectNameFirstUpper};
	private boolean has${objectNameFirstUpper} = false;
	private String treeUrl;
	
	<#list fieldList as var>
		<#if var[1] == 'Integer'>
	private int ${var[7]};				//${var[2]}
	public int get${var[8]}() {
		return ${var[7]};
	}
	public void set${var[8]}(int ${var[7]}) {
		this.${var[7]} = ${var[7]};
	}
		<#elseif var[1] == 'Double'>
	private Double ${var[7]};			//${var[2]}
	public Double get${var[8]}() {
		return ${var[7]};
	}
	public void set${var[8]}(Double ${var[7]}) {
		this.${var[7]} = ${var[7]};
	}
		<#else>
	private String ${var[7]};			//${var[2]}
	public String getF${var[8]}() {
		return ${var[7]};
	}
	public void set${var[8]}(String ${var[7]}) {
		this.${var[7]} = ${var[7]};
	}
		</#if>
	</#list>

	public String get${objectNameFirstUpper}Id() {
		return ${objectNameFirstLower}Id;
	}
	public void set${objectNameFirstUpper}Id(String ${objectNameFirstLower}Id) {
		this.${objectNameFirstLower}Id = ${objectNameFirstLower}Id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public ${objectNameFirstUpper} get${objectNameFirstUpper}() {
		return ${objectNameFirstLower};
	}
	public void set${objectNameFirstUpper}(${objectNameFirstUpper} ${objectNameFirstLower}) {
		this.${objectNameFirstLower} = ${objectNameFirstLower};
	}
	public List<${objectNameFirstUpper}> getSub${objectNameFirstUpper}() {
		return sub${objectNameFirstUpper};
	}
	public void setSub${objectNameFirstUpper}(List<${objectNameFirstUpper}> sub${objectNameFirstUpper}) {
		this.sub${objectNameFirstUpper} = sub${objectNameFirstUpper};
	}
	public boolean isHas${objectNameFirstUpper}() {
		return has${objectNameFirstUpper};
	}
	public void setHas${objectNameFirstUpper}(boolean has${objectNameFirstUpper}) {
		this.has${objectNameFirstUpper} = has${objectNameFirstUpper};
	}
	public String getTreeUrl() {
		return treeUrl;
	}
	public void setTreeurl(String treeUrl) {
		this.treeUrl = treeUrl;
	}
	
}
