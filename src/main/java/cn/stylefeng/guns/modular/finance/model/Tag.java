package cn.stylefeng.guns.modular.finance.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

@TableName("cer_tag")
public class Tag extends Model<Tag> {
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @TableField("id")
    private Integer id;
    /**
     * 外键id(凭证录入表主键id)
     */
    @TableField("foreign_key_id")
    private Integer foreign_key_id;
    /**
     * 审批状态''0''未审批 ''1''已审批
     */
    @TableField("approval_status")
    private Integer approval_status;
    /**
     * 审批人
     */
    @TableField("approval_people")
    private String approval_people;
    //过账状态''0''未过账 ''1''已过账
    @TableField("posting_status")
    private Integer posting_status;
    //过账人
    @TableField("posting_people")
    private String posting_people;

    public Integer getForeign_key_id() {
        return foreign_key_id;
    }

    public void setForeign_key_id(Integer foreign_key_id) {
        this.foreign_key_id = foreign_key_id;
    }

    public Integer getApproval_status() {
        return approval_status;
    }

    public void setApproval_status(Integer approval_status) {
        this.approval_status = approval_status;
    }

    public String getApproval_people() {
        return approval_people;
    }

    public void setApproval_people(String approval_people) {
        this.approval_people = approval_people;
    }

    public Integer getPosting_status() {
        return posting_status;
    }

    public void setPosting_status(Integer posting_status) {
        this.posting_status = posting_status;
    }

    public String getPosting_people() {
        return posting_people;
    }

    public void setPosting_people(String posting_people) {
        this.posting_people = posting_people;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "foreign_key_id=" + foreign_key_id +
                ", approval_status=" + approval_status +
                ", approval_people='" + approval_people + '\'' +
                ", posting_status=" + posting_status +
                ", posting_people='" + posting_people + '\'' +
                '}';
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
