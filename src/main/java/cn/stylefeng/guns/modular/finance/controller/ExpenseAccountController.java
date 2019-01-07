package cn.stylefeng.guns.modular.finance.controller;

import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.common.node.SubjectNode;
import cn.stylefeng.guns.core.common.node.ZTreeNode;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.finance.model.ExpenseAccount;
import cn.stylefeng.guns.modular.finance.service.IExpenseAccountService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 费用科目控制器
 *
 * @author fengshuonan
 * @Date 2018-11-21 10:58:37
 */
@Controller
@RequestMapping("/expenseAccount")
public class ExpenseAccountController extends BaseController {

    private String PREFIX = "/finance/expenseAccount/";

    @Autowired
    private IExpenseAccountService expenseAccountService;


    /**
     * 跳转费用科目展示框
     */
    @RequestMapping("/expenseAccount_subject")
    public String entry_subject() {
        return PREFIX + "subject.html";
    }
    /**
     * 跳转到费用科目首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "expenseAccount.html";
    }

    /**
     * 跳转到添加费用科目
     */
    @RequestMapping("/expenseAccount_add/{expenseAccountId}")
    public String expenseAccountAdd(@PathVariable Integer expenseAccountId, Model model) {
        model.addAttribute("item",expenseAccountId);
        return PREFIX + "expenseAccount_add.html";
    }

    /**
     * 跳转到修改费用科目
     */
    @RequestMapping("/expenseAccount_update/{expenseAccountId}")
    public String expenseAccountUpdate(@PathVariable Integer expenseAccountId, Model model) {
        ExpenseAccount expenseAccount = expenseAccountService.selectById(expenseAccountId);
        model.addAttribute("item",expenseAccount);
        LogObjectHolder.me().set(expenseAccount);
        return PREFIX + "expenseAccount_edit.html";
    }

    /**
     * 获取费用科目列表
     */
    @RequestMapping(value = "/list")
    @Permission
    @ResponseBody
    public Object list(String condition,String accountCode,@RequestParam(required = false) Integer deptid) {
        ExpenseAccount expenseAccount = new ExpenseAccount();
        String code = null;
        if(condition!=null&&!condition.equals("")){
            expenseAccount = expenseAccountService.selectById(condition);
            code = expenseAccount.getAccountCode();
        }else{
            code = "0";
        }
        if(accountCode!=null&&!accountCode.equals("")){
            expenseAccount = expenseAccountService.selectByCode(accountCode);
            code = expenseAccount.getSuperiorCode();
        }
        //expenseAccountService.selectList(null) 原查询方法
        return expenseAccountService.selectByParent(deptid==null?code:(deptid+""));
    }

    /**
     * 新增费用科目
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ExpenseAccount expenseAccount) {
        List<ExpenseAccount> list = expenseAccountService.selectByParent(expenseAccount.getSuperiorCode());
        String accountCode = list.get(list.size()-1).getAccountCode();
        if(accountCode!=null&&!accountCode.equals("")){
            int ac =  Integer.parseInt(accountCode);
            expenseAccount.setAccountCode((ac+1)+"");
        }else{
            expenseAccount.setAccountCode(expenseAccount.getSuperiorCode()+"01");
        }
        expenseAccountService.insert(expenseAccount);
        return SUCCESS_TIP;
    }

    /**
     * 删除费用科目
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer expenseAccountId) {
        expenseAccountService.deleteByIdManual(expenseAccountId);
        //expenseAccountService.deleteById(expenseAccountId); 原删除方法
        return SUCCESS_TIP;
    }

    /**
     * 修改费用科目
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ExpenseAccount expenseAccount) {
        expenseAccountService.edit(expenseAccount);
        return SUCCESS_TIP;
    }

    /**
     * 费用科目详情
     */
    @RequestMapping(value = "/detail/{expenseAccountId}")
    @ResponseBody
    public Object detail(@PathVariable("expenseAccountId") Integer expenseAccountId) {
        return expenseAccountService.selectById(expenseAccountId);
    }

    /**
     * 获取费用科目的tree列表
     */
    @RequestMapping(value = "/tree/{pId}")
    @ResponseBody
    public List<SubjectNode> tree(@PathVariable("pId") String pId) {
        List<SubjectNode> tree = this.expenseAccountService.tree(pId);
       /* tree.add(SubjectNode.createParent());*/
        return tree;
    }

    /**
     * 获取费用科目的tree列表
     */
    @RequestMapping(value = "/treeEa")
    @ResponseBody
    public List<ZTreeNode> treeEa() {
        List<ZTreeNode> tree = this.expenseAccountService.treeEa();
        tree.add(ZTreeNode.createParent());
        return tree;
    }
}
