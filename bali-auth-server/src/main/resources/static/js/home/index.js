layui.use([], function () {
    var myChart = echarts.init(document.getElementById('user-views'));

    // 指定图表的配置项和数据
    var option = {
        tooltip: {trigger: "axis"},
        calculable: !0,
        legend: {data: ["访问量", "下载量", "平均访问量"]},
        xAxis: [{
            type: "category",
            data: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"]
        }],
        yAxis: [{type: "value", name: "访问量", axisLabel: {formatter: "{value} 万"}}, {
            type: "value",
            name: "下载量",
            axisLabel: {formatter: "{value} 万"}
        }],
        series: [{
            name: "访问量",
            type: "line",
            data: [900, 850, 950, 1e3, 1100, 1050, 1e3, 1150, 1250, 1370, 1250, 1100]
        }, {
            name: "下载量",
            type: "line",
            yAxisIndex: 1,
            data: [850, 850, 800, 950, 1e3, 950, 950, 1150, 1100, 1240, 1e3, 950]
        }, {name: "平均访问量", type: "line", data: [870, 850, 850, 950, 1050, 1e3, 980, 1150, 1e3, 1300, 1150, 1e3]}]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
});