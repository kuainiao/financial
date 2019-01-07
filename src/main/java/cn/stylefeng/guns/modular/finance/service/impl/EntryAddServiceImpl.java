package cn.stylefeng.guns.modular.finance.service.impl;

import cn.stylefeng.guns.modular.finance.dao.EntryGlmMapper;
import cn.stylefeng.guns.modular.finance.model.Entry;
import cn.stylefeng.guns.modular.finance.model.EntrySchedule;
import cn.stylefeng.guns.modular.finance.service.IEntryAddService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 凭证录入表 服务实现类
 * </p>
 *
 * @author chenwenjie
 * @since 2018-11-12
 */
@Service
@Transactional
public class EntryAddServiceImpl extends ServiceImpl<EntryGlmMapper, Entry> implements IEntryAddService {

    /**
     * 数据处理
     *将前台传过来的数据封装成insert语句理想形式
     * @param list
     * @return
     */
    public List<EntrySchedule> setEntryValues(List<EntrySchedule> list, Entry entry) {
        List<EntrySchedule> esList = new ArrayList<EntrySchedule>();
        int i = 0;//定义一个常量，用于凭证附表分组排序
        for (EntrySchedule entrySchedule : list) {//将前台传来凭证附表进行循环
            i++;
            entrySchedule.setForeignKey(entry.getId());//添加外键
            entrySchedule.setRownum(i);
            if (entrySchedule.getDebitAmount() != null && !entrySchedule.getDebitAmount().equals("")) {//判断借方金额是否为空，用于添加原币金额
                entrySchedule.setOriginalCurrency(entrySchedule.getDebitAmount());//原币金额
            } else {
                entrySchedule.setOriginalCurrency(entrySchedule.getCreditAmount());
            }
            if (entrySchedule.getExpenseAccount() != null && !entrySchedule.getExpenseAccount().equals("")) {
                int index = entrySchedule.getExpenseAccount().indexOf(" ");//获取第一个空格的下标
                //根据下标截取空格前面的字符，存入数据库
                entrySchedule.setExpenseAccount(entrySchedule.getExpenseAccount().substring(0, index == -1 ? entrySchedule.getExpenseAccount().length() : index));
                esList.add(entrySchedule);
            }
        }
        return esList;
    }

    /**
     * 新增凭证
     *
     * @param entry
     * @return
     */
    @Override
    public int insertGlm(Entry entry) {
        entry.setCreateTime(new Date());
        //新增凭证主表
        int entryRum = this.baseMapper.insertGlm(entry);
        List<EntrySchedule> list = entry.getEntrySchedule();
        //数据处理
        list = this.setEntryValues(list, entry);
        //新增凭证附表
        int scheduleRum = this.baseMapper.insertGlmSchedule(list);
        //全部新增成功返回状态1
        if (entryRum > 0 && scheduleRum > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 修改凭证数据
     *
     * @param entry
     * @return
     */
    @Override
    public void updateEntryAndEntrySchedule(Entry entry, String entryInitial, String entryAfter) {
        this.baseMapper.updateEntry(entry); //修改主表数据
        String[] entryScheInitial = entryInitial.replace("[", "").replace("]", "").split(",");
        String[] entryScheAfter = entryAfter.replace("[", "").replace("]", "").split(","); //获取
        List<EntrySchedule> list = entry.getEntrySchedule(); //获取副数据集合
        boolean isEntryScheduleId = Arrays.equals(entryScheInitial, entryScheAfter);
        if (isEntryScheduleId) { //副数据id不变
            this.updateEntryScheduleUntis(list, entry); //修改
        } else {//副数据id改变
            List<Integer> ids = getDifferentId(entryScheInitial, entryScheAfter); //获取减少的id值
            if (ids != null && ids.size() > 0) {
                this.baseMapper.entryScheduleDelete(ids);  //执行附表删除
            }
            this.updateEntryScheduleUntis(list, entry); //修改
        }
    }

    /**
     * 执行修改包装方法
     *
     * @param list
     * @param entry
     */
    public void updateEntryScheduleUntis(List<EntrySchedule> list, Entry entry) {
        List<EntrySchedule> newSchedule = new ArrayList<>();  //新增数据集合
        list = this.setEntryValues(list, entry);
        entry.setEntrySchedule(list);
        for (EntrySchedule scheduleList : list) {
            if (scheduleList.getId() == null) { //若id为空则新增
                scheduleList.setForeignKey(entry.getId());
                newSchedule.add(scheduleList);
            } else {
                this.baseMapper.updateEntrySchedule(scheduleList); //修改附表数据
            }
        }
        if (newSchedule != null && newSchedule.size() > 0) {
            this.baseMapper.insertGlmSchedule(newSchedule); //新增附表数据
        }
    }

    /**
     * 获取修改中减少的id值
     *
     * @param entryInitial
     * @param entryAfter
     * @return
     */
    public List<Integer> getDifferentId(String[] entryInitial, String[] entryAfter) {
        List<String> listInitial = Arrays.asList(entryInitial);
        List<String> listAfter = Arrays.asList(entryAfter);
        List<Integer> newList = new ArrayList<>();
        for (String t : listInitial) {
            if (!listAfter.contains(t)) {
                newList.add(Integer.valueOf(t.replace("\"", "")));  //去除字符串的双引号
            }
        }
        return newList;
    }

    /**
     * 根据凭证ID获取凭证信息
     *
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> selectEntryIdScheduleList(Integer Id) {
        List<Map<String, Object>> entryIdScheduleList = this.baseMapper.selectEntryIdScheduleList(Id); //根据凭证ID获取凭证信息
        List<Map<String, Object>> accountAll = this.selectAccountAll(); //获取所有费用科目
        for (Map list : entryIdScheduleList) {
            List<Map<String, Object>> newAccount = new ArrayList<>();
            newAccount = this.getParentAccountName(accountAll,newAccount,String.valueOf(list.get("superior_code")));  //递归获取上级科目
            String accunt = list.get("expense_account") + "   ";
            for (int i = 0; i < newAccount.size(); i++) {                   //拼接费用字符串去前台展示  1011 资产-固定资产
                accunt += newAccount.get(i).get("subiect_name") + " - " ;
            }
            accunt += list.get("subiect_name");                             //拼接多核算字符串 公司-部门-员工
            if(ToolUtil.isNotEmpty(list.get("contacts_dnit"))){
                accunt += " - " + list.get("contacts_dnit");
            }
            if(ToolUtil.isNotEmpty(list.get("department"))){
                accunt += " - " + list.get("department");
            }
            if(ToolUtil.isNotEmpty(list.get("staff"))){
                accunt += " - " + list.get("staff");
            }
            list.put("expense_account", accunt);
        }
        return entryIdScheduleList;
    }


    /**
     * 获取所有费用科目
     */
    public List<Map<String,Object>> selectAccountAll(){
       return this.baseMapper.selectAccountAll();
    }

    /**
     * 递归获取上级费用科目
     * @param accountAll
     * @param newAccount
     * @param pCode
     * @return
     */
    public List<Map<String,Object>> getParentAccountName(List<Map<String,Object>> accountAll,List<Map<String,Object>> newAccount,String pCode){
        int i = 0;
        for (Map list : accountAll){
            if(pCode.equals(list.get("account_code"))){
                newAccount.add(i,list);
                i++;
                this.getParentAccountName(accountAll,newAccount,String.valueOf(list.get("superior_code")));
            }
        }
        return newAccount;
    }


    /*@Override
    public List<Entry> selectBySettlement(String document) {
        return this.baseMapper.selectBySettlement(document);
    }*/

    /**
     *添加凭证展示提供数据支持
     * @return
     */
    @Override
    public Map<String,Object> getEntry(String document){
        Map<String,Object> map = new HashMap<>();
        map.put("document",document);
        map.put("settlement_mouth_state","1");
        List<Entry> listEntry = this.selectNotPosting(map);
        int i = 1;
        int sequence_number = 1;
        if(ToolUtil.isNotEmpty(listEntry)){  //未结账
            map.put("period",Integer.valueOf(listEntry.get(0).getPeriod())); //获取最新一条未结账期间
            for (Entry es : listEntry) {
                if(es.getDocumentWord()==1){
                    i++;
                }
            }
            sequence_number = listEntry.size()+1;
        }else if(ToolUtil.isEmpty(listEntry)){ //已结账
            map.put("settlement_state","0");
            map.put("settlement_mouth_state","0");
            listEntry = this.selectNotPosting(map);
            if(ToolUtil.isNotEmpty(listEntry)){
                map.put("period",Integer.valueOf(listEntry.get(0).getPeriod())+1);//获取最新一条已结账期间
                Calendar cal = Calendar.getInstance();
                int month = cal.get(Calendar.MONTH )+1; //获取当前月份
                int year = cal.get(Calendar.YEAR);  //获取当前年份
                cal.setTime(listEntry.get(0).getBusinessDate());
                if(cal.get(Calendar.MONTH) == month && cal.get(Calendar.YEAR) == year){
                    map.put("period",month);
                }
            }else{
                map.put("period","1");
            }
        }
        map.put("document_number",i+"");
        map.put("sequence_number",sequence_number+"");
        return map;
    }

    /**
     * 获取未过帐或已过帐数据
     */
    public List<Entry> selectNotPosting(Map<String,Object> map ){
        return this.baseMapper.selectNotPosting(map);
    }

    @Override
    public void updatEntry(List<Integer> id) {
        this.baseMapper.updatEntry(id);
    }

}
