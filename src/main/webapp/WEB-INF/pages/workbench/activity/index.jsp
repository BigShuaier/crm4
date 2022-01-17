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
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
	<script type="text/javascript" src="jquery/common/common.js"></script>
    <!--  PAGINATION plugin -->
    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>
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
		//多条件分页查询市场活动列表数据
		queryActivityListForPageByCondition(1,2)
		//给查询按钮绑定事件
		$("#queryActivityBtn").on("click",function () {
			queryActivityListForPageByCondition(1,$("#demo_pag1").bs_pagination("getOption","rowsPerPage"))
		})
		//创建
		$("#createActivityBtn").on("click",function () {
			var htmlStr=""
			$.ajax({
				url:"workbench/activity/queryAllUserList.do",
				type:"get",
				success:function (data) {
					 htmlStr="<option >"+"--------------请选择-------------"+"</option>"
					$.each(data,function (index,item) {
						htmlStr+="<option value="+item.id+">"+item.name+"</option>"
					})
					$("#create-marketActivityOwner").html(htmlStr)
					document.forms[0].reset()
					$("#createActivityModal").modal("show")
				}

			})
		})
		//保存
		$("#saveCreateActivityBtn").on("click",function () {
			var ownerName=$.trim($("#create-marketActivityOwner").val())
			if(ownerName==""){
				alert("所有者不能为空")
				return;
			}
			var activityName=$.trim($("#create-marketActivityName").val())
			if(activityName==""){
				alert("名称不能为空")
				return;
			}
			var cost=$.trim($("#create-cost").val())
			if(!/^\d+(\.{0,1}\d+){0,1}$/.test(cost)){
				alert("请输入正确的金额")
				return
			}
			//获取页面其他数据
			var startDate=$.trim($("#create-startDate").val())
			var endDate=$.trim($("#create-endDate").val())
			var description=$.trim($("#create-description").val())
			$.ajax({
				url:"workbench/activity/saveCreateActivity.do",
				type:"post",
				data:{
					name:activityName,
					owner:ownerName,
					startDate:startDate,
					endDate:endDate,
					cost:cost,
					description:description
				},
				success:function (data) {
				 if(data.code==1){
				 	alert("添加成功")
					 $("#createActivityModal").modal("hide")
					 queryActivityListForPageByCondition(1,$("#demo_pag1").bs_pagination("getOption","rowsPerPage"))
				 }
				}
			})
		})
		//修改
		$("#editActivityBtn").on("click",function () {
			var checked=$("#tBody input[type=checkbox]:checked")
			if(checked.size()!=1){
				alert("只能选择一条数据")
				return
			}
			var activityId=checked.val()
			$.ajax({
				url:"workbench/activity/editActivity.do",
				type:"get",
				data:{
					id:activityId
				},
				success:function (data) {
					var htmlStr="<option>"+"------请选择-------"+"</option>"
					$.each(data.userList,function (index,item) {
						htmlStr+="<option value="+item.id+">"+item.name+"</option>"
					})

					$("#edit-marketActivityOwner").html(htmlStr)

					document.forms[0].reset()

					$("#edit-marketActivityOwner").val(data.activity.owner)
					$("#edit-marketActivityName").val(data.activity.name)
					$("#edit-startDate").val(data.activity.startDate)
					$("#edit-endDate").val(data.activity.endDate)
					$("#edit-cost").val(data.activity.cost)
					$("#edit-description").val(data.activity.description)
					$("#edit-id").val(data.activity.id)

					$("#editActivityModal").modal("show")
				}
			})
		//给更新按钮添加一个单机事件
		$("#saveEditActivityBtn").on("click",function () {
			//
			//获取所有的值
			var owner=$.trim($("#edit-marketActivityOwner").val())
			if(owner==""){
				alert("所有者不能为空")
				return
			}
			//获取名称
			//判断是否为空
			var name=$.trim($("#edit-marketActivityName").val())
			if(name==""){
				alert("名称不能为空")
				return
			}
			var id=$.trim($("#edit-id").val())
			var cost=$.trim($("#edit-cost").val())
			var startDate=$.trim($("#edit-startDate").val())
			var endDate=$.trim($("#edit-endDate").val())
			var description=$.trim($("#edit-description").val())
			$.ajax({
				url:"workbench/activity/saveEditActivity.do",
				type:"post",
				data:{
					id:id,
					name:name,
					owner:owner,
					startDate:startDate,
					endDate:endDate,
					cost:cost,
					description:description
				},
				success:function (data) {
					if(data.code==1){
						alert("更新成功")
						$("#editActivityModal").modal("hide")
						queryActivityListForPageByCondition(1,$("#demo_pag1").bs_pagination("getOption","rowsPerPage"))
					}
				}
			})

		})
		})
		//删除
		$("#deleteActivityBtn").on("click",function () {
			//获取所有被选中的checkbox对象
			var checkeds=$("#tBody input[type=checkbox]:checked")
			//判断选中的数量是否为0
			if(checkeds.size()==0){
				alert("请选择数据")
				return
			}
			//获取checkbox的value值，将id每个记录的id进行字符转拼接
			var ids=""
			$.each(checkeds,function (intdex,item) {
				ids+="id="+$(item).val()+"&"
			})
			ids=ids.substring(0,ids.length-1)
			//发起异步请求
			$.ajax({
				url:"workbench/activity/deleteActivityById.do",
				type:"post",
				data:ids,
				success:function (data) {
					if(data.code==1){
						alert("您成功删除了"+data.data+"条数据")
						queryActivityListForPageByCondition(1,$("#demo_pag1").bs_pagination("getOption","rowsPerPage"))
					}else {
						alert(data.message)
					}
				}
			})
		})
		//批量导出
		$("#exportActivityAllBtn").on("click",function () {
			window.location.href="workbench/activity/exportActivityAll.do"
		})
		//打开批量导出模态窗口
		$("#importActivityListBtn").on("click",function () {

			$("#importActivityModal").modal("show")

		})
		//给导入按钮创建单击事件
		$("#importActivityBtn").on("click",function () {
			//获取文件名称
			var allPathFileName=$("#activityFile").val().toString()

			//获取文件名后缀
			var suffix=allPathFileName.substring(allPathFileName.lastIndexOf(".")+1).toLowerCase()

			//判断文件类型
			if(suffix!="xls"){
				alert("仅支持xls结尾的文件")
				return
			}
			//判断文件大小
			var upLoadFileSize=$("#activityFile")[0].files[0].size
			//验证文件的大小不能超过5M
			if(upLoadFileSize>(1024*1024*5)){
				alert("上传文件不能超过5M")
				return
			}

			//创建js的form对象
			var form =new FormData()

			//获取上传文件对象
			var upLoadFileObject=$("#activityFile")[0].files[0]
			form.append("activityFile", upLoadFileObject)
			//alert(form)
			//发送ajax请求
			$.ajax({
				url:"workbench/activity/importActivity.do",
				type:"post",
				data:form,
				processData:false,
				contentType:false,
				success:function (data) {
					if(data.code==1){
						alert("您成功添加了"+data.data+"条数据")
						$("#importActivityModal").modal("hide")
						queryActivityListForPageByCondition(1,$("#demo_pag1").bs_pagination("getOption","rowPerPage"))
					}else {
						data.message
					}
				}
			})
		})
		//给选择导出窗口添加单击事件
		$("#exportActivityXzBtn").on("click",function () {
			var checkeds=$("#tBody input[type=checkbox]:checked")
			if(checkeds.size()==0){
				alert("请选择要导出的数据")
				return
			}
			var ids=""
			//编写请求参数字符串
			$.each(checkeds,function (index,item) {
				ids+="id="+$(item).val()+"&"
			})
			ids=ids.substring(0,ids.length-1)
			//发起请求
			window.location.href="workbench/activity/exportActivityXz.do?"+ids
			/*$.ajax({
				url:"",
				type:"get",
				data:ids,
				success:function (data) {
					if(data.code==1){
						alert("导出成功")
					}else {
						data.message
					}
				}
			})*/
		})
	});
	//多条件分页查询市场活动列表数据
	function queryActivityListForPageByCondition(pageNo,pageSize) {
		$("#chkedAll").prop("checked",false);
		//获取查询参数
			var activityName=$.trim($("#query-name").val())
			var ownerName=$.trim($("#query-owner").val())
			var startDate=$.trim($("#query-startDate").val())
			var endDate=$.trim($("#query-endDate").val())
			$.ajax({
				url:"workbench/activity/queryActivityListForPageByCondition.do",
				type:"get",
				data:{
					activityName:activityName,
					ownerName:ownerName,
					startDate:startDate,
					endDate:endDate,
					pageNo:pageNo,
					pageSize:pageSize,
				},
				success:function (data) {
					var htmlStr=""
					$.each(data.activityList,function(index,item){
						htmlStr+="<tr class=\"active\">"
						htmlStr+="<td><input type=\"checkbox\" value=\""+item.id+"\"/></td>"
						htmlStr+="<td><a style=\"text-decoration: none; cursor: pointer\"; onclick=\"window.location.href='workbench/activity/detail.do?id="+item.id+"';\">"+item.name+"</a></td>"
						htmlStr+="<td>"+item.owner+"</td>"
						htmlStr+="<td>"+item.startDate+"</td>"
						htmlStr+="<td>"+item.endDate+"</td>"
						htmlStr+="</tr>"
					})
					$("#tBody").html(htmlStr)

					$("#demo_pag1").bs_pagination({
						currentPage:pageNo,//当前页

						rowsPerPage:pageSize,//每页显示条数
						totalRows:data.totalRows,//总条数
						totalPages: data.totalPage,//总页数

						visiblePageLinks:5,//显示的翻页卡片数

						showGoToPage:true,//是否显示"跳转到第几页"
						showRowsPerPage:true,//是否显示"每页显示条数"
						showRowsInfo:true,//是否显示"记录的信息"

						//每次切换页号都会自动触发此函数，函数能够返回切换之后的页号和每页显示条数
						onChangePage: function(e,pageObj) { // returns page_num and rows_per_page after a link has clicked
							$("#chkedAll").prop("checked",false)
							queryActivityListForPageByCondition(pageObj.currentPage,pageObj.rowsPerPage)
						}
					});
				}
			})
	}

</script>
</head>
<body>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">

					<form class="form-horizontal" role="form" id="activityForm">

						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-marketActivityOwner">

								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                            </div>
						</div>

						<div class="form-group">
							<label for="create-startDate" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control mydate" id="create-startDate" readonly="true">
							</div>
							<label for="create-endDate" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control mydate" id="create-endDate" readonly="true">
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>

					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="saveCreateActivityBtn" type="button" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">

					<form class="form-horizontal" role="form">
					    <input type="hidden" id="edit-id">
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-marketActivityOwner">
									<c:forEach items="${userList}" var="u">
										<option value="${u.id}">${u.name}</option>
									</c:forEach>
								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName" >
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startDate" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control mydate" id="edit-startDate" readonly>
							</div>
							<label for="edit-endDate" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control mydate" id="edit-endDate" readonly >
							</div>
						</div>

						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control " id="edit-cost" >
							</div>
						</div>

						<div class="form-group">
							<label for="edit-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-description"></textarea>
							</div>
						</div>

					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="saveEditActivityBtn" type="button" class="btn btn-primary">更新</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 导入市场活动的模态窗口 -->
    <div class="modal fade" id="importActivityModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 85%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">导入市场活动</h4>
                </div>
                <div class="modal-body" style="height: 350px;">
                    <div style="position: relative;top: 20px; left: 50px;">
                        请选择要上传的文件：<small style="color: gray;">[仅支持.xls格式]</small>
                    </div>
                    <div style="position: relative;top: 40px; left: 50px;">
                        <input type="file" id="activityFile">
                    </div>
                    <div style="position: relative; width: 400px; height: 320px; left: 45% ; top: -40px;" >
                        <h3>重要提示</h3>
                        <ul>
                            <li>操作仅针对Excel，仅支持后缀名为XLS的文件。</li>
                            <li>给定文件的第一行将视为字段名。</li>
                            <li>请确认您的文件大小不超过5MB。</li>
                            <li>日期值以文本形式保存，必须符合yyyy-MM-dd格式。</li>
                            <li>日期时间以文本形式保存，必须符合yyyy-MM-dd HH:mm:ss的格式。</li>
                            <li>默认情况下，字符编码是UTF-8 (统一码)，请确保您导入的文件使用的是正确的字符编码方式。</li>
                            <li>建议您在导入真实数据之前用测试文件测试文件导入功能。</li>
                        </ul>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button id="importActivityBtn" type="button" class="btn btn-primary">导入</button>
                </div>
            </div>
        </div>
    </div>


	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">

			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="query-name">
				    </div>
				  </div>

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="query-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control mydate" type="text" id="query-startDate" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control mydate" type="text" id="query-endDate">
				    </div>
				  </div>

				  <button id="queryActivityBtn" type="button" class="btn btn-default">查询</button>
				  <button id="resetActivityBtn" type="reset" class="btn btn-default">重置</button>

				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button id="createActivityBtn" type="button" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button id="editActivityBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button id="deleteActivityBtn" type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				<div class="btn-group" style="position: relative; top: 18%;">
                    <button id="importActivityListBtn" type="button" class="btn btn-default" ><span class="glyphicon glyphicon-import"></span> 上传列表数据（导入）</button>
                    <button id="exportActivityAllBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（批量导出）</button>
                    <button id="exportActivityXzBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（选择导出）</button>
                </div>
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="chkedAll"/></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="tBody">

					</tbody>
				</table>
                <!--创建容器-->
                <div id="demo_pag1"></div>
			</div>


		</div>

	</div>
</body>
</html>
