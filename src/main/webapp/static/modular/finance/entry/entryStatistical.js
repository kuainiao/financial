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
        {title: '业务日期', field: 'business_date', align: 'center', valign: 'middle', sortable: true, width: '100px'},
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
 * 打开搜索页面
 */
Entry.openShowSearch = function () {
    var index = layer.open({
        type: 2,
        title: '搜索',
        area: ['650px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/entry/search'
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
 * 查询凭证展示列表
 */
Entry.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Entry.table.refresh({query: queryData});
};

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
    var table = new BSTreeTable(Entry.id, "/entry/statistical", defaultColunms);
    table.setExpandColumn(2);
    table.setIdField("id");
    table.setCodeField("code");
    table.setParentCodeField("pcode");
    table.setExpandAll(false);
    table.init();
    Entry.table = table;
});

/**
 * 全选事件
 */
function selectAll() {
    var _ipt = $("#EntryTable").find("input[name='select_item']");
    var isCheck = $("input[name='selectAll']").is(':checked');
    if(isCheck){
        _ipt.prop('checked', true);
    }else {
        _ipt.prop('checked', false);
    }
}