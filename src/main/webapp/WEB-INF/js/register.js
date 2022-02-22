layui.use(['form','layer','jquery'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer
        $ = layui.jquery;

form.on('submit(register)',function(data){
    $.ajax({
        type:'post',
         url:'../index/register',
         data:data.field,
        dataType:'json',
        traditional:true,
        success:function(res){
            if(res.code==200){
                window.location.href="../"
            }else{
                layer.alert(res.msg,{icon:2})
            }
        }
    })
    return false;
})

    //表单输入效果
    $(".registerBody .input-item").click(function(e){
        e.stopPropagation();
        $(this).addClass("layui-input-focus").find(".layui-input").focus();
    })
    $(".registerBody .layui-form-item .layui-input").focus(function(){
        $(this).parent().addClass("layui-input-focus");
    })
    $(".registerBody .layui-form-item .layui-input").blur(function(){
        $(this).parent().removeClass("layui-input-focus");
        if($(this).val() != ''){
            $(this).parent().addClass("layui-input-active");
        }else{
            $(this).parent().removeClass("layui-input-active");
        }
    })
})

//更新验证码
function changeCode(){
var img=document.getElementById("codeImg");
img.src="../index/getCode?time="+new Date().getTime();
}