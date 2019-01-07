/**
 * 初始化凭证展示详情对话框
 */
var EntryInfoDlg = {
    entryInfoData : {}
};

/**
 * 清除数据
 */
EntryInfoDlg.clearData = function() {
    this.entryInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
EntryInfoDlg.set = function(key, val) {
    this.entryInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
EntryInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
EntryInfoDlg.close = function() {
    parent.layer.close(window.parent.Entry.layerIndex);
}

/**
 * 收集数据
 */
EntryInfoDlg.collectData = function() {
    this
    .set('id')
    .set('businessDate')
    .set('sequenceNumber')
    .set('documentWord')
    .set('documentNumber')
    .set('documentsNumber')
    .set('preparedBy')
    .set('createTime')
    .set('settlementState')
    .set('lumpSum')
    .set('remarks');
}

/**
 * 提交添加
 */
EntryInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/entry/add", function(data){
        Feng.success("添加成功!");
        window.parent.Entry.table.refresh();
        EntryInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.entryInfoData);
    ajax.start();
}
/**
 * 结转损益
 */
EntryInfoDlg.varryOver = function(){
    this.clearData();
    this.collectData();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/entryGlm/profitLoss", function(data){
        Feng.success("结转成功!");
        window.parent.Entry.table.refresh();
        EntryInfoDlg.close();
    },function(data){
        Feng.error("结转失败!" + data.responseJSON.message + "!");
    });
    ajax.set("entry",JSON.stringify(this.entryInfoData));
    ajax.start();
}

/**
 * 提交修改
 */
EntryInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/entry/update", function(data){
        Feng.success("修改成功!");
        window.parent.Entry.table.refresh();
        EntryInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.entryInfoData);
    ajax.start();
}

$(function() {

});
