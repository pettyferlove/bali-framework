layui.use(['form', 'laydate'], function () {
    let form = layui.form, layer = layui.layer, laydate = layui.laydate;

    form.verify({
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

    $("#upload").on("click", function (){
        layer.open({
            type: 2,
            title: '上传头像',
            area: ['1000px', '700px'],
            content: '/personal/avatar'
            , yes: function (index, layero) {
                return false;
            }
            , btn2: function (index) {
                return false;
            }
        });
    })

    form.on('submit(form-submit)', function (data) {
        data.field.userAvatar = $('#avatar').attr("src");
        $.ajax({
            type: "PUT",
            url: "/personal/info",
            data: JSON.stringify(data.field),
            contentType: "application/json",
            dataType: "json",
            success: function (res) {
                if (res.message) {
                    layer.msg(res.message, {icon: 2});
                } else {
                    layer.msg('更新成功');
                }
            },
            error: function (err) {
                layer.msg('更新失败', {icon: 2});
            }
        })
    })

})

function updateAvatar() {
    let data = {};
    data.userAvatar = $('#avatar').attr("src");
    $.ajax({
        type: "PUT",
        url: "/personal/info",
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "json",
        success: function (res) {
            if (res.message) {
                layer.msg(res.message, {icon: 2});
            } else {
                layer.msg('更新成功');
            }
        },
        error: function (err) {
            layer.msg('更新失败', {icon: 2});
        }
    })
}