"use strict";
var util = {
    /**
     * 获取工程基准路径
     * @returns {string}
     */
    get_base_path: function () {
        var url = document.location.toString();
        var arrayUrl = url.split("//");
        var index = arrayUrl[1].indexOf("/");
        //stop省略，截取从start开始到结尾的所有字符
        var relativeUrl = arrayUrl[1].substring(index);
        if (relativeUrl.indexOf("?") != -1) {
            relativeUrl = relativeUrl.split("?")[0];
        }
        var relativeUrls = relativeUrl.split("/");
        var context_path = relativeUrls[1];
        return url.substring(0, url.indexOf(context_path)) + context_path;
    }
};