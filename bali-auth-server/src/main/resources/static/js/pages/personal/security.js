layui.use(['form'], function () {
    let form = layui.form, layer = layui.layer;


    form.verify({
        password: [
            /^[\S]{6,64}$/
            , '密码必须6到64位，且不能出现空格'
        ]
    });

    form.on('submit(password-form-submit)', function (form) {
        let data = form.field;
        if(data.newPassword !== data.repeatPassword){
            layer.msg('重复密码与新密码不一致', {icon: 2});
        }
        $.ajax({
            type: "POST",
            url: "/user/change/password",
            data: JSON.stringify(data),
            contentType: "application/json",
            dataType: "json",
            success: function (res) {
                if (res.message) {
                    layer.msg(res.message, {icon: 2});
                } else {
                    layer.msg('修改成功');
                }
            },
            error: function (err) {
                layer.msg('修改失败', {icon: 2});
            }
        })
        return false;
    });
});