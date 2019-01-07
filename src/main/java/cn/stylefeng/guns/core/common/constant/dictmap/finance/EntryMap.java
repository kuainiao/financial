package cn.stylefeng.guns.core.common.constant.dictmap.finance;

import cn.stylefeng.guns.core.common.constant.dictmap.base.AbstractDictMap;

public class EntryMap extends AbstractDictMap {
    @Override
    public void init() {
        put("entry","凭证");
        put("id","主键");
    }

    @Override
    protected void initBeWrapped() {

    }
}
