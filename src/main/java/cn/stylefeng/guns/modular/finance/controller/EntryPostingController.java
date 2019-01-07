package cn.stylefeng.guns.modular.finance.controller;

import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.modular.finance.service.IEntryService;
import cn.stylefeng.guns.modular.finance.service.ITagService;
import cn.stylefeng.guns.modular.finance.warpper.entryWarpper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 凭证过账控制器
 *
 * @author chenwenjie
 * @Date 2018-11-22
 */
@Controller
@RequestMapping(value = "/posting")
public class EntryPostingController extends BaseController {
    private String PREFIX = "finance/posting/";

    @Autowired
    private IEntryService entryService;
    @Autowired
    private ITagService tagService;
    /**
     * 去过账页面
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "posting.html";
    }

    /**
     * 获取凭证展示列表/(搜索)
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String,Object> map) {
        List<Map<String,Object>> entryList = entryService.selectPostingScheduleList(map); //获取凭证列表
        return super.warpObject(new entryWarpper(entryList));
    }

    /**
     * 凭证过账
     */
    @RequestMapping(value = "/posting")
    @ResponseBody
    public Object posting(@RequestParam  String[] entryId){
        List<Map<String,Object>> listMap = tagService.listMap(entryId);
        for (Map<String,Object> map : listMap){
            tagService.updatePosting((String) map.get("name"),(String)map.get("entryId"));
        }
        return SUCCESS_TIP;
    }
}
