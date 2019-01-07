package cn.stylefeng.guns.modular.finance.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.modular.finance.service.IAuditpermissionsService;
import cn.stylefeng.guns.modular.finance.model.Auditpermissions;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenwenjie
 * @version 1.0
 * @Date 2018-11-19 14:29:35
 * 权限设置控制器 主要管理 审核、过账权限功能的 增删改查
 */
@Controller
@RequestMapping("/auditpermissions")
public class AuditpermissionsController extends BaseController {

    private String PREFIX = "/finance/auditPermissions/";

    @Autowired
    private IAuditpermissionsService auditpermissionsService;

    /**
     * 跳转到审核权限设置首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "auditpermissions.html";
    }
    /**
     * 跳转到过账权限设置页面
     */
    @RequestMapping("/auditPosting")
    public String auditPostingIndex(){
        return PREFIX+ "auditPosting.html";
    }
    /**
     * 跳转到添加审核权限设置
     */
    @RequestMapping("/auditpermissions_add")
    public String auditpermissionsAdd() {
        return PREFIX + "auditpermissions_add.html";
    }
    /**
     * 跳转到过账权限设置
     */
    @RequestMapping("/auditPosting/AuditPosting_add")
    public String auditPostingAuditPosting_add(){return PREFIX + "auditPosting_add.html";}
    /**
     * 跳转到修改审核权限设置
     */
    @RequestMapping("/auditpermissions_update/{auditpermissionsId}")
    public String auditpermissionsUpdate(@PathVariable Integer auditPermissionsId, Model model) {
        Auditpermissions auditPermissions = auditpermissionsService.selectById(auditPermissionsId);//查询审核权限详情信息
        List<String> name = auditpermissionsService.prepared_by_list();//审核权限人
        model.addAttribute("item",auditPermissions);
        model.addAttribute("name",name);
        LogObjectHolder.me().set(auditPermissions);
        return PREFIX + "auditpermissions_edit.html";
    }
    /**
     * 跳转到修改过账权限设置
     */
    @RequestMapping("/auditPosting/AuditPosting_update/{auditpermissionsId}")
    public String auditPostingUpdate(@PathVariable Integer auditPermissionsId , Model model){
        Auditpermissions auditPermissions = auditpermissionsService.selectById(auditPermissionsId);//查询过账权限详情信息
        List<String> name = auditpermissionsService.prepared_by_list();//过账人
        model.addAttribute("item",auditPermissions);
        model.addAttribute("name",name);
        LogObjectHolder.me().set(auditPermissions);
        return PREFIX + "auditPosting_edit.html";
    }

    /**
     * 获取审核权限设置列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Map<String,String> map = new HashMap<String, String>();
        map.put("approval_people","1");
        return auditpermissionsService.findAuditPermissions(ShiroKit.getUser().getName(),map);
    }
    /**
     * 获取过账权限设置列表
     */
    @RequestMapping(value = "/auditPosting/list")
    @ResponseBody
    public Object auditPostingList(String condition){
        Map<String,String> map = new HashMap<String, String>();
        map.put("posting_people","1");
        return auditpermissionsService.findAuditPermissions(ShiroKit.getUser().getName(),map);
    }
    /**
     * 新增审核权限设置
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Auditpermissions auditPermissions) {
        Map<String,String> listName = auditpermissionsService.findAudit(ShiroKit.getUser().getName());
        if(listName != null ){//已存在配置信息更新
            auditpermissionsService.updateApproval(auditPermissions);
        }else{ //为存在则插入
            auditpermissionsService.insert(auditPermissions);
        }
        return SUCCESS_TIP;
    }

    /**
     * 新增过账权限设置
     */
    @RequestMapping(value = "/auditPosting/add")
    @ResponseBody
    public Object auditPostingAdd(Auditpermissions auditPermissions){
        Map<String,String> listName = auditpermissionsService.findAudit(ShiroKit.getUser().getName());
        if(listName != null){//该用户已配置审核设置
            auditpermissionsService.updatePosting(auditPermissions);
        }else{//新增
            auditpermissionsService.insert(auditPermissions);
        }
        return SUCCESS_TIP;
    }
    /**
     * 删除审核权限设置
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer auditpermissionsId) {
        String name = auditpermissionsService.findPosting(auditpermissionsId);
        if(StringUtils.isNotEmpty(name)){//当有过账权限的时候走更新
            Map<String,String> map = new HashMap<String, String>();
            map.put("id", String.valueOf(auditpermissionsId));
            map.put("approvalPeople",null);
            auditpermissionsService.deleApproval(map);
        }else { //没有过账权限时走删除
            auditpermissionsService.deleteById(auditpermissionsId);
        }
        return SUCCESS_TIP;
    }
    /**
     * 删除过账权限设置
     */
    @RequestMapping(value = "/auditPosting/delete")
    @ResponseBody
    public Object auditPostingDelete(@RequestParam Integer auditpermissionsId){
        String name = auditpermissionsService.findApproval(auditpermissionsId);
        if(StringUtils.isNotEmpty(name)){//当有审核权限的时候走更新
            Map<String,String> map = new HashMap<String, String>();
            map.put("id", String.valueOf(auditpermissionsId));
            map.put("postingPeople",null);
            auditpermissionsService.deletePosting(map);
        }else{ //没有审核权限时走删除
            auditpermissionsService.deleteById(auditpermissionsId);
        }
        return SUCCESS_TIP;
    }
    /**
     * 修改审核权限设置
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Auditpermissions auditpermissions) {
        auditpermissionsService.updateById(auditpermissions);
        return SUCCESS_TIP;
    }
    /**
     * 修改过账权限设置
     */
    @RequestMapping(value = "/auditPosting/update")
    @ResponseBody
    public Object auditPostingUpdate(Auditpermissions auditpermissions) {
        auditpermissionsService.updateById(auditpermissions);
        return SUCCESS_TIP;
    }
    /**
     * 审核权限设置详情
     */
    @RequestMapping(value = "/detail/{auditpermissionsId}")
    @ResponseBody
    public Object detail(@PathVariable("auditpermissionsId") Integer auditpermissionsId) {
        return auditpermissionsService.selectById(auditpermissionsId);
    }

    /**
     * 获取制单人
     */
    @RequestMapping(value = "/prepared_by_list")
    @ResponseBody
    public Object prepared_by_list(){
        Map<String,Object> map =new HashMap<String, Object>();
        List<String> name=auditpermissionsService.prepared_by_list();
        map.put("name",name);
        return map;
    }
}
