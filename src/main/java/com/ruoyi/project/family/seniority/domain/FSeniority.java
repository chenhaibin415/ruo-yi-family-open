package com.ruoyi.project.family.seniority.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 辈分管理对象 f_seniority
 * 
 * @author haibin
 * @date 2022-08-02
 */
public class FSeniority extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 辈分编号 */
    private Integer sId;

    /** 家族编号 */
    @Excel(name = "家族编号")
    private Integer surnameId;

    /** 家族名称 */
    @Excel(name = "家族名称")
    private String surnameName;

    /** 辈序号 */
    @Excel(name = "辈序号")
    private Integer numSort;

    /** 辈名称 */
    @Excel(name = "辈名称")
    private String numTag;

    /** 行辈字 */
    @Excel(name = "行辈字")
    private String cnTag;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setsId(Integer sId)
    {
        this.sId = sId;
    }

    public Integer getsId()
    {
        return sId;
    }

    public void setSurnameId(Integer surnameId)
    {
        this.surnameId = surnameId;
    }

    public Integer getSurnameId()
    {
        return surnameId;
    }

    public String getSurnameName() {
        return surnameName;
    }

    public void setSurnameName(String surnameName) {
        this.surnameName = surnameName;
    }

    public void setNumSort(Integer numSort)
    {
        this.numSort = numSort;
    }

    public Integer getNumSort()
    {
        return numSort;
    }
    public void setNumTag(String numTag)
    {
        this.numTag = numTag;
    }

    public String getNumTag()
    {
        return numTag;
    }
    public void setCnTag(String cnTag)
    {
        this.cnTag = cnTag;
    }

    public String getCnTag()
    {
        return cnTag;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("sId", getsId())
            .append("surnameId", getSurnameId())
            .append("surnameName", getSurnameName())
            .append("numSort", getNumSort())
            .append("numTag", getNumTag())
            .append("cnTag", getCnTag())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
