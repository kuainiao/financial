package cn.stylefeng.guns.modular.finance.controller;

import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.modular.finance.model.Entry;
import cn.stylefeng.guns.modular.finance.service.IAuditpermissionsService;
import cn.stylefeng.guns.modular.finance.service.IEntryService;
import cn.stylefeng.guns.modular.finance.service.ITagService;
import cn.stylefeng.guns.modular.finance.warpper.entryWarpper;
import cn.stylefeng.guns.modular.system.model.User;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;


/**
 * @author chenwenjie
 * @version 1.0
 * @Date 2018-11-12 14:03:36
 * 凭证控制器 负责凭证功能的所有业务操作 和展示
 */
@Controller
@RequestMapping("/entry")
public class EntryController extends BaseController {

    private String PREFIX = "finance/entry/";

    @Autowired
    private IEntryService entryService;
    @Autowired
    private ITagService tagService;
    @Autowired
    private IAuditpermissionsService auditpermissionsService;

    /**
     * 跳转到添加凭证展示
     */
    @RequestMapping("/entry_subject")
    public String entry_subject() {
        return "finance/expenseAccount/subject.html";
    }
    /**
     * 跳转到凭证展示首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "entry.html";
    }

    /**
     * 跳转到添加凭证展示
     */
    @RequestMapping("/entry_add")
    public String entryAdd() {
        return PREFIX + "entry_add.html";
    }

    /**
     * 跳转到修改凭证展示
     */
    @RequestMapping("/entry_update/{entryId}")
    public String entryUpdate(@PathVariable Integer entryId, Model model) {
        Entry entry = entryService.selectById(entryId);
        model.addAttribute("item",entry);
        LogObjectHolder.me().set(entry);
        return PREFIX + "entry_edit.html";
    }

    /**
     * 获取凭证展示列表/(搜索)
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @Permission
    public Object list(@RequestParam Map<String,Object> map) {
        if(map.size()==0){
            map.put("dateClass","1");
        }
        List<Map<String,String>> listName = auditpermissionsService.findAuditName(ShiroKit.getUser().getName(),map);
        map.put("name",ShiroKit.getUser().getName());
        List<Map<String,Object>> entryList = entryService.selectTreeParentList(map,listName); //获取凭证列表
        return super.warpObject(new entryWarpper(entryList));
    }

    /**
     * 新增凭证展示
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Entry entry) {
        entryService.insert(entry);
        return SUCCESS_TIP;
    }

    /**
     * 修改凭证展示
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Entry entry) {
        entryService.updateById(entry);
        return SUCCESS_TIP;
    }

    /**
     * 打开搜索框
     */
    @RequestMapping(value = "/search")
    public String search(Model model) {
        Map<String,Object> map = new HashMap<>();
        map.put("staff_id",ShiroKit.getUser().getId());
        List<Map<String,Object>> list = entryService.selectSearchList(map);
        model.addAttribute("list",list);
        return PREFIX + "entry_search.html";
    }

    /**
     * 查询搜索方案值
     */
    @RequestMapping(value = "/searchPlan")
    @ResponseBody
    public Object searchPlan(@RequestParam Map<String,Object> map) {
        Map<String,Object> searchMap = entryService.selectSearchMap(map);
        return searchMap;
    }

    /**
     * 保存搜索方案
     */
    @RequestMapping(value = "/saveSearch")
    @ResponseBody
    public Object saveSearch(@RequestParam Map<String,Object> map){
        entryService.saveSearch(map);
        return SUCCESS_TIP;
    }

    /**
     * 删除搜索方案
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteSearch")
    @ResponseBody
    public Object deleteSearch(String id){
        entryService.deleteSearch(id);
        return SUCCESS_TIP;
    }

    /**
     * 凭证展示详情
     */
    @RequestMapping(value = "/detail/{entryId}")
    @ResponseBody
    public Object detail(@PathVariable("entryId") Integer entryId) {
        return entryService.selectById(entryId);
    }
    /**
     * 删除凭证展示
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String[] entryId) {
        List<Map<String,Object>> listMap =tagService.listMap(entryId);//整理需要插入的数据
        entryService.deleteEntry(listMap);
        return SUCCESS_TIP;
    }
    /**
     * 审批按钮
     */
    @RequestMapping(value = "/approval")
    @ResponseBody
    public Object approval(@RequestParam String[] entryId){
 /*     Stream.of(entryId).forEach(s -> System.out.println("**"+s));*/
        List<Map<String,Object>> Listmap =tagService.listMap(entryId);//整理需要插入的数据
        tagService.approval(Listmap);//新增审核数据
        return SUCCESS_TIP;
    }
    /**
     *反审功能
     */
    @RequestMapping(value = "/reverseApproval")
    @ResponseBody
    public Object reverseApproval(@RequestParam String[] entryId){
        List<Map<String,Object>> listMap = tagService.listMap(entryId);
        tagService.reverseApproval(listMap);
        return SUCCESS_TIP;
    }

    /**
     * 获取用户所有信息
     */
    @RequestMapping(value = "/getUser")
    @ResponseBody
    public List<User> getUser(){
        return entryService.selectUsers();
    }

    /**
     * 去凭证统计页面
     */
    @RequestMapping(value = "/statisticalPage")
    public String statisticalPage(){return PREFIX +"entryStatistical.html";};
    /**
     * 凭证统计列表
     */
    @RequestMapping(value = "/statistical")
    @ResponseBody
    public Object statisticalList(@RequestParam Map<String,Object> map){
        if(map.size()==0){
            map.put("dateClass","1");
        }
        List<Map<String,Object>> entryList = entryService.findstatisticalList(map); //获取凭证列表
        return super.warpObject(new entryWarpper(entryList));
    }

    /**
     * 去结账页面
     * @return
     */
    @RequestMapping(value = "/checkout")
    public String checkout(){return PREFIX + "checkout.html";}

    /**
     * 结账  0 结账 1反结账 2 反过账当前凭证 3 年结账
     * @param  state 类型  period 期间
     */
    @RequestMapping(value = "/checkoutFunction")
    @ResponseBody
    public Object checkoutFunction(String state,String period){
        Map<String,Object> map = new HashMap<String, Object>();
        String periodData = entryService.findAccountingPeriod(period);//未结账的会计期间
        if("0".equals(periodData) && !"3".equals(state)){//当前期间没有凭证数据
            map.put("code",201);
            map.put("message","第"+period+"会计期间没有凭证数据");
            return map;
        }
        map = entryService.selectBranch(state,period);//执行判断执行那条业务分支，并执行
        return map;
    }


}
