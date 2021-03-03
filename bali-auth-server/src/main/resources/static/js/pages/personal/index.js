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
        console.log(data)
    })

})