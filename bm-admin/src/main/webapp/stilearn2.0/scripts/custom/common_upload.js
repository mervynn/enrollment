;var uploadUrl;
var demo_h5_upload_ops = {
    init:function(){
        this.eventBind();
    },
    eventBind:function(){
        var that = this;
        $("#upload").change(function(){
            var reader = new FileReader();
            reader.onload = function (e) {
                that.compress(this.result);
            };
            if(typeof(this.files[0]) != "undefined"){
            	reader.readAsDataURL(this.files[0]);
            }
        });
    },
    compress : function (res) {
        var that = this;
        var img = new Image(),
            maxH = 300;

        img.onload = function () {
            var cvs = document.createElement('canvas'),
                ctx = cvs.getContext('2d');

            if(img.height > maxH) {
                img.width *= maxH / img.height;
                img.height = maxH;
            }
            cvs.width = img.width;
            cvs.height = img.height;

            ctx.clearRect(0, 0, cvs.width, cvs.height);
            ctx.drawImage(img, 0, 0, img.width, img.height);
            var dataUrl = cvs.toDataURL('image/jpeg', 1);
            $(".img_wrap").width($(".img_wrap").height() * img.width / img.height);
            $(".img_wrap").attr("src",dataUrl);
            $(".img_wrap").show();
            // 上传略
            that.upload( dataUrl );
        };

        img.src = res;
    },
    upload:function( imageData ){
        /*上次方法屏蔽了*/
        //return;
        $.ajax({
            url:uploadUrl,
            type:'POST',
            data:{ imageData:imageData },
            dataType:'json',
            success:function( res ){
            	$("#pictureUrl").val(res);
            	// 置空该元素的错误警告
            	$("#picture_error").text("");
            }
        });
    }
};