layui.use('upload', function(){
    let upload = layui.upload;
    let container = document.querySelector('.img-container');
    let image = container.getElementsByTagName('img').item(0);
    let options = {
        aspectRatio: 1,
        preview: '.img-preview',
        ready: function (e) {
            console.log(e.type);
        },
        cropstart: function (e) {
            console.log(e.type, e.detail.action);
        },
        cropmove: function (e) {
            console.log(e.type, e.detail.action);
        },
        cropend: function (e) {
            console.log(e.type, e.detail.action);
        },
        crop: function (e) {
            console.log(e);
        },
        zoom: function (e) {
            console.log(e.type, e.detail.ratio);
        }
    };
    let cropper = new Cropper(image, options);


    upload.render({
        elem: '#upload'
        ,auto: false
        ,done: function(res){
            //上传完毕回调
        }
        ,error: function(){
            //请求异常回调
        },
        choose: function(obj){
            obj.preview(function(index, file, result){
                image.src = URL.createObjectURL(file);
                if (cropper) {
                    cropper.destroy();
                }
                cropper = new Cropper(image, options);
                //obj.resetFile(index, file, '123.jpg'); //重命名文件名，layui 2.3.0 开始新增

                //这里还可以做一些 append 文件列表 DOM 的操作

                //obj.upload(index, file); //对上传失败的单个文件重新上传，一般在某个事件中使用
                //delete files[index]; //删除列表中对应的文件，一般在某个事件中使用
            });
        }
    });
});
