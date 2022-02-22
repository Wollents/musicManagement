layui.use(['table', 'form', 'jquery', 'layer'], function () {
    var table = layui.table; //实例化对象
    var form = layui.form;
    var $ = layui.jquery,
        layer = layui.layer
    //执行一个 table 实例
    var tableIns = table.render({
        elem: '#usertable', //需要实例化表格的id
        height: 600,
        url: '../user/getAll' //数据接口,向后台发送请求，获取对应数据
        ,
        title: '用户表',
        page: true //开启分页
        ,
        //id: 'testReload',
        //default表示框架的默认按钮 大多数情况需自定义按钮
        toolbar: '#headDemo' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        ,
        //				totalRow: true //开启合计行
        //					,
        cols: [//两个中括号一起写，界面没错，到服务器中，导致后面的js代码无法加载
            [ //表头
                {
                    type: 'checkbox',
                    fixed: 'left'
                }, {
                field: 'id',
                title: 'ID',
                //width: 80,
                sort: true,
                fixed: 'left',
            }, {
                field: 'name',
                title: '用户名',
                //width: 80
            }, {
                field: 'phone',
                title: '电话号码',
            }, {
                field: 'password',
                title: '密码',
            }, {
                field: 'state',
                title: '状态',
                templet: function (d) {
                    //可以写在里面写逻辑关系
                    //根据数据库的值 0,1显示不同的状态
                    var s = d.state
                    var vvid = d.id
                    if (s == 1) {
                        return '<input type="checkbox" checked value="' + vvid + '" name="state"  lay-skin="switch" lay-text="上线|下线" lay-filter="userstate">'
                    } else
                        return '<input type="checkbox" value="' + vvid + '" name="state"  lay-skin="switch" lay-text="上线|下线" lay-filter="userstate">'
                }
            },
                {
                    field: 'sex',
                    title: '性别',
                    //width: 80,
                    sort: true
                }, {
                field: 'pic',
                title: '头像',
                templet: function (d) {//函数返回了一个参数d，
                    //可以写业务逻辑
                    var path = d.pic;
                    console.log(path)
                    return '<img src="../' + path + '" height="100px" width="100px"/>'
                }
            }, {
                field: 'isadmin',
                title: '权限',
                templet: function (d) {
                    //可以写在里面写逻辑关系
                    //根据数据库的值 0,1显示不同的状态
                    var i = d.isadmin
                    var vid = d.id
                    if (i == 1) {
                        return '<input type="checkbox" checked value="' + vid + '" name="isAdmin"  lay-skin="switch" lay-text="管理员|普通" lay-filter="userisAdmin">'
                    } else
                        return '<input type="checkbox" value="' + vid + '" name="isAdmin"  lay-skin="switch" lay-text="管理员|普通" lay-filter="userisAdmin">'
                }
            }, {
                field: 'email',
                title: '邮箱',
            }, {
                fixed: 'right',
                width: 165,
                align: 'center',
                toolbar: '#barDemo'
            }
            ]
        ]
    });

    //改变状态
    form.on('switch(userstate)', function (data) {
        var check = data.elem.checked == true ? 1 : 0;
        var vvid = data.value;
        $.ajax({//jquery的ajax形式
            type: 'post',//请求方式
            url: '../user/updatestate',//向后台发送请求的地址
            data: {//请求传来的参数 向后台传入的参数 key-value integer status
                'state': check,
                'id': vvid
            },
            dataType: 'json',//告诉后台必须返回的数据格式必须是json格式的数据
            success: function (res) {//当请求成功以后的回调函数 res会直接接受后台返回的json格式数据
                layer.msg(res.msg)
            }
        })
    });
    //改变管理员权限
    form.on('switch(userisAdmin)', function (data) {
        var check = data.elem.checked == true?1:0;
        var vid = data.value;
        $.ajax({//jquery的ajax形式
            type: 'post',//请求方式
            url: '../user/updateisAdmin',//向后台发送请求的地址
            data: {//请求传来的参数 向后台传入的参数 key-value integer status
                'isadmin': check,
                'id': vid
            },
            dataType: 'json',//告诉后台必须返回的数据格式必须是json格式的数据
            success: function (res) {//当请求成功以后的回调函数 res会直接接受后台返回的json格式数据
                layer.msg(res.msg)
            }
        })
    });

    //监听头工具栏事件
    table.on('toolbar(usertest)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id)//获取到选中的复选框
            , datas = checkStatus.data; //获取选中的数据 是一个数组类型的值
        switch (obj.event) {
            case 'add':
                layer.msg('添加');//需要进行视频上传操作，向后台提供视频信息
                layer.open({
                    type: 2,
                    title: '添加用户',
                    area:['60%','60%'],
                    content: 'addUser.html'
                })
                break;
            case 'delete'://实现批量数据删除
                if (datas.length === 0) {
                    //需选中某一行
                    layer.msg('请选择一行');
                } else {
                    var ids = new Array()//声明一个数组来存放选中的数据id
                    for (var i in datas) {
                        ids[i] = datas[i].id
                    }
                    //layer.msg('删除');
                    //编写一个弹出层
                    layer.confirm("数据可贵！是否确认删除", function (index) {
                        //index表示当前layer弹出层的索引，可以用来关闭当前弹出层
                        //向后台发送请求
                        $.ajax({
                            type: 'post',
                            url: '../user/deleteById',
                            data: {
                                'ids': ids
                            },
                            dataType: 'json',
                            traditional: true,//向后台传送数组的时候，必须设置为true
                            success: function (res) {//向后台成功请求成功
                                //res将后接受返回后台返回的成功或者失败的消息   Result
                                if (res.code == 200) {
                                    layer.alert(res.msg, {icon: 1}, function (index) {
                                        //成功后刷新表格
                                        layer.close(index);
                                        tableIns.reload();
                                    })
                                } else {
                                    layer.alert(res.msg, {icon: 2}, function (index) {
                                        layer.close(index)
                                    })//失败直接关闭
                                }
                            }
                        })
                    })
                }
                break;
        }
        ;
    });


    //监听行工具事件
    table.on('tool(usertest)', function (obj) { //注：tool 是工具条事件名，videotest 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            , layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'del') {
            var id = data.id
            var ids = new Array();
            ids[0] = id;
            layer.confirm('真的删除行么?', function (index) {

                //向服务端发送删除指令
                $.ajax({
                    type: 'post',
                    url: '../user/deleteById',
                    data: {
                        'ids': ids
                    },
                    dataType: 'json',
                    traditional: true,//向后台传送数组的时候，必须设置为true
                    success: function (res) {//向后台成功请求成功
                        //res将后接受返回后台返回的成功或者失败的消息   Result
                        if (res.code == 200) {
                            layer.msg(res.msg)
                            obj.del(); //删除对应行（tr）的DOM结构
                            layer.close(index);
                        } else {
                            layer.close(index);
                        }
                    }
                })
            });
        }
    });
});