package cn.stylefeng.guns.modular.finance.service;

import cn.stylefeng.guns.modular.finance.model.OaDataSource;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

public interface IOaDataSourceService extends IService<OaDataSource> {
    /**
     * 查询OA公司岗位表
     */
    public List<Map<String, Object>>  listMap(String state);

    /**
     * 查询部门
     * @param bianma 公司编码
     * @return
     */
    public List<Map<String, Object>> listMapCompany(String bianma);

    /**
     * 部门职员
     */
    public List<Map<String,Object>> listMapClerk(String bianma);
}
