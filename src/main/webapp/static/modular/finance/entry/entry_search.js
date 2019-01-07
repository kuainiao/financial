/**
 * 凭证展示管理初始化
 */
var search = {
    layerIndex: -1,
    object : null
};
/**
 * 初始化凭证展示详情对话框
 */
var searchDlg = {
    entryInfoData : {}
};


/**
 * 清除数据
 */
search.clearData = function() {
    this.entryInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
search.set = function(key, val) {
    this.entryInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    this.entryInfoData['posting_status'] =($("input[name='posting_status']:checked").val());
    this.entryInfoData['approval_status'] = ($("input[name='approval_status']:checked").val());
    return this;
};
/**
 * 搜索收集数据
 */
search.collectData = function() {
    this
        .set('start_period')
        .set('end_period')
        .set('business_date_start')
        .set('business_date_end')
        .set('document_word')
        .set('prepared_by')
        .set('approval_people')
        .set('posting_people')
        .set('summary')
        .set('expense_account')
        .set('start_document_number')
        .set('end_document_number')
        .set('start_credit_amount')
        .set('end_credit_amount')
        .set('start_debit_amount')
        .set('end_debit_amount')
        .set('start_original_currency')
        .set('end_original_currency')
        .set('dateClass')
        .set('search_remark');
};

/**
 * 重置搜索框
 */
search.searchResete = function (){
    $("#start_period").val(""); //会计期间
    $("#end_period").val("");   //会计期间
    $("input[name='posting_status']").get(0).checked=true;  //过账标记
    $("input[name='approval_status']").get(0).checked=true; //审批标记
    $("#business_date_start").val("");//业务日期
    $("#business_date_end").val("");//业务日期
    $("#document_word").val("");//凭证字
    $("#prepared_by").val("");//制单人
    $("#approval_people").val("");//审核人
    $("#posting_people").val("");//过账人
    $("#summary").val("");//摘要
    $("#expense_account").val("");//会计科目
    $("#start_document_number").val("");//凭证号
    $("#end_document_number").val("");//凭证号
    $("#start_credit_amount").val("");//贷方金额
    $("#end_credit_amount").val("");//贷方金额
    $("#start_debit_amount").val("");//借方金额
    $("#end_debit_amount").val("");//借方金额
    $("#start_original_currency").val("");//原币金额
    $("#end_original_currency").val("") //原币金额
    $("#search_remark").val("");//搜索方案名
};
/**
 * 搜索条件赋值
 * @param obj
 * @constructor
 */
search.Assignment = function (obj){
    var id = $(obj).val();
    if(id != undefined && id !='' && id != null){
        var ajax = new $ax(Feng.ctxPath + "/entry/searchPlan", function (data) {
            $("#start_period").val(data.search_period_start); //会计期间
            $("#end_period").val(data.search_period_end);   //会计期间
            $("input[name='posting_status'][value="+data.search_posting_status+"]").attr('checked','checked');
            $("input[name='approval_status'][value="+data.search_approval_status+"]").attr('checked','checked');
            $("#business_date_start").val(data.search_business_date_start);//业务日期
            $("#business_date_end").val(data.search_business_date_end);//业务日期
            $("#document_word").val(data.search_document_word);//凭证字
            $("#prepared_by").val(data.search_prepared_by);//制单人
            $("#approval_people").val(data.search_approval_people);//审核人
            $("#posting_people").val(data.search_posting_people);//过账人
            $("#summary").val(data.search_summary);//摘要
            $("#expense_account").val(data.search_expense_account);//会计科目
            $("#start_document_number").val(data.search_start_document_number);//凭证号
            $("#end_document_number").val(data.search_end_document_number);//凭证号
            $("#start_credit_amount").val(data.search_start_credit_amount);//贷方金额
            $("#end_credit_amount").val(data.search_end_credit_amount);//贷方金额
            $("#start_debit_amount").val(data.search_start_debit_amount);//借方金额
            $("#end_debit_amount").val(data.search_end_debit_amount);//借方金额
            $("#start_original_currency").val(data.search_start_original_currency);//原币金额
            $("#end_original_currency").val(data.search_end_original_currency) //原币金额
        }, function (data) {
            Feng.error("查询失败!" + data.responseJSON.message + "!");
        });
        ajax.set("id",id);
        ajax.start();
    }else{
        location.reload();
    }
};


/**
 * 关闭此对话框
 */
search.close = function() {
    layer.close(search.layerIndex);
};
/**
 * 关闭搜索框
 */
search.closeSearch = function(){
    parent.layer.close(window.parent.Entry.layerIndex);
};
/**
 * 搜索提交
 */
search.openEntryclickSubmit = function(){
    this.clearData();
    this.collectData();
    parent.Entry.table.refresh({query:this.entryInfoData});
    Feng.success("查询成功!");
    search.closeSearch();
};
/**
 * 删除搜索方案
 */
search.deleteSearch = function(){
    var operation = function(){
        var id = $("#search_plan").val();
        var ajax = new $ax(Feng.ctxPath + "/entry/deleteSearch", function () {
            Feng.success("删除成功!");
            location.reload();
        }, function (data) {
            Feng.error("删除失败!" +data.responseJSON.message + "!");
        });
        ajax.set("id",id);
        ajax.start();
    };
    Feng.confirm("是否确认删除?",operation);
}

/**
 * 费用科目
 */
search.openAddSubject = function (obj) {
    var index = layer.open({
        id: 'subject_Open',
        type: 2,
        title: '费用科目',
        area: ['600px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/expenseAccount/expenseAccount_subject'
    });
    this.layerIndex = index;
    search.object = obj;
};

/**
 * 打开搜索方案保存框
 */
search.opensaveSearch = function () {
    var index = layer.open({
        type: 1,
        title: '搜索方案',
        area: ['300px', '200px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: "<div><input type='text' placeholder='查询方案另存为' id='search_remark' name='search_remark'  style='margin-top: 10%;margin-left: 20%;width: 60%;height: 34px;border: 1px solid #e5e6e7;color: inherit;font-size: 14px;'/></div>" +
                 "<div style='margin-top: 8%;  margin-left: 30%;'> <button type='button' onclick='search.searchSubmit()' class='btn btn-primary btn-sm button-margin' style=' height: 31px; margin-left: 10px!important'>保存</button>\n" +
                 " <button type='button' onclick='search.close()'  class='btn btn-primary btn-sm button-margin' style='height: 31px; margin-left: 20px!important'>取消</button></div>"
    });
    this.layerIndex = index;
};

/**
 * 搜索方案提交
 */
search.searchSubmit = function(){
    this.clearData();
    this.collectData();
    var ajax = new $ax(Feng.ctxPath + "/entry/saveSearch", function (data) {
        Feng.success("保存成功!");
        location.reload();
    }, function (data) {
        Feng.error("保存失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.entryInfoData);
    ajax.start();
    search.close();
};

/**
 * 加载下拉框值
 */
$(function () {
    var ajax = new $ax(Feng.ctxPath + "/entry/getUser", function (data) {
        $.each(data,function (index,val) {
            $("#prepared_by").append("<option value="+val.name+">"+val.name+"</option>");
            $("#approval_people").append("<option value="+val.name+">"+val.name+"</option>");
            $("#posting_people").append("<option value="+val.name+">"+val.name+"</option>");
        });
    }, function (data) {
        Feng.error("查询人员失败!" + data.responseJSON.message + "!");
    });
    ajax.start();
    //会计期间
    for(var i=1; i<13; i++){
        $("#start_period").append("<option value="+i+">第"+i+"期</option>")
        $("#end_period").append("<option value="+i+">第"+i+"期</option>")
    }
});