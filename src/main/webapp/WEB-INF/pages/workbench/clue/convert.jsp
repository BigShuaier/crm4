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


    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

<script type="text/javascript">
	$(function(){
        //当容器加载完成，对容器调用工具函数
        $(".mydate").datetimepicker({
            language:'zh-CN',//语言
            format:'yyyy-mm-dd',//日期格式
            minView:'month',//日期选择器上最小能选择的日期的视图
            initialDate:new Date(),// 日历的初始化显示日期，此处默认初始化当前系统时间
            autoclose:true,//选择日期之后，是否自动关闭日历
            todayBtn:true,//是否显示当前日期的按钮
            clearBtn:true,//是否显示清空按钮
        });
		$("#isCreateTransaction").click(function(){
			if(this.checked){
				$("#create-transaction2").show(200);
			}else{
				$("#create-transaction2").hide(200);
			}
		});
        //给市场活动源查询按钮添加单击事件
        $("#searchActivityBtn").on("click",function (){

			$("#searchActivityModal").modal("show")

        })
		$("#searchActivityText").on("keyup",function (){
			var clueId="${clue.id}"
			$.ajax({
				url:"workbench/clue/searchActivity.do",
				type:"post",
				data:{
					clueId:clueId
				},
				success:function (data) {
					var htmlStr=""
					$.each(data,function (index,item) {
						htmlStr+="<tr>"
						htmlStr+="<td><input type=\"radio\" onclick=\"checkConvertActivity('"+item.id+"','"+item.name+"')\" name=\"activity\"/></td>"
						htmlStr+="<td>"+item.name+"</td>"
						htmlStr+="<td>"+item.startDate+"</td>"
						htmlStr+="<td>"+item.endDate+"</td>"
						htmlStr+="<td>"+item.owner+"</td>"
						htmlStr+="</tr>"
					})
					$("#tBody").html(htmlStr)
				}
			})
		})
		//给转换按钮绑定一个单击事件


		$("#convertBtn").on("click",function () {
			//获取需要转换的参数
			var clueId=$("#clueId").val()
			var isCreateTransaction=$("#isCreateTransaction").prop("checked")
			var amountOfMoney=$.trim($("#amountOfMoney").val())
			var tradeName=$.trim($("#tradeName").val())
			var expectedClosingDate=$("#expectedClosingDate").val()
			var stage=$("#stage").val()
			var activityId=$("#activityId").val()

			//发起转换请求
			$.ajax({
				url:"workbench/clue/convert.do",
				type:"post",
				data:{
					clueId:clueId,
					isCreateTransaction:isCreateTransaction,
					amountOfMoney:amountOfMoney,
					tradeName:tradeName,
					expectedClosingDate:expectedClosingDate,
					stage:stage,
					activityId:activityId
				},
				success:function (data) {
					if(data.code==1){
						alert("转换成功")
						window.location.href="workbench/clue/index.do"
					}else{
						data.message
					}
				}
			})
		})
	});
	function checkConvertActivity(id,name){
		$("#activityId").val(id)
		$("#activityName").val(name)
		$("#searchActivityModal").modal("hide")
	}
</script>

</head>
<body>

	<!-- 搜索市场活动的模态窗口 -->
	<div class="modal fade" id="searchActivityModal" role="dialog" >
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">搜索市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input id="searchActivityText" type="text" class="form-control" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>开始日期</td>
								<td>结束日期</td>
								<td>所有者</td>
								<td></td>
							</tr>
						</thead>
						<tbody id="tBody">

						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<div id="title" class="page-header" style="position: relative; left: 20px;">
		<h4>转换线索 <small>${clue.fullName}${clue.appellation}-${clue.company}</small></h4>
	</div>
	<div id="create-customer" style="position: relative; left: 40px; height: 35px;">
		新建客户：${clue.company}
	</div>
	<div id="create-contact" style="position: relative; left: 40px; height: 35px;">
		新建联系人：${clue.fullName}${clue.appellation}
	</div>
	<div id="create-transaction1" style="position: relative; left: 40px; height: 35px; top: 25px;">
		<input type="checkbox" id="isCreateTransaction"/>
		为客户创建交易
	</div>
	<div id="create-transaction2" style="position: relative; left: 40px; top: 20px; width: 80%; background-color: #F7F7F7; display: none;" >

		<form>
		  <div class="form-group" style="width: 400px; position: relative; left: 20px;">
		    <label for="amountOfMoney">金额</label>
		    <input type="text" class="form-control" id="amountOfMoney">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="tradeName">交易名称</label>
		    <input type="text" class="form-control" id="tradeName" value="${clue.company}-">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="expectedClosingDate">预计成交日期</label>
		    <input type="text" class="form-control mydate" id="expectedClosingDate" readonly>
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="stage">阶段</label>
		    <select id="stage"  class="form-control">
		    	<option>----请选择----</option>
		    	<c:forEach items="${stageList}" var="s">
					<option value="${s.id}">${s.value}</option>
				</c:forEach>
		    </select>
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="activityName">市场活动源&nbsp;&nbsp;<a id="searchActivityBtn"  style="text-decoration: none;"><span class="glyphicon glyphicon-search"></span></a></label>
		    	<input type="hidden" id="activityId">
			  <input type="text" class="form-control" id="activityName" placeholder="点击上面搜索" readonly>
		  </div>
		</form>

	</div>

	<div id="owner" style="position: relative; left: 40px; height: 35px; top: 50px;">
		记录的所有者：<br>
		<b>${clue.owner}</b>
		<input id="clueId" type="hidden" value="${clue.id}">
	</div>
	<div id="operation" style="position: relative; left: 40px; height: 35px; top: 100px;">
		<input id="convertBtn" class="btn btn-primary" type="button" value="转换">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input class="btn btn-default" type="button" value="取消">
	</div>
</body>
</html>
