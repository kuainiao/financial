package cn.stylefeng.guns.modular.finance.service;

import cn.stylefeng.guns.modular.finance.model.Auditpermissions;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 审核权限控制表 服务类
 * </p>
 *
 * @author chenwenjie
 * @since 2018-11-19
 */
public interface IAuditpermissionsService extends IService<Auditpermissions> {

   List<Auditpermissions> findAuditPermissions(String name,Map<String,String> map);

   List<String> prepared_by_list();

   List<Map<String,String>> findAuditName(String name,Map<String,Object> map);

   Map<String,String> findAudit(String name);

   /**
    * 更新过账权限配置信息
    * @param auditpermissions
    */
   void updatePosting(Auditpermissions auditpermissions);

   /**
    * 更新审核配置信息
    */
   void updateApproval(Auditpermissions auditpermissions);

   /**
    *查询审核权限
    */
   String findApproval(Integer auditpermissionsId);
   /**
    * 查询过账权限
    */
   String findPosting(Integer auditpermissionsId);

   /**
    * 删除过账
    */
   void deletePosting(Map<String,String> map);
   /**
    * 删除审核
    */
   void deleApproval(Map<String,String> map);
}
