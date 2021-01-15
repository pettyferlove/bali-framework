layui.use(['layer'], function () {
    let layer = layui.layer;


    $("#upload").on("click", function (){
        layer.open({
            type: 2,
            title: '上传头像',
            area: ['50%', '50%'],
            content: '/personal/avatar/'
            , yes: function (index, layero) {
                return false;
            }
            , btn2: function (index) {
                return false;
            }
        });
    })

})