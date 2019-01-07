package cn.stylefeng.guns.modular.finance.dao;

import cn.stylefeng.guns.modular.finance.model.Entry;
import cn.stylefeng.guns.modular.finance.model.EntrySchedule;
import cn.stylefeng.guns.modular.finance.model.ExpenseAccount;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 凭证录入表 Mapper 接口,与附表关联
 * </p>
 *
 * @author chenwenjie
 * @since 2018-11-12
 */
public interface EntryGlmMapper extends BaseMapper<Entry> {
    /**
     * 新增主表数据
     * @param entry
     * @return
     */
    int insertGlm(Entry entry);

    /**
     * 新增凭证副表数据
     * @param entrySchedule
     * @return
     */
    int insertGlmSchedule(@Param("entrySchedule") List<EntrySchedule> entrySchedule);

    /**
     * 修改凭证主表数据
     * @param entry
     * @return
     */
    void updateEntry(Entry entry);

    /**
     * 修改凭证副表数据
     * @param entry
     * @return
     */
    void updateEntrySchedule(EntrySchedule entrySchedule);

    /**
     * 按照结算状态查询数据，用于统计凭证号
     * @return
     */
    /*public List<Entry> selectBySettlement(@Param("document") String document);*/

    /**
     * 修改凭证时附表删除
     * @param ids
     */
    void entryScheduleDelete(@Param("ids") List<Integer> ids);

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

    /**
     * 获取未过帐或已过帐数据
     */
    List<Entry> selectNotPosting(Map<String,Object> map);

    void updatEntry(List<Integer> id);
}
