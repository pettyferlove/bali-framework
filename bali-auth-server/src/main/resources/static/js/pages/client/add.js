layui.use(['form'], function () {
    let form = layui.form, layer = layui.layer
    //监听提交
    form.on('submit(form-submit)', function (data) {
        let authorizedGrantTypes = [];
        $("input:checkbox[name='authorizedGrantTypes']:checked").each(function(){
            authorizedGrantTypes.push($(this).val());
        });
        data.field.authorizedGrantTypes = authorizedGrantTypes.join(",") ;

        let scope = [];
        $("input:checkbox[name='scope']:checked").each(function(){
            scope.push($(this).val());
        });
        data.field.scope = scope.join(",") ;
        let index = parent.layer.getFrameIndex(window.name);
        $.ajax({
            type: "POST",
            url: "/client/create",
            data: JSON.stringify(data.field),
            contentType: "application/json",
            dataType: "json",
            success: function (res) {
                if (res.message) {
                    layer.msg(res.message, {icon: 2});
                } else {
                    parent.layer.close(index);
                    parent.layer.open({
                        type: 1,
                        anim: 2,
                        area: ['520', '250'],
                        title: '客户端令牌',
                        shadeClose: false,
                        content: '<div style="padding: 10px">\n' +
                            '    <blockquote class="layui-elem-quote">请妥善保存，关闭后无法再次获取以下信息</blockquote>\n' +
                            '    <table class="layui-table" lay-even="" lay-skin="nob">\n' +
                            '        <colgroup>\n' +
                            '            <col width="250">\n' +
                            '            <col width="250">\n' +
                            '            <col>\n' +
                            '        </colgroup>\n' +
                            '        <thead>\n' +
                            '        <tr>\n' +
                            '            <th>Client ID</th>\n' +
                            '            <th>'+res.data.clientId+'</th>\n' +
                            '        </tr>\n' +
                            '        </thead>\n' +
                            '        <tbody>\n' +
                            '        <tr>\n' +
                            '            <td>Client Secret</td>\n' +
                            '            <td>'+res.data.clientSecret+'</td>\n' +
                            '        </tr>\n' +
                            '        </tbody>\n' +
                            '    </table>\n' +
                            '</div>'
                        , yes: function (index) {
                            layer.close(index);
                        }
                    });
                }
            },
            error: function (err) {
                layer.msg('新增失败', {icon: 2});
            }
        })
        return false;
    });
});