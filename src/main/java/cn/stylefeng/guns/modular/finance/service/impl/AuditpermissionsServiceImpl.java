package cn.stylefeng.guns.modular.finance.service.impl;

import cn.stylefeng.guns.modular.finance.model.Auditpermissions;
import cn.stylefeng.guns.modular.finance.dao.AuditpermissionsMapper;
import cn.stylefeng.guns.modular.finance.service.IAuditpermissionsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 审核权限控制表 服务实现类
 * </p>
 *
 * @author chenwenjie
 * @since 2018-11-19
 */
@Service
public class AuditpermissionsServiceImpl extends ServiceImpl<AuditpermissionsMapper, Auditpermissions> implements IAuditpermissionsService {
    @Override
    public List<Auditpermissions> findAuditPermissions(String name,Map<String,String> map){
        return this.baseMapper.findAuditPermissions(name,map);
    }
    @Override
    public List<String> prepared_by_list() {
        return this.baseMapper.prepared_by_list();
    }

    @Override
    public List<Map<String,String>> findAuditName(String name,Map<String,Object> map) {
        return this.baseMapper.findAuditName(name,map);
    }

    @Override
    public Map<String, String> findAudit(String name) {
        return this.baseMapper.findAudit(name); }

    /**
     * 更新权限配置信息
     *
     * @param auditpermissions
     */
    @Override
    public void updatePosting(Auditpermissions auditpermissions) {this.baseMapper.updatePosting(auditpermissions);}

    /**
     * 更新审核配置信息
     *
     * @param auditpermissions
     */
    @Override
    public void updateApproval(Auditpermissions auditpermissions) {
        this.baseMapper.updateApproval(auditpermissions);
    }

    /**
     * 查询审核权限
     *
     * @param auditpermissionsId
     */
    @Override
    public String findApproval(Integer auditpermissionsId) {
        return this.baseMapper.findApproval(auditpermissionsId);
    }

    /**
     * 查询过账权限
     *
     * @param auditpermissionsId
     */
    @Override
    public String findPosting(Integer auditpermissionsId) {
        return this.baseMapper.findPosting(auditpermissionsId);
    }

    /**
     * 删除过账
     *
     * @param map
     */
    @Override
    public void deletePosting(Map<String, String> map) {
        this.baseMapper.deletePosting(map);
    }

    /**
     * 删除审核
     *
     * @param map
     */
    @Override
    public void deleApproval(Map<String, String> map) {
        this.baseMapper.deleApproval(map);
    }

}
