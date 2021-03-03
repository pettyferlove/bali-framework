function dataURLtoBlob(dataUrl) {
    let arr = dataUrl.split(',')
    let mime = arr[0].match(/:(.*?);/)[1]

    let b = atob(arr[1])
    let n = b.length
    let u8arr = new Uint8Array(n)
    while (n--) {
        u8arr[n] = b.charCodeAt(n)
    }
    return new Blob([u8arr], { type: mime })
}

layui.use('upload', function(){
    let layer = layui.layer
    let upload = layui.upload;
    let container = document.querySelector('.img-container');
    let image = container.getElementsByTagName('img').item(0);
    let options = {
        aspectRatio: 1,
        preview: '.img-preview',
        ready: function (e) {
        },
        cropstart: function (e) {
        },
        cropmove: function (e) {
        },
        cropend: function (e) {
        },
        crop: function (e) {
        },
        zoom: function (e) {
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
            });
        }
    });

    $("#left").on("click", function () {
        cropper.rotate(-90);
    })
    $("#right").on("click", function () {
        cropper.rotate(90);
    })
    let index = parent.layer.getFrameIndex(window.name);
    $('#crop').on("click", function() {
        let img = cropper.getCroppedCanvas().toDataURL("image/jpeg", 0.1);
        let file = dataURLtoBlob(img)

        let formData = new FormData();
        formData.append("file", file, "avatar");
        formData.append("security","PublicRead");
        formData.append("group","avatar");
        formData.append("storage","CloudAliyunOSS");

        $.ajax({
            url: "/personal/avatar/upload",
            type: "POST",
            contentType: false,
            processData: false,
            data: formData,
            success: function (res) {
                parent.$('#avatar').attr("src", res.data.url);
                parent.updateAvatar();
                parent.layer.close(index);
            },
            error: function (err) {
                layer.msg('头像保存失败', {icon: 2});
            }
        });
    })
});
