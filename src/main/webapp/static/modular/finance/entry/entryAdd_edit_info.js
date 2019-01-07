/**
 * 初始化凭证展示详情对话框
 */
var EntryInfoDlgEdit = {
    entryInfoData : {}
};

EntryInfoDlgEdit.EntryScheduleData = [];
EntryInfoDlgEdit.entryInitial = [];//凭证副表初始id
EntryInfoDlgEdit.entryAfter = [] ;//凭证副表提交后的id

/**
 * 清除数据
 */
EntryInfoDlgEdit.clearData = function() {
    this.entryInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
EntryInfoDlgEdit.set = function(key, val) {
    this.entryInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
EntryInfoDlgEdit.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
EntryInfoDlgEdit.close = function() {
    parent.layer.close(window.parent.Entry.layerIndex);
};

/**
 * 收集数据
 */
EntryInfoDlgEdit.collectData = function() {
    var trList = $("#subjectTable").children("tbody").children("tr");
    for (var i=0;i<trList.length;i++) {
        EntryInfoDlgEdit.EntrySchedule = {
            id:null,
            summary:null,
            expenseAccount:null,
            debitAmount:null,
            creditAmount:null,
            contactsDnit:null,
            department:null,
            staff:null
        };
        var tdArr = trList.eq(i).find("td");
        var expense_account = tdArr.eq(0).find('input').val();//会计科目
        var summary = tdArr.eq(1).find('textarea').val();//摘要
        var debit_amount = tdArr.eq(2).find('input').val();//  借方金额
        var credit_amount = tdArr.eq(3).find('input').val();//  贷方金额
        var inputArr = trList.eq(i).find("input");
        var contactsDnit = inputArr.eq(3).val();// 往来单位核算
        var department = inputArr.eq(4).val(); //部门
        var staff = inputArr.eq(5).val();//员工
        var Sid = inputArr.eq(6).val();//id
        EntryInfoDlgEdit.EntrySchedule.summary=summary;
        EntryInfoDlgEdit.EntrySchedule.expenseAccount=expense_account;
        EntryInfoDlgEdit.EntrySchedule.debitAmount=debit_amount;
        EntryInfoDlgEdit.EntrySchedule.creditAmount=credit_amount;
        EntryInfoDlgEdit.EntrySchedule.contactsDnit=contactsDnit;
        EntryInfoDlgEdit.EntrySchedule.department=department;
        EntryInfoDlgEdit.EntrySchedule.staff=staff;
        EntryInfoDlgEdit.EntrySchedule.id=Sid;
        EntryInfoDlgEdit.EntryScheduleData[i]=EntryInfoDlgEdit.EntrySchedule;
    }
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
        .set('settlementMouthState')
        .set('lumpSum')
        .set('remarks')
        .set('period')
        .set('entrySchedule',EntryInfoDlgEdit.EntryScheduleData)
    ;
};

/**
 * 多核算弹出框
 */
EntryInfoDlgEdit.otherAccounting = function() {
    var other = $("#other").val();
    if(other == '2' ){
        var index = layer.open({
            type: 2,
            title: '多核算',
            area: ['400px', '300px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/entryGlm/otherAccounting'
        });
        this.layerIndex = index;
    }
}

/**
 * 提交修改
 */
EntryInfoDlgEdit.editSubmit = function() {
    var ii = layer.load();
    this.clearData();
    this.collectData();
    //获取修改后的id数组
    var trList = $("#subjectTable").children("tbody").children("tr");
    var id = 0;
    for (var i=0;i<trList.length;i++) {
        id = EntryInfoDlgEdit.EntryScheduleData[i].id
        if(id != undefined && id != null){
            EntryInfoDlgEdit.entryAfter[i] = id;
        }
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/entryGlm/update", function(data){
        layer.close(ii);
        Feng.success("修改成功!");
        window.parent.Entry.table.refresh();
        EntryInfoDlgEdit.close();

    },function(data){
        layer.close(ii);
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set("entry",JSON.stringify(this.entryInfoData));
    ajax.set("entryInitial",JSON.stringify(EntryInfoDlgEdit.entryInitial));
    ajax.set("entryAfter",JSON.stringify(EntryInfoDlgEdit.entryAfter));
    ajax.start();
    layer.close(ii);
};

/**
 * 借贷金额只能输入一方，且借方金额求和
 */
$("input[name='debitAmount']").bind("input change", function () {
    var next = $(this).parent("td").next().children().val();//获取当前input的兄弟节点，用于只能输入借贷一方
    if(next!=null&&next!==""){//限制借贷金额只能输入一方
        $(this).val("");
        $(this).css("disabled","disabled");
    }else{
        var sum = 0;
        $("input[name='debitAmount']").each(function () {//累加求和
            var num = $(this).val();
            sum += Number(num) ;
        });
        sum.toFixed(2);
        $("#debitAmountAll").val(sum);
        $("#lumpSum").val(sum);//赋值总金额
    }
});
/**
 * 限制借贷金额只能输入一方，且进行贷方金额求和
 */
$("input[name='creditAmount']").bind("input change", function () {
    var prev = $(this).parent("td").prev().children().val();//获取当前input的兄弟节点，用于只能输入借贷一方
    if(prev!=null&&prev!==""){//限制借贷金额只能输入一方
        $(this).val("");
        $(this).css("disabled","disabled");
    }else{
        var sum = 0;
        $("input[name='creditAmount']").each(function () {//累加求和
            var num = $(this).val();
            sum += Number(num) ;
        });
        sum.toFixed(2);
        $("#creditAmountAll").val(sum);//赋值总金额
        if($("#debitAmountAll").val()!=null&&$("#debitAmountAll").val()!=""&&$("#debitAmountAll").val()>sum){
            $("#lumpSum").val($("#debitAmountAll").val());
        }else{
            $("#lumpSum").val(sum);
        }
    }
});
/**
 * 点击平衡方法
 */
EntryInfoDlgEdit.balance = function () {
    var debitAmountAll = $("#debitAmountAll").val();//借方金额合计
    var creditAmountAll = $("#creditAmountAll").val();//贷方金额合计
    if(Number(debitAmountAll)!=Number(creditAmountAll)){//当借贷不平衡时，触发平衡方法
        var expenseAccountLength = $("input[name='expenseAccount']").length;
        var subtraction = Number(debitAmountAll)-Number(creditAmountAll);
        if(subtraction>0){
            for (var i= expenseAccountLength; i>=0; i--){
                var creditAmount = $("input[name='creditAmount']").eq(i).val();
                if(creditAmount!=null&&creditAmount!=""){
                    $("input[name='creditAmount']").eq(i).val(Number(subtraction)+Number(creditAmount));
                    $("#creditAmountAll").val(debitAmountAll);
                    $("#lumpSum").val(debitAmountAll);
                    break;
                }
            }
        }else{
            var cd = Number(creditAmountAll)-Number(debitAmountAll);//贷方金额高于借方金额时
            for (var i= expenseAccountLength; i>=0; i--){
                var debitAmount = $("input[name='debitAmount']").eq(i).val();
                if(debitAmount!=null&&debitAmount!=""){
                    $("input[name='debitAmount']").eq(i).val(Number(cd)+Number(debitAmount));
                    $("#debitAmountAll").val(creditAmountAll);
                    $("#lumpSum").val(creditAmountAll);
                    break;
                }
            }
        }
    }
}
/**
 * 点击插入单元格方法
 */
EntryInfoDlgEdit.stick = function () {
    setTimeout(function(){
        var html = "<tr style='border-bottom: 1px #ccc solid'>";
        html += "<td onclick='Subject.openAddSubject(this)' style='height: 35px;'><input type=\"hidden\" name=\"expenseAccount\" onblur=\"EntryInfoDlg.blurVal(this)\"><input style='width:100%;height: 40px;border: none;overflow: hidden; resize:none;line-height: 40px;' placeholder='请选择'onblur='EntryInfoDlg.blurVal(this)'></td>";
        html += "<td style='height: 40px;'><input  maxlength='20' style='width:100%;height:45px;border: 0px;padding: 0 5px;'placeholder='不得多余20字' onblur='EntryInfoDlg.blurVal(this)'></td>";
        html += "<td style='height: 40px;'><input onkeyup='value=value.replace(/[^\d]/g,'.')' maxlength='10' style='width:100%;height:45px;border: 0px;padding: 0 5px;' placeholder='0.00' type='text' name='debitAmount' onblur='EntryInfoDlg.blurVal(this)'></td>";
        html += "<td style='height: 40px;'><input onkeyup='value=value.replace(/[^\d]/g,'.')' maxlength='10' style='width:100%;height:45px;border: 0px;padding: 0 5px;' placeholder='0.00' type='text' name='creditAmount' onblur='EntryInfoDlg.blurVal(this)'></td>";
        html +="<input type='hidden' name='contacts_dnit' value=''/>" ;
        html +="<input type='hidden' name='department' value=''/><input type='hidden' name='staff' value=''/>";
        html += "</tr>";
        EntryInfoDlgEdit.cellObject.parent("td").parent("tr").after(html);
        $("input[name='debitAmount']").bind("input change", function () {
            var next = $(this).parent("td").next().children().val();
            if(next!=null&&next!==""){
                $(this).val("");
                $(this).css("disabled","disabled");
            }else{
                var sum = 0;
                $("input[name='debitAmount']").each(function () {
                    var num = $(this).val();
                    sum += Number(num) ;
                });
                sum.toFixed(2);
                $("#debitAmountAll").val(sum);
                $("#lumpSum").val(sum);
            }
        });
        $("input[name='creditAmount']").bind("input change", function () {
            var prev = $(this).parent("td").prev().children().val();
            if(prev!=null&&prev!==""){
                $(this).val("");
                $(this).css("disabled","disabled");
            }else{
                var sum = 0;
                $("input[name='creditAmount']").each(function () {
                    var num = $(this).val();
                    sum += Number(num) ;
                });
                sum.toFixed(2);
                $("#creditAmountAll").val(sum);
                if($("#debitAmountAll").val()!=null&&$("#debitAmountAll").val()!=""){
                    $("#lumpSum").val($("#debitAmountAll").val());
                }else{
                    $("#lumpSum").val(sum);
                }
            }
        });
    },200);

};
/**
 * 点击删除当前单元格方法
 */
EntryInfoDlgEdit.outCell = function () {
    setTimeout(function(){
        EntryInfoDlgEdit.cellObject.parent("td").parent("tr").remove();
    },200);
};

EntryInfoDlgEdit.blurVal = function(value){
    EntryInfoDlgEdit.cellObject = $(value);
};
/**
 * 凭证字改变，重新查询凭证号，赋值
 */
EntryInfoDlgEdit.documentChange = function (value) {
    var ajax = new $ax(Feng.ctxPath + "/entryGlm/documentChange/"+value, function(data){
        $("#documentNumber").val(data.documentNumber);
    },function(data){
        Feng.error("错误!" + data.responseJSON.message + "!");
    });
    ajax.set("value",JSON.stringify(value));
    ajax.start();
};

/**
 * 加载数据
 */
$(function () {
    //给凭证字下拉框赋值
    var selectValue = $("#hideDocumentWord").val();
    $("#documentWord").find("option[value="+selectValue+"]").attr("selected",true);
    //计算借方总金额和贷方总金额
    var trs = $($("#subjectTable").children()[1]).children(); //获取行集合
    var trLength = $(trs).length;
    var debitAmountAll = 0;
    var creditAmountAll = 0;
    var debitAmount = 0;
    var creditAmoun = 0;
    var sId = 0;
    for(var i=0;i<trLength;i++){
        debitAmount = $($($(trs[i]).find("td")[2]).children()).val(); //获取借方金额值
        creditAmoun = $($($(trs[i]).find("td")[3]).children()).val(); //获取贷方金额值
        sId = $($(trs[i]).find("input")[6]).val(); //获取初始id值
        if(debitAmount != undefined && debitAmount != null){
            debitAmountAll = Number(debitAmountAll) + Number(debitAmount); //计算借方总金额
        }
        if(creditAmoun != undefined && creditAmoun != null){
            creditAmountAll = Number(creditAmountAll) + Number(creditAmoun); //计算贷方总金额
        }
        if(sId != undefined && sId != null){
            EntryInfoDlgEdit.entryInitial[i]=sId;//将初始id存入数组
        }
    }
    $("#debitAmountAll").val(debitAmountAll);
    $("#creditAmountAll").val(creditAmountAll);

});

