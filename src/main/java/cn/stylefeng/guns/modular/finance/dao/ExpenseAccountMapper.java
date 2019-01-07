package cn.stylefeng.guns.modular.finance.dao;

import cn.stylefeng.guns.core.common.node.SubjectNode;
import cn.stylefeng.guns.core.common.node.ZTreeNode;
import cn.stylefeng.guns.modular.finance.model.ExpenseAccount;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 科目代码表 Mapper 接口
 * </p>
 *
 * @author gongliming
 * @since 2018-11-21
 */
public interface ExpenseAccountMapper extends BaseMapper<ExpenseAccount> {
    /**
     * 按上级ID查询费用科目详情
     * @param code
     * @return
     */
    List<ExpenseAccount> selectByParent(String code);

    /**
     * 按编码查询费用科目
     * @param code
     * @return
     */
    ExpenseAccount selectByCode(String code);

    /**
     * 删除费用科目，实际修改状态
     * @param id
     */
    void deleteByIdManual(Integer id);
    /**
     * 获取ztree的节点列表
     */
    List<SubjectNode> tree(String pid);

    /**
     * 修改费用科目
     * @param expenseAccount
     */
    void edit(ExpenseAccount expenseAccount);

    /**
     * 获取ztree的节点列表，用于展示费用科目
     */
    List<ZTreeNode> treeEa();

    /**
     * 通过上级ID查询出下面所有子集不包括本身
     * @param id
     * @return
     */
    List<ExpenseAccount> selectByParentList(@Param("id") String id);
}
