layui.use(['laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element', 'slider'], function () {
    let laydate = layui.laydate
        , layer = layui.layer //弹层
        , table = layui.table

    //执行一个 table 实例
    table.render({
        elem: '#table'
        , height: 420
        , url: '/tenant/list' //数据接口
        , title: '租户表'
        , page: true //开启分页
        , toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        , cols: [[ //表头
            {type: 'checkbox', fixed: 'left'}
            , {field: 'tenantId', title: '租户ID', sort: true, fixed: 'left'}
            , {field: 'tenantName', title: '租户名'}
            , {field: 'linkMan', title: '联系人', sort: true, totalRow: true}
            , {field: 'contactNumber', title: '联系电话'}
            , {field: 'email', title: '电子邮箱'}
            , {field: 'address', title: '地址', sort: true,}
            , {field: 'status', title: '启用状态', width: 80, align: 'center', templet: '#tenantStatus'}
            , {field: 'createTime', title: '创建时间', sort: true,}
            , {field: 'modifyTime', title: '最后修改时间', sort: true,}
            , {fixed: 'right', width: 165, align: 'center', toolbar: '#action'}
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
                    tenantName: $('#tenantName').val()
                }
            }, 'data');
        },
        reset: function () {
            $('#tenantName').val("");
            table.reload('table', {
                page: {
                    curr: 1
                }, where: {
                    tenantName: null
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
                layer.open({
                    type: 2,
                    title: '添加租户',
                    btn: ['创建', '取消'],
                    area: ['70%', '70%'],
                    content: '/tenant/add'
                    , yes: function (index, layero) {
                        let submit = layero.find('iframe').contents().find("#form-submit");
                        submit.click();
                    }
                    , btn2: function (index) {
                        layer.close(index)
                        return false;
                    }
                });
                break;
            case 'update':
                if (data.length === 0) {
                    layer.msg('请选择一行');
                } else if (data.length > 1) {
                    layer.msg('只能同时编辑一个');
                } else {
                    layer.open({
                        type: 2,
                        title: '编辑租户',
                        btn: ['保存', '取消'],
                        area: ['70%', '70%'],
                        content: '/tenant/edit/' + checkStatus.data[0].id
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
                break;
            case 'delete':
                if (data.length === 0) {
                    layer.msg('请选择一行');
                } else {
                    layer.confirm('真的删除行么', function (index) {
                        $.ajax({
                            type: "DELETE",
                            url: "/tenant/delete/" + checkStatus.data[0].id,
                            success: function (res) {
                                layer.msg('删除成功');
                                table.reload('table')
                            },
                            error: function (err) {
                                layer.msg('删除失败', {icon: 2});
                            }
                        })
                        layer.close(index);
                    });
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
            layer.confirm('真的删除行么', function (index) {
                $.ajax({
                    type: "DELETE",
                    url: "/tenant/delete/" + data.id,
                    success: function (res) {
                        layer.msg('删除成功');
                        table.reload('table')
                    },
                    error: function (err) {
                        layer.msg('删除失败', {icon: 2});
                    }
                })
                layer.close(index);
            });
        } else if (layEvent === 'edit') {
            layer.open({
                type: 2,
                title: '编辑租户',
                btn: ['保存', '取消'],
                area: ['70%', '70%'],
                content: '/tenant/edit/' + data.id
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
    });

});