/*确定按钮把数据绑定到添加页面当前点击的tr 隐藏input中*/
determine = function (){
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var unit = $("#unit").find("option:selected").text();//公司
    var department = $("#department").find("option:selected").text();//部门
    var staff = $("#staff").find("option:selected").text();//员工
    var object = $($(parent.Subject.object).children()).val();
    object+="-"+unit+"-"+department+"-"+staff;
    $($(parent.Subject.object).children()).val(object);//把多核算内容显示在页面上
    $($(parent.Subject.object).parent().find("input")[4]).val(unit);
    $($(parent.Subject.object).parent().find("input")[5]).val(department);
    $($(parent.Subject.object).parent().find("input")[6]).val(staff);
    parent.layer.close(index);
};
/*取消*/
remove = function() {
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    parent.layer.close(index);
};
/*function changeF() {
    document.getElementById('id_input').value = document.getElementById('unit').options[document.getElementById('unit').selectedIndex].value;
}*/
//触发部门数据加载
function checkField(value){
    var reg = /^[0-9]*$/;
    var department = new Array();
    if(reg.test(value)){
        var ajax = new $ax(Feng.ctxPath + "/oaData/gainDepartment", function (data) {
            department= data;
        }, function (data) {
            Feng.error("获取部门失败!" + data.responseJSON.message + "!");
        });
        ajax.set("bianma",value);
        ajax.start();
        var str="" +
            "<option>请选择部门</option>";
        for(var i=0;i<department.length;i++){
            str+="<option value='" +
                 department[i].BIANMA+
                "'>"+department[i].NAME+"</option>";
        }
        $("#department").find("option").remove()//删除之前的部门信息
        $("#department").append(str);//数据插入到页面
    }else{
        $("#department").find("option").remove();//
        $("#staff").find("option").remove();
    }
}
//触发部门职员数据加载
function checkFieldStaff(value){
    var reg = /^[0-9]*$/;
    var staff = new Array();
    if(reg.test(value)){
        var ajax = new $ax(Feng.ctxPath + "/oaData/gainClerk", function (data) {
            staff = data;
        }, function (data) {
            Feng.error("获取员工失败!" + data.responseJSON.message + "!");
        });
        ajax.set("bianma",value);
        ajax.start();
        var str="";
        for(var i=0;i<staff.length;i++){
            str+="<option value='" +
                staff[i].BIANMA+
                "'>"+staff[i].NAME+"</option>";
        }
        $("#staff").find("option").remove();
        $("#staff").append(str);
    }else{
        $("#staff").find("option").remove();
    }

}
//页面加载
$(function () {
    var company= new Array();
    var ajax = new $ax(Feng.ctxPath + "/oaData/gainCompany", function (data) {
        company = data;
    }, function (data) {
        Feng.error("获取公司失败!" + data.responseJSON.message + "!");
    });
    ajax.set("satre","2");
    ajax.start();
    var contacts_dnit = parent.$("#contacts_dnit").val();
    var department = parent.$("#department").val();
    var staff = parent.$("#staff").val();
    if(contacts_dnit == '1'){//判断是否有往来单位权限
        var str = "<tr style='height: 50px'>" +
            "<td style='width: 40%'>往来单位核算</td>" +
            "<td style='width: 60%'>" +
            "<select style='width: 80%; height: 40px' id='unit' onchange=\"checkField(this.value)\">" +
            "<option>请选择单位</option>" ;
        for (var i =0 ;i<company.length;i++){
            str +="<option value='" +
                 +company[i].BIANMA +
                "'>"+company[i].NAME+"</option>";
        }
        str+="</select></td>"+
             "</tr>";
        $("#accounting").append(str);
        $("#unit").select2({
            tags: true,
        });
    }

    if(department == '1'){//判断是否有部门核算权限
        var str = "<tr style='height: 50px'>" +
            "<td style='width: 40%'>部门核算</td>" +
            "<td style='width: 60%'><select style='width: 80%;height: 40px' id='department' onchange=\"checkFieldStaff(this.value)\">" +
            "</select>" +
            "</td>" +
            "</tr>"
        $("#accounting").append(str);
        $("#department").select2({
            tags: true,
        });
    }
    if(staff == '1'){//判断是否有职员核算权限
        var str = "<tr style='height: 50px'>" +
            "<td style='width: 40%'>职员核算</td>" +
            "<td style='width: 40%'><select style='width: 80%;height: 40px' id='staff'>" +
            "</select></td>" +
            "</tr>"
        $("#accounting").append(str);
        $("#staff").select2({
            tags: true,
        });
    }
});
