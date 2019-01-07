package cn.stylefeng.guns.modular.finance.service;


import cn.stylefeng.guns.modular.finance.model.Tag;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 凭证录入数据标记表 服务类
 * </p>
 *
 * @author chenwenjie
 * @since 2018-11-16
 */
public interface ITagService extends IService<Tag> {

    void approval( List<Map<String,Object>> Listmap);

    List<Map<String,Object>> listMap (String[] entryId);

    void reverseApproval (List<Map<String,Object>> listMap);

    void updatePosting (String name ,String entryId);
}
