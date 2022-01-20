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
		//
        queryClueListForPageByCondition(1,1)
		$("#queryByCondition").on("click",function () {
			queryClueListForPageByCondition(1,$("#demo_pag1").bs_pagination("getOption","rowsPerPage"))
		})
		//给创建保存按钮添加单击时间
		$("#saveCreateClueBtn").on("click",function () {

			var  owner=$.trim($("#create-clueOwner").val())
			if(owner==""){
				alert("所有者不能为空")
				return
			}


			var company=$.trim($("#create-company").val())
			if(company==""){
				alert("公司名称不能为空")
				return
			}


			var name=$.trim($("#create-fullName").val())
			if(name ==""){
				alert("姓名不能为空")
				return
			}

			//其他信息获取
			var job=$("#create-job").val()

			var email=$("#create-email").val()

			var phone=$("#create-phone").val()

			var website=$("#create-website").val()

			var mphone=$("#create-mphone").val()

			var source=$("#create-source").val()

			var state=$("#create-state").val()

			var description=$("#create-description").val()

			var contactSummary=$("#create-contactSummary").val()

			var nextContactTime=$("#create-nextContactTime").val()

			var address=$("#create-address").val()

			var appellation=$("#create-appellation").val()




			$.ajax({
				url:"workbench/clue/saveCreateClue.do",
				type:"get",
				data:{
					owner:owner,
					job:job,
					company:company,
					fullName:name,
					email:email,
					phone:phone,
					website:website,
					state:state,
					mphone:mphone,
					source:source,
					description:description,
					contactSummary:contactSummary,
					nextContactTime:nextContactTime,
					address:address,
					appellation:appellation
				},
				success:function (data) {
					if(data.code==1){
						alert("创建成功")
						$("#createClueModal").modal("hide")
						queryClueListForPageByCondition(1,$("#demo_pag1").bs_pagination("getOption","rowsPerPage"))
					}else {
						alert(data.message)
					}
				}

			})



		})
		//给修改按钮添加单击事件
		$("#editClueBtn").on("click",function(){

			//获取被选中的记录
			var checked=$("#tBody input[type=checkbox]:checked")

			//判断是否为空
			if(checked.size()!=1){
				alert("只能选择一条数据")

				return
			}

			//获取id
			var id = checked.val()
			//异步请求-数据回显
			$.ajax({
				url:"workbench/clue/editClueBtnShow.do",
				type:"get",
				data:{
					id:id
				},
				success:function (data) {
					$("#edit-clueId").val(data.id)

					$("#edit-clueOwner").val(data.owner)

					$("#edit-company").val(data.company)

					$("#edit-surname").val(data.fullName)
					//其他信息获取
					$("#edit-job").val(data.job)
					$("#edit-email").val(data.email)
					$("#edit-phone").val(data.phone)
					$("#edit-website").val(data.website)
					$("#edit-mphone").val(data.mphone)
					$("#edit-source").val(data.source)
					$("#edit-status").val(data.state)
					$("#edit-describe").val(data.description)
					$("#edit-contactSummary").val(data.contactSummary)
					$("#edit-nextContactTime").val(data.nextContactTime)
					$("#edit-address").val(data.address)
					$("#edit-call").val(data.appellation)
					$("#edit-clueCreateBy").val(data.createBy)
					$("#edit-clueCreateTime").val(data.createTime)
					$("#editClueModal").modal("show")
				}
			})



		})
		//修改界面保存按钮事件
		$("#saveUpdateClue").on("click",function (){
			var  owner=$.trim($("#edit-clueOwner").val())
			if(owner==""){
				alert("所有者不能为空")
				return
			}
			var company=$.trim($("#edit-company").val())
			if(company==""){
				alert("公司名称不能为空")
				return
			}
			var name=$.trim($("#edit-surname").val())
			if(name ==""){
				alert("姓名不能为空")
				return
			}

			//其他信息获取
			var id=$("#edit-clueId").val()
			var job=$("#edit-job").val()
			var email=$("#edit-email").val()
			var phone=$("#edit-phone").val()
			var website=$("#edit-website").val()
			var mphone=$("#edit-mphone").val()
			var source=$("#edit-source").val()
			var state=$("#edit-status").val()
			var description=$("#edit-describe").val()
			var contactSummary=$("#edit-contactSummary").val()
			var nextContactTime=$("#edit-nextContactTime").val()
			var address=$("#edit-address").val()
			var appellation=$("#edit-call").val()
			var createTime=$("#edit-clueCreateTime").val()
			var createBy=$("#edit-clueCreateBy").val()
			$.ajax({
				url:"workbench/clue/saveUpdateClue.do",
				type:"get",
				data:{
					id:id,
					owner:owner,
					job:job,
					company:company,
					fullName:name,
					email:email,
					phone:phone,
					website:website,
					state:state,
					mphone:mphone,
					source:source,
					description:description,
					contactSummary:contactSummary,
					nextContactTime:nextContactTime,
					address:address,
					appellation:appellation,
					createTime:createTime,
					createBy:createBy
				},
				success:function (data) {
					if(data.code==1){
						alert("更新成功")
					$("#editClueModal").modal("hide")
						queryClueListForPageByCondition($("#demo_pag1").bs_pagination("getOption","currentPage"),queryClueListForPageByCondition($("#demo_pag1").bs_pagination("getOption","rowsPerPage")))
					}else {
						data.message
					}


				}
			})
		})
		//给删除按钮绑定一个单击事件
		$("#deleteClueBtn").on("click",function () {
			var checkeds=$("#tBody input[type=checkbox]:checked")
			if(checkeds.size()==0){
				alert("请选择要删除的数据")
				return
			}
			var ids=""
			$.each(checkeds,function (index,item) {
				ids+="id="+$(item).val()+"&"
			})
			$.ajax({
				url:"workbench/clue/deleteClue.do",
				type:"post",
				data:ids,
				success:function (data) {
					if(data.code==1){
						alert("您成功删除了"+data.data+"条数据")
						queryClueListForPageByCondition(1,$("#demo_pag1").bs_pagination("getOption","rowsPerPage"))
					}else {
						data.message
					}
				}
			})
		})

	});
	//多条件分页查询市场活动列表数据
	function queryClueListForPageByCondition(pageNo,pageSize){
		$("#chkedAll").prop("checked",false)
		var name=$.trim($("#query-name").val())
		var company=$.trim($("#query-company").val())
		var phone=$.trim($("#query-phone").val())
		var source=$.trim($("#query-source").val())
		var owner=$.trim($("#query-owner").val())
		var mphone=$.trim($("#query-mphone").val())
		var clueState=$.trim($("#query-clueState").val())
		$.ajax({
			url:"workbench/clue/queryClueListForPageByCondition.do",
			type:"get",
			data:{
			    name:name,
                company:company,
                phone:phone,
                source:source,
                owner:owner,
                mphone:mphone,
                clueState:clueState,
                pageNo:pageNo,
                pageSize:pageSize

			},
			success:function (data) {
			    var htmlStr=""
				$.each(data.clueList,function (index,item) {
                   htmlStr+="<tr>";
                   htmlStr+="<td><input type=\"checkbox\" value='"+item.id+"'/></td>";
                   htmlStr+="<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/clue/detailClue.do?id="+item.id+"';\">"+item.fullName+item.appellation+"</a></td>";
                   htmlStr+="<td>"+item.company+"</td>";
                   htmlStr+="<td>"+item.phone+"</td>";
                   htmlStr+="<td>"+item.mphone+"</td>";
                   htmlStr+="<td>"+item.source+"</td>";
                   htmlStr+="<td>"+item.owner+"</td>";
                   htmlStr+="<td>"+item.state+"</td>";
                   htmlStr+="</tr>";
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
						queryClueListForPageByCondition(pageObj.currentPage,pageObj.rowsPerPage)
					}
				});
			}
		})
	}
</script>
</head>
<body>

	<!-- 创建线索的模态窗口 -->
	<div class="modal fade" id="createClueModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">创建线索</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">

						<div class="form-group">
							<label for="create-clueOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-clueOwner">
">
									<option >----请选择----</option>
								  <c:forEach items="${userList}" var="u">
										<option value="${u.id}">${u.name}</option>
								  </c:forEach>
								</select>
							</div>
							<label for="create-company" class="col-sm-2 control-label">公司<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-company">
							</div>
						</div>

						<div class="form-group">
							<label for="create-appellation" class="col-sm-2 control-label">称呼</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-appellation">
								  <option></option>
								  <c:forEach items="${appellationList}" var="a">
										<option value="${a.id}">${a.value}</option>
								  </c:forEach>
								</select>
							</div>
							<label for="create-fullName" class="col-sm-2 control-label">姓名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-fullName">
							</div>
						</div>

						<div class="form-group">
							<label for="create-job" class="col-sm-2 control-label">职位</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-job">
							</div>
							<label for="create-email" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-email">
							</div>
						</div>

						<div class="form-group">
							<label for="create-phone" class="col-sm-2 control-label">公司座机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-phone">
							</div>
							<label for="create-website" class="col-sm-2 control-label">公司网站</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-website">
							</div>
						</div>

						<div class="form-group">
							<label for="create-mphone" class="col-sm-2 control-label">手机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-mphone">
							</div>
							<label for="create-state" class="col-sm-2 control-label">线索状态</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-state">
								  <option></option>
								  <c:forEach items="${clueStateList}" var="cs">
									  <option value="${cs.id}">${cs.value}</option>
								  </c:forEach>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label for="create-source" class="col-sm-2 control-label">线索来源</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-source">
								  <option></option>
								  <c:forEach items="${sourceList}" var="s">
										<option value="${s.id}">${s.value}</option>
								  </c:forEach>
								</select>
							</div>
						</div>


						<div class="form-group">
							<label for="create-description" class="col-sm-2 control-label">线索描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>

						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>

						<div style="position: relative;top: 15px;">
							<div class="form-group">
								<label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
								<div class="col-sm-10" style="width: 81%;">
									<textarea class="form-control" rows="3" id="create-contactSummary"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="text" class="form-control" id="create-nextContactTime">
								</div>
							</div>
						</div>

						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

						<div style="position: relative;top: 20px;">
							<div class="form-group">
                                <label for="create-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="create-address"></textarea>
                                </div>
							</div>
						</div>
					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="saveCreateClueBtn" type="button" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 修改线索的模态窗口 -->
	<div class="modal fade" id="editClueModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">修改线索</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">

						<div class="form-group">
							<label for="edit-clueOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input id="edit-clueId" type="hidden">
								<input id="edit-clueCreateBy" type="hidden">
								<input id="edit-clueCreateTime" type="hidden">
								<select class="form-control" id="edit-clueOwner">
									<c:forEach items="${userList}" var="u">
										<option value="${u.id}">${u.name}</option>
									</c:forEach>
								</select>
							</div>
							<label for="edit-company" class="col-sm-2 control-label">公司<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-company" value="动力节点">
							</div>
						</div>

						<div class="form-group">
							<label for="edit-call" class="col-sm-2 control-label">称呼</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-call">
								  <option></option>
									<c:forEach items="${appellationList}" var="a">
										<option value="${a.id}">${a.value}</option>
									</c:forEach>
								</select>
							</div>
							<label for="edit-surname" class="col-sm-2 control-label">姓名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-surname" value="李四">
							</div>
						</div>

						<div class="form-group">
							<label for="edit-job" class="col-sm-2 control-label">职位</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-job" value="CTO">
							</div>
							<label for="edit-email" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-email" value="lisi@bjpowernode.com">
							</div>
						</div>

						<div class="form-group">
							<label for="edit-phone" class="col-sm-2 control-label">公司座机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-phone" value="010-84846003">
							</div>
							<label for="edit-website" class="col-sm-2 control-label">公司网站</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-website" value="http://www.bjpowernode.com">
							</div>
						</div>

						<div class="form-group">
							<label for="edit-mphone" class="col-sm-2 control-label">手机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-mphone" value="12345678901">
							</div>
							<label for="edit-status" class="col-sm-2 control-label">线索状态</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-status">
								  <option></option>
									<c:forEach items="${clueStateList}" var="cs">
										<option value="${cs.id}">${cs.value}</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label for="edit-source" class="col-sm-2 control-label">线索来源</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-source">
								  <option></option>
									<c:forEach items="${sourceList}" var="s">
										<option value="${s.id}">${s.value}</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe">这是一条线索的描述信息</textarea>
							</div>
						</div>

						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>

						<div style="position: relative;top: 15px;">
							<div class="form-group">
								<label for="edit-contactSummary" class="col-sm-2 control-label">联系纪要</label>
								<div class="col-sm-10" style="width: 81%;">
									<textarea class="form-control" rows="3" id="edit-contactSummary">这个线索即将被转换</textarea>
								</div>
							</div>
							<div class="form-group">
								<label for="edit-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="text" class="form-control" id="edit-nextContactTime" value="2017-05-01">
								</div>
							</div>
						</div>

						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="edit-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="edit-address">北京大兴区大族企业湾</textarea>
                                </div>
                            </div>
                        </div>
					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary"  id="saveUpdateClue">更新</button>
				</div>
			</div>
		</div>
	</div>




	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>线索列表</h3>
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
				      <div class="input-group-addon">公司</div>
				      <input class="form-control" type="text" id="query-company">
				    </div>
				  </div>

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">公司座机</div>
				      <input class="form-control" type="text" id="query-phone">
				    </div>
				  </div>

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">线索来源</div>
					  <select class="form-control" id="query-source">
					  	  <option></option>
						  <c:forEach items="${sourceList}" var="s">
							  <option value="${s.id}">${s.value}</option>
						  </c:forEach>
					  </select>
				    </div>
				  </div>

				  <br>

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="query-owner">
				    </div>
				  </div>



				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">手机</div>
				      <input class="form-control" type="text" id="query-mphone">
				    </div>
				  </div>

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">线索状态</div>
					  <select class="form-control" id="query-clueState">
					  	<option></option>
						  <c:forEach items="${clueStateList}" var="cs">
							  <option value="${cs.id}">${cs.value}</option>
						  </c:forEach>
					  </select>
				    </div>
				  </div>

				  <button type="button" class="btn btn-default" id="queryByCondition">查询</button>
					<button type="reset" class="btn btn-default">重置</button>
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 40px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#createClueModal"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" data-toggle="modal" id="editClueBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteClueBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>


			</div>
			<div style="position: relative;top: 50px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="chkedAll"/></td>
							<td>名称</td>
							<td>公司</td>
							<td>公司座机</td>
							<td>手机</td>
							<td>线索来源</td>
							<td>所有者</td>
							<td>线索状态</td>
						</tr>
					</thead>
					<tbody id="tBody">

					</tbody>
				</table>
				<div id="demo_pag1"></div>
			</div>

			<div>

			</div>

		</div>

	</div>
</body>
</html>
