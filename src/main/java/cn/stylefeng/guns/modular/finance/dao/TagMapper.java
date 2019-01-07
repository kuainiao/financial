package cn.stylefeng.guns.modular.finance.dao;

import cn.stylefeng.guns.modular.finance.model.Tag;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 凭证标记表 Mapper 接口
 * </p>
 *
 * @author chenwenjie
 * @since 2018-11-12
 */
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 审批
     */
    void approval(@Param("listMap") List<Map<String,Object>> Listmap);

    /**
     * 反审
     */
    void reverseApproval(@Param("listMap") List<Map<String,Object>> lisMap);

    /**
     * 过账
     */
    void updatePosting(@Param("name")String name ,@Param("entryId")String entryId);
}
