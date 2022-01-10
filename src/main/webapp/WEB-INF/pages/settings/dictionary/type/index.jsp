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

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script>
	$(function () {
		queryDicTypes()
        editDicType()
		deleteDicType()
	})
	function queryDicTypes() {
		$.ajax({
			url:"settings/dictionary/type/index/queryAllDicType.do",
			type:"get",
			success:function(data){
				//window.alert(data.length)
				var htmlStr=""
				$.each(data,function (index,item) {
					htmlStr+="<tr class=\"active\">"
					htmlStr+="<td><input type=\"checkbox\" value="+item.code+"></td>"
					htmlStr+="<td>"+(index+1)+"</td>"
					htmlStr+="<td>"+item.code+"</td>"
					htmlStr+="<td>"+item.name+"</td>"
					htmlStr+="<td>"+item.name+"</td>"
					htmlStr+="</tr>"

				})
				$("#tBody").html(htmlStr)
			}
		})

	}
    function editDicType() {
        //给编辑按钮添加单击事件
        $("#editDicTypeBtn").on("click",function () {
            //获取页面中已经选中的编码值
            var checked=$("#tBody input[type='checkbox']:checked")

            //判读用户选中记录的个数
			if(checked.size()==0){
				alert("请选择一条数据")
				return
			}
            if(checked.size()!=1){
                alert("只能编辑一条数据")
                return;
            }

            //获取所选中记录的编码
            var code = checked.val();
            //向后台发起同步请求
            window.location.href='settings/dictionary/type/index/edit.do?code='+code
        })
    }
    function deleteDicType() {
		$("#deleteDicTypeBtn").on("click",function () {
			var checkeds=$("#tBody input[type='checkbox']:checked")
			if(checkeds.size()==0){
				alert("请选择一条数据")
			}else {
				//window.alert(checkeds.size())
				var htmlStr = ""
				$.each(checkeds, function (index,checked) {
					htmlStr += "code=" + $(checked).val() + "&"
				})
				htmlStr = htmlStr.substring(0, htmlStr.length - 1)
						$.ajax({
							url: "settings/dictionary/type/index/delete.do",
							type: "post",
							data: htmlStr,
							success: function (data) {
								if (data.code == 1) {
									window.alert("您成功删除了"+data.data+"条数据")
									queryDicTypes()
								} else {
									window.alert(data.message)
								}

							}
						})
					}

			})

	}
</script>
</head>
<body>

	<div>
		<div style="position: relative; left: 30px; top: -10px;">
			<div class="page-header">
				<h3>字典类型列表</h3>
			</div>
		</div>
	</div>
	<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px;">
		<div class="btn-group" style="position: relative; top: 18%;">
			<button id="createDicTypeBtn" type="button" class="btn btn-primary" onclick="window.location.href='settings/dictionary/type/index/save.do'"><span class="glyphicon glyphicon-plus" ></span> 创建</button>
		  <button id="editDicTypeBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
		  <button id="deleteDicTypeBtn" type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>
	<div style="position: relative; left: 30px; top: 20px;">
		<table class="table table-hover">
			<thead>
				<tr style="color: #B3B3B3;">
					<td><input type="checkbox" id="chkedAll"/></td>
					<td>序号</td>
					<td>编码</td>
					<td>名称</td>
					<td>描述</td>
				</tr>
			</thead>
			<tbody id="tBody">
				<%--<tr class="active">
					<td><input type="checkbox" /></td>
					<td>1</td>
					<td>sex</td>
					<td>性别</td>
					<td>性别</td>
				</tr>
				<tr class="active">
					<td><input type="checkbox" /></td>
					<td>2</td>
					<td>appellation</td>
					<td>称呼</td>
					<td>称呼</td>
				</tr>--%>
			</tbody>
		</table>
	</div>

</body>
</html>
