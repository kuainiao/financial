package cn.stylefeng.guns.modular.finance.dao;

import cn.stylefeng.guns.modular.finance.model.Auditpermissions;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 审核权限控制表 Mapper 接口
 * </p>
 *
 * @author chenwenjie
 * @since 2018-11-19
 */
public interface AuditpermissionsMapper extends BaseMapper<Auditpermissions> {
    /**
     * 查询本人审核权限配置数据
     * @param name 制单人名字
     * @return
     */
    List<Auditpermissions> findAuditPermissions(@Param(value = "name") String name,@Param(value="map")Map<String,String> map);
    /**
     * 获取用户名
     * @return
     */
    List<String> prepared_by_list();

    /**
     * 根据登录用户获取到它配置的审核权限
     */
    List<Map<String,String>> findAuditName(@Param(value="name")String name,@Param(value="map")Map<String,Object> map);
    /**
     * 获取当前人添加的权限
     */
    Map<String,String> findAudit(String name);
    /**
     * 更新配置权限(审核)
     * @param auditpermissions
     */
    void updatePosting(Auditpermissions auditpermissions);

    /**
     * 更新审核配置权限
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
    void deletePosting(@Param(value="map")Map<String,String> map);
    /**
     * 删除审核
     */
    void deleApproval(@Param(value="map")Map<String, String> map);
}
