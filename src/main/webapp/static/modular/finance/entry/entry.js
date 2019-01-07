/**
 * 凭证展示管理初始化
 */
var Entry = {
    id: "EntryTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};
/**
 * 初始化凭证展示详情对话框
 */
var EntryInfoDlg = {
    entryInfoData : {}
};

var seItemID = new Array();
/**
 * 初始化表格的列
 */
Entry.initColumn = function () {
    var columns;
    columns = [
        {field: 'selectItem', checkbox: true},
        {title: 'id', field: 'id', align: 'center', valign: 'middle', sortable: true, hidden: true},
        {
            title: '凭证字', field: 'document_word', align: 'center', valign: 'middle', sortable: true,
            formatter: function (value, row, index) {
                switch (value) {
                    case 1:
                        return "记-" + row.document_number;
                    case 2:
                        return "收-" + row.document_number;
                    case 3:
                        return "付-" + row.document_number;
                    case 4:
                        return "转-" + row.document_number;
                }
            }
        },
        {
            title: '期间', field: 'period', align: 'center', valign: 'middle', sortable: true,
            formatter: function (value, row, index) {
                return "<a onclick='Entry.openEntryDetail("+row.id+")'>" + value + "</a>";
            }
        },
        {
            title: '过账', field: 'posting_status', align: 'center', valign: 'middle', sortable: true,
            formatter: function (value, row, index) {
                if (value == 1) {
                    return "已过账";
                } else {
                    return "未过账";
                }
            }
        },
        {
            title: '审核', field: 'approval_status', align: 'center', valign: 'middle', sortable: true,
            formatter: function (value, row, index) {
                if (value == '1') {
                    return "已审核";
                } else {
                    return "未审核";
                }
            }
        },
        {title: '业务日期', field: 'business_date', align: 'center', valign: 'middle', sortable: true},
        {title: '凭证号', field: 'document_number', align: 'center', valign: 'middle', sortable: true, hidden: true},
        {title: '制单人', field: 'prepared_by', align: 'center', valign: 'middle', sortable: true},
        {title: '审核人', field: 'approval_people', align: 'center', valign: 'middle', sortable: true},
        {title: '过账人', field: 'posting_people', align: 'center', valign: 'middle', sortable: true},
        {title: '摘要', field: 'summary', align: 'center', valign: 'middle', sortable: true},
        {title: '科目代码', field: 'account_code', align: 'center', valign: 'middle', sortable: true},
        {title: '科目名称', field: 'subiect_name', align: 'center', valign: 'middle', sortable: true},
        {title: '借方金额', field: 'debit_amount', align: 'center', valign: 'middle', sortable: true},
        {title: '贷方金额', field: 'credit_amount', align: 'center', valign: 'middle', sortable: true},
        {title: '凭证编号', field: 'code', align: 'center', valign: 'middle', sortable: true, hidden: true},
        {title: '凭证父编号', field: 'pcode', align: 'center', valign: 'middle', sortable: true, hidden: true}
    ];
    return columns;
};

/**
 * 检查是否选中(单选)
 */
Entry.check = function () {
    var selected = $('#' + this.id).bootstrapTreeTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Entry.seItem = selected[0];
        return true;
    }
};
/**
 * 检查是否选中(多选 审批)
 */
Entry.checkApproval = function () {
    seItemID = new Array();
    var selected = $('#' + this.id).bootstrapTreeTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
     /*   console.log(selected);*/
        for(var i=0;i<selected.length;i++){
            if(selected[i].approval_status == '已审核'){/*判断该条数据是否已经为审核数据*/
                Feng.info("有数据已是审核过的数据");
                return false;
            }else if (selected[i].posting_status == '已过账'){
                Feng.info("有数据已是过帐数据，不支持任何操作");
                return false;
            }
            else{
                seItemID[i] = (selected[i].id);
            }

        }
        return true;
    }
};
/**
 * 检查是否选中(多选 反审)
 */
Entry.checkreverseApproval = function () {
    seItemID = new Array();
    var selected = $('#' + this.id).bootstrapTreeTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        for(var i=0;i<selected.length;i++){
            if (selected[i].posting_status == '已过账'){
                Feng.info("有数据以是过帐数据，不支持任何操作");
                return false;
            }else{
                seItemID[i] = (selected[i].id);
            }

        }
        return true;
    }
};
/**
 * 检查是否选中(多选)
 * 过账
 */
Entry.checkPosting = function () {
    seItemID = new Array();
    var selected = $('#' + this.id).bootstrapTreeTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        for(var i=0;i<selected.length;i++){
            if(selected[i].approval_status != '已审核'){/*判断该条数据是否已经为审核数据*/
                Feng.info("有数据未审核");
                return false;
            }else if (selected[i].posting_status == '已过账'){
                Feng.info("有数据已过帐");
                return false;
            } else{
                seItemID[i] = (selected[i].id);
            }
        }
        return true;
    }
};

/**
 * 删除凭证
 */
Entry.checkRemove = function () {
    seItemID = new Array();
    var selected = $('#' + this.id).bootstrapTreeTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        for(var i=0;i<selected.length;i++){
            if(selected[i].approval_status == '已审核'){/*判断该条数据是否已经为审核数据*/
                Feng.info("已审核数据不支持删除");
                return false;
            }else {
                seItemID[i] = (selected[i].id);
            }
        }
        return true;
    }
};
/**
 * 过账按钮
 */
Entry.openPosting = function (){
    if (this.checkPosting()) {
        var ajax = new $ax(Feng.ctxPath + "/posting/posting", function (data) {
            Feng.success("过账成功!");
            Entry.table.refresh();
        }, function (data) {
            Feng.error("过账失败!" + data.responseJSON.message + "!");
        });
        ajax.set("entryId",JSON.stringify(seItemID));
        ajax.start();
    }
};

/**
 * 点击添加凭证展示
 */
Entry.openAddEntry = function () {
    var index = layer.open({
        type: 2,
        title: '凭证录入',
        area: ['1000px', '636px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/entryGlm/entry_add'
    });
    this.layerIndex = index;
};

/**
 * 打开搜索页面
 */
Entry.openShowSearch = function () {
    var index = layer.open({
        type: 2,
        title: '搜索',
        area: ['760px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/entry/search',
        skin:'layer-div',
    });
    this.layerIndex = index;
};
/**
 * 打卡期末结账页面
 */
Entry.openCheckout = function(){
  var index = layer.open({
     type:2,
     title:'期末结帐',
     area: ['450px', '350px'], //宽高
     fix: false, //不固定
     maxmin: true,
     content: Feng.ctxPath + '/entry/checkout'
  });
  this.layerIndex = index;
};
/**
 * 点击修改凭证展示
 */
Entry.openUpdateEntry = function () {
     var selected = $('#' + this.id).bootstrapTreeTable('getSelections');
     if(selected.length < 1){
         Feng.info("请先选中表格中的某一记录！");
         return false
     }
     var isApproval = selected[0].approval_status;
     if(isApproval != '未审核'){
         Feng.info("已审核数据不能修改！");
         return false;
     }
    var isPosting =  selected[0].posting_status;
    if(isPosting != '未过账'){
        Feng.info("已过账数据不能修改");
        return false;
    }
    var index = layer.open({
        type: 2,
        title: '凭证修改',
        area: ['1000px', '636px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + "/entryGlm/entry_update/"+selected[0].id
    });
    this.layerIndex = index;
};

/**
 * 打开查看凭证展示详情
 */
Entry.openEntryDetail = function (id) {
    var index = layer.open({
        type: 2,
        title: '凭证展示详情',
        area: ['1000px', '636px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/entryGlm/entry_view/' + id

    });
    this.layerIndex = index;
};

/**
 * 清除数据
 */
Entry.clearData = function() {
    this.entryInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
Entry.set = function(key, val) {
    this.entryInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    this.entryInfoData['posting_status'] =($("input[name='posting_status']:checked").val());
    this.entryInfoData['approval_status'] = ($("input[name='approval_status']:checked").val());
    return this;
};
/**
 * 搜索收集数据
 */
Entry.collectData = function() {
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
        .set('dateClass')
        .set('end_original_currency');
};

/**
 * 关闭此对话框
 */
Entry.close = function() {
    layer.close(Entry.layerIndex);
};
/**
 * 审批按钮
 */
Entry.openEntryApproval = function (){
    if (this.checkApproval()) {
        var ajax = new $ax(Feng.ctxPath + "/entry/approval", function (data) {
            Feng.success("审批成功!");
            Entry.table.refresh();
        }, function (data) {
            Feng.error("审批失败!" + data.responseJSON.message + "!");
        });

        ajax.set("entryId",JSON.stringify(seItemID));
        ajax.start();
    }
}

/**
 * 反审按钮
 */
Entry.openEntryReverseApproval = function (){
    if (this.checkreverseApproval()) {
        var ajax = new $ax(Feng.ctxPath + "/entry/reverseApproval", function (data) {
            Feng.success("反审成功!");
            Entry.table.refresh();
        }, function (data) {
            Feng.error("反审失败!" + data.responseJSON.message + "!");
        });
        ajax.set("entryId",JSON.stringify(seItemID));
        ajax.start();
    }
}
/**
 * 删除凭证展示
 */
Entry.openRemove = function () {
    if (this.checkRemove()) {
        var ajax = new $ax(Feng.ctxPath + "/entry/delete", function (data) {
            Feng.success("删除成功!");
            Entry.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("entryId",JSON.stringify(seItemID));
        ajax.start();
    }
};


/**
 * 查询凭证展示列表
 */
Entry.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Entry.table.refresh({query: queryData});
};
/**
 * 点击弹出结转损益页面
 */
Entry.selectByParentList = function(){
    var index = layer.open({
        type: 2,
        title: '结转损益',
        area: ['800px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/entryGlm/carryOver'
    });
    this.layerIndex = index;
}

/**
 *数据类型
 */
Entry.openDateClass = function () {
    this.clearData();
    this.collectData();
    if(this.entryInfoData.dateClass == "1"){
        $("#approval").removeAttr("disabled");
        $("#posting").attr("disabled","true");
    }else {
        $("#posting").removeAttr("disabled");
        $("#approval").attr("disabled","true");
    }
    Entry.table.refresh({query:this.entryInfoData});
};
/**
 * table加载列表
 */
$(function () {
    $("#posting").attr("disabled","true");
    var defaultColunms = Entry.initColumn();
    var table = new BSTreeTable(Entry.id, "/entry/list", defaultColunms);
    table.setExpandColumn(2);
    table.setIdField("id");
    table.setCodeField("code");
    table.setParentCodeField("pcode");
    table.setExpandAll(false);
    table.init();
    Entry.table = table;
});