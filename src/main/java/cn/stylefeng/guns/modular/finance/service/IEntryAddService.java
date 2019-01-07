package cn.stylefeng.guns.modular.finance.service;

import cn.stylefeng.guns.modular.finance.model.Entry;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 凭证录入表 服务类
 * </p>
 *
 * @author chenwenjie
 * @since 2018-11-12
 */
public interface IEntryAddService extends IService<Entry> {

    int insertGlm(Entry entry);

    /**
     * 修改凭证数据
     * @param entry
     * @return
     */
    void updateEntryAndEntrySchedule(Entry entry,String entryInitial, String entryAfter);

    /**
     * 根据凭证ID获取凭证信息
     * @param map
     * @return
     */
    List<Map<String, Object>> selectEntryIdScheduleList(Integer Id);

    /**
     * 获取所有费用科目
     */
    List<Map<String,Object>> selectAccountAll();

    /*public List<Entry> selectBySettlement(String document);*/

    /**
     *添加凭证展示提供数据支持
     * @return
     */
    Map<String,Object> getEntry(String value);

    /**
     * 获取未过帐或已过帐数据
     */
    List<Entry> selectNotPosting(Map<String,Object> map);

    void updatEntry(List<Integer> id);
}
