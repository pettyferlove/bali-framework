layui.use(['form'], function(){
    let form = layui.form,layer = layui.layer

    form.verify({
        contactNumber: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (value && value.length > 0) {
                if (
                    !/^[1][3-9][0-9]{9}$/.test(value) &&
                    !/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/.test(value)
                ) {
                    if (!/^[1][3-9][0-9]{9}$/.test(value)) {
                        return "不是有效的手机号码";
                    } else {
                        return "固定电话有误，请重填";
                    }
                }
            } else {
                return "联系电话是必填项，不能为空";
            }
        },
        email: function (value, item) {
            if (value && value.length > 0) {
                if (!/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/.test(value)) {
                    return '不是有效的email地址';
                }
            } else {
                return "请输入电子邮箱";
            }
        },
    });

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