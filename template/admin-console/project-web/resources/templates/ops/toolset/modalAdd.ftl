<div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">添加组件工具</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal" id="moduleForm" method="post" action="${appPath}/admin/ops/tools/saveRequest">
          <div class="form-group">
            <label for="widgetName" class="col-sm-2 control-label">组件名称</label>
            <div class="col-sm-9">
            	<input type="text" class="form-control" id="widgetName" name="widgetName" autocomplete="off" placeholder="">
            	<input type="hidden" id="id" name="id" value="">
            </div>
          </div>
          <div class="form-group">
            <label for="code" class="col-sm-2 control-label">组件类型</label>
            <div class="col-sm-9">
              <label class="radio-inline">
				<input type="radio" name="widgetType" id="radio_1" value="1" checked> 开发工具
			  </label>
			  <label class="radio-inline">
				<input type="radio" name="widgetType" id="radio_2" value="2"> 业务工具
			  </label>
            </div>
          </div>
          <div class="form-group">
            <label for="idcard" class="col-sm-2 control-label">Html代码</label>
            <div class="col-sm-9">
            	  <textarea type="text" class="form-control" name="widgetHtml" id="F_html" rows="10" placeholder=""></textarea>
            </div>
          </div>
          <div class="form-group">
            <label for="comment" class="col-sm-2 control-label">Ajax代码</label>
            <div class="col-sm-9">
            	  <textarea type="text" class="form-control" name="widgetAjax" id="F_ajax" rows="6" placeholder=""></textarea>
            </div>
          </div>
          <div class="form-group">
            <label for="comment" class="col-sm-2 control-label">Function代码</label>
            <div class="col-sm-9">
            	  <textarea type="text" class="form-control" name="widgetFunction" id="F_function" rows="3" placeholder=""></textarea>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-offset-8 col-sm-4">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="J_save" onclick="clickSaveBtn();">确定</button>
            </div>
          </div>
        </form>
      </div>