var Checkout = {
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};
/**
 * 前进
 */
Checkout.closingNoteForward = function(){
    $("#closingNote").hide();//隐藏提示框
    $("#checkoutFunction").show();
};
/**
 * 提示语
 */
Checkout.markedWords = function(){
    Feng.info("年结账过后，本年所有凭证信息不在支持然后操作，请慎重!");
}
/**
 * 取消
 */
Checkout.cancel = function () {
    parent.layer.close(window.parent.Entry.layerIndex);
};
/**
 * 反结账
 */
Checkout.antiSettlement = function(){
    $("#period").show();
};
/**
 * 提交
 */
Checkout.submitCheckout = function () {
    var period = $("#period").val();
    var state = $("input[name='checkout']:checked").val();
    var ajax = new $ax(Feng.ctxPath + "/entry/checkoutFunction/", function(data){
        if(data.code == 201){
            Feng.info(data.message + "!");
        }else if(data.code == 202){
            Feng.info(data.message + "!");
            parent.layer.close(window.parent.Entry.layerIndex);
        }else if(data.code == 200){
            Feng.success(data.message + "!");
            parent.layer.close(window.parent.Entry.layerIndex);
            window.parent.Entry.table.refresh();
        }
    },function(data){
        Feng.error("错误!" + data.message + "!");
    });
    ajax.set("state",state);
    ajax.set("period",period);
    ajax.start();
};