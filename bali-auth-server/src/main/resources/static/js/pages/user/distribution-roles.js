layui.use(['form', 'transfer', 'layer', 'util'], function () {
    var $ = layui.$
        , transfer = layui.transfer
        , layer = layui.layer
        , form = layui.form
        , util = layui.util;

    let userId = $("#userId").val();

    $.ajax({
        type: "GET",
        url: "/user/" + userId + "/role",
        contentType: "application/json",
        dataType: "json",
        success: function (res) {
            if (res.message) {
                layer.msg(res.message, {icon: 2});
            } else {
                let selected = res.data.selected;
                let selectedIds = [];
                selected.forEach(i => {
                    selectedIds.push(i.id)
                })
                let toBeSelected = res.data.toBeSelected;
                transfer.render({
                    elem: '#role_select'
                    , title: ['待选角色', '已选角色']
                    , parseData: function (item) {
                        return {
                            "value": item.id
                            , "title": item.roleName + "-" + item.role
                            , "disabled": item.status === 0
                        }
                    }
                    , id: 'role_select'
                    , value: selectedIds
                    , data: toBeSelected
                    , width: 300
                    , height: 350 //定义高度
                })
            }
        }
    })

    form.on('submit(form-submit)', function (data) {
        let selectedData = transfer.getData('role_select');
        let roleIds = [];
        selectedData.forEach(i=>{
            roleIds.push(i.value)
        })
        let index = parent.layer.getFrameIndex(window.name);
        $.ajax({
            type: "PUT",
            url: "/user/" + userId + "/role",
            data: { roleIds: roleIds.join(",") },
            dataType: "json",
            success: function (res) {
                if (res.message) {
                    layer.msg(res.message, {icon: 2});
                } else {
                    layer.msg('更新成功');
                    parent.layui.table.reload('table');
                    parent.layer.close(index);
                }
            },
            error: function (err) {
                layer.msg('更新失败', {icon: 2});
            }
        })
        return false;
    });
});