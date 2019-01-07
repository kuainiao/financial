/**
 * 初始化审核权限设置详情对话框
 */
var AuditpermissionsInfoDlg = {
    auditpermissionsInfoData : {}
};

/**
 * 清除数据
 */
AuditpermissionsInfoDlg.clearData = function() {
    this.auditpermissionsInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AuditpermissionsInfoDlg.set = function(key, val) {
    this.auditpermissionsInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}
/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AuditpermissionsInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
AuditpermissionsInfoDlg.close = function() {
    parent.layer.close(window.parent.Auditpermissions.layerIndex);
}

/**
 * 收集数据
 */
AuditpermissionsInfoDlg.collectData = function() {
    this
    .set('id')
    .set('preparedBy')
    .set('approvalPeople');
}

/**
 * 提交添加
 */
AuditpermissionsInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/auditpermissions/add", function(data){
        Feng.success("添加成功!");
        window.parent.Auditpermissions.table.refresh();
        AuditpermissionsInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.auditpermissionsInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
AuditpermissionsInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/auditpermissions/update", function(data){
        Feng.success("修改成功!");
        window.parent.Auditpermissions.table.refresh();
        AuditpermissionsInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.auditpermissionsInfoData);
    ajax.start();
}

$(function () {
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/auditpermissions/prepared_by_list", function(data){
       for (var i=0 ; i<data.name.length;i++){
           $("#approvalPeople").append("<option value='"+data.name[i]+"'>"+data.name[i]+"</option>");
       }
    },function(data){
        Feng.error("获取员工名失败!" + data.responseJSON.message + "!");
    });
    ajax.set(null);
    ajax.start();
});
