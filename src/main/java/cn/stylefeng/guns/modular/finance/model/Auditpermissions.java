package cn.stylefeng.guns.modular.finance.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 审核权限控制表
 * </p>
 *
 * @author chenwenjie
 * @since 2018-11-19
 */
@TableName("cer_auditpermissions")
public class Auditpermissions extends Model<Auditpermissions> {

    private static final long serialVersionUID = 1L;

    private int id;
    /**
     * 制单人
     */
    @TableField("prepared_by")
    private String preparedBy;
    /**
     * 审核人
     */
    @TableField("approval_people")
    private String approvalPeople;

    /**
     *过账人
     */
    @TableField("posting_people")
    private String postingPeople;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPostingPeople() {
        return postingPeople;
    }

    public void setPostingPeople(String postingPeople) {
        this.postingPeople = postingPeople;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getApprovalPeople() {
        return approvalPeople;
    }

    public void setApprovalPeople(String approvalPeople) {
        this.approvalPeople = approvalPeople;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Auditpermissions{" +
        ", id=" + id +
        ", preparedBy=" + preparedBy +
        ", approvalPeople=" + approvalPeople +
        "}";
    }
}
