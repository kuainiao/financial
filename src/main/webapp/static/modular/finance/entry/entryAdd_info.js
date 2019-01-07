/**
 * 初始化凭证展示详情对话框
 */
var EntryInfoDlg = {
    entryInfoData : {}
};

EntryInfoDlg.EntryScheduleData = [];

EntryInfoDlg.cellObject = "";

/**
 * 清除数据
 */
EntryInfoDlg.clearData = function() {
    this.entryInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
EntryInfoDlg.set = function(key, val) {
    this.entryInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
EntryInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
EntryInfoDlg.close = function() {
    parent.layer.close(window.parent.Entry.layerIndex);
};

/**
 * 收集数据
 */
EntryInfoDlg.collectData = function() {
    /*$("input[name='expense_account']").each(function(){
        if($(this).val()!=""&&$(this).val()!=null){
            EntryInfoDlg.EntrySchedule.expenseAccount($(this).val());
        }
    });*/
    var trList = $("#subjectTable").children("tbody").children("tr");
    for (var i=0;i<trList.length;i++) {
        EntryInfoDlg.EntrySchedule = {
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
        var summary = tdArr.eq(1).find('input').val();//摘要
        var debit_amount = tdArr.eq(2).find('input').val();//  借方金额
        var credit_amount = tdArr.eq(3).find('input').val();//  贷方金额
        var inputArr = trList.eq(i).find("input");
        var contactsDnit = inputArr.eq(3).val();// 往来单位核算
        var department = inputArr.eq(4).val(); //部门
        var staff = inputArr.eq(5).val();//员工
        EntryInfoDlg.EntrySchedule.summary=summary;
        EntryInfoDlg.EntrySchedule.expenseAccount=expense_account;
        EntryInfoDlg.EntrySchedule.debitAmount=debit_amount;
        EntryInfoDlg.EntrySchedule.creditAmount=credit_amount;
        EntryInfoDlg.EntrySchedule.contactsDnit=contactsDnit;
        EntryInfoDlg.EntrySchedule.department=department;
        EntryInfoDlg.EntrySchedule.staff=staff;
        EntryInfoDlg.EntryScheduleData[i]=EntryInfoDlg.EntrySchedule;
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
    .set('entrySchedule',EntryInfoDlg.EntryScheduleData)
    ;
};
/**
 * 费用科目
 */
EntryInfoDlg.openAddSubject = function () {
    var index = layer.open({
        type: 2,
        title: '费用科目',
        area: ['600px', '400px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/expenseAccount/expenseAccount_subject'
    });
    this.layerIndex = index;
};

/**
 * 多核算弹出框
 */
EntryInfoDlg.otherAccounting = function() {
    var other = $("#other").val();
    if(other == '2' ){
        var index = layer.open({
            type: 2,
            title: '多核算',
            area: ['500px', '400px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/entryGlm/otherAccounting'
        });
        this.layerIndex = index;
    }
}
/**
 * 提交添加
 */
EntryInfoDlg.addSubmit = function() {
    this.clearData();
    this.collectData();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/entryGlm/add", function(data){
        Feng.success("添加成功!");
        window.parent.Entry.table.refresh();
        EntryInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set("entry",JSON.stringify(this.entryInfoData));
    ajax.start();
};

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
EntryInfoDlg.balance = function () {
    console.log(event);
    var debitAmountAll = $("#debitAmountAll").val();//借方金额合计
    var creditAmountAll = $("#creditAmountAll").val();//贷方金额合计
    if(Number(debitAmountAll)!=Number(creditAmountAll)){//当借贷不平衡时，触发平衡方法
        var expenseAccountLength = $("input[name='expenseAccount']").length;//获取费用科目处长度
        var subtraction = Number(debitAmountAll)-Number(creditAmountAll);//获取两边总金额相差数
        if(subtraction>0){//当借方金额大于贷方金额时触发
            for (var i= expenseAccountLength; i>=0; i--){
                var creditAmount = $("input[name='creditAmount']").eq(i).val();//获取所有借方金额节点
                if(creditAmount!=null&&creditAmount!=""){
                    $("input[name='creditAmount']").eq(i).val(Number(subtraction)+Number(creditAmount));//改变借方金额值，使借贷平衡
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
EntryInfoDlg.stick = function () {
    setTimeout(function(){
        var html = "<tr style='border-bottom: 1px #ccc solid'>";//创建tr元素，用于插入
        html += "<td onclick='Subject.openAddSubject(this)' style='height: 40px;'><input style='width:100%;height:40px;border: 0px;' type='hidden' name='expenseAccount' onblur='EntryInfoDlg.blurVal(this)'><input style='width:100%;height: 45px;border: none;resize:none;' placeholder='请选择' onblur=\"EntryInfoDlg.blurVal(this)\"></td>";
        html += "<td style='height: 40px;'><input style='width:100%;height:45px;border: 0px;padding: 0 5px;' name='summary' placeholder='不得多于20字' onblur='EntryInfoDlg.blurVal(this)'/></td>";
        html += "<td style='height: 40px;'><input onkeyup='value=value.replace(/[^\\d]/g,"+"."+")' maxlength='10' style='width:100%;height:45px;border: 0px;padding: 0 5px;' placeholder='0.00' type='text' name='debitAmount' onblur='EntryInfoDlg.blurVal(this)'></td>";
        html += "<td style='height: 40px;'><input onkeyup='value=value.replace(/[^\\d]/g,"+"."+")' maxlength='10' style='width:100%;height:45px;border: 0px;padding: 0 5px;' placeholder='0.00' type='text' name='creditAmount' onblur='EntryInfoDlg.blurVal(this)'></td>";
        html +="<input type='hidden' name='contacts_dnit' value=''/>" ;
        html +="<input type='hidden' name='department' value=''/><input type='hidden' name='staff' value=''/>";
        html += "</tr>";
        EntryInfoDlg.cellObject.parent("td").parent("tr").after(html);//将创建的tr插入进去
        //重新触发累加方法
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
}
/**
 * 点击删除当前单元格方法
 */
EntryInfoDlg.outCell = function () {
    setTimeout(function(){
        EntryInfoDlg.cellObject.parent("td").parent("tr").remove();
    },200);
}
/**
 * 点击获取当前元素节点并储存起来，用于新增或者删除单元格
 * @param value
 */
EntryInfoDlg.blurVal = function(value){
    EntryInfoDlg.cellObject = $(value);
}
/**
 * 凭证字改变，重新查询凭证号，赋值
 */
EntryInfoDlg.documentChange = function (value) {
    var ajax = new $ax(Feng.ctxPath + "/entryGlm/documentChange/"+value, function(data){
        $("#documentNumber").val(data.documentNumber);//将获取到的凭证号赋值
    },function(data){
        Feng.error("错误!" + data.responseJSON.message + "!");
    });
    ajax.set("value",JSON.stringify(value));
    ajax.start();
}


$(function() {
    var timestamp = new Date();
    var year = timestamp.getFullYear(); //获取完整的年份(4位,1970-????)
    var mouth = timestamp.getMonth(); //获取当前月份(0-11,0代表1月)
    var day =  timestamp.getDate(); //获取当前日(1-31)
    $("#businessDate").val(year+"-"+(mouth+1)+"-"+day);
    //$("#period").val((mouth+1));
});