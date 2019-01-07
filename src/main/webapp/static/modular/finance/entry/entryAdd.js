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
 * 初始化表格的列
 */
Entry.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '业务日期', field: 'businessDate', visible: true, align: 'center', valign: 'middle'},
            {title: '顺序号', field: 'sequenceNumber', visible: true, align: 'center', valign: 'middle'},
            {title: '凭证字', field: 'documentWord', visible: true, align: 'center', valign: 'middle'},
            {title: '凭证号', field: 'documentNumber', visible: true, align: 'center', valign: 'middle'},
            {title: '单据数', field: 'documentsNumber', visible: true, align: 'center', valign: 'middle'},
            {title: '制单人', field: 'preparedBy', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
            {title: '结算状态', field: 'settlementState', visible: true, align: 'center', valign: 'middle'},
            {title: '总金额', field: 'lumpSum', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Entry.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Entry.seItem = selected[0];
        return true;
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
 * 打开查看凭证展示详情
 */
Entry.openEntryDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '凭证展示详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/entryGlm/entry_update/' + Entry.seItem.id
        });
        this.layerIndex = index;
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

$(function () {
    var defaultColunms = Entry.initColumn();
    var table = new BSTable(Entry.id, "/entryGlm/list", defaultColunms);
    table.setPaginationType("client");
    Entry.table = table.init();
});
