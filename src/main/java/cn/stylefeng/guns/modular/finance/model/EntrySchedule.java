package cn.stylefeng.guns.modular.finance.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 凭证录入附表，用于储存摘要，会计科目，借贷金额
 * </p>
 *
 * @author gongliming
 * @since 2018-11-12
 */
@TableName("cer_entry_schedule")
public class EntrySchedule extends Model<EntrySchedule> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 凭证摘要
     */
    @TableField("summary")
    private String summary;

    /**
     * 会计科目
     */
    @TableField("expense_account")
    private String expenseAccount;

    /**
     * 借方金额
     */
    @TableField("debit_amount")
    private Double debitAmount;

    /**
     * 贷方金额
     */
    @TableField("credit_amount")
    private Double creditAmount;
    /**
     * 分组排序
     */
    @TableField("rownum")
    private Integer rownum;

    /**
     * 外键
     */
    @TableField("foreign_key")
    private Integer foreignKey;

    /**
     * 原币金额
     */
    @TableField("original_currency")
    private Double originalCurrency;


    /**
     * 多核算公司
     */
    @TableField("contactsDnit")
    private String contactsDnit;
    /**
     * 部门核算
     */
    @TableField("department")
    private String department;
    /**
     * 职员核算
     */
    @TableField("staff")
    private String staff;

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

    public Integer getId() {
        return id;
    }

    public String getSummary() {
        return summary;
    }

    public String getExpenseAccount() {
        return expenseAccount;
    }

    public Double getDebitAmount() {
        return debitAmount;
    }

    public Double getCreditAmount() {
        return creditAmount;
    }

    public Integer getRownum() {
        return rownum;
    }

    public Integer getForeignKey() {
        return foreignKey;
    }

    public Double getOriginalCurrency() {
        return originalCurrency;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setExpenseAccount(String expenseAccount) {
        this.expenseAccount = expenseAccount;
    }

    public void setDebitAmount(Double debitAmount) {
        this.debitAmount = debitAmount;
    }

    public void setCreditAmount(Double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public void setRownum(Integer rownum) {
        this.rownum = rownum;
    }

    public void setForeignKey(Integer foreignKey) {
        this.foreignKey = foreignKey;
    }

    public void setOriginalCurrency(Double originalCurrency) {
        this.originalCurrency = originalCurrency;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "EntrySchedule{" +
                "id=" + id +
                ", summary=" + summary +
                ", expenseAccount=" + expenseAccount +
                ", debitAmount=" + debitAmount +
                ", creditAmount=" + creditAmount +
                ", rownum=" + rownum +
                ", foreignKey=" + foreignKey +
                ", originalCurrency=" + originalCurrency +
                ", contactsDnit="+ contactsDnit+
                ", department="+ department+
                ", staff="+ staff+
                '}';
    }
}
