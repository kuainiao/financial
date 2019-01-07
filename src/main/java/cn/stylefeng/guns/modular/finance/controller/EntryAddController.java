package cn.stylefeng.guns.modular.finance.controller;

import cn.stylefeng.guns.core.common.annotion.BussinessLog;
import cn.stylefeng.guns.core.common.constant.dictmap.finance.EntryMap;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.finance.model.Entry;
import cn.stylefeng.guns.modular.finance.model.EntrySchedule;
import cn.stylefeng.guns.modular.finance.model.ExpenseAccount;
import cn.stylefeng.guns.modular.finance.service.IEntryAddService;
import cn.stylefeng.guns.modular.finance.service.IEntryService;
import cn.stylefeng.guns.modular.finance.service.IExpenseAccountService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/entryGlm")
public class EntryAddController extends BaseController {

    private String PREFIX = "/finance/entry/";//菜单地址

    @Autowired
    private IEntryAddService entryGlmService;//注入凭证接口
    @Autowired
    private IExpenseAccountService expenseAccountService;//注入费用科目接口
    @Autowired
    private IEntryService entryService;//注入凭证新增类接口

    /**
     * 跳转到凭证展示首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "entryGlm.html";
    }

    /**
     * 跳转到添加凭证展示
     */
    @RequestMapping("/entry_add")
    public String entryAdd(Model model) {
        //查询所有为结转数据，用于查出最新凭证号，顺序号
        Map<String,Object> map = entryGlmService.getEntry(null);
        model.addAttribute("document_number",map.get("document_number"));//凭证号
        model.addAttribute("sequence_number",map.get("sequence_number"));//顺序号
        model.addAttribute("period",map.get("period"));//期间
        return PREFIX + "entry_addGlm.html";
    }

    /**
     * 获取凭证展示列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return entryGlmService.selectList(null);
    }

    /**
     * 新增凭证
     */
    @BussinessLog(value = "新增凭证" , key = "entry",dict = EntryMap.class)
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(String entry) {
        Entry et = JSONObject.parseObject(entry, Entry.class);//将前台传过来的json数据转换成实体类
        entryGlmService.insertGlm(et);
        return SUCCESS_TIP;
    }
    /**
     * 去修改凭证展示页面
     */
    @RequestMapping(value = "/entry_update/{Id}")
    public String entryUpdate(@PathVariable Integer Id, Model model)  {
        List<Map<String,Object>> entrySchedule = entryGlmService.selectEntryIdScheduleList(Id);//查询详细信息
        model.addAttribute("entrySchedule",entrySchedule);//返回详细信息
        LogObjectHolder.me().setListStart(entrySchedule);
        return PREFIX + "entryGlm_edit.html";
    }

    /**
     * 查看详情
     */
    //@BussinessLog(value = "查看详情" , key = "id",dict = EntryMap.class,type = "2")
    @RequestMapping(value = "/entry_view/{Id}")
    public String entry_view(@PathVariable Integer Id, Model model)  {
        List<Map<String,Object>> entrySchedule = entryGlmService.selectEntryIdScheduleList(Id);//查询处详细信息
        model.addAttribute("entrySchedule",entrySchedule);//出入model传入前台
        return PREFIX + "entry_view.html";
    }
    /**
     * 跳转到多核算页面
     */
    @RequestMapping(value = "/otherAccounting")
    public Object otherAccounting(){ return PREFIX + "otherAccounting.html";}
    /**
     * 修改凭证
     */
    @BussinessLog(value = "修改凭证" , key = "entry",dict = EntryMap.class,type = "2")
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(String entry,String entryInitial,String entryAfter) {
        Entry et = JSONObject.parseObject(entry, Entry.class);
        entryGlmService.updateEntryAndEntrySchedule(et,entryInitial,entryAfter);
        List<Map<String,Object>> entrySchedule = entryGlmService.selectEntryIdScheduleList(et.getId()); //为日志提供对比支持
        LogObjectHolder.me().setListEnd(entrySchedule);
        return SUCCESS_TIP;
    }

    /**
     * 凭证字改变，关联改变凭证号
     * @param value
     * @return
     */
    @RequestMapping(value="/documentChange/{value}")
    @ResponseBody
    public Object documentChange(@PathVariable Integer value){
        //根据凭证字查询有多少条未结转信息，集合长度+1为最新凭证号
        Map<String,Object> map = entryGlmService.getEntry(value+"");
        map.put("documentNumber",map.get("sequence_number"));//凭证号
        return map;
    }

    /**
     *去结转损益界面
     * @return
     */
    @RequestMapping(value = "/carryOver")
    public String carryOver(Model model){
        //获取父级编码为6的所有子集元素
        List<ExpenseAccount> exList = expenseAccountService.selectByParentList("6");
        //获取父级编码为4104的所有子集元素
        List<ExpenseAccount> exListFP = expenseAccountService.selectByParentList("4104");
        model.addAttribute("exList",exList);//将集合放入model传入前台
        model.addAttribute("exListFP",exListFP);
        return PREFIX + "carryOver.html";
    }

    /**
     * 进行损益结转
     * @return
     */
    @RequestMapping(value = "/profitLoss")
    @ResponseBody
    public Object profitLoss(String entry){
        Entry et = JSONObject.parseObject(entry, Entry.class);
        Map<String,Object> map = new HashMap<String,Object>();
        if(map.size()==0){
            map.put("dateClass","1");
        }
        List<Integer> ids = new ArrayList<Integer>();
        //获取父级编码为6的所有子集元素
        List<ExpenseAccount> exList = expenseAccountService.selectByParentList("6");
        //获取凭证列表
        List<Map<String,Object>> entryList = entryService.findstatisticalList(map);
        Entry entryy = new Entry();//创建凭证实体类，用于新增
        List<EntrySchedule> esList = new ArrayList<EntrySchedule> ();
        for (ExpenseAccount expenseAccount : exList) {
            EntrySchedule es = new EntrySchedule();
            EntrySchedule esc = new EntrySchedule();
            double debitAmount =  0;//借方金额
            double creditAmount = 0;//贷方金额
            for (Map<String,Object> mapp : entryList) {
                if(expenseAccount.getAccountCode().equals(mapp.get("expense_account"))){//当会计科目
                    ids.add((Integer) mapp.get("id"));
                    debitAmount += Double.parseDouble((String)mapp.get("debit_amount"));//累加借方金额
                    creditAmount += Double.parseDouble((String)mapp.get("credit_amount"));//累加贷方金额
                }
                es.setExpenseAccount((String)mapp.get("expense_account"));//会计科目
                esc.setExpenseAccount("4103");
            }
            es.setSummary(et.getRemarks());//摘要
            esc.setSummary(et.getRemarks());//摘要
            if(debitAmount>creditAmount){//当借方金额大于贷方金额时，储存借方金额，反之储存贷方金额
                es.setDebitAmount(debitAmount-creditAmount);
                es.setCreditAmount(null);
                esc.setDebitAmount(null);
                esc.setCreditAmount(creditAmount-debitAmount);
            }else{
                es.setDebitAmount(null);
                es.setCreditAmount(creditAmount-debitAmount);
                esc.setDebitAmount(debitAmount-creditAmount);
                esc.setCreditAmount(null);
            }
            if(debitAmount!=0||creditAmount!=0){
                esList.add(es);
                esList.add(esc);

            }

        }
        entryy.setSequenceNumber(0);//顺序号
        entryy.setDocumentNumber(0);//凭证号
        entryy.setDocumentsNumber(0);//单据数
        entryy.setDocumentWord(et.getDocumentWord());//凭证号
        entryy.setPreparedBy(et.getPreparedBy());//制单人~
        entryy.setSettlementState("0");
        entryy.setEntrySchedule(esList);
        entryGlmService.insertGlm(entryy);
        entryGlmService.updatEntry(ids);
        return SUCCESS_TIP;
    }


}
