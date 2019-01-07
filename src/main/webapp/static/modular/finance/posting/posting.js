/**
 * 凭证展示管理初始化
 */
var Posting = {
    id: "PostingTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};
/**
 * 初始化凭证展示详情对话框
 */
var PostingInfoDlg = {
    postingInfoData : {}
};

var seItemID = new Array();
/**
 * 初始化表格的列
 */
Posting.initColumn = function () {
    return [
        {
            field: 'selectItem', checkbox: true,
            formatter: function (value, row, index) {
                return "<input type='hidden' value=" + row.rownum + ">"
            }
        },
        {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '期间', field: 'period', visible: false, align: 'center', valign: 'middle'},
        {title: '过账', field: 'posting_status', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if(value==1){
                    return "以过账";
                }else{
                    return "未过账";
                }
            }
        },
        {title: '审核', field: 'approval_status', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if(value=='1'){
                    return "以审刻";
                }else{
                    return "未审刻";
                }
            }
        },
        {title: '业务日期', field: 'business_date', visible: false, align: 'center', valign: 'middle'},
        {title: '凭证字', field: 'document_word', visible: true, align: 'center', valign: 'middle'},
        {title: '凭证号', field: 'document_number', visible: false, align: 'center', valign: 'middle'},
        {title: '摘要', field: 'summary', visible: true, align: 'center', valign: 'middle'},
        {title: '科目代码', field: 'expense_account', visible: true, align: 'center', valign: 'middle'},
        {title: '科目名称', field: '', visible: true, align: 'center', valign: 'middle'},
        {title: '往来单位', field: '', visible: false, align: 'center', valign: 'middle'},
        {title: '部门', field: '', visible: false, align: 'center', valign: 'middle'},
        {title: '职员', field: '', visible: false, align: 'center', valign: 'middle'},
        {title: '业务编号', field: '', visible: false, align: 'center', valign: 'middle'},
        {title: '原币金额', field: '', visible: true, align: 'center', valign: 'middle'},
        {title: '借方金额', field: 'debit_amount', visible: true, align: 'center', valign: 'middle'},
        {title: '贷方金额', field: 'credit_amount', visible: true, align: 'center', valign: 'middle'},
        {title: '制单人', field: 'prepared_by', visible: true, align: 'center', valign: 'middle'},
        {title: '审核人', field: 'approval_people', visible: true, align: 'center', valign: 'middle'},
        {title: '过账人', field: 'posting_people', visible: true, align: 'center', valign: 'middle'},
        {title: '分组顺序号', field: 'rownum', visible:false, align: 'center' ,valigm: 'middle'},
        {title: '顺序号', field: 'sequence_number', visible: false, align: 'center', valign: 'middle'},
        {title: '单据数', field: 'documents_number', visible: false, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'create_time', visible: false, align: 'center', valign: 'middle'},
        {title: '结算状态', field: 'settlement_state', visible: false ,align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if(value=='0'){
                    return "已结算";
                }else{
                    return "未结算";
                }
            }
        },
        {title: '总金额', field: 'lump_sum', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中(单选)
 */
Posting.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Posting.seItem = selected[0];
        return true;
    }
};
/**
 * 检查是否选中(多选 审批)
 */
Posting.checkApproval = function () {
    seItemID = new Array();
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        for(var i=0;i<selected.length;i++){
                seItemID[i] = (selected[i].id);
        }
        return true;
    }
};
/**
 * 检查是否选中(多选 反审)
 */
Posting.checkreverseApproval = function () {
    seItemID = new Array();
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        for(var i=0;i<selected.length;i++){
            seItemID[i] = (selected[i].id);
        }
        return true;
    }
};
//搜索按钮鼠标悬停事件
Posting.openPostingHover = function (){
    $("#hover").css('display','block');

};
/**
 * 清除数据
 */
Posting.clearData = function() {
    this.postingInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
Posting.set = function(key, val) {
    this.postingInfoData [key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    this.postingInfoData ['posting_status'] =($("input[name='posting_status']:checked").val());
    this.postingInfoData ['approval_status'] = ($("input[name='approval_status']:checked").val());
    return this;
}
/**
 * 搜索收集数据
 */
Posting.collectData = function() {
    this
        .set('start_period')
        .set('end_period')
        .set('business_date')
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
        .set('end_original_currency');
}
Posting.searchResete = function (){
    $("#start_period").val(""); //会计期间
    $("#end_period").val("");   //会计期间
    $("input[name='posting_status']").get(0).checked=true;  //过账标记
    $("input[name='approval_status']").get(0).checked=true; //审批标记
    //$($("input[name='posting_status']")[0]).checked=true;  //过账标记
    //$($("input[name='approval_status']")[0]).checked=true; //审批标记
    $("#business_date").val("");//业务日期
    $("#document_word").val("");//凭证字
    $("#prepared_by").val("");//制单人
    $("#approval_people").val("");//制单人
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
    $("#end_original_currency").val("");//原币金额
}
/**
 * 关闭此对话框
 */
Posting.close = function() {
    layer.close(Entry.layerIndex);
}
//搜索提交按钮
Posting.openPostingclickSubmit = function(){
    this.clearData();
    this.collectData();
    Posting.table.refresh({query:this.postingInfoData});
    Feng.success("查询成功!");
    $("#hover").css('display','none');
    Posting.close();
    Posting.openPostingclickRemove();
}
/*Entry.opnEntryRemoved = function (){
         $("#hover").css('display','none');
}*/
//查询页面取消按钮
Posting.openPostingclickRemove = function (){
    $("#business_date").val("");//业务日期
    $("#summary").val("");//摘要
    $("#hover").css('display','none');
}
/**
 * 过账按钮
 */
Posting.openPosting = function (){
    if (this.checkApproval()) {
        var ajax = new $ax(Feng.ctxPath + "/posting/posting", function (data) {
            Feng.success("过账成功!");
            Posting.table.refresh();
        }, function (data) {
            Feng.error("过账失败!" + data.responseJSON.message + "!");
        });

        ajax.set("entryId",JSON.stringify(seItemID));
        ajax.start();
    }
}
/**
 * 查询凭证展示列表
 */
Posting.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Posting.table.refresh({query: queryData});
};
/**
 * table加载列表
 */
$(function () {
    var defaultColunms = Posting.initColumn();
    var table = new EnTable(Posting.id, "/posting/list", defaultColunms,Posting.removeTableCheckbox);
    table.setPaginationType("client");
    Posting.table = table.init();
});

/**
 * 删除复选框
 */
Posting.removeTableCheckbox = function(){
    var objTr = $($("#PostingTable").children()[1]).children();//获取行对象
    var row = objTr.length; //获取table行
    for (var i = 0; i < row; i++) {
        var valueCheckbox = $($($(objTr[i]).children()[0]).children()[1]).val();//获取input框隐藏值
        if(valueCheckbox != 1){
            $($($(objTr[i]).children()[0]).children()[0]).remove();  //隐藏checkbox框
        }
    }
}
$("#hover").blur(function () {
    //alert("--");
    $("#hover").css('display','block');
});