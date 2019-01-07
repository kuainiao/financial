package cn.stylefeng.guns.modular.finance.controller;

import cn.stylefeng.guns.modular.finance.service.IOaDataSourceService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
/**
 * @author chenwenjie
 * @version 1.0
 * @Date 2018-11-12 14:03:36
 * oa数据源控制器 主要功能获取OA数据库数据
 */
@Controller
@RequestMapping("/oaData")
public class OaDataSourceController extends BaseController {
    private String PREFIX = "finance/oaData/";

    @Autowired
    private IOaDataSourceService oaDataSourceService;

    /**
     * 查询公司
     * @param satre ‘2’
     * @return
     */
    @RequestMapping(value = "/gainCompany")
    @ResponseBody
    public Object gainCompany (String satre){
        return  oaDataSourceService.listMap(satre);
    }

    /**
     * 查询部门
     * @param  bianma 告诉编码
     */
    @RequestMapping(value = "/gainDepartment")
    @ResponseBody
    public Object gainDepartment (String bianma){
        return oaDataSourceService.listMapCompany(bianma);
    }

    /**
     * 查询部门职员
     * @param bianma 部门编码
     */
    @RequestMapping(value = "/gainClerk")
    @ResponseBody
    public Object gainClerk(String bianma){
        return oaDataSourceService.listMapClerk(bianma);
    }
}
