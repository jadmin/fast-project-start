<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!-- Automatic generated on ${datetime} by CrudCodeGenerator wirtten by Gerald Chen -->

<mapper namespace="${daoPackage}.${Entity}DAO">

	<resultMap type="${entityPackage}.${Entity}DO" id="${entity}Map">
${columns_mapping}
    </resultMap>
    
    <sql id="AllColumns">
${all_columns}
    </sql>
    
    <sql id="InfoManageSet">
        <set>
${columns_manage}
        </set>
    </sql>
    
    <sql id="QueryCondition">
    	<if test="id != null">
            AND id = #{id} 
        </if>${eq_segment}${like_segment}${notlike_segment}

        <if test="ids != null and ids.size() > 0">
            AND id IN
            <foreach item="id" index="index" collection="ids"
                     open="(" separator="," close=")">
                #{id}
            </foreach>
        </if>${in_segment}${notin_segment}
        
        <if test="gmtCreateRange != null and gmtCreateRange.start != null">
            <![CDATA[
            AND gmt_create >= #{gmtCreateRange.start} 
        	]]>
        </if>
        <if test="gmtCreateRange != null and gmtCreateRange.end != null">
            <![CDATA[
            AND gmt_create <= #{gmtCreateRange.end} 
        	]]>
        </if>
        <if test="gmtModifyRange != null and gmtModifyRange.start != null">
            <![CDATA[
            AND gmt_modify >= #{gmtModifyRange.start} 
        	]]>
        </if>
        <if test="gmtModifyRange != null and gmtModifyRange.end != null">
            <![CDATA[
            AND gmt_modify <= #{gmtModifyRange.end} 
        	]]>
        </if>
        
    </sql>
    
    <sql id="OrderBy">
    	<choose>
	        <when test="orderByList != null and orderByList.size() > 0">
	            <foreach item="orderby" index="index" collection="orderByList" open="" separator="," close="">
                	${orderby.key} ${orderby.value}
            	</foreach>
	        </when>
	        <otherwise>
	            gmt_create DESC 
	        </otherwise>
    	</choose>
    </sql>
    
    <select id="getById" resultMap="${entity}Map">
    	SELECT  
    		<include refid="AllColumns" /> 
    	FROM ${entity_table} WHERE id = #{id} LIMIT 1
    </select>
    
    <select id="queryList" parameterType="${entityQueryPackage}.${Entity}Query" resultMap="${entity}Map">
    	SELECT  
    		<include refid="AllColumns" />
    	FROM ${entity_table}  
    	WHERE 1=1 
    		<include refid="QueryCondition" /> 
    	ORDER BY 
    		<include refid="OrderBy" /> 
        LIMIT #{start}, #{limit}
    </select>
    
    <select id="count" parameterType="${entityQueryPackage}.${Entity}Query" resultType="java.lang.Integer">
    	SELECT count(id) 
    	FROM ${entity_table}  
    	WHERE 1=1 
    		<include refid="QueryCondition" /> 
    </select>

    <insert id="insert" parameterType="${entityPackage}.${Entity}DO" useGeneratedKeys="true" keyProperty="id">
        INSERT ${entity_table} 
        <include refid="InfoManageSet" />
    </insert>
    
    <insert id="insertBatch" parameterType="java.util.List" useGeneratedKeys="true" keyProperty="id"> 
	    INSERT INTO ${entity_table}(${table_columns_exclude_id})  
	    VALUES 
	    <foreach collection="entityList" item="obj" index="index" separator=",">  
	       (${table_columns_value})  
	    </foreach>  
    </insert>

    <update id="update" parameterType="${entityPackage}.${Entity}DO">
        UPDATE ${entity_table} 
        <include refid="InfoManageSet" />
        WHERE id = #{id} 
        <if test="version != null">
            AND version=#{version}
        </if>
    </update>
    
    <delete id="deleteById">
    	DELETE FROM ${entity_table} WHERE id = #{id}
    </delete>
    

</mapper>