package com.ruoyi.project.family.marry.domain;

import com.ruoyi.common.utils.DateUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 家族婚配管理对象 f_marry
 * 
 * @author haibin
 * @date 2022-09-12
 */
public class FMarry extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    private Integer mId;

    /** 家族成员编号 */
    @Excel(name = "家族成员编号")
    private Integer memberId;

    /** 配偶编号 */
    @Excel(name = "配偶编号")
    private Integer mateId;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    //多表联合查询新增的字段
    /** 家族编号 */
    private Integer surnameId;
    /** 男方名字 */
    private String memName;
    /** 男方生日 */
    private Date memBirthday;
    /** 女方姓氏 */
    private String mateSurname;
    /** 女方名字 */
    private String mateName;
    /** 女方生日 */
    private Date mateBirthday;
    /** 家族姓氏 */
    private String clanSurname;
    /** 家族姓名称 */
    private String surnameName;
    /** 男方全名 */
    private String memFullName;
    /** 女方全名 */
    private String mateFullName;

    public void setmId(Integer mId)
    {
        this.mId = mId;
    }

    public Integer getmId()
    {
        return mId;
    }
    public void setMemberId(Integer memberId)
    {
        this.memberId = memberId;
    }

    public Integer getMemberId()
    {
        return memberId;
    }
    public void setMateId(Integer mateId)
    {
        this.mateId = mateId;
    }

    public Integer getMateId()
    {
        return mateId;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public Integer getSurnameId() {
        return surnameId;
    }

    public void setSurnameId(Integer surnameId) {
        this.surnameId = surnameId;
    }

    public String getMemName() {
        return memName;
    }

    public void setMemName(String memName) {
        this.memName = memName;
    }

    public Date getMemBirthday() {
        return memBirthday;
    }

    public void setMemBirthday(Date memBirthday) {
        this.memBirthday = memBirthday;
    }

    public String getMateSurname() {
        return mateSurname;
    }

    public void setMateSurname(String mateSurname) {
        this.mateSurname = mateSurname;
    }

    public String getMateName() {
        return mateName;
    }

    public void setMateName(String mateName) {
        this.mateName = mateName;
    }

    public Date getMateBirthday() {
        return mateBirthday;
    }

    public void setMateBirthday(Date mateBirthday) {
        this.mateBirthday = mateBirthday;
    }

    public String getClanSurname() {
        return clanSurname;
    }

    public void setClanSurname(String clanSurname) {
        this.clanSurname = clanSurname;
    }

    public String getSurnameName() {
        return surnameName;
    }

    public void setSurnameName(String surnameName) {
        this.surnameName = surnameName;
    }

    /** 配偶成员出生日期格式化用于解决前端表格显示相差一个小时的问题 */
    public String getMateBirthdayStr(){
        return DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, mateBirthday);
    }

    /** 家族出生日期格式化用于解决前端表格显示相差一个小时的问题 */
    public String getMemBirthdayStr(){
        return DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, memBirthday);
    }

    public String getMemFullName() {
        String memFullName1 = "-";
        if(this.clanSurname != null && this.memName != null)
        {
            memFullName1 = this.clanSurname + this.memName;
        }
        return memFullName1;
    }

    public void setMemFullName(String memFullName) {
        this.memFullName = memFullName;
    }

    public String getMateFullName() {
        String mateFullName1 = "-";
        if(this.mateSurname != null && this.mateName != null)
        {
            if(!this.mateName.equals("")){
                mateFullName1 = this.mateSurname + this.mateName;
            }else{
                mateFullName1 = this.mateSurname + "氏";
            }

        }
        return mateFullName1;
    }

    public void setMateFullName(String mateFullName) {
        this.mateFullName = mateFullName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("mId", getmId())
            .append("memberId", getMemberId())
            .append("mateId", getMateId())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
