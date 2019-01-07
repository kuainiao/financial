package cn.stylefeng.guns.modular.finance.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 科目代码表
 * </p>
 *
 * @author gongliming
 * @since 2018-11-21
 */
@TableName("cer_expense_account")
public class ExpenseAccount extends Model<ExpenseAccount> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 科目代码
     */
    @TableField("account_code")
    private String accountCode;
    /**
     * 科目名称
     */
    @TableField("subiect_name")
    private String subiectName;
    /**
     * 上级代码
     */
    @TableField("superior_code")
    private String superiorCode;
    /**
     * 科目类别名称
     */
    @TableField("account_category")
    private String accountCategory;
    /**
     * 余额方向
     */
    @TableField("balance_direction")
    private String balanceDirection;
    /**
     * 辅助核算 1 单一 2多核
     */
    @TableField("other_accounting")
    private Integer otherAccounting;
    /**
     * 往来单位核算
     */
    @TableField("contacts_dnit")
    private String contactsDnit;
    /**
     * 部门核算
     */
    private String department;
    /**
     * 职员核算
     */
    private String staff;
    /**
     * 本条数据状态 0 显示 1删除
     */
    @TableField("STATUS")
    private String status;
    /**
     * 科目类型0贸易 非贸易1
     */
    @TableField("cost_type")
    private String costType;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getSubiectName() {
        return subiectName;
    }

    public void setSubiectName(String subiectName) {
        this.subiectName = subiectName;
    }

    public String getSuperiorCode() {
        return superiorCode;
    }

    public void setSuperiorCode(String superiorCode) {
        this.superiorCode = superiorCode;
    }

    public String getAccountCategory() {
        return accountCategory;
    }

    public void setAccountCategory(String accountCategory) {
        this.accountCategory = accountCategory;
    }

    public String getBalanceDirection() {
        return balanceDirection;
    }

    public void setBalanceDirection(String balanceDirection) {
        this.balanceDirection = balanceDirection;
    }

    public Integer getOtherAccounting() {
        return otherAccounting;
    }

    public void setOtherAccounting(Integer otherAccounting) {
        this.otherAccounting = otherAccounting;
    }

    public String getContactsDnit() {
        return contactsDnit;
    }

    public void setContactsDnit(String contactsDnit) {
        this.contactsDnit = contactsDnit;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ExpenseAccount{" +
        ", id=" + id +
        ", accountCode=" + accountCode +
        ", subiectName=" + subiectName +
        ", superiorCode=" + superiorCode +
        ", accountCategory=" + accountCategory +
        ", balanceDirection=" + balanceDirection +
        ", otherAccounting=" + otherAccounting +
        ", contactsDnit=" + contactsDnit +
        ", department=" + department +
        ", staff=" + staff +
        ", status=" + status +
        "}";
    }
}
