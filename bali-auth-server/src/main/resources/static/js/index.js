$(function () {
    $("body").on("click", "*[path]", function () {
        let e = $(this);
        let path = e.attr("path");
        let title = e.attr("title");
        console.log("click")
        $("iframe").attr("src", path)
        selectMenu(path);
    })

    function selectMenu(path) {
        $(".layui-this").each(function () {
            $(this).removeClass('layui-this');
        });
        $(".header-menu a").each(function () {
            let t_path = $(this).attr('path');
            if (path === t_path) {
                $(this).parent().addClass('layui-this');
                $(this).parents(".layui-nav-item").addClass('layui-this');
                $(this).parents(".layui-nav-child").removeClass('layui-show');
            }
        });
    }
})