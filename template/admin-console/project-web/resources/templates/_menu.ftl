<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- Sidebar user panel -->
      <div class="user-panel">
        <div class="pull-left image">
          <img src="${appPath}/images/user2-160x160.jpg" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
          <p>${name!""}</p>
          <a href="#"><i class="fa fa-circle text-success"></i> 在线</a>
          <a href="#" class="m_logout"><i class="fa fa-sign-out text-danger"></i> 注销</a>
        </div>
      </div>
      <form action="#" method="get" class="sidebar-form" id="sidebar-form">
        <div class="input-group">
          <input type="text" name="q" class="form-control" placeholder="搜索" id="search-input">
          <span class="input-group-btn">
            <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
            </button>
          </span>
        </div>
      </form>
       
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <ul class="sidebar-menu" data-widget="tree">
        <!-- <li class="header">V${version!"1.0.0"}</li> -->
	<#if isLogined?? && isLogined==true>
        <@menuNode menus />
	</#if>        
      </ul>
    </section>
    <!-- /.sidebar -->
</aside>