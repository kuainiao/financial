
/**
 * 费用科目
 */
var Subject = {
    layerIndex: -1,
    object:null
};


/**
 * 费用科目6大类切换
 * @param obj
 * @param pId
 */
Subject.activeMenu=function(obj,pId){
    $(obj).parent().siblings().removeClass();
    $(obj).parent().addClass("active");
    Subject.tree(pId);
};

/**
 * 费用科目六大类双击取值
 * @param obj
 * @param pId
 */
Subject.getSubjectVal=function(pId){
    if(parent.search.object != undefined && parent.search.object != null){//判断搜索框页面对象是否为空
        $(parent.search.object).val(pId);
        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        parent.layer.close(index);
    }
};

/**
 * 打开费用科目
 */
Subject.openAddSubject = function (obj) {
    var index = layer.open({
        type: 2,
        title: '费用科目',
        area: ['600px', '400px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/expenseAccount/expenseAccount_subject'
    });
    this.layerIndex = index;
    Subject.object = obj;
};

/**
 * 加载ztree
 * @param pId
 */
Subject.tree=function(pId){
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var ztree = new $ZTreeObj("deptTree","/expenseAccount/tree/"+pId);
    var settings = {
        view : {
            dblClickExpand : true,
            selectedMulti : false,
            fontCss: getFontCss,
            nameIsHTML: true
        },
        data : {simpleData : {enable : true}},
        callback : {
            onClick : function (event, treeId, treeNode) {
                var subjectsMenu = treeNode.name.replace(/[^0-9]/ig,"");//获取点击的费用科目编码号
                var val="";
                var other = treeNode.accounting;    //判断是否为多核算
                var contacts_dnit = treeNode.contactsDnit; //往来单位核算
                var department = treeNode.department; //部门核算
                var staff = treeNode.staff; //职员核算
                parent.$("#other").val(other);
                parent.$("#contacts_dnit").val(contacts_dnit);
                parent.$("#department").val(department);
                parent.$("#staff").val(staff);
                do{ //循环出它所有的父级内容包括本身
                    var str = treeNode.name;
                    val = str.replace(/&nbsp;/ig, "")+" - "+val;
                    treeNode = treeNode.getParentNode();
                }while (treeNode != null);

                val =$("#subjectsClass").find(".active").find("a").html() +" - "+val;
                val = val.replace(/[0-9]/ig,"");//去除数字
                if(parent.Subject != undefined && parent.Subject != null){  //判断新增凭证页面对象是否为空
                    $($(parent.Subject.object).children()).val(subjectsMenu+"  "+val.substring(0,val.length-2));
                    $($(parent.Subject.object).parent().find("input")[3]).val("");
                    $($(parent.Subject.object).parent().find("input")[4]).val("");
                    $($(parent.Subject.object).parent().find("input")[5]).val("");
                    if(parent.EntryInfoDlg != undefined && parent.EntryInfoDlg != null){
                        parent.EntryInfoDlg.otherAccounting();//多核算
                    }else{
                        parent.EntryInfoDlgEdit.otherAccounting();//多核算
                    }
                }else if(parent.search.object != undefined && parent.search.object != null){//判断搜索框页面对象是否为空
                    $(parent.search.object).val(subjectsMenu);
                }else {
                    Feng.error("老天！对象丢咯！");
                }
                parent.layer.close(index);
            },
            onDblClick:this.ondblclick
        }
    };
    ztree.setSettings(settings);
    ztree.init();

};

$(function () {
    Subject.tree('1');
});

/**
 * ztree高亮显示
 * @param treeId
 * @param treeNode
 * @returns {*}
 */
function getFontCss(treeId, treeNode) {
    return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
}





