/**
 * 初始化审核权限设置详情对话框
 */
var AuditPostingInfoDlg = {
    AuditPostingInfoData : {}
};

/**
 * 清除数据
 */
AuditPostingInfoDlg.clearData = function() {
    this.AuditPostingInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AuditPostingInfoDlg.set = function(key, val) {
    this.AuditPostingInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}
/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AuditPostingInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
AuditPostingInfoDlg.close = function() {
    parent.layer.close(window.parent.AuditPosting.layerIndex);
}

/**
 * 收集数据
 */
AuditPostingInfoDlg.collectData = function() {
    this
        .set('id')
        .set('preparedBy')
        .set('postingPeople');

}

/**
 * 提交添加
 */
AuditPostingInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/auditpermissions/auditPosting/add", function(data){
        Feng.success("添加成功!");
        window.parent.AuditPosting.table.refresh();
        AuditPostingInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.AuditPostingInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
AuditPostingInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/auditpermissions/auditPosting/update", function(data){
        Feng.success("修改成功!");
        window.parent.AuditPosting.table.refresh();
        AuditPostingInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.AuditPostingInfoData);
    ajax.start();
}

$(function () {
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/auditpermissions/prepared_by_list", function(data){
        for (var i=0 ; i<data.name.length;i++){
            $("#postingPeople").append("<option value='"+data.name[i]+"'>"+data.name[i]+"</option>");
        }
    },function(data){
        Feng.error("获取员工名失败!" + data.responseJSON.message + "!");
    });
    ajax.set(null);
    ajax.start();
});
