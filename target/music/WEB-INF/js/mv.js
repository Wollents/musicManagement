<!--mv播放-->
function fun(path){
    console.log(path);
    var mv = '<video width="100%" height="100%" autoplay="autoplay" src="' + path + '"></video>';
    layui.use('layer', function () {
        var layer = layui.layer;
        layer.open({
            type: 1,
            title: '观看视频',
            area:['80%','80%'],
            content: mv
        })
    })
}

layui.use(['table','form', 'jquery','layer'], function () {
    var table = layui.table,
        form = layui.form,
        $ = layui.jquery,
        layer = layui.layer;
    //执行一个 table 实例
    var tableIns = table.render({
        elem: '#mvTable', //要实例化表格的id
        height: 580,
        url: '../mv/getAll' //数据接口 后台发送请求获取数据
        ,
        title: 'mv信息',
        page: true //开启分页
        ,
        toolbar: '#headDemo' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        ,
        cols: [
            [ //表头
                {
                    type: 'checkbox',
                    fixed: 'left'
                }, {
                field: 'id', //和返回的json格式的key值一样
                title: 'ID',
                align: 'center',
                sort: true
            }, {
                field: 'userid',
                title: '上传者ID',
                align: 'center'
            }, {
                field: 'mvdesc',
                title: '视频描述',
                align: 'center'
            }, {
                field: 'singer',
                title: '演唱者',
                align: 'center'
            }, {
                field: 'mvpath',
                title: '视频内容',
                align: 'center',
                templet: function (d) {
                    var path = d.mvpath;
                    return '<button class="layui-btn layui-btn-xs layui-btn-radius layui-btn-normal" onclick="fun(\'../' + path + '\')">播放</button>';
                }
            }, {
                field: 'mvtime',
                title: '时长',
                sort: true,
                align: 'center'
            }, {
                field: 'playnum',
                title: '播放次数',
                sort: true,
                align: 'center'
            }, {
                field: 'likecounts',
                title: '点赞数',
                sort: true,
                align: 'center'
            }, {
                field: 'createtime',
                title: '上传时间',
                sort: true,
                align: 'center'
            }, {
                field: 'status',
                title: '审核状态',
                align: 'center',
                templet: function (d) {
                    //可以在这里写逻辑关系
                    //根据0 1 显示不同的状态
                    var sta = d.status;
                    var mid = d.id;
                    if(sta == 1){
                        return '<input type="checkbox" value="' + mid + '" checked name="status" lay-skin="switch" lay-text="成功|失败" lay-filter="mvstatus">'
                    }else if(sta == 0){
                        return '<input type="checkbox" value="' + mid + '" name="status" lay-skin="switch" lay-text="成功|失败" lay-filter="mvstatus">'
                    }
                }
            }, {
                fixed: 'right',
                width: 165,
                align: 'center',
                toolbar: '#barDemo'
            }
            ]
        ]
    });

    form.on('switch(mvstatus)', function (data) {
        //更改mv审核状态
        var isChecked = data.elem.checked == true?1:0;
        var mid = data.value;
        $.ajax({
            type: 'post',
            url: '../mv/updateStatus',
            data: {
                'status': isChecked,
                'id': mid
            },
            dataType: 'json',
            success: function (res) {
                layer.msg(res.msg);
            }
        })
    });

    //监听头工具栏事件
    table.on('toolbar(mvTest)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id) //获取选择的复选框
            ,data = checkStatus.data; //获取选中的数据
        switch(obj.event){
            case 'addMv':
                layer.open({
                    type: 2,
                    title: 'mv',
                    area: ['70%','70%'],
                    content: 'addMv.html'
                });
                break;
            // case 'updateMv':
            //     if(data.length === 0){
            //         layer.msg('请选择一行');
            //     } else if(data.length > 1){
            //         layer.msg('只能同时编辑一个');
            //     } else {
            //         layer.alert('编辑 [id]：'+ checkStatus.data[0].id);
            //     }
            //     break;
            case 'deleteMv':
                //批量删除
                if(data.length === 0){
                    layer.msg('请选择一行');
                } else {
                    var id=new Array();
                    for(var i in data){
                        id[i] = data[i].id;
                    }
                    layer.confirm('是否确认删除选中的mv？', {icon: 3, title: '提示'}, function (index) {
                        $.ajax({
                            type: 'post',
                            url: '../mv/deleteById',
                            data: {
                                'id': id
                            },
                            dataType: 'json',
                            traditional: true,
                            success: function (res) {
                                if(res.code == 200){
                                    layer.alert(res.msg, {icon: 1}, function (index) {
                                        tableIns.reload();
                                        layer.close(index);
                                    })
                                }else{
                                    layer.alert(res.msg,{icon: 2}, function (index) {
                                        layer.close(index);
                                    })
                                }
                            }
                        })
                    })
                }
                break;
        }
    });

    //监听行工具事件
    table.on('tool(mvTest)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            ,layEvent = obj.event; //获得 lay-event 对应的值
        if(layEvent === 'detailMv'){
            $.ajax({
                type: 'post',
                url: '../mv/details',
                data: {
                    'id': data.id
                },
                dataType: 'json',
                success: function (res) {
                    layer.open({
                        type: 2,
                        title: 'mv',
                        area: ['70%', '70%'],
                        content: 'detailMv.html',
                        success: function(layero, index){
                            var body = layer.getChildFrame('body', index);
                            console.log(body.html()) //得到iframe页的body内容
                            body.find('#userid').val(res.userid);
                            body.find('#size').val(res.size);
                            body.find('#mvheight').val(res.mvheight);
                            body.find('#mvwidth').val(res.mvwidth);
                            body.find('#mvwidth').val(res.mvwidth);
                            body.find('#mvpath').val(res.mvpath);
                            body.find('#mvdesc').val(res.mvdesc);
                        }
                    })
                }
            })
        } else if(layEvent === 'delMv'){
            var id = new Array();
            id[0] = data.id;
            layer.confirm('是否确认删除这支mv？', {icon: 3, title: '提示'}, function(index){
                //向服务端发送删除指令
                $.ajax({
                    type: 'post',
                    url: '../mv/deleteById',
                    data: {
                        'id': id
,                   },
                    dataType: 'json',
                    traditional: true,
                    success: function (res) {
                        if(res.code == 200){
                            layer.msg(res.msg);
                            obj.del(); //删除对应行（tr）的DOM结构
                            layer.close(index);
                        }else{
                            layer.msg(res.msg);
                        }
                    }
                })
            })
        } else if(layEvent === 'editMv'){
            $.ajax({
                type: 'post',
                url: '../mv/editDetail',
                data: {
                    'id': data.id
                },
                dataType: 'json',
                success: function (res) {
                    layer.open({
                        type: 2,
                        title: 'mv',
                        area: ['70%','70%'],
                        content: 'editMv.html',
                        success: function(layero, index){
                            var body = layer.getChildFrame('body', index);
                            console.log(body.html()) //得到iframe页的body内容
                            body.find('#id').val(res.id);
                            body.find('#userid').val(res.userid);
                            body.find('#mvdesc').val(res.mvdesc);
                            body.find('#playnum').val(res.playnum);
                            body.find('#likecounts').val(res.likecounts);
                        }
                    })
                }
            });
        }
    });

});