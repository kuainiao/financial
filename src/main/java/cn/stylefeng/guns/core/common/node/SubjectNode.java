package cn.stylefeng.guns.core.common.node;

/**
 * jquery ztree 插件的节点
 *
 * @author zxx
 * @date 2018年11月21日 下午8:25:14
 */
public class SubjectNode {
    private int subjectId;      //主键id

    private String id;         //节点id

    private String pId;        //父节点id

    private String name;     //节点名称

    private Boolean open;    //是否打开节点

    private Boolean checked; //是否被选中

    private String accounting;  //辅助核算 1 单一 2多核

    private String contactsDnit; //往来单位核算

    private String department;//部门核算

    private String staff;//职员核算

    public String getContactsDnit() {
        return contactsDnit;
    }

    public void setContacts_dnit(String contactsDnit) {
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

    public SubjectNode(){};

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Boolean getChecked() {
        return checked;
    }

    public String getAccounting() {
        return accounting;
    }

    public void setAccounting(String accounting) {
        this.accounting = accounting;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
    public SubjectNode(int subjectId, String id, String pId, String name, Boolean open, Boolean checked,String accounting,String contactsDnit,String department,String staff) {
        this.subjectId = subjectId;
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.open = open;
        this.checked = checked;
        this.accounting = accounting;
        this.contactsDnit = contactsDnit;
        this.department = department;
        this.staff = staff;
    }

    @Override
    public String toString() {
        return "SubjectNode{" +
                "subjectId=" + subjectId +
                ", id='" + id + '\'' +
                ", pId='" + pId + '\'' +
                ", name='" + name + '\'' +
                ", open=" + open +
                ", checked=" + checked +
                ", accounting=" + accounting +
                ", contactsDnit=" + contactsDnit +
                ", department=" + department +
                ", staff="+ staff +
                '}';
    }

    public static SubjectNode createParent() {
        SubjectNode zTreeNode = new SubjectNode();
        zTreeNode.setChecked(true);
        zTreeNode.setId("0");
        zTreeNode.setName("顶级");
        zTreeNode.setOpen(true);
        zTreeNode.setpId("0");
        return zTreeNode;
    }

}
