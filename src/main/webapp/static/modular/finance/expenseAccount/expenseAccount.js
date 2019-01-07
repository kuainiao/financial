/**
 * 费用科目管理初始化
 */
var ExpenseAccount = {
    id: "ExpenseAccountTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    deptid:0
};

/**
 * 初始化表格的列
 */
ExpenseAccount.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            //{title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '科目代码', field: 'accountCode', visible: true, align: 'center', valign: 'middle',formatter:function (value, row, index) {
                    return value+"<input type='hidden' name='ownCode' value='"+value+"'>";
                }},
            {title: '科目名称', field: 'subiectName', visible: true, align: 'center', valign: 'middle',formatter:ExpenseAccount.operateFormatter},
            {title: '上级代码', field: 'superiorCode', visible: true, align: 'center', valign: 'middle',formatter:function (value, row, index) {
                    return value+"<input type='hidden' name='code' value='"+value+"'>";
                }},
            {title: '科目类别名称', field: 'accountCategory', visible: true, align: 'center', valign: 'middle'},
            //{title: '科目数据类型', field: 'costType',        visible:true,  align:'center',  valign:'middle',
            //    formatter: function (value, row, index) {
            //        if(value=='0'){
            //                  return "贸易";
            //              }else if(value == '1'){
            //                  return "非贸易";
            //              }else{
            //                  return '暂未分类';
            //              }
            //          }
            //      },
            {title: '余额方向', field: 'balanceDirection', visible: true, align: 'center', valign: 'middle'},
            //{title: '辅助核算 1 单一 2多核', field: 'otherAccounting', visible: true, align: 'center', valign: 'middle'},
            //{title: '往来单位核算', field: 'contactsDnit', visible: true, align: 'center', valign: 'middle'},
            //{title: '部门核算', field: 'department', visible: true, align: 'center', valign: 'middle'},
            //{title: '职员核算', field: 'staff', visible: true, align: 'center', valign: 'middle'},
            //{title: '本条数据状态 0 显示 1删除', field: 'status', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ExpenseAccount.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ExpenseAccount.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加费用科目
 */
ExpenseAccount.openAddExpenseAccount = function () {
    var code = $("input[name='code']:first").val();
    if(typeof code == "undefined"){
        code = $("#ownCode").val();
    }
    var index = layer.open({
        type: 2,
        title: '添加费用科目',
        area: ['500px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/expenseAccount/expenseAccount_add/' + code
    });
    this.layerIndex = index;
};

/**
 * 打开查看费用科目详情
 */
ExpenseAccount.openExpenseAccountDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '费用科目详情',
            area: ['500px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/expenseAccount/expenseAccount_update/' + ExpenseAccount.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除费用科目
 */
ExpenseAccount.delete = function () {
    if (this.check()) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/expenseAccount/delete", function (data) {
                Feng.success("删除成功!");
                ExpenseAccount.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("expenseAccountId",ExpenseAccount.seItem.id);
            ajax.start();
        }
        Feng.confirm("确认删除当前费用科目？",operation);
    }
};

ExpenseAccount.operateFormatter = function (value, row, index) {
    var btSelectItem = row.id;
    return "<a href='javascript:;' onclick='ExpenseAccount.lowerLevel("+btSelectItem+","+index+")'>"+value+"</a>";
}
/**
 * 点击进入下一层
 * @param value
 */
ExpenseAccount.lowerLevel = function (value,index) {
    var code = $("input[name='code']:first").val();
    var ownCode = $("input[name='ownCode']:eq("+index+")").val();
    $("#code").val(code);
    $("#ownCode").val(ownCode);
    var queryData = {};
    queryData['accountCode'] = null;
    queryData['condition'] = value;
    ExpenseAccount.table.refresh({query: queryData});
}
/**
 * 返回上一层
 */
ExpenseAccount.previous = function () {
    var code = $("input[name='code']:first").val();
    /*var value = $("#code").val();
    if(typeof code=="undefined"){
        code = value;
    }*/
    $("#code").val(code);
    var queryData = {};
    queryData['condition'] = null;
    queryData['accountCode'] = code;
    ExpenseAccount.table.refresh({query: queryData});
};

ExpenseAccount.search = function () {
    var queryData = {};
    queryData['deptid'] = ExpenseAccount.deptid;
    ExpenseAccount.table.refresh({query: queryData});
}

ExpenseAccount.onClickDept = function (e, treeId, treeNode) {
    ExpenseAccount.deptid = treeNode.id;
    ExpenseAccount.search();
};

$(function () {
    var defaultColunms = ExpenseAccount.initColumn();
    var table = new BSTable(ExpenseAccount.id, "/expenseAccount/list", defaultColunms);
    table.setPaginationType("client");
    ExpenseAccount.table = table.init();
    var ztree = new $ZTree("deptTree", "/expenseAccount/treeEa");
    ztree.bindOnClick(ExpenseAccount.onClickDept);
    ztree.init();
});