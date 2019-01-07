/**
 * 初始化费用科目详情对话框
 */
var ExpenseAccountInfoDlg = {
    expenseAccountInfoData : {}
};

/**
 * 清除数据
 */
ExpenseAccountInfoDlg.clearData = function() {
    this.expenseAccountInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ExpenseAccountInfoDlg.set = function(key, val) {
    this.expenseAccountInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ExpenseAccountInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ExpenseAccountInfoDlg.close = function() {
    parent.layer.close(window.parent.ExpenseAccount.layerIndex);
}

/**
 * 收集数据
 */
ExpenseAccountInfoDlg.collectData = function() {
    this
    .set('id')
    .set('accountCode')
    .set('subiectName')
    .set('superiorCode')
    .set('accountCategory')
    .set('costType',$("input[name='costType']:checked").val())
    .set('balanceDirection',$("input[name='balanceDirection']:checked").val())
    .set('otherAccounting',$("input[name='otherAccounting']:checked").val())
    .set('contactsDnit',typeof $("#contactsDnit:checked").val()=="undefined"?"0":"1")
    .set('department',typeof $("#department:checked").val()=="undefined"?"0":"1")
    .set('staff',typeof $("#staff:checked").val()=="undefined"?"0":"1")
    .set('status');
}

/**
 * 提交添加
 */
ExpenseAccountInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/expenseAccount/add", function(data){
        Feng.success("添加成功!");
        window.parent.ExpenseAccount.table.refresh();
        ExpenseAccountInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.expenseAccountInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ExpenseAccountInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/expenseAccount/update", function(data){
        Feng.success("修改成功!");
        window.parent.ExpenseAccount.table.refresh();
        ExpenseAccountInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.expenseAccountInfoData);
    ajax.start();
}

$(function() {
    $("input[name='otherAccounting']").change(function () {
        if($("input[name='otherAccounting']:checked").val()==2){
            $("#contactsDnit").prop('checked', 'checked');
            $("#department").prop('checked', 'checked');
            $("#staff").prop('checked', 'checked');
            $("#contactsDnit").removeAttr("disabled");
            $("#department").removeAttr("disabled");
            $("#staff").removeAttr("disabled");
        }else{
            $("#contactsDnit").removeAttr('checked');
            $("#department").removeAttr('checked');
            $("#staff").removeAttr('checked');
            $("#contactsDnit").attr("disabled","disabled");
            $("#department").attr("disabled","disabled");
            $("#staff").attr("disabled","disabled");
        }
    });
});


