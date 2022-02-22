layui.use(['table', 'form', 'jquery', 'layer'], function () {
    var table = layui.table; //实例化对象
    var form = layui.form;
    var $ = layui.jquery,
        layer = layui.layer
    //执行一个 table 实例
    table.render({
        elem: '#persontable', //需要实例化表格的id
        height: 600,
        url: '../user/getAll',//数据接口,向后台发送请求，获取对应数据
        title: '用户表',
        page: true //开启分页
        ,
        //default表示框架的默认按钮 大多数情况需自定义按钮
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
                        return '<input type="checkbox" checked value="' + vvid + '" name="state"  lay-skin="switch" lay-text="上线|下线" lay-filter="userstate" disabled>'
                    } else if (s == 0) {
                        return '<input type="checkbox" value="' + vvid + '" name="state"  lay-skin="switch" lay-text="上线|下线" lay-filter="userstate" disabled>'
                    }
                }
            },
                {
                    field: 'sex',
                    title: '性别',
                    //width: 80,
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
                        return '<input type="checkbox" checked value="' + vid + '" name="isAdmin"  lay-skin="switch" lay-text="管理员|普通" lay-filter="userisAdmin" disabled>'
                    } else
                        return '<input type="checkbox" value="' + vid + '" name="isAdmin"  lay-skin="switch" lay-text="管理员|普通" lay-filter="userisAdmin" disabled>'

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
    //


    //
    table.on('tool(persontest)', function (obj) {   //注：tool 是工具条事件名，videotest 是 table 原始容器的属性 lay-filter="对应的值"
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
        else if(layEvent === 'edit'){
            $.ajax({
                type:'post',
                url:'../user/editUser',
                data:{
                    'id': data.id
                },
                dataType: 'json',
                success: function (res) {
                    layer.open({
                        type: 2,
                        title: '修改用户信息',
                        area: ['60%', '60%'],
                        content: 'editPerson.html',
                        success: function(layero, index) {
                            var body = layer.getChildFrame('body', index);
                            console.log(body.html()) //得到iframe页的body内容
                            body.find('#id').val(res.id);
                            //id, name,   phone,password,  sex,   email,  pic
                            body.find('#name').val(res.name);
                            body.find('#phone').val(res.phone);
                            body.find('#password').val(res.password);
                            // body.find('#sex').val(res.sex);
                            body.find('#email').val(res.email);
                            body.find('#pic').val(res.pic);
                        }
                    })
                }
            })
        }
    });
})