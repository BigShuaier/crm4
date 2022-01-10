<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script>
		$(function (){
			//给输入框添加失去焦点事件
			$("#create-code").on("blur",function () {
				//获取文本框的值
				var code1=$.trim($("#create-code").val())
				//验证编码是否为空
				if(code1==""){
					$("#codeMsg").html("编码不能为空")
					return;
				}
				//非空：验证此编码是否存在，请求方式：异步请求
				$.ajax({
					url:"settings/dictionary/type/index/save/iscode.do",
					type:"post",
					data:{code1:code1},
					success:function (data) {
						if(data.code==1){
							$("#codeMsg").html("")
                    }else {
                            $("#codeMsg").html(data.message)
                        }
					}
				})
			})
            $("#saveCreateDicTypeBtn").on("click",function () {
                //要求：数据字典编码类型验证通过
                //让保存按钮触发文本框失去焦点事件
                $("#create-code").blur()
                //获取文本框中的值
                var code1=$.trim($("#create-code").val())
                var name=$.trim($("#create-name").val())
                var descreption=$.trim($("#create-description").val())
                var codeMsg=$("#codeMsg").html()
                if(""==codeMsg){
                    $.ajax({
                        url:"settings/dictionary/type/index/save/saveDicType.do",
                        type:"post",
                        data:{code:code1,
                                name:name,
                                description:descreption
                    },
                    success:function (data) {
                        if(data.code==1){
                            window.alert("提交成功")
                            window.location.href="settings/dictionary/type/index.do"
                        }else {
                            window.alert(data.message)
                        }
                    }
                })}

            })
                //异步请求提交
            })

	</script>
</head>
<body>

	<div style="position:  relative; left: 30px;">
		<h3>新增字典类型</h3>
	  	<div style="position: relative; top: -40px; left: 70%;">
			<button id="saveCreateDicTypeBtn" type="button" class="btn btn-primary">保存</button>
			<button type="button" class="btn btn-default" onclick="window.history.back();">取消</button>
		</div>
		<hr style="position: relative; top: -40px;">
	</div>
	<form class="form-horizontal" role="form">

		<div class="form-group">
			<label for="create-code" class="col-sm-2 control-label" >编码<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-code" style="width: 200%;">
				<span id="codeMsg" style="color: red"></span>
			</div>
		</div>

		<div class="form-group">
			<label for="create-name" class="col-sm-2 control-label">名称</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-name" style="width: 200%;">
			</div>
		</div>

		<div class="form-group">
			<label for="create-description" class="col-sm-2 control-label">描述</label>
			<div class="col-sm-10" style="width: 300px;">
				<textarea class="form-control" rows="3" id="create-description" style="width: 200%;"></textarea>
			</div>
		</div>
	</form>

	<div style="height: 200px;"></div>
</body>
</html>
