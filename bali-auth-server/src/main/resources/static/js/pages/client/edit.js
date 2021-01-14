layui.use(['form'], function(){
    let form = layui.form,layer = layui.layer
    //监听提交
    form.on('submit(form-submit)', function(data){

        let index = parent.layer.getFrameIndex(window.name);
        $.ajax({
            type: "PUT",
            url: "/client/update",
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