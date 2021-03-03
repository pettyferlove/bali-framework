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
    console.log(index)
    $('#crop').on("click", function() {
        let img = cropper.getCroppedCanvas().toDataURL("image/png");

        img = img.split(',')[1];
        img = window.atob(img);
        let ia = new Uint8Array(img.length);
        for (let i = 0; i < img.length; i++) {
            ia[i] = img.charCodeAt(i);
        }
        let file = new Blob([ia], {
            type: "image/png"
        });

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
                parent.layer.close(index);
            },
            error: function (err) {
                console.log(err)
            }
        });
    })
});
