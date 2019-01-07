/**
 * 审核权限设置管理初始化
 */
var Auditpermissions = {
    id: "AuditpermissionsTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Auditpermissions.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '制单人', field: 'preparedBy', visible: true, align: 'center', valign: 'middle'},
            {title: '审核人', field: 'approvalPeople', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Auditpermissions.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Auditpermissions.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加审核权限设置
 */
Auditpermissions.openAddAuditpermissions = function () {
  var size=$(":input[name='btSelectItem']").length;
  console.log(size);
    if(size==0){
        var index = layer.open({
            type: 2,
            title: '添加审核权限设置',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/auditpermissions/auditpermissions_add'
        });
        this.layerIndex = index;
    }else{
        Feng.info("一个人只能配置一条");
    }

};

/**
 * 打开查看审核权限设置详情
 */
Auditpermissions.openAuditpermissionsDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '审核权限设置详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/auditpermissions/auditpermissions_update/' + Auditpermissions.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除审核权限设置
 */
Auditpermissions.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/auditpermissions/delete", function (data) {
            Feng.success("删除成功!");
            Auditpermissions.table.refresh();
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
Auditpermissions.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Auditpermissions.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Auditpermissions.initColumn();
    var table = new BSTable(Auditpermissions.id, "/auditpermissions/list", defaultColunms);
    table.setPaginationType("client");
    Auditpermissions.table = table.init();
});
