layui.use(['form'], function(){
    let form = layui.form,layer = layui.layer
    //监听提交
    form.on('submit(form-submit)', function(data){
        if(!data.field.status){
            data.field.status = 0
        }
        let index = parent.layer.getFrameIndex(window.name);
        $.ajax({
            type: "PUT",
            url: "/tenant/update",
            data: JSON.stringify(data.field),
            contentType: "application/json",
            dataType: "json",
            success: function (res) {
                layer.msg('修改成功');
                parent.layui.table.reload('table');
                parent.layer.close(index);
            },
            error: function (err) {
                layer.msg('修改失败', {icon: 2});
            }
        })
        return false;
    });
});