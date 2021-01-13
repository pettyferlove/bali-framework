layui.use(['form', 'laydate'], function () {
    let form = layui.form, layer = layui.layer, laydate = layui.laydate;

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
                if(res.message){
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