layui.use(['laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element', 'slider'], function () {
    let laydate = layui.laydate
        , layer = layui.layer //弹层
        , table = layui.table

    //执行一个 table 实例
    table.render({
        elem: '#table'
        , height: 420
        , url: '/role/list' //数据接口
        , title: '角色表'
        , page: true //开启分页
        , toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        , cols: [[ //表头
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', title: 'ID', sort: true, fixed: 'left'}
            , {field: 'role', title: '角色'}
            , {field: 'roleName', title: '角色名', sort: true, totalRow: true}
            , {field: 'description', title: '描述'}
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

    //监听头工具栏事件
    table.on('toolbar(filter)', function (obj) {
        let checkStatus = table.checkStatus(obj.config.id)
            , data = checkStatus.data; //获取选中的数据
        switch (obj.event) {
            case 'add':
                layer.open({
                    type: 2,
                    title: '添加角色',
                    btn: ['创建', '取消'],
                    area: ['50%', '50%'],
                    content: '/role/add'
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
                        title: '编辑角色',
                        btn: ['保存', '取消'],
                        area: ['50%', '50%'],
                        content: '/role/edit/' + checkStatus.data[0].id
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
                    layer.msg('删除');
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
                obj.del();
                layer.close(index);
            });
        } else if (layEvent === 'edit') {
            layer.open({
                type: 2,
                title: '编辑角色',
                btn: ['保存', '取消'],
                area: ['50%', '50%'],
                content: '/role/edit/' + data.id
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

    //将日期直接嵌套在指定容器中
    let dateIns = laydate.render({
        elem: '#laydateDemo'
        , position: 'static'
        , calendar: true //是否开启公历重要节日
        , mark: { //标记重要日子
            '0-10-14': '生日'
            , '2020-01-18': '小年'
            , '2020-01-24': '除夕'
            , '2020-01-25': '春节'
            , '2020-02-01': '上班'
        }
        , done: function (value, date, endDate) {
            if (date.year == 2017 && date.month == 11 && date.date == 30) {
                dateIns.hint('一不小心就月底了呢');
            }
        }
        , change: function (value, date, endDate) {
            layer.msg(value)
        }
    });

});