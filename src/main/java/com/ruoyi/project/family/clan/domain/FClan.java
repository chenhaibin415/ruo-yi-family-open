package com.ruoyi.project.family.clan.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 家族对象 f_clan
 * 
 * @author haibin
 * @date 2022-08-03
 */
public class FClan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 家族编号 */
    private Integer cId;

    /** 家族编码 */
    @Excel(name = "家族编码")
    private String cCode;

    /** 家族姓氏 */
    @Excel(name = "家族姓氏")
    private String surname;

    /** 家族名称 */
    @Excel(name = "家族名称")
    private String name;

    /** 家族简介 */
    private String intro;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setcId(Integer cId)
    {
        this.cId = cId;
    }

    public Integer getcId()
    {
        return cId;
    }
    public void setcCode(String cCode)
    {
        this.cCode = cCode;
    }

    public String getcCode()
    {
        return cCode;
    }
    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public String getSurname()
    {
        return surname;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setIntro(String intro)
    {
        this.intro = intro;
    }

    public String getIntro()
    {
        return intro;
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
            .append("cId", getcId())
            .append("cCode", getcCode())
            .append("surname", getSurname())
            .append("name", getName())
            .append("intro", getIntro())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
