package cn.stylefeng.guns.modular.finance.dao;

import cn.stylefeng.guns.modular.finance.model.Entry;
import cn.stylefeng.guns.modular.system.model.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 凭证录入表 Mapper 接口
 * </p>
 *
 * @author chenwenjie
 * @since 2018-11-12
 */
public interface EntryMapper extends BaseMapper<Entry> {
    /**
     *获取凭证列表
     * @return
     */
    public List<Map<String,Object>> selectTreeParentList(@Param(value="map") Map<String,Object> map,@Param(value="name")List<Map<String,String>> name);


    /**
     *获取凭证列表
     * @return
     */
    public List<Map<String,Object>> selectTreeChildrenList(@Param(value = "listId")List listId);

    /**
     * 获取凭证列表（未过账）
     * 2018-11-22
     *
     * @param map
     */
    public List<Map<String, Object>> selectPostingScheduleList(@Param(value="map") Map<String, Object> map);

    /**
     * 获取用户所有信息
     */
    public List<User> selectUsers();
    /**
     * 删除凭证
     */
    public void deleteEntry(@Param("listMap")List<Map<String,Object>> entryId);

    /**
     * 查询搜索方案
     * @return
     */
    public List<Map<String,Object>> selectSearchList(@Param("map") Map map);

    /**
     * 保存搜索方案
     * @param map
     */
    public void  saveSearch(@Param("map") Map map);

    /**
     * 查询搜索方案赋值
     * @param map
     * @return
     */
    public Map<String,Object> selectSearchMap(@Param("map") Map map);

    /**
     * 删除搜索方案
     * @param id
     */
    public void deleteSearch(@Param("id") String id);

    /**
     * 查询凭证统计
     * @param map
     * @return
     */
    public List<Map<String,Object>> findstatisticalList(@Param("map") Map<String,Object> map);
    /**
     * 查询未结账的会计期间
     */
    public String findAccountingPeriod(@Param("period")String period);
    /**
     * 查询当前期间所有数据是否以及进行损益
     * @return
     */
    public String profitLoss(@Param("period") String period);

    /**
     * 反过账
     * @param period 期间
     */
    public void antiSettlement(@Param("period") String period);

    /**
     * 删除损益凭证根据会计期间
     * @param period
     */
    public void deleteProfitEntry(@Param("period") String period);
    /**
     * 查询当前期间是否已进行结账
     */
    public String settleAccounts(@Param("period") String period);
    /**
     * 限制操作权限
     */
    public void limitOperation();
    /**
     * 删除结账状态
     */
    public void removeSettleAccountsState(@Param("period") String period);
    /**
     * 期末结帐
     */
    public int settleAccountsDispose(@Param("period") String period);
    /**
     * 年结账
     */
    public int yearsAccounts();
    /**
     * 查询未进行月结账的数据
     */
    public int selectNotMouthSettlement();
}
