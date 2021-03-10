layui.use(['layer', 'table'], function () {
    let layer = layui.layer
        , table = layui.table

    let module = '/role';
    let moduleName = '角色';

    table.render({
        elem: '#table'
        , height: 420
        , url: module + '/list' //数据接口
        , title: moduleName + '列表'
        , page: true //开启分页
        , toolbar: '#defaultToolbar' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        , cols: [[ //表头
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', title: 'ID', sort: true, fixed: 'left'}
            , {field: 'role', title: '角色'}
            , {field: 'roleName', title: '角色名', sort: true, totalRow: true}
            , {field: 'description', title: '描述'}
            , {field: 'createTime', title: '创建时间', sort: true,}
            , {field: 'modifyTime', title: '最后修改时间', sort: true,}
            , {fixed: 'right', width: 125, align: 'center', toolbar: '#action'}
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
            let roleName = $('#roleName');
            table.reload('table', {
                page: {
                    curr: 1
                }
                , where: {
                    roleName: roleName.val()
                }
            }, 'data');
        },
        reset: function () {
            $('#roleName').val("");
            table.reload('table', {
                page: {
                    curr: 1
                }, where: {
                    roleName: null
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
        switch (obj.event) {
            case 'add':
                add();
                break;
            case 'update':
                if (data.length === 0) {
                    layer.msg('请选择一个角色');
                } else if (data.length > 1) {
                    layer.msg('只能同时编辑一个角色');
                } else {
                    edit(checkStatus.data[0].id);
                }
                break;
            case 'delete':
                if (data.length === 0) {
                    layer.msg('请选择一个角色');
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
        }
    });

    function add(){
        layer.open({
            type: 2,
            title: '添加' + moduleName,
            btn: ['创建', '取消'],
            area: ['50%', '50%'],
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
        layer.confirm('确认删除所选角色？', function (index) {
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
            area: ['50%', '50%'],
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

});