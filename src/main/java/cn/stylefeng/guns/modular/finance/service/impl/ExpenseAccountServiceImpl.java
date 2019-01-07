package cn.stylefeng.guns.modular.finance.service.impl;

import cn.stylefeng.guns.core.common.node.SubjectNode;
import cn.stylefeng.guns.core.common.node.ZTreeNode;
import cn.stylefeng.guns.modular.finance.dao.ExpenseAccountMapper;
import cn.stylefeng.guns.modular.finance.model.ExpenseAccount;
import cn.stylefeng.guns.modular.finance.service.IExpenseAccountService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 科目代码表 服务实现类
 * </p>
 *
 * @author gongliming
 * @since 2018-11-21
 */
@Service
public class ExpenseAccountServiceImpl extends ServiceImpl<ExpenseAccountMapper, ExpenseAccount> implements IExpenseAccountService {

    @Override
    public void deleteByIdManual(Integer id) {
        this.baseMapper.deleteByIdManual(id);
    }

    /**
     * 按照上级ID查询费用科目
     * @param code
     * @return
     */
    @Override
    public List<ExpenseAccount> selectByParent(String code) {
        return this.baseMapper.selectByParent(code);
    }

    /**
     * 按照编码查询费用科目
     * @param code
     * @return
     */
    @Override
    public ExpenseAccount selectByCode(String code) {
        return this.baseMapper.selectByCode(code);
    }

    /**
     * 查询费用科目
     * @return
     */
    @Override
    public List<SubjectNode> tree(String pId) {
        List<SubjectNode> ztree = new ArrayList<>();
        List<SubjectNode> ztreeAll = this.baseMapper.tree(null);
        ztree = assembleTree(ztreeAll, ztree, pId);
        return ztree;
//        List<SubjectNode> treeAll = new ArrayList<>();
//        List<SubjectNode> ztree = this.baseMapper.tree(pId);
//        treeAll.addAll(ztree);
//        if (ztree != null && ztree.size() > 0){
//            for (SubjectNode node : ztree) {
//                List<SubjectNode> nodes = tree(node.getId());
//                treeAll.addAll(nodes);
//            }
//        }
//        return treeAll;
    }

    /**
     * 费用科目基础修改
     * @param expenseAccount
     */
    @Override
    public void edit(ExpenseAccount expenseAccount) {
        this.baseMapper.edit(expenseAccount);
    }

    @Override
    public List<ZTreeNode> treeEa() {
        return this.baseMapper.treeEa();
    }

    @Override
    public List<ExpenseAccount> selectByParentList(String id) {
        return this.baseMapper.selectByParentList(id);
    }

    /**
     * 数据拼装
     * @param ztreeAll
     * @param ztree
     * @param pId
     * @return
     */
    public List<SubjectNode> assembleTree(List<SubjectNode> ztreeAll,List<SubjectNode> ztree,String pId){
        for(SubjectNode ztreeNode : ztreeAll){
            if(ztreeNode.getpId().equals(pId)){
                ztreeNode.setName(ztreeNode.getId()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ztreeNode.getName());
                ztree.add(ztreeNode);
                assembleTree(ztreeAll,ztree, ztreeNode.getId());
            }
        }
        return ztree;
    }
}
