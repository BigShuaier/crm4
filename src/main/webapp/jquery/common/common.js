$(function () {
    //全选
    $("#chkedAll").on("click",function () {
        $("#tBody input[type='checkbox']").prop("checked",$("#chkedAll").prop("checked"))
    })
    //反选
    $("#tBody").on("click","input[type='checkbox']",function () {
        if($("#tBody input[type='checkbox']").size()==$("#tBody input[type='checkbox']:checked").size()){
            $("#chkedAll").prop("checked",true)
        }else {
            $("#chkedAll").prop("checked",false)
        }
    })
})
