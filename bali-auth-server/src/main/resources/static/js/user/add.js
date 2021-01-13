layui.use(['form', 'laydate'], function () {
    let form = layui.form, layer = layui.layer, laydate = layui.laydate;
    lay('.date-select').each(function(){
        laydate.render({
            elem: this
            ,format:'yyyy-MM-dd HH:mm:ss'
            ,type:'datetime'
            ,trigger: 'click'
        });
    });
    form.on('submit(form-submit)', function (data) {
        if(!data.field.status){
            data.field.status = 0
        }
        let index = parent.layer.getFrameIndex(window.name);
        $.ajax({
            type: "POST",
            url: "/user/create",
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