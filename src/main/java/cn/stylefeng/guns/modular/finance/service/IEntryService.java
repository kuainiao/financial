package cn.stylefeng.guns.modular.finance.service;

import cn.stylefeng.guns.modular.finance.model.Entry;
import cn.stylefeng.guns.modular.system.model.User;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

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
public interface IEntryService extends IService<Entry> {
    /**
     * 获取凭证列表
     * 2018年11月13日 上午9:24
     */
    public List<Map<String, Object>> selectTreeParentList(Map<String,Object> map,List<Map<String,String>> name);

    /**
     * 获取凭证列表（未过账）
     * 2018-11-22
     */
    public List<Map<String, Object>> selectPostingScheduleList(Map<String,Object> map);

    /**
     * 获取用户所有信息
     */
    public List<User> selectUsers();
    /**
     * 删除凭证
     */
    public void deleteEntry(List<Map<String,Object>> entryId);

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
     * 凭证统计
     * @param map 搜索条件
     * @return
     */
    List<Map<String,Object>> findstatisticalList(Map<String,Object> map);
    /**
     * 查询未结账的会计期间
     */
    public String findAccountingPeriod(String period);
    /**
     * 查询当前期间所有数据是否以及进行损益
     */
    public String profitLoss(String period);
    /**
     * 反过账
     */
    public Map<String,Object> antiSettlement(String period);
    /**
     * 期末反结帐
     */
    public Map<String,Object> settleAccounts (String period);
    /**
     * 期末结转
     */
    public Map<String,Object> settleAccountsDispose(String period);

    /**
     * 判断执行那条业务分支
     */
    public Map<String,Object> selectBranch(String state,String period);
    /**
     * 年结账
     */
    public Map<String,Object> yearsAccounts();
}
