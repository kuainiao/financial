package cn.stylefeng.guns.modular.finance.warpper;


import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

public class entryWarpper extends BaseControllerWrapper {
    public entryWarpper(Map<String, Object> single) {
        super(single);
    }

    public entryWarpper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public entryWarpper(Page<Map<String, Object>> page) {
        super(page);
    }

    public entryWarpper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {

    }
}
