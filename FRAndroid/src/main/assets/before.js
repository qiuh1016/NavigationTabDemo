/**全局作用域，用于传递给其他函数*/
var global = this;

/**添加一个log函数，可以用在javascript中进行输出调试*/
var loggerClass = Packages.com.fr.android.stable.IFLogger;
log = function (msg) {
    loggerClass.error(msg);
};

/**扩展函数对象*/
Function.prototype.createDelegate = function (obj) {
    var method = this;
    var args = arguments[1];
    var appendArgs = arguments[2];
    return function () {
        var callArgs = args || arguments;
        if (appendArgs === true) {
            callArgs = Array.prototype.slice.call(arguments, 0);
            callArgs = callArgs.concat(args);
        } else if (typeof appendArgs == "number") {
            callArgs = Array.prototype.slice.call(arguments, 0);
            // copy arguments first
            var applyArgs = [appendArgs, 0].concat(args);
            // create method call params
            Array.prototype.splice.apply(callArgs, applyArgs);
            // splice them in
        }
        return method.apply(obj, callArgs);
    };

};

/**定义全局的FR对象*/
FR = {};

// TODO:还没有实现国际化函数
FR.i18nText = function (key) {
    return 'i18n';
};

FR.tc = function (fn, context) {
    try {
        return fn.call(context);
    } catch (e) {
        log(e);
        throw e;
    }
};

_g = function(){
    return globalObject;
};

/**引入消息相关的java类用于定义和实现本地化的消息控件*/
var messageClass = Packages.com.fr.android.script.IFMessage;
FR.Msg = {};
FR.Msg.toast = function (message) {
    messageClass.toast(javaContext, message);
};

FR.Msg.alert = function (title, message, callback) {
    messageClass.alert(javaContext, global, title, message, callback);
};

FR.Msg.prompt = function (title, message, value, callback) {
    messageClass.prompt(javaContext, global, title, message, value, callback);
};

FR.Msg.confirm = function (title, message, callback) {
    messageClass.confirm(javaContext, global, title, message, callback);
};


/**
 * 客户端计算公式
 * @static
 * @param {String} formula 要计算的公式
 * @param {Object} initValue 初始值
 * @param {Boolean} must  是否必须总是计算
 * @returns {Object} 计算后的值
 */
FR.formulaEvaluator = function(formula, initValue, must) {
    return function() {
        if(initValue == undefined || must) {
            return formula;
        }

        return initValue.toString();
    }
};

/**引入超链java类用实现响应 超链*/
var hyperlinkClass = Packages.com.fr.android.script.IFJSDoHyperlink;

FR.doHyperlinkByPost = function (url, config, target, feature) {
    if(url && url.url) {
        hyperlinkClass.doHyperlinkByPost(javaContext, global, jsonToString(url), config, jssessionid);
    } else {
        hyperlinkClass.doHyperlinkByPost(javaContext, global, url, config, jssessionid);
    }
};

FR.doHyperlinkByGet = function (url, config, target, feature) {
    if(url && url.url) {// json格式 or 字符串
        hyperlinkClass.doHyperlinkByGet(javaContext, global, jsonToString(url), config, jssessionid);
    } else {
        hyperlinkClass.doHyperlinkByGet(javaContext, global, url, config, jssessionid);
    }
};


var ajaxClass = Packages.com.fr.android.script.IFJSAjax;

FR.ajax = function(jsonString){
    ajaxClass.doAjax(javaContext, global, jsonString);
};


var locationHelper = Packages.com.fr.android.script.IFLocationHelper;

/**
 * 地理位置
 * @param fn
 */
FR.location = function (fn) {
    //初始化GPS
    locationHelper.location(javaContext,global,fn);
};

var codeHelper = Packages.com.fr.android.utils.IFCodeUtils;

FR.cjkEncode = function(str){
    return String(codeHelper.cjkEncode(str));  //返回值转化为js的String
};

FR.cjkDecode = function(str){
    return String(codeHelper.cjkDecode(str));
};

var timer = Packages.com.fr.android.script.IFJSTimer;

setTimeout = function(fn,time){
    return timer.setTimeout(javaContext, global, fn, time, jssessionid);
};

setInterval = function(fn, time) {
    return timer.setInterval(javaContext, global, fn, time, jssessionid);
};

clearTimeout = function(id) {
    timer.clearInterval(id, jssessionid);
};

clearInterval = function(id) {
    timer.clearInterval(id, jssessionid);
};

var webUtils = Packages.com.fr.android.script.IFJSWebUtils;
FR.Chart = {};
FR.Chart.WebUtils = FR.Chart.WebUtils || {};
FR.Chart.WebUtils.getChart = function(str){
    return webUtils.getChart(str);
};

FR.logoutApp = function() {
    webUtils.logoutApp(javaContext, global);
};

FR.sentMail = function(mail) {
    webUtils.sentMail(javaContext, jsonToString(mail));
};

function jsonToString (obj){
    var THIS = this;
    switch(typeof(obj)){
        case 'string':
            return '"' + obj.replace(/(["\\])/g, '\\$1') + '"';
        case 'array':
            return '[' + obj.map(THIS.jsonToString).join(',') + ']';
        case 'object':
            if(obj instanceof Array){
                var strArr = [];
                var len = obj.length;
                for(var i=0; i<len; i++){
                    strArr.push(THIS.jsonToString(obj[i]));
                }
                return '[' + strArr.join(',') + ']';
            }else if(obj==null){
                return 'null';

            }else{
                var string = [];
                for (var property in obj) string.push(THIS.jsonToString(property) + ':' + THIS.jsonToString(obj[property]));
                return '{' + string.join(',') + '}';
            }
        case 'number':
            return obj;
        case false:
            return obj;
    }
}

