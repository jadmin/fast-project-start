<#-- 首页 -->
<#assign homePage = ['/console/', '/console/index'] />

<#-- 名单中心 -->
<#assign bwlMenu = [
	'/console/bwl/blackWhiteBizEnum',
	'/console/bwl/blackWhiteList'
]
/>


<#-- 工具中心 -->
<#assign toolMenu = [
	'/console/ops/devtools',
	'/console/ops/biztools',
	'/console/ops/toolset'
]
/>


<#-- 用户管理 -->
<#assign userMenu = [
	'/console/user', 
	'/console/profile'
]
/>


<#-- 系统管理 -->
<#assign sysMenu = [
	'/console/resource', 
	'/console/menu',
	'/console/role'
]
/>


<#-- 判断当前菜单是否是激活状态 -->
<#macro isActiveMenu menuArr servletPath>
    <#list menuArr as it>
    	<#if Utils.matchUrl(it, servletPath) >active<#break></#if>
    </#list>
</#macro>

<#macro isCurrentActive menuUri servletPath>
    <#if Utils.matchUrl(menuUri, servletPath) >active</#if>
</#macro>

<#macro isHitActive menuUriArr servletPath>
    <#list menuUriArr as it>
    	<#if Utils.matchUrl(it, servletPath) >active<#break></#if>
    </#list>
</#macro>

<#macro icon iconCss ifNone>
<#if Utils.isBlank(iconCss)>${ifNone}<#else>${iconCss}</#if>
</#macro>

<#-- 菜单节点渲染 -->
<#macro menuNode menuArr>
    <#list menuArr as menu>
    	<#if menu.type==1 && menu.hasChild()==true>
    		<li class="treeview">
    			<a href="#">
                  <i class="<@icon menu.iconCss 'fa fa-folder' />"></i>
                  <span>${menu.name}</span>
                  <span class="pull-right-container">
          			<i class="fa fa-angle-left pull-right"></i>
        		  </span>
    			</a>
    			<ul class="treeview-menu">
    				<@menuNode menu.children />
    			</ul>
    		</li>
    	<#else>
    		<li class="<@isCurrentActive '${menu.uri}' servletPath />">
    			<a href="${appPath}${menu.uri}">
    				<i class="<@icon menu.iconCss 'fa fa-circle-o' />"></i>
    				<span>${menu.name}</span>
		            <span class="pull-right-container"></span>
    			</a>
    		</li>
    	</#if>
    </#list>
</#macro>



  

