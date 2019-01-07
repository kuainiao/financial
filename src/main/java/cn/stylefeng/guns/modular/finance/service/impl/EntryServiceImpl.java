package cn.stylefeng.guns.modular.finance.service.impl;

import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.modular.finance.dao.EntryMapper;
import cn.stylefeng.guns.modular.finance.model.Entry;
import cn.stylefeng.guns.modular.finance.service.IEntryService;
import cn.stylefeng.guns.modular.system.model.User;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 凭证录入表 服务实现类
 * </p>
 *
 * @author chenwenjie
 * @since 2018-11-12
 */
@Service
public class EntryServiceImpl extends ServiceImpl<EntryMapper, Entry> implements IEntryService {
    /**
     * 获取凭证列表tree
     * 2018年11月13日 上午9:24
     */
    @Override
    public List<Map<String, Object>> selectTreeParentList(Map<String, Object> map, List<Map<String, String>> name) {
        List<Map<String, Object>> listParent = this.baseMapper.selectTreeParentList(map,name);
        List listId = new ArrayList();
        for(Map list : listParent){
            listId.add(list.get("id"));
        }
        List<Map<String, Object>> listChidren = new ArrayList<>();
        if(listId.size()>0 && listId != null){
            listChidren = this.baseMapper.selectTreeChildrenList(listId);
        }
        listParent = this.numberDebitAmount(listParent,listChidren);
        listParent.addAll(listChidren);
        return listParent;
    }

    /**
     * 计算借方和贷方总金额
     * @param listParent
     * @param listChidren
     * @return
     */
    public List<Map<String,Object>> numberDebitAmount(List<Map<String,Object>> listParent,List<Map<String, Object>> listChidren){
            for(Map list : listParent){
                double debit_amountNum = 0; //借方金额
                double credit_amountNum = 0; //贷方金额
                for(Map listChi : listChidren){
                    if(listChi.get("foreign_key").equals(list.get("id"))){
                        if(listChi.get("debit_amount") != null && !"".equals(listChi.get("debit_amount"))){
                            debit_amountNum += Double.parseDouble(String.valueOf(listChi.get("debit_amount")));
                        }
                        if(listChi.get("credit_amount") != null && !"".equals(listChi.get("credit_amount"))){
                            credit_amountNum += Double.parseDouble(String.valueOf(listChi.get("credit_amount")));
                        }
                    }
                }
                list.put("debit_amount",debit_amountNum);
                list.put("credit_amount",credit_amountNum);
            }
        return listParent;
    }

    /**
     * 获取凭证列表（未过账）
     * 2018-11-22
     *
     * @param map
     */
    @Override
    public List<Map<String, Object>> selectPostingScheduleList(Map<String, Object> map) {
        return this.baseMapper.selectPostingScheduleList(map);
    }

    /**
     * 获取用户所有信息
     */
    public List<User> selectUsers(){
        return this.baseMapper.selectUsers();
    }

    /**
     * 删除凭证
     *
     * @param entryId
     */
    @Override
    public void deleteEntry(List<Map<String,Object>> entryId) {this.baseMapper.deleteEntry(entryId);}
    /**
     * 查询搜索方案
     * @return
     */
    @Override
    public List<Map<String,Object>> selectSearchList(Map map) {
        return this.baseMapper.selectSearchList(map);
    }

    /**
     * 保存搜索方案
     * @param map
     */
    @Override
    public void saveSearch(Map map) {
        map.put("staff_id", ShiroKit.getUser().getId());
        this.baseMapper.saveSearch(map);
    }

    /**
     * 查询搜索方案赋值
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> selectSearchMap(Map map) {
        return this.baseMapper.selectSearchMap(map);
    }

    /**
     * 删除搜索方案
     * @param id
     */
    @Override
    public void deleteSearch(String id) {
        this.baseMapper.deleteSearch(id);
    }

    /**
     * 凭证统计
     *
     * @param map 搜索条件
     * @return
     */
    @Override
    public List<Map<String, Object>> findstatisticalList(Map<String, Object> map) {
        return this.baseMapper.findstatisticalList(map);
    }
    /**
     * 查询会计期间
     */
    @Override
    public String findAccountingPeriod(String period){
        return this.baseMapper.findAccountingPeriod(period);
    }
    /**
     * 查询当前期间所有数据是否以及进行损益
     */
    @Override
    public String profitLoss(String period) {
        return this.baseMapper.profitLoss(period);
    }

    /**
     * 反过账
     * @param period
     */
    @Override
    public Map<String, Object> antiSettlement(String period) {
        this.baseMapper.antiSettlement(period);//反过账
        this.baseMapper.deleteProfitEntry(period);//删除损益凭证根据会计期间
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("code",200);
        map.put("message","反过账成功!");
        return map;
    }

    /**
     * 期末反结帐
     */
    @Override
    public Map<String,Object> settleAccounts(String period){
        Map<String,Object> map = new HashMap<String, Object>();
        //判断该期间是否已结账
        String settleAccountsState = this.baseMapper.settleAccounts(period);
        if("1".equals(settleAccountsState )){//未结账
            map.put("code",201);
            map.put("message",period+"会计期间未结账!");
        }else if("0".equals(settleAccountsState)){//已结账  进行反结账处理
            this.baseMapper.limitOperation();//如果有未结账数据进行限制其操作权限
            this.baseMapper.deleteProfitEntry(period);// 去除损益凭证数据
            this.baseMapper.removeSettleAccountsState(period);//去除结账状态
            map.put("code",200);
            map.put("message","第"+period+"会计期间反结账成功!");
        }else{
            map.put("code",201);
            map.put("message",period+"会计期间未有数据!");
        }
        return map;
    }

    /**
     * 期末结转
     * @param period
     */
    @Override
    public Map<String, Object> settleAccountsDispose(String period) {
        Map<String,Object> map = new HashMap<String, Object>();
        int size = this.baseMapper.settleAccountsDispose(period);
        if(size >0){
            map.put("code",200);
            map.put("message","第"+period+"会计期间结账成功!");
        }
        return map;
    }

    /**
     * 判断执行那条业务分支
     *
     * @param state 业务类别 0结账
     * @param period 会计期间
     **/
    @Override
    public Map<String, Object> selectBranch(String state,String period) {
        String profit = this.profitLoss(period);//查询结账期间是否以及进行损益处理
        Map<String,Object> map = new HashMap<String, Object>();
        switch (state){
            case "0"://结账
                if(StringUtils.isEmpty(profit)){//判断是否进行损益处理
                    map.put("code",202);
                    map.put("message","第"+period+"会计期间还没有进行结账损益");
                    return map;
                }else{
                    map = this.settleAccountsDispose(period);//结账
                }
                break;
            case "1"://反指定会计期间的结账凭证
                map = this.settleAccounts(period);
                break;
            case "2"://反过账凭证
                map = this.antiSettlement(period);
                break;
            case "3"://年结账
                map = this.yearsAccounts();
                break;
            default: map.put("code",201);map.put("message","没有任何操作");
        }
        return map;
    }

    /**
     * 年结账
     */
    @Override
    public Map<String, Object> yearsAccounts() {
        Map<String,Object> map = new HashMap<String,Object>();
        int selectNotMouthSettlementNumber = this.baseMapper.selectNotMouthSettlement();//查询是否有数据未进行月结账
        if(selectNotMouthSettlementNumber >0){
            map.put("code",201);
            map.put("message","有数据未进行月结账,暂不支持年结账");
            return map;
        }
        int index = this.baseMapper.yearsAccounts();//反过账
        if(index!=0){
            map.put("code",200);
            map.put("message","年结账成功!");
        }else {
            map.put("code",500);
            map.put("message","年结账失败!");
        }
        return map;
    }


}
