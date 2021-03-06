layui.use(['form'], function () {
    let form = layui.form, layer = layui.layer

    form.verify({
        scope: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (value && value.length > 0) {
                if (!/^[a-zA-Z.]*$/.test(value)) {
                    return "只能输入英文字符或小数点";
                }
            } else {
                return "域是必填项，不能为空";
            }
        }
    });

    form.on('submit(form-submit)', function (data) {
        if (!data.field.auto) {
            data.field.auto = 'false'
        }
        let index = parent.layer.getFrameIndex(window.name);
        $.ajax({
            type: "POST",
            url: "/client/scope/create",
            data: JSON.stringify(data.field),
            contentType: "application/json",
            dataType: "json",
            success: function (res) {
                if (res.message) {
                    layer.msg(res.message, {icon: 2});
                } else {
                    layer.msg('新增成功');
                    parent.layui.table.reload('table');
                    parent.layer.close(index);
                }
            },
            error: function (err) {
                layer.msg('新增失败', {icon: 2});
            }
        })
        return false;
    });
});