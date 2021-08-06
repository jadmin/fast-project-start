/**
 * 将长整型时间戳转换成字符串(yyyy-MM-dd HH:mm:ss)
 */
function formatTime(time, format = 'yyyy-MM-dd HH:mm:ss') {
	var datetime = new Date();
	datetime.setTime(time);
	var year = datetime.getFullYear();
	var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
	var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
	var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
	var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
	var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
	if (format=='yyyy-MM-dd' || format=='yyyyMMdd' || format=='yyyy/MM/dd') {
		return year + "-" + month + "-" + date;
	} else if (format == 'HH:mm:ss') {
		return hour + ":" + minute + ":" + second;
	} else if (format=='yyyy') {
		return year;
	} else if (format=='yyyyMM') {
		return year + "" + month;
	}
	
	return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
}

function isEmpty(e) {
	return (null == e || "undefined" == typeof(e) || "" == e);
}

function isBlank(str) {
	if( isEmpty(str) ) {
		return true;
	}
	return isEmpty(str.toString().trim());
}

function isNullObject(obj) {
	return (obj == null || typeof(obj) == "undefined");
}

function splitAndTrim(str, sep) {
	if( isBlank(str) ) {
		return new Array();
	}
	var arr = str.trim().split(sep);
	var array = new Array();
	for(var i = 0; i < arr.length; i++) {
        if( isBlank(arr[i]) ) {
        	continue;
        }
        array.push(arr[i].trim());
    }
	return array;
}

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

/**
 * 判断某个元素是否存在于数组
 */
function isInArray(arr, value) {
    for(var i = 0; i < arr.length; i++) {
        if(value === arr[i]) {
            return true;
        }
    }
    return false;
}

/**
 * 人民币分转元
 */
function toYuan(fen) {
	if(isEmpty(fen)) {
		return "";
	}
	var yuan = fen/100.0;
	return yuan.toFixed(2);
}

/**
 * 小数转百分比
 */
function toPercent(point, percentChars = false) {
	if(isEmpty(point)) {
		return "";
	}
    var str = Number(point*100).toFixed(2);
    if(percentChars == true) {
    	str += "%";
    }
    return str;
}

/**
 * 将长整形数值转换成'yyyy-MM-dd HH:mm:ss'格式的时间戳字符串
 */
function toTimestampString(time) {
	var datetime = new Date();
	datetime.setTime(time);
	var year = datetime.getFullYear();
	var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
	var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
	var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
	var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
	var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
	return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
}

/**
 * 将长整形数值转换成'yyyy-MM-dd HH:mm:ss'格式的时间戳字符串
 */
function toTimestampWithoutSecondString(time) {
	var datetime = new Date();
	datetime.setTime(time);
	var year = datetime.getFullYear();
	var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
	var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
	return year + "-" + month + "-" + date
}

function timestampFormatter(value, row, index) {
	return toTimestampString(value);
}

function sleepDelay(numberMillis) { 
	var now = new Date(); 
	var exitTime = now.getTime() + numberMillis; 
	while (true) {
		now = new Date(); 
		if (now.getTime() > exitTime) {
			return;
		}
	} 
}

function totalPageNum(total, pageSize) {
	var num = total%pageSize;
	if(num > 0) {
		return parseInt(total/pageSize) + 1;
	}
	
	return parseInt(total/pageSize);
}





