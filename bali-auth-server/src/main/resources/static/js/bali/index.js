$(function () {
    $("body").on("click", "*[lay-href]", function () {
        let e = $(this);
        let url = e.attr("lay-href");
        let title = e.attr("lay-text");
        console.log("click")
        $("iframe").attr("src", url)
    })
})