layui.use(['layer', 'table'], function () {
    let layer = layui.layer
        , table = layui.table

    let module = '/client/scope';
    let moduleName = '应用域';

    table.render({
        elem: '#table'
        , height: 420
        , url: module + '/list' //数据接口
        , title: moduleName + '列表'
        , page: true //开启分页
        , toolbar: 'default'
        , cols: [[ //表头
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', title: 'ID', hide: true}
            , {field: 'scope', width: 200, title: '域', align: 'center', templet: '#scopeTpl'}
            , {field: 'description', title: '描述信息'}
            , {field: 'auto', title: '是否自动授权', width: 140, align: 'center', sort: true, totalRow: true, templet: '#autoTpl'}
            , {field: 'modifyTime', title: '最后修改时间', width: 180 , align: 'center', sort: true}
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
                    del(checkStatus.data[0].id);
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
            area: ['40%', '60%'],
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

    function del(id){
        layer.confirm('真的删除行么', function (index) {
            $.ajax({
                type: "DELETE",
                url: module + '/delete/' + id,
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
            area: ['40%', '60%'],
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