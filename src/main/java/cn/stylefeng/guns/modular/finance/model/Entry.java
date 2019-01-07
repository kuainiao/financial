package cn.stylefeng.guns.modular.finance.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 凭证录入表
 * </p>
 *
 * @author chenwenjie
 * @since 2018-11-12
 */
@TableName("cer_entry")
public class Entry extends Model<Entry> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 业务日期
     */
    @TableField("business_date")
    private Date businessDate;
    /**
     * 顺序号，所有凭证信息顺序号，结算后清零
     */
    @TableField("sequence_number")
    private Integer sequenceNumber;
    /**
     * 凭证字，关联字典表，一般为，记，收，付，转
     */
    @TableField("document_word")
    private Integer documentWord;
    /**
     * 跟凭证字关联，所有数目相加等于顺序号
     */
    @TableField("document_number")
    private Integer documentNumber;
    /**
     * 单据数，所录凭证有多少张单据
     */
    @TableField("documents_number")
    private Integer documentsNumber;
    /**
     * 制单人，与登入者主键
     */
    @TableField("prepared_by")
    private String preparedBy;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 结算状态,1为未结算，0为已结算
     */
    @TableField("settlement_state")
    private String settlementState;

    /**
     * 结算状态,1为未结算，0为已结算
     */
    @TableField("settlement_mouth_state")
    private String settlementMouthState;

    /**
     * 总金额
     */
    @TableField("lump_sum")
    private Double lumpSum;

    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;

    /**
     * 会计期间
     * @return
     */
    @TableField("period")
    private int period;
    /**
     * 录入表附表，用于储存会计科目等
     */
    private List<EntrySchedule> entrySchedule;

    public List<EntrySchedule> getEntrySchedule() {
        return entrySchedule;
    }

    public void setEntrySchedule(List<EntrySchedule> entrySchedule) {
        this.entrySchedule = entrySchedule;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Integer getDocumentWord() {
        return documentWord;
    }

    public void setDocumentWord(Integer documentWord) {
        this.documentWord = documentWord;
    }

    public Integer getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(Integer documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Integer getDocumentsNumber() {
        return documentsNumber;
    }

    public void setDocumentsNumber(Integer documentsNumber) {
        this.documentsNumber = documentsNumber;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSettlementState() {
        return settlementState;
    }

    public void setSettlementState(String settlementState) {
        this.settlementState = settlementState;
    }

    public Double getLumpSum() {
        return lumpSum;
    }

    public void setLumpSum(Double lumpSum) {
        this.lumpSum = lumpSum;
    }

    public String getSettlementMouthState() {
        return settlementMouthState;
    }

    public void setSettlementMouthState(String settlementMouthState) {
        this.settlementMouthState = settlementMouthState;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "id=" + id +
                ", businessDate=" + businessDate +
                ", sequenceNumber=" + sequenceNumber +
                ", documentWord=" + documentWord +
                ", documentNumber=" + documentNumber +
                ", documentsNumber=" + documentsNumber +
                ", preparedBy='" + preparedBy + '\'' +
                ", createTime=" + createTime +
                ", settlementState='" + settlementState + '\'' +
                ", settlementMouthState='" + settlementMouthState + '\'' +
                ", lumpSum=" + lumpSum +
                ", remarks='" + remarks + '\'' +
                ", period=" + period +
                ", entrySchedule=" + entrySchedule +
                '}';
    }
}
