

//要用模板，一定要用这个来“实例化”layui。
layui.use(['table','form','jquery','layer'], function () {
    var table = layui.table;
    var layer = layui.layer;
    var form = layui.form;
    var $ = layui.jquery;
    //这里的table就是上面的use函数中的参数
    //如果要用多个模块，则用一个[]包含起来。
    var tableIns = table.render({
        elem: '#videotable'//表格的id，是上面body的id，这里可理解为执行代码。
        , height: 420
        , url: '../type/getAll' //数据接口，向后台发送请求获取对应的数据
        ,
        title: '音乐分类信息',
        page: true //开启分页
        , toolbar: '#headDemo' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        , cols: [
            [ //表头,注意两个中括号不可以写在一起，会导致代码不能正常加载。
                {type: 'checkbox', fixed: 'left'}
                , {field: 'tid', title: 'TID', width: 80, sort: true, fixed: 'left', totalRowText: '合计：'}
                , {field: 'tname', title: '类型'}
                , {
                field: 'tdesc', title: '类型内容描述', width:170
                // templet: function (d) {
                //     var path = d.videoPath;
                //     return '<button class="layui-btn layui-btn-radius layui-btn-xs" onclick="fun(\' ../' + path + '\')">播放</button>'
                // }
            }
                , {fixed: 'right', width: 165, align: 'center', toolbar: '#barDemo'}
            ]
        ]
    });
    //监听选项卡
    form.on('switch(videostatus)', function(data){
        var el = data.elem;
        var ch = data.elem.checked;
        var val = data.value;
        console.log(el);
        console.log(ch);
        console.log(val);
        //向后台发送请求 ajax ，使用jquery的形式变现ajax
        var check = (data.elem.checked == true)?1:0;
        var vid = data.value;
        $.ajax({//jquery的ajax，这里经常用！！！！
            type: 'post',//请求的方式，post/get
            url: '../videos/updateStatus',//向后台发送请求的地址
            data:{ //表示向后台传入的参数 key-value的形式
                'status': check,
                'id': vid
            },
            dataType:'json',//告诉后台必须返回的数据必须是jaso格式的数据
            success:function(res){//当请求成功以后的回调函数返回
                //res会直接接受后台返回的json格式
                layer.msg(res.msg);
            }


        })
    });
    //监听头部工具栏
    table.on('toolbar(test)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id)
            ,datas = checkStatus.data; //获取选中的数据，是一个数组类型的值！
        var tids = new Array();
        for(var i in datas)
        {
            tids[i] = datas[i].tid;
        }
        switch(obj.event){
            case 'add':
                layer.open({
                    type: 2,
                    title: "新增类型",
                    area: ['60%', '60%'],
                    content: 'upload.html'
                })
                //我需要进行类型上传操作
                break;

            case 'delete':
                //我需要获取到我选中行的数据（---id）
                if(datas.length === 0){
                    layer.msg('请选择一行');
                } else {
                    layer.confirm("你确认删除吗？",function(index){
                        //index表示当前layer弹出层的索引（可以用来关闭当前的弹出层，既可以通过索引来操作）
                    //向后台发送请求-> ajax
                        $.ajax({
                            type:'post',
                            url:'../type/deleteById',
                            data:{
                                'tid':tids
                            },
                            dataType:'json',
                            traditional:true,
                            success:function(res){
                                //res将会接受后台返回的result对象
                                //假设状态码200是成功的标志
                                if(res.code == 200){
                                    //icon属性可以更改框框中的图标，1-6为不同的样式皮肤
                                    //function为回调函数，结束后可以执行function函数
                                    layer.alert(res.msg, {icon:1},function(index){
                                        layer.close(index);
                                        tableIns.reload();
                                        //table.reload('Videotable');
                                    });
                                    //需要去刷新表格！
                                } else{
                                    layer.alert(res.msg,{icon:2},function(index){
                                        layer.close(index);
                                    });
                                    //直接将提示关闭掉
                                }
                            }
                        })
                    })
                }
                break;
        };
    });

    table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）

        if(layEvent === 'detail'){ //查看
            $.ajax({
                type:'post',
                url:'../type/details',
                data:{
                    'tid':data.tid
                },
                dataType:'json',
                success:function(res){

                    layer.open({
                        type:2,
                        title:'预览信息',
                        area:['70%','70%'],
                        content:'detail.html',
                        success: function(layero, index){
                            var body = layer.getChildFrame('body', index);
                            //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                            console.log(body.html()) //得到iframe页的body内容
                            body.find('#tid').val(res.tname);
                            body.find('#info').val(res.tdesc);
                        }
                    })
                }
            })

            //do somehing
        } else if(layEvent === 'del'){ //删除
            var tids = new Array();
            tids[0] = data.tid;
            console.log(tids[0]);
            layer.confirm('真的删除行么', function(index){
                obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                layer.close(index);
                //向服务端发送删除指令
                $.ajax({
                    type:'post',
                    url:'../type/deleteById',
                    data:{
                        'tid':tids
                    },
                    dataType:'json',
                    traditional:true,
                    success:function(res){
                        //res将会接受后台返回的result对象
                        //假设状态码200是成功的标志
                        if(res.code == 200){
                           layer.msg(res.msg);
                            obj.del();
                            layer.close(index);
                        } else{
                            layer.msg(res.msg);
                            //直接将提示关闭掉
                        }
                    }
                })
            });
        }  else if(layEvent === 'edit'){
            $.ajax({
                type:'post',
                url:'../type/details',
                data:{
                    'tid':data.tid
                },
                dataType:'json',
                success:function(res){

                    layer.open({
                        type:2,
                        title:'编辑信息',
                        area:['70%','70%'],
                        content:'edit.html',
                        success: function(layero, index){
                            var body = layer.getChildFrame('body', index);
                            //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                            console.log(body.html()) //得到iframe页的body内容
                            body.find('#tid').val(res.tid);
                            body.find('#tname').val(res.tname);
                            body.find('#tdesc').val(res.tdesc);
                        }
                    })
                }
            })
        }
    });
})