/**
 * 审核权限设置管理初始化
 */
var AuditPosting = {
    id: "AuditPostingTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
AuditPosting.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '制单人', field: 'preparedBy', visible: true, align: 'center', valign: 'middle'},
        {title: '过账人', field: 'postingPeople', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
AuditPosting.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        AuditPosting.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加审核权限设置
 */
AuditPosting.openAddAuditPosting = function () {
    var size=$(":input[name='btSelectItem']").length;
    console.log(size);
    if(size==0){
        var index = layer.open({
            type: 2,
            title: '添加审核权限设置',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/auditpermissions/auditPosting/AuditPosting_add'
        });
        this.layerIndex = index;
    }else{
        Feng.info("一个人只能配置一条");
    }

};

/**
 * 打开查看审核权限设置详情
 */
AuditPosting.openAuditPostingDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '审核权限设置详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/auditpermissions/auditPosting/AuditPosting_update/' + AuditPosting.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除审核权限设置
 */
AuditPosting.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/auditpermissions/auditPosting/delete", function (data) {
            Feng.success("删除成功!");
            AuditPosting.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("auditpermissionsId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询审核权限设置列表
 */
AuditPosting.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    AuditPosting.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = AuditPosting.initColumn();
    var table = new BSTable(AuditPosting.id, "/auditpermissions/auditPosting/list", defaultColunms);
    table.setPaginationType("client");
    AuditPosting.table = table.init();
});
