<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${objectName}Mapper">
	
	<resultMap type="${objectName}" id="${objectNameLower}ResultMap">
		<id column="${objectNameOriginal}_id" property="${objectNameLower}Id"/>
		<result column="name" property="name"/>
		<result column="parent_id" property="parentId"/>
	</resultMap>
	
	<!--表名 -->
	<sql id="tableName">
		"${tabletop}${objectNameOriginal}"
	</sql>
	
	<!-- 字段 -->
	<sql id="SelectField">
	<#list fieldList as var>
		"${var[0]}" as ${var[7]},	
	</#list>
		"${objectNameOriginal}_id"  as ${objectNameFirstLower}Id,
		"parent_id" as parentId,
		"name"
	</sql>
	<!-- 字段 -->
	<sql id="InsertField">
	<#list fieldList as var>
		"${var[0]}",	
	</#list>
		"${objectNameOriginal}_id",
		"parent_id",
		"name"
	</sql>
	
	<!-- 字段值 -->
	<sql id="FieldValue">
	<#list fieldList as var>
			${r"#{"}${var[0]}${r"}"},	
	</#list>
			${r"#{"}${objectNameFirstLower}Id${r"}"},
			${r"#{"}parentId${r"}"},
			${r"#{"}name${r"}"}
	</sql>
	
	<!-- 新增-->
	<insert id="save" parameterType="pd">
		insert into
		<include refid="tableName"></include>
		(
		<include refid="InsertField"></include>
		) values (
		<include refid="FieldValue"></include>
		)
	</insert>
	
	<!-- 删除-->
	<delete id="delete" parameterType="pd">
		delete from
		<include refid="tableName"></include>
		where 
			"${objectNameOriginal}_id" = ${r"#{"}${objectNameFirstLower}Id${r"}"}
	</delete>
	
	<!-- 修改 -->
	<update id="edit" parameterType="pd">
		update
		<include refid="tableName"></include>
			set 
	<#list fieldList as var>
			<#if var[3] == "是">
				"${var[0]}" = ${r"#{"}${var[0]}${r"}"},	
			</#if>
	</#list>
			"name" = ${r"#{"}name${r"}"},
			"${objectNameOriginal}_id" = "${objectNameOriginal}_id"
			where 
				"${objectNameOriginal}_id" = ${r"#{"}${objectNameFirstLower}Id${r"}"}
	</update>
	
	<!-- 通过ID获取数据 -->
	<select id="findById" parameterType="pd" resultType="pd">
		select 
		<include refid="SelectField"></include>
		from 
		<include refid="tableName"></include>
		where 
			"${objectNameOriginal}_id" = ${r"#{"}${objectNameFirstLower}Id${r"}"}
	</select>
	
	<!-- 列表 -->
	<select id="datalistPage" parameterType="page" resultType="pd">
		select
		<include refid="SelectField"></include>
		from 
		<include refid="tableName"></include>
		where 1=1
		<if test="pd.${objectNameFirstLower}Id!= null and pd.${objectNameFirstLower}Id != ''"><!-- 检索 -->
		and "parent_id" = ${r"#{"}pd.${objectNameFirstLower}Id${r"}"}
		</if>
		<if test="pd.keywords!= null and pd.keywords != ''"><!-- 关键词检索 -->
			and
				(
				<!--	根据需求自己加检索条件
					字段1 LIKE CONCAT(CONCAT('%', ${r"#{pd.keywords})"},'%')
					 or 
					字段2 LIKE CONCAT(CONCAT('%', ${r"#{pd.keywords})"},'%') 
				-->
				)
		</if>
	</select>
	
	<!-- 通过ID获取其子级列表 -->
	<select id="listByParentId" parameterType="String" resultMap="${objectNameLower}ResultMap">
		select 
		<include refid="SelectField"></include>
		from 
		<include refid="tableName"></include>
		where 
			"PARENT_ID" = ${r"#{parentId}"}  order by "name" 
	</select>
	
	<!-- 列表(全部) -->
	<select id="listAll" parameterType="pd" resultType="pd">
		select
		<include refid="SelectField"></include>
		from 
		<include refid="tableName"></include>
	</select>
	
	
	<!-- zx 149156999 qq(碌碌)->
</mapper>