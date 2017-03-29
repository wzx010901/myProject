<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${objectName}Mapper">
	
	<!--表名 -->
	<sql id="tableName">
		${tabletop}${objectNameOriginal}
	</sql>
	
	<!-- 查询字段 -->
	<sql id="SelectField">
	<#list fieldList as var>
		${var[0]} as ${var[7]},	
	</#list>
		${objectNameOriginal}_ID as ${objectNameFirstLower}Id
	</sql>
	<!-- 插入字段 -->
	<sql id="InsertField">
	<#list fieldList as var>
		${var[0]},	
	</#list>
		${objectName}_ID
	</sql>
	
	<!-- 字段值 -->
	<sql id="FieldValue">
	<#list fieldList as var>
		${r"#{"}${var[7]}${r"}"},	
	</#list>
		${r"#{"}${objectNameFirstLower}Id${r"}"}
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
			${objectNameOriginal}_ID = ${r"#{"}${objectNameFirstLower}Id${r"}"}
	</delete>
	
	<!-- 修改 -->
	<update id="edit" parameterType="pd">
		update
		<include refid="tableName"></include>
		set 
	<#list fieldList as var>
		<#if var[3] == "是">
			${var[0]} = ${r"#{"}${var[7]}${r"}"},
		</#if>
	</#list>
		${objectName}_ID = ${objectName}_ID
		where 
		${objectName}_ID = ${r"#{"}${objectNameFirstLower}Id${r"}"}
	</update>
	
	<!-- 通过ID获取数据 -->
	<select id="findById" parameterType="pd" resultType="pd">
		select 
		<include refid="SelectField"></include>
		from 
		<include refid="tableName"></include>
		where 
			${objectName}_ID = ${r"#{"}${objectNameFirstLower}Id${r"}"}
	</select>
	
	<!-- 列表 -->
	<select id="datalistPage" parameterType="page" resultType="pd">
		select
		<include refid="SelectField"></include>
		from 
		<include refid="tableName"></include>
		where 1=1
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
	
	<!-- 列表(全部) -->
	<select id="listAll" parameterType="pd" resultType="pd">
		select
		<include refid="SelectField"></include>
		from 
		<include refid="tableName"></include>
	</select>
	
	<!-- 批量删除 -->
	<delete id="deleteAll" parameterType="String">
		delete from
		<include refid="tableName"></include>
		where 
			${objectName}_ID in
		<foreach item="item" index="index" collection="array" open="(" separator="," close=")">
                 ${r"#{item}"}
		</foreach>
	</delete>
	
	<!-- zx1491569999qq(碌碌) -->
</mapper>