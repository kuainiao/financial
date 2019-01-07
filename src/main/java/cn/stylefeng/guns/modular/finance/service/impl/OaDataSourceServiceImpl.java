package cn.stylefeng.guns.modular.finance.service.impl;

import cn.stylefeng.guns.core.common.annotion.DataSource;
import cn.stylefeng.guns.core.common.constant.DatasourceEnum;
import cn.stylefeng.guns.modular.finance.dao.OaDataMapper;
import cn.stylefeng.guns.modular.finance.model.OaDataSource;
import cn.stylefeng.guns.modular.finance.service.IOaDataSourceService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import java.util.List;
import java.util.Map;

@Service
public class OaDataSourceServiceImpl extends ServiceImpl<OaDataMapper,OaDataSource> implements IOaDataSourceService  {
    @Value("${mybatis.oaDataSource}")
    private String libraryName;//获取库名
    /**
     * 查询OA公司岗位表
     */
    @Override
    @DataSource(name = DatasourceEnum.DATA_SOURCE_BIZ)
    public List<Map<String, Object>> listMap(String state) {
        System.out.println(libraryName+"***");
        return this.baseMapper.listMap(state,libraryName);
    }

    /**
     * 查询部门
     *
     * @param bianma 公司编码
     * @return
     */
    @Override
    public List<Map<String, Object>> listMapCompany(String bianma) {
        return this.baseMapper.listMapCompany(bianma,libraryName);
    }

    /**
     * 部门职员
     *
     * @param bianma
     */
    @Override
    public List<Map<String, Object>> listMapClerk(String bianma) {
        return this.baseMapper.listMapClerk(bianma,libraryName);
    }

}
