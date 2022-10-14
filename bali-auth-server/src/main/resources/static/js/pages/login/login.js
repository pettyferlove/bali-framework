layui.use('form', function(){
    let form = layui.form;
    form.verify({
        username: function(value, item){ //value：表单的值、item：表单的DOM对象
            if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
                return '用户名不能有特殊字符';
            }
            if(/(^\_)|(\__)|(\_+$)/.test(value)){
                return '用户名首尾不能出现下划线\'_\'';
            }
            if(/^\d+\d+\d$/.test(value)){
                return '用户名不能全为数字';
            }
        },
        password: [
            /^[\S]{6,64}$/
            ,'密码必须6到64位，且不能出现空格'
        ],
        captcha: function (value, item) {
            if (value && value.length > 0) {
                //
            } else {
                return "请输入验证码";
            }
        },
    });
});

function uuid() {
    const len = 32;//32长度
    let radix = 16;//16进制
    const chars = '0123456789abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz'.split('');
    let uuid = [], i;
    radix = radix || chars.length;
    if(len) {
        for(i = 0; i < len; i++)uuid[i] = chars[0 | Math.random() * radix];
    } else {
        let r;
        uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
        uuid[14] = '4';
        for(i = 0; i < 36; i++) {
            if(!uuid[i]) {
                r = 0 | Math.random() * 16;
                uuid[i] = chars[(i === 19) ? (r & 0x3) | 0x8 : r];
            }
        }
    }
    return uuid.join('');
}

function closeError() {
    $("#login_error").hide()
}

let machineCode;

function loadCaptcha() {
    if(!machineCode) {
        machineCode = uuid();
        $("#machine_code").val(machineCode);
    }
    $("#captcha_img").attr("src", "/captcha/render/" + machineCode);
}

$(function () {
    loadCaptcha();
    $("#captcha_img").on("click", function () {
        loadCaptcha();
    })
})