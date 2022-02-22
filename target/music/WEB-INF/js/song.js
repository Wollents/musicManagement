<!--播放视频-->
function fun(path) {
    var path = '<audio width="100%" height="100%"' + 'controls="controls" autoplay="autoplay" src="../' + path + '"></audio>';
    //弹出一个页面层  来加载一个音频标签audio
    layui.use('layer', function () {
        var layer = layui.layer;
        layer.open({
            type: 1,
            title: '歌曲试听',
            content: path,
        });
    })
}

layui.use(['table', 'form', 'layer', 'jquery', 'slider'], function () {
    var table = layui.table
    form = layui.form,
        $ = layui.jquery,
        layer = layui.layer,
        slider = layui.slider
    //执行一个table 实例
    var tableIns = table.render({
        elem: '#songtable',//需要实例化表格的id
        height: 600,
        url: '../song/getAll', //向后台发送请求获取对应的数据
        title: '歌曲管理',
        page: true,
        cellMinWidth: 120,
        toolbar: '#headDemo',//default表示框架的默认按钮,大多数情况需要自定义按钮
        cols: [
            [
                {
                    type: 'checkbox', //复选框
                    fixed: 'left'  //固定在最左侧
                }, {
                field: 'id', //必须和返回的json格式数据的key值一摸一样
                title: 'ID', width: 60, sort: true, //排序
                fixed: 'left'
            },
                {field: 'stitle', title: '歌曲名称'},
                {field: 'songtime', title: '歌曲时长', width: 90},
                {field: 'singer', title: '歌手'},
                {field: 'albumname', title: '专辑'},
                {field: 'tid', title: '风格', width: 60},
                {field: 'userid', title: '上传者', width: 80},
                {
                    field: 'songpath', title: '歌曲路径', width: 80,
                    templet: function (d) {
                        var path = d.songpath;
                        return "<button class='layui-btn layui-btn-radius  layui-btn layui-btn-sm' onclick='fun(\"" + path + "\")'>播放</button>"
                    }
                },
                {field: 'size', title: '歌曲大小', width: 110},
                {
                    field: 'albumpic', title: '专辑封面',
                    // 自定义数据--》图片
                    templet: function (d) {
                        //可以写业务逻辑  也可以转换数据
                        var path = d.albumpic;
                        console.log(path)
                        return '<img src="../' + path + '" width="400px" height="600px"/>'
                    }
                },
                {
                    field: 'lyricpath', title: '歌词',
                    templet: function (d) {
                        var lyic = d.lyricpath;
                        return '<img src="../' + lyic + '" width="400px" height="400px"/>'
                    }
                },
                {field: 'playnum', title: '播放次数', width: 110, sort: true},
                {field: 'createtime', title: '创建时间'},
                {field: 'likecounts', title: '点赞数', width: 100, sort: true},
                {
                    field: 'status', title: '状态', width: 85,
                    templet: function (d) {
                        // 可以在这里写逻辑关系
                        // 根据0  1   显示不同的状态
                        var s = d.status
                        var vid = d.id
                        if (s == 1) {
                            return '<input type="checkbox"  value="' + vid + '" checked  name="status"  lay-skin="switch" lay-text="上线|下线" lay-filter="audiostatus">'
                        } else if (s == 0) {
                            return '<input type="checkbox"  value="' + vid + '" name="status"  lay-skin="switch" lay-text="上线|下线" lay-filter="audiostatus">'
                        }
                    }
                },
                {fixed: 'right', width: 165, align: 'center', toolbar: '#barDemo' // 绑定行工具栏
                }
            ]
        ]
        , id: 'testReload'
    });
    // 监听状态操作switch开关
    form.on('switch(audiostatus)', function (data) {
        console.log(data.elem); //得到checkbox原始DOM对象
        console.log(data.elem.checked); //开关是否开启，true或者false
        console.log(data.value); //开关value值
        //向后台发送请求  ajax  使用jquery的形式编写ajax
        var check = data.elem.checked == true ? 1 : 0;
        var vid = data.value;
        $.ajax({ //jquery的ajax形式
            type: 'post',//请求的方式
            url: '../song/updateStatus',//向后台发送请求的地址
            data: { //表示向后台传入的参数 key-value
                'status': check,
                'id': vid
            },
            dataType: 'json',//告诉后台返回的数据必须是json格式的数据
            success: function (res) { // 当请求成功以后的回调函数  res会直接接受后台返回的json格式的数据
                layer.msg(res.msg)
            }
        })
    });
    //实现头工具栏监听
    //监听头工具栏事件
    table.on('toolbar(songtest)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id) //获取到选中的复选框
            , data = checkStatus.data; //获取选中的数据 是一个数组类型的值
        //声明一个数组，存放选中的数据id
        var ids = new Array()
        for (var i in data) {
            ids[i] = data[i].id
        }
        switch (obj.event) {
            case 'add':
                layer.open({
                    type: 2,
                    title: '上传视频',
                    area: ['60%', '60%'],
                    content: 'addsong.html'
                })
                break;
            case 'delete':
                //获取到选中行的数据 --（id）
                //实现批量删除
                if (data.length === 0) {
                    layer.msg('请选择一行');
                } else {
                    //编写一个弹出层
                    layer.confirm("数据可贵！是否确认删除？", function (index) {
                        //index 表示当前layer弹出层的索引（可以用来关闭当前弹出层）
                        //向后台发送请求
                        $.ajax({
                            type: 'POST',
                            url: '../song/deleteById',
                            data: {
                                'ids': ids
                            },
                            dataType: 'json',
                            traditional: true,//向后台传送数组的时候，必须设置为true
                            success: function (res) {
                                //res将会接收后台返回的成功或者失败的消息-->Result
                                if (res.code == 200) {
                                    layer.alert(res.msg, {icon: 1}, function (index) {
                                        layer.close(index)
                                        //需要去刷新表格
                                        tableIns.reload();
                                    })
                                } else {
                                    layer.alert(res.msg, {icon: 2}, function (index) {
                                        layer.close(index)
                                    })
                                }
                            }
                        })
                    })
                }
                break;
        }
        ;
    });
    //监听行工具栏
    //监听行工具事件
    table.on('tool(songtest)', function (obj) {
        //注：tool 是工具条事件名，songtest 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //obj.data:获得当前行数据（一行数据）
            , layEvent = obj.event; //获得 lay-event 对应的值
        var id = data.id;
        var ids = new Array();
        ids[0] = id;
        if (layEvent === 'detail') {
            //需要向后台重新获取详情信息
            $.ajax({
                type: 'POST',
                url: '../song/details',
                data: { //ajax的data：表明数据
                    'id': data.id
                },
                dataType: 'json',
                success: function (res) {
                    // 获取到了最新的详情数据
                    //弹框
                    layer.open({
                        type: 2,
                        title: '预览详情信息',
                        area: ['70%', '70%'],
                        content: 'detailSong.html',
                        //页面跳转以后需要赋值
                        //跳转成功以后需要执行的回调函数
                        success: function (layero, index) {
                            //获取子页面body标签及内容
                            var body = layer.getChildFrame('body', index);
                            // var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                            // console.log(body.html()) //得到iframe页的body内容
                            body.find('#songpath').val(res.songpath)
                            body.find('#stitle').val(res.stitle)
                        }
                    })
                }
            })
        } else if (layEvent === 'del') {
            layer.confirm('真的删除行么', function (index) {
                //向服务端发送删除指令
                $.ajax({
                    type: 'POST',
                    url: '../song/deleteById',
                    data: {
                        'ids': ids
                    },
                    dataType: 'json',
                    traditional: true,//向后台传送数组的时候，必须设置为true
                    success: function (res) {
                        //res将会接收后台返回的成功或者失败的消息-->Result
                        if (res.code == 200) {
                            layer.msg(res.msg)
                            obj.del(); //删除对应行（tr）的DOM结构
                            layer.close(index);
                        } else {
                            layer.msg(res.msg);
                        }
                    }
                })
            });
        }else if(layEvent === 'edit'){
            $.ajax({
                type: 'post',
                url: '../song/editDetail',
                data: {
                    'id': data.id
                },
                dataType: 'json',
                success: function (res) {
                    layer.open({
                        type: 2,
                        title: 'song',
                        area: ['70%','70%'],
                        content: 'editSong.html',
                        success: function(layero, index){
                            var body = layer.getChildFrame('body', index);
                            console.log(body.html()) //得到iframe页的body内容
                            body.find('#id').val(res.id);
                            body.find('#userid').val(res.userid);
                            body.find('#playnum').val(res.playnum);
                            body.find('#likecounts').val(res.likecounts);
                        }
                    })
                }
            });
        }
    });

    //搜索
    $("#re").on("click", function () {
        tableIns.reload({
            url: "../song/selectByIdLike",
            where: {
                "id": $("#songLike").val()
            }
            , page: {
                curr: 1
            }
        })
    })

});