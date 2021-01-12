layui.use(['form'], function () {
    let form = layui.form, layer = layui.layer

    //自定义验证规则
    form.verify({
        role: function (value) {
            if (value && !/^([a-zA-z_]{1})([\w]*)$/g.test(value)) {
                return '只能输入英文字符和下划线';
            }
        }
        , pass: [
            /^[\S]{6,12}$/
            , '密码必须6到12位，且不能出现空格'
        ]
    });

    //监听提交
    form.on('submit(form-submit)', function (data) {
        let index = parent.layer.getFrameIndex(window.name);
        $.ajax({
            type: "POST",
            url: "/role/create",
            data: JSON.stringify(data.field),
            contentType: "application/json",
            dataType: "json",
            success: function (res) {
                layer.msg('新增成功');
                parent.layui.table.reload('table');
                parent.layer.close(index);
            },
            error: function (err) {
                layer.msg('新增失败', {icon: 2});
            }
        })
        return false;
    });
});