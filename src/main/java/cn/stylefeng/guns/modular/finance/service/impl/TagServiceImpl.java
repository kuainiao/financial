package cn.stylefeng.guns.modular.finance.service.impl;


import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.modular.finance.dao.TagMapper;
import cn.stylefeng.guns.modular.finance.model.Tag;
import cn.stylefeng.guns.modular.finance.service.ITagService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * <p>
 * 凭证录入数据标记表 服务实现类
 * </p>
 *
 * @author chenwenjie
 * @since 2018-11-16
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {



    @Override
    public void approval( List<Map<String,Object>> Listmap) {
        this.baseMapper.approval(Listmap);
    }
    /**
     * 整理需要新增的数据
     * @param entryId
     * @return
     */
    @Override
    public List<Map<String,Object>> listMap (String[] entryId) {
        String name = String.valueOf(ShiroKit.getUser().getName());
        List<Map<String, Object>> Listmap = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < entryId.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            String id = entryId[i].replaceAll("\\p{Punct}", "");
                map.put("entryId",id);//去除[]这两个字符
                map.put("name", name);
                Listmap.add(map);
        }
         List<Map<String,Object>> tmpList=new ArrayList<Map<String,Object>>();
         Set<String> keysSet = new HashSet<String>();
        for(Map<String, Object> map : Listmap){
            String keys = (String) map.get("entryId");
            int beforeSize = keysSet.size();
            keysSet.add(keys);
            int afterSize = keysSet.size();
            if(afterSize == beforeSize + 1){
            tmpList.add(map);
            }
        }
        return tmpList;
    }

    /**
     * 反审
     * @param listMap
     */
    @Override
    public void reverseApproval(List<Map<String, Object>> listMap) {
        this.baseMapper.reverseApproval(listMap);
    }

    @Override
    public void updatePosting(String name , String entryId) {
        this.baseMapper.updatePosting(name,entryId);
    }

}
