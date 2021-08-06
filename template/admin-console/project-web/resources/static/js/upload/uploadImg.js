let imgSrcMap = new Map();

$(function () {
    // 鼠标经过显示删除按钮
    $('.content-img-list').on('mouseover', '.content-img-list-item', function () {
        $(this).children('div').removeClass('hide');
    });
    // 鼠标离开隐藏删除按钮
    $('.content-img-list').on('mouseleave', '.content-img-list-item', function () {
        $(this).children('div').addClass('hide');
    });

    // 单个图片删除
    $(".content-img-list").on("click", '.content-img-list-item a .gcllajitong', function () {
        let key = $(this).attr('key')
        imgSrcMap.delete(key);
        addNewContent(key, null)
    });

});


//图片展示
function addNewContent(key, imgSrc) {
    let imgSrcList = imgSrcMap.get(key);
    if (imgSrcList === undefined) {
        imgSrcList = [];
    }
    if (imgSrc !== null) {
        imgSrcList.push(imgSrc);
    }
    imgSrcMap.set(key, imgSrcList);
    let obj = key + ' .content-img-list';
    if (imgSrcList.length < 1) { //显示上传按钮
        $(key + ' .file').show();
    } else {
        $(key + ' .file').hide();
    }
    $(obj).html("");
    for (let a = 0; a < imgSrcList.length; a++) {
        let oldBox = $(obj).html();
        $(obj).html(oldBox + '<li class="content-img-list-item"><img src="' + imgSrcList[a] + '" alt="">' +
            '<div class="hide"><a index="' + a + '" class="delete-btn"><i class="gcl gcllajitong"' + ' key="' + key + '"></i></a></div>' +
            '</li>');
    }
}

// 建立可存取到file的url
function getObjectURL(file, appPath) {
    let formData = new FormData();
    formData.append('file', file);
    let url = null;
    $.ajax({
        url: appPath + "/admin/rss/upload",
        type: 'post',
        data: formData,
        dataType: "json",
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function (data) {
            if (data.status) {
                url = data.entry;
            } else {
                alert(data.message);
            }
        },
        error: function (request) {
            alert("网络错误，请重试！");
        },
    });
    return url;
}

function initImg(key) {
    imgSrcMap.delete(key);
}

function getImgByKey(key) {
    return imgSrcMap.get(key);
}
