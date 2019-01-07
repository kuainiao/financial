package cn.stylefeng.guns.modular.finance.service;

import cn.stylefeng.guns.core.common.node.SubjectNode;
import cn.stylefeng.guns.core.common.node.ZTreeNode;
import cn.stylefeng.guns.modular.finance.model.ExpenseAccount;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 科目代码表 服务类
 * </p>
 *
 * @author gongliming
 * @since 2018-11-21
 */
public interface IExpenseAccountService extends IService<ExpenseAccount> {
    /**
     * 按上级ID查出所有子集,或者根据上级编码
     * @return
     */
    List<ExpenseAccount> selectByParent(String code);

    /**
     * 按编码查询出详细信息
     * @param code
     * @return
     */
    ExpenseAccount selectByCode(String code);

    /**
     * 删除方法，实际上修改状态
     * @param id
     */
    void deleteByIdManual(Integer id);
    /**
     * 获取ztree的节点列表
     */
    List<SubjectNode> tree(String Pid);

    /**
     * 修改费用科目
     * @param expenseAccount
     */
    void edit(ExpenseAccount expenseAccount);

    /**
     * 获取ztree的节点列表，用于展示费用科目
     */
    List<ZTreeNode> treeEa();

    List<ExpenseAccount> selectByParentList(String id);
}
