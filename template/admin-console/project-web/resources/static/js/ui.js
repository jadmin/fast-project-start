/**
 * 分页页码显示工具条，需引用bootstrap.js/ jQuery.js / bootstrap.css即可
 * <ul class="pagination" style="margin:0 0" id="J_pageBar">
 * </ul>
 */
function pagebar(totalPage, currentPage, groupNum) {
	if(1 >= totalPage) { // 仅有1页
		$("#J_pageBar").hide();
		return;
	}
	var html = "";
	var canFirst = (currentPage <= totalPage) && currentPage > 1;
	var canPrev = (currentPage - 1) >= 1;
	var canNext = (currentPage + 1) <= totalPage;
	var canLast = currentPage < totalPage;
	
	
	var canFirstHtml = "";
	if(canFirst) {
		canFirstHtml = '<li class="page-first"><a href="javascript:goPage(1)">«</a></li>';
	} else {
		canFirstHtml = '<li class="page-first disabled"><a href="javascript:void(0)">«</a></li>';
	}
	html += canFirstHtml;
	
	var canPrevHtml = "";
	if(canPrev) {
		canPrevHtml = '<li class="page-pre"><a href="javascript:goPage(' + (currentPage-1) + ')">‹</a></li>';
	} else {
		canPrevHtml = '<li class="page-pre disabled"><a href="javascript:void(0)">‹</a></li>';
	}
	html += canPrevHtml;
	
	// 得到要显示页码数量的一半  
    var offset = parseInt(groupNum / 2);  
    // 得到开始页码和结束页码  
    var minNum = currentPage - offset;  
    var maxNum = currentPage + offset;  
  
    // 修复较小的页码  

    // 修复结束页码  
    if (minNum < 1) {  
        var tmp = 0 - minNum + 1 + maxNum;  
        minNum = 1;  
        maxNum = (tmp > totalPage ? totalPage : tmp);  
    }  
  
    // 修复开始页码  
    if (maxNum > totalPage) {  
        var tmp = maxNum - totalPage;  
        maxNum = totalPage;  
        minNum = minNum - tmp;  
        if (minNum < 1) {  
            minNum = 1;  
        }  
    }  
	
	for(var i = minNum; i <= maxNum; i++) {
		if(i == currentPage) {
			html += '<li class="page-number active"><a href="javascript:void(0)">' + i + '</a></li>';
		} else {
			html += '<li class="page-number"><a href="javascript:goPage(' + i + ')">' + i + '</a></li>';
		}
	}
	
	var canNextHtml = "";
	if(canNext) {
		canNextHtml = '<li class="page-next"><a href="javascript:goPage(' + (currentPage+1) + ')">›</a></li>';
	} else {
		canNextHtml = '<li class="page-next disabled"><a href="javascript:void(0)">›</a></li>';
	}
	html += canNextHtml;
	
	var canLastHtml = "";
	if(canLast) {
		canLastHtml = '<li class="page-last"><a href="javascript:goPage(' + totalPage + ')">»</a></li>';
	} else {
		canLastHtml = '<li class="page-last disabled"><a href="javascript:void(0)">»</a></li>';
	}
	html += canLastHtml;
	
	
	$("#J_pageBar").html(html);
	$("#J_pageBar").show();
}

/**
 * 输出label标签组, bootstrap标签按钮样式
 */
function grouplabel(cssClass, array, rows = false) {
	if("undefined" == typeof(array) || null == array || 0 >= array.length) {
		return '';
	}
	var html = '';
	if(rows) {
		for(var i = 0; i < array.length; i++) {
			if(i == 0) {
				html += "<div><span class='" + cssClass + "'>" + array[i] + "</span></div>";
			} else {
				html += "<div style='margin-top:5px'><span class='" + cssClass + "'>" + array[i] + "</span></div>";
			}
	    }
		return html;
	}
	
	for(var i = 0; i < array.length; i++) {
		html += "<span class='" + cssClass + "'>" + array[i] + "</span>&nbsp;";
    }
	return html;
}

/**
 * 输出单个标签
 */
function label(labelStyle, text, withLastGap = false) {
	var cssClass = 'label label-' + labelStyle;
	var html = '<span class="' + cssClass + '">' + text + '</span>';
	if(withLastGap===true) {
		html += '&nbsp;';
	}
	return html;
}

/**
 * 根据枚举值输出标签样式
 * var config = {
	  '-1' : 'danger:不可用',
	  '0' : 'primary:调试中',
	  '1' : 'success:可用',
      '2' : 'gray:XXX'
	};
 */
function labelByEnum(value, config) {
	for(var k in config) {
		if(k == value) {
			var pair = config[k];
			var arr = splitAndTrim(pair, ':');
			return label(arr[0], arr[1]);
		}
	}
}

/**
 * 根据枚举值输出文本内容
 * var config = {
	  '1' : '显示1',
      '2' : '显示3',
      '3' : '显示3'
	};
 */
function textByEnum(value, config) {
	for(var k in config) {
		if(k == value) {
			return config[k];
		}
	}
}

function codeText(text) {
	return '<code>' + text + '</code>';
}


