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
	<script type="text/javascript" src="jquery/common/common.js"></script>
	<script>
		$(function () {
			queryDicValue()
			editDicValue()
			deleteDicValue()
		})
		function queryDicValue() {
			$.ajax({
				url:"settings/dictionary/value/index/queryAllDicValue.do",
				type:"get",
				success:function (data) {
					var htmlStr=""
					$.each(data,function (index,item) {
						htmlStr+="<tr class=\"active\">"
						htmlStr+="<td><input type=\"checkbox\"  name=\""+item.typeCode+"\" value="+item.id+"></td>"
						htmlStr+="<td>"+(index+1)+"</td>"
						htmlStr+="<td>"+item.value+"</td>"
						htmlStr+="<td>"+item.text+"</td>"
						htmlStr+="<td>"+item.orderNo+"</td>"
						htmlStr+="<td>"+item.typeCode+"</td>"
						htmlStr+="<tr>"
					})
					$("#tBody").html(htmlStr)
				}
			})
		}
		function editDicValue(){
			$("#editDicValueBtn").on("click",function(){
				var checked=$("#tBody input[type='checkbox']:checked")
				if(checked.size()==0){
					window.alert("请选择一条数据")
					return;
				}
				if(checked.size()!=1){
					alert("只能选择一条数据")
					return;
				}
				var id=checked.val()
				var typeCode=checked.attr("name")

				window.location.href="settings/dictionary/value/index/editDicValue.do?id="+id+"&typeCode="+typeCode
			})
		}
		function deleteDicValue() {
			$("#deleteDicValueBtn").on("click",function () {
				//获取所有被选中的数据
				var checkeds=$("#tBody input[type='checkbox']:checked")
				//判断是否被选中
				if(checkeds.size()==0){
					alert("请选择数据")
				}
				//将选中的记录的id遍历拼接成字符串
				var ids=""
				var typeCodes=""
				$.each(checkeds,function (index,item) {
					typeCodes+="typeCode="+$(item).attr("name")+"&"
					ids+="id="+$(item).val()+"&"
				})
				ids=ids+typeCodes
				ids=ids.substring(0,ids.length-1)
				alert(ids)
				//异步请求
				$.ajax({
					url:"settings/dictionary/value/index/deleteDicValue.do",
					type:"post",
					data:ids,
					success:function (data) {
						if(data.code==1){
							alert("您成功删除了"+data.data +"数据")
							queryDicValue()
						}else {
							alert(data.message)
						}
					}
				})
			})
		}
	</script>
</head>
<body>

	<div>
		<div style="position: relative; left: 30px; top: -10px;">
			<div class="page-header">
				<h3>字典值列表</h3>
			</div>
		</div>
	</div>
	<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px;">
		<div class="btn-group" style="position: relative; top: 18%;">
		  <button id="createDicValueBtn" type="button" class="btn btn-primary" onclick="window.location.href='settings/dictionary/value/index/save.do'"><span class="glyphicon glyphicon-plus" ></span> 创建</button>
		  <button id="editDicValueBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
		  <button id="deleteDicValueBtn" type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>
	<div style="position: relative; left: 30px; top: 20px;">
		<table class="table table-hover">
			<thead>
				<tr style="color: #B3B3B3;">
					<td><input type="checkbox" id="chkedAll"/></td>
					<td>序号</td>
					<td>字典值</td>
					<td>文本</td>
					<td>排序号</td>
					<td>字典类型编码</td>
				</tr>
			</thead>
			<tbody id="tBody">

			</tbody>
		</table>
	</div>

</body>
</html>
