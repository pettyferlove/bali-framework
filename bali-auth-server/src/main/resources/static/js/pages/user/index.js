layui.use(['layer', 'table'], function () {
    let layer = layui.layer
        , table = layui.table

    let module = '/user';
    let moduleName = '用户';

    table.render({
        elem: '#table'
        , height: 420
        , url: module + '/list' //数据接口
        , title: moduleName + '列表'
        , page: true //开启分页
        , toolbar: '#userToolbar' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        , cols: [[ //表头
            {type: 'checkbox', fixed: 'left'}
            , {type: 'id', fixed: 'left', hide: true}
            , {field: 'userName', title: '姓名', sort: true, fixed: 'left', templet: '#userNameTpl'}
            , {field: 'nickName', title: '用户昵称', sort: true}
            , {field: 'email', title: '电子邮箱'}
            , {field: 'status', title: '账号状态', width: 100, align: 'center', templet: '#tatusTpl'}
            , {field: 'userChannel', title: '用户渠道', width: 100, align: 'center', templet: '#userChannelTpl'}
            , {field: 'modifierName', title: '最后修改人', sort: true,}
            , {field: 'modifyTime', title: '最后修改时间', sort: true,}
            , {fixed: 'right', width: 185, align: 'center', toolbar: '#action'}
        ]], response: {
            statusCode: 200 //重新规定成功的状态码为 200，table 组件默认为 0
        }
        , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
            return {
                "code": res.status, //解析接口状态
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
                    userName: $('#userName').val()
                }
            }, 'data');
        },
        reset: function () {
            $('#userName').val("");
            table.reload('table', {
                page: {
                    curr: 1
                }, where: {
                    userName: null
                }
            }, 'data');
        }
    };

    $('.search .layui-btn').on('click', function () {
        let type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });


    table.on('toolbar(filter)', function (obj) {
        let checkStatus = table.checkStatus(obj.config.id)
            , data = checkStatus.data; //获取选中的数据
        let ids = [];
        data.forEach(i => {
            ids.push(i.id);
        })
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
                    edit(checkStatus.data[0].id, checkStatus.data[0].userChannel);
                }
                break;
            case 'reset':
                if (data.length === 0) {
                    layer.msg('请选择一行');
                } else {
                    layer.confirm('是否将所选账号的用户密码重置为123456？', function (index) {
                        $.ajax({
                            type: "PUT",
                            url: module + '/reset/password',
                            data: {ids: ids.join(','), password: 123456},
                            success: function (res) {
                                if (res.message) {
                                    layer.msg(res.message, {icon: 2});
                                } else {
                                    layer.msg('重置成功');
                                    table.reload('table')
                                }
                            },
                            error: function (err) {
                                layer.msg('重置失败', {icon: 2});
                            }
                        })
                        layer.close(index);
                    });
                }
                break;
            case 'delete':
                if (data.length === 0) {
                    layer.msg('请选择一行');
                } else if (data.length > 1) {
                    layer.msg('只能同时删除一行');
                } else {
                    del(checkStatus.data[0].id);
                }
                break;
        }
        ;
    });

    table.on('tool(filter)', function (obj) {
        let data = obj.data
            , layEvent = obj.event;
        if (layEvent === 'distributionRoles') {
            if (data.userChannel === 'web' || data.userChannel === 'maintainer') {
                layer.open({
                    type: 2,
                    title: '分配角色',
                    btn: ['更新', '取消'],
                    area: ['754px', '470px'],
                    content: module + '/distribution-roles/' + data.id
                    , yes: function (index, layero) {
                        let submit = layero.find('iframe').contents().find("#form-submit");
                        submit.click();
                    }
                    , btn2: function (index) {
                        layer.close(index)
                        return false;
                    }
                });
            } else {
                layer.msg('不支持在Web端修改移动端用户信息');
            }
        } else if (layEvent === 'del') {
            del(data.id);
        } else if (layEvent === 'edit') {
            edit(data.id, data.userChannel);
        }
    });

    function add() {
        layer.open({
            type: 2,
            title: '添加' + moduleName,
            btn: ['创建', '取消'],
            area: ['70%', '70%'],
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

    function del(id) {
        layer.confirm('真的删除行么', function (index) {
            $.ajax({
                type: "DELETE",
                url: module + '/delete/' + id,
                success: function (res) {
                    if (res.message) {
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

    function edit(id, type) {
        if (type === 'web' || type === 'maintainer') {
            layer.open({
                type: 2,
                title: '编辑' + moduleName,
                btn: ['保存', '取消'],
                area: ['70%', '70%'],
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
        } else {
            layer.msg('不支持在Web端修改移动端用户信息');
        }
    }

});