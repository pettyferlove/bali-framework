layui.use(['form', 'laydate'], function () {
    let form = layui.form, layer = layui.layer, laydate = layui.laydate;

    form.verify({
        loginId: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
                return '用户名不能有特殊字符';
            }
            if (/(^\_)|(\__)|(\_+$)/.test(value)) {
                return '用户名首尾不能出现下划线\'_\'';
            }
            if (/^\d+\d+\d$/.test(value)) {
                return '用户名不能全为数字';
            }
        },
        password: [
            /^[\S]{6,64}$/
            , '密码必须6到64位，且不能出现空格'
        ],
        email: function (value, item) {
            if (value !== "") {
                if (!/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/.test(value)) {
                    return '不是有效的email地址';
                }
            }
        }
    });

    lay('.date-select').each(function () {
        laydate.render({
            elem: this
            , format: 'yyyy-MM-dd HH:mm:ss'
            , type: 'datetime'
            , trigger: 'click'
        });
    });

    let tId = $("#tId").val();
    $.ajax({
        type: "GET",
        url: "/tenant/all",
        contentType: "application/json",
        dataType: "json",
        success: function (res) {
            $.each(res.data, function (index, item) {
                if (tId === item.tenantId) {
                    $('#tenantId').append(new Option(item.tenantName, item.tenantId, false, true));
                } else {
                    $('#tenantId').append(new Option(item.tenantName, item.tenantId, false, false));
                }
            });
            layui.form.render("select");
        }
    })

    form.on('submit(form-submit)', function (data) {
        if (!data.field.status) {
            data.field.status = 0
        }
        let index = parent.layer.getFrameIndex(window.name);
        $.ajax({
            type: "PUT",
            url: "/user/update",
            data: JSON.stringify(data.field),
            contentType: "application/json",
            dataType: "json",
            success: function (res) {
                if (res.message) {
                    layer.msg(res.message, {icon: 2});
                } else {
                    layer.msg('修改成功');
                    parent.layui.table.reload('table');
                    parent.layer.close(index);
                }
            },
            error: function (err) {
                layer.msg('修改失败', {icon: 2});
            }
        })
        return false;
    });
});