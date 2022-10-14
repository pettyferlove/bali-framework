layui.use(['layer', 'table'], function () {
    let layer = layui.layer
        , table = layui.table

    let module = '/client';
    let moduleName = '应用';

    table.render({
        elem: '#table'
        , height: 420
        , url: module + '/list' //数据接口
        , title: moduleName + '列表'
        , page: true //开启分页
        , toolbar: '#defaultToolbar' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        , cols: [[ //表头
            {type: 'checkbox', fixed: 'left'}
            , {field: 'applicationName', title: '应用名称', sort: true, fixed: 'left'}
            , {field: 'authorizedGrantTypes', title: '授权模式'}
            , {field: 'webServerRedirectUri', title: '回调URL'}
            , {field: 'accessTokenValidity', title: 'Token有效时间', width: 100, sort: true, totalRow: true}
            , {field: 'createTime', title: '创建时间', sort: true,}
            , {field: 'modifyTime', title: '最后修改时间', sort: true,}
            , {fixed: 'right', width: 200, align: 'center', toolbar: '#action'}
        ]], response: {
            statusCode: 0 //重新规定成功的状态码为 200，table 组件默认为 0
        }
        , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
            return {
                "code": res.code, //解析接口状态
                "count": res.data.total, //解析数据长度
                "data": res.data.records //解析数据列表
            };
        }
    });

    let $ = layui.$, active = {
        reload: function () {
            table.reload('table', {
                page: {
                    curr: 1
                }
                , where: {
                    clientId: $('#clientId').val()
                }
            }, 'data');
        },
        reset: function () {
            $('#clientId').val("");
            table.reload('table', {
                page: {
                    curr: 1
                }, where: {
                    clientId: null
                }
            }, 'data');
        }
    };

    $('.search .layui-btn').on('click', function () {
        let type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    //监听头工具栏事件
    table.on('toolbar(filter)', function (obj) {
        let checkStatus = table.checkStatus(obj.config.id)
            , data = checkStatus.data; //获取选中的数据
        switch (obj.event) {
            case 'add':
                add();
                break;
            case 'update':
                if (data.length === 0) {
                    layer.msg('请选择一行');
                } else if (data.length > 1) {
                    layer.msg('只能同时编辑一个');
                } else {
                    edit(checkStatus.data[0].id);
                }
                break;
            case 'delete':
                if (data.length === 0) {
                    layer.msg('请选择一行');
                } else {
                    let ids = $.map(data, function(item){
                        return item.id;
                    }).join(',');
                    del(ids);
                }
                break;
        }
        ;
    });

    //监听行工具事件
    table.on('tool(filter)', function (obj) {
        let data = obj.data
            , layEvent = obj.event;
        if (layEvent === 'detail') {
            layer.msg('查看操作');
        } else if (layEvent === 'del') {
            del(data.id);
        } else if (layEvent === 'edit') {
            edit(data.id);
        } else if (layEvent === 'viewSecret') {
            viewSecret(data.id);
        }
    });

    function add(){
        layer.open({
            type: 2,
            title: '添加' + moduleName,
            btn: ['创建', '取消'],
            area: ['60%', '80%'],
            content: module + '/add'
            , yes: function (index, layero) {
                let submit = layero.find('iframe').contents().find("#form-submit");
                submit.click();
            }
            , btn2: function (index) {
                layer.close(index)
                return false;
            }
        });
    }

    function del(ids){
        layer.confirm('确认删除所选客户端？', function (index) {
            $.ajax({
                type: "DELETE",
                url: module + '/delete/' + ids,
                success: function (res) {
                    if(res.message){
                        layer.msg(res.message, {icon: 2});
                    } else {
                        layer.msg('删除成功');
                        table.reload('table')
                    }
                },
                error: function (err) {
                    layer.msg('删除失败', {icon: 2});
                }
            })
            layer.close(index);
        });
    }

    function edit(id) {
        layer.open({
            type: 2,
            title: '编辑' + moduleName,
            btn: ['保存', '取消'],
            area: ['60%', '80%'],
            content: module + '/edit/' + id
            , yes: function (index, layero) {
                let submit = layero.find('iframe').contents().find("#form-submit");
                submit.click();
            }
            , btn2: function (index) {
                layer.close(index)
                return false;
            }
        });
    }

    function viewSecret(id) {
        $.ajax({
            type: "GET",
            url: "/client/view-secret/" + id,
            dataType: "json",
            success: function (res) {
                if (res.message) {
                    layer.msg(res.message, {icon: 2});
                } else {
                    layer.open({
                        type: 1,
                        anim: 2,
                        area: ['520', '250'],
                        title: '客户端令牌',
                        shadeClose: false,
                        content: '<div style="padding: 10px">\n' +
                            '    <blockquote class="layui-elem-quote">旧版本生成的Client不支持查看ClientSecret</blockquote>\n' +
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
                layer.msg('获取客户端信息失败', {icon: 2});
            }
        })
    }

});