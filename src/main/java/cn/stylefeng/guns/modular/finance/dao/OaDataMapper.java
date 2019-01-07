package cn.stylefeng.guns.modular.finance.dao;
import cn.stylefeng.guns.modular.finance.model.OaDataSource;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Oa数据库Mapper
 */
public interface OaDataMapper extends BaseMapper<OaDataSource> {
    /**
     * 查询OA公司岗位表
     */
   public List<Map<String, Object>> listMap(@Param("state") String state,@Param("libraryName") String libraryName);

    /**
     * 部门
     * @param bianma
     * @return
     */
   public List<Map<String,Object>> listMapCompany(@Param("bianma")String bianma,@Param("libraryName")String libraryName);

    /**
     * 部门职员
     */
    public List<Map<String,Object>> listMapClerk(@Param("bianma")String bianma,@Param("libraryName") String libraryName);
}
