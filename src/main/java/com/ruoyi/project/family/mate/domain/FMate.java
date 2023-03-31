package com.ruoyi.project.family.mate.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.utils.DateUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 配偶成员对象 f_mate
 * 
 * @author haibin
 * @date 2022-08-13
 */
public class FMate extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /** 编号 */
    @Excel(name = "编号")
    private Integer mId;

    /** 家族编号 */
    @Excel(name = "家族编号")
    private Integer surnameId;

    /** 家族名称 */
    @Excel(name = "家族名称")
    private String surnameName;

    /** 姓氏 */
    @Excel(name = "姓氏")
    private String surname;

    /** 名字 */
    @Excel(name = "名字")
    private String name;

    /** 农历标识（0代表阳历 1代表农历） */
    @Excel(name = "农历标识", readConverterExp = "0=阳历,1=农历")
    private String lunarFlag;

    /** 出生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthday;

    /** 出生日期(大写) */
    private String birthdayMark;

    /** 出生时辰 */
    private String barthHour;

    /** 头像url */
    private String headUrl;

    /** 父亲姓名 */
    @Excel(name = "父亲姓名")
    private String fatherName;

    /** 家庭地址 */
    @Excel(name = "家庭地址")
    private String address;

    /** 手机号 */
    private String phoneNum;

    /** 微信号 */
    private String wechatNum;

    /** 状态标识（0代表未登记 1代表已登记） */
    @Excel(name = "状态标识", readConverterExp = "0=未登记,1=已登记")
    private String statusFlag;

    /** 死亡标识（0代表健在 1代表死亡） */
    @Excel(name = "死亡标识", readConverterExp = "0=健在,1=死亡")
    private String deathFlag;

    /** 死亡日期 */
    private Date deathday;

    /** 死亡日期(大写) */
    private String deathdayMark;

    /** 殡葬地址 */
    private String funeralAddr;

    /** 享年岁数 */
    private Integer ageofDeath;

    /** 个人简介 */
    private String intro;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /**************************扩展属性********************************/

    /** 不匹配编号 */
    private List<Integer> notInMIdList;

    /** 配偶（丈夫）编号集合字符串 */
    private String memberIds;

    /** 配偶（丈夫）姓名字符串 */
    private String memberNames;

    /** 按姓名完全匹配 */
    private String selectName;
    /** 姓名全称 */
    public String getFullName() {
        String fullname = this.surname + this.name;
        if(this.name == null || "".equals(this.name)){
            fullname = this.surname + "氏";
        }
        return fullname;
    }

    /** 动态计算年龄(异常时返回字符串空) */
    public String getAge(){
        String ageStr = "";
        try{
            ageStr = DateUtils.getUserAge(birthday) + "";
        }catch (Exception ex){

        }
        return ageStr;
    }

    /** 出生日期格式化用于解决前端表格显示相差一个小时的问题 */
    public String getBirthdayStr(){
        return DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, birthday);
    }

    public void setmId(Integer mId)
    {
        this.mId = mId;
    }

    public Integer getmId()
    {
        return mId;
    }

    public Integer getSurnameId() {
        return surnameId;
    }

    public void setSurnameId(Integer surnameId) {
        this.surnameId = surnameId;
    }


    public String getSurnameName() {
        return surnameName;
    }

    public void setSurnameName(String surnameName) {
        this.surnameName = surnameName;
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
    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    public String getLunarFlag() {
        return lunarFlag;
    }

    public void setLunarFlag(String lunarFlag) {
        this.lunarFlag = lunarFlag;
    }
    public Date getBirthday()
    {
        return birthday;
    }
    public void setBirthdayMark(String birthdayMark)
    {
        this.birthdayMark = birthdayMark;
    }

    public String getBirthdayMark()
    {
        return birthdayMark;
    }
    public void setBarthHour(String barthHour)
    {
        this.barthHour = barthHour;
    }

    public String getBarthHour()
    {
        return barthHour;
    }
    public void setHeadUrl(String headUrl)
    {
        this.headUrl = headUrl;
    }

    public String getHeadUrl()
    {
        return headUrl;
    }
    public void setFatherName(String fatherName)
    {
        this.fatherName = fatherName;
    }

    public String getFatherName()
    {
        return fatherName;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }
    public void setPhoneNum(String phoneNum)
    {
        this.phoneNum = phoneNum;
    }

    public String getPhoneNum()
    {
        return phoneNum;
    }
    public void setWechatNum(String wechatNum)
    {
        this.wechatNum = wechatNum;
    }

    public String getWechatNum()
    {
        return wechatNum;
    }
    public void setDeathFlag(String deathFlag)
    {
        this.deathFlag = deathFlag;
    }

    public String getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(String statusFlag) {
        this.statusFlag = statusFlag;
    }
    public String getDeathFlag()
    {
        return deathFlag;
    }
    public void setDeathday(Date deathday)
    {
        this.deathday = deathday;
    }

    public Date getDeathday()
    {
        return deathday;
    }
    public void setDeathdayMark(String deathdayMark)
    {
        this.deathdayMark = deathdayMark;
    }

    public String getDeathdayMark()
    {
        return deathdayMark;
    }
    public void setFuneralAddr(String funeralAddr)
    {
        this.funeralAddr = funeralAddr;
    }

    public String getFuneralAddr()
    {
        return funeralAddr;
    }
    public void setAgeofDeath(Integer ageofDeath)
    {
        this.ageofDeath = ageofDeath;
    }

    public Integer getAgeofDeath()
    {
        return ageofDeath;
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

    /**************************扩展属性方法********************************/
    public List<Integer> getNotInMIdList() {
        return notInMIdList;
    }

    public void setNotInMIdList(List<Integer> notInMIdList) {
        this.notInMIdList = notInMIdList;
    }

    public String getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(String memberIds) {
        this.memberIds = memberIds;
    }

    public String getMemberNames() {
        return memberNames;
    }

    public void setMemberNames(String memberNames) {
        this.memberNames = memberNames;
    }

    public String getSelectName() {
        return selectName;
    }

    public void setSelectName(String selectName) {
        this.selectName = selectName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("mId", getmId())
            .append("surnameId", getSurnameId())
            .append("surname", getSurname())
            .append("name", getName())
            .append("birthday", getBirthday())
            .append("birthdayMark", getBirthdayMark())
            .append("barthHour", getBarthHour())
            .append("headUrl", getHeadUrl())
            .append("fatherName", getFatherName())
            .append("address", getAddress())
            .append("phoneNum", getPhoneNum())
            .append("wechatNum", getWechatNum())
            .append("statusFlag", getStatusFlag())
            .append("deathFlag", getDeathFlag())
            .append("deathday", getDeathday())
            .append("deathdayMark", getDeathdayMark())
            .append("funeralAddr", getFuneralAddr())
            .append("ageofDeath", getAgeofDeath())
            .append("remark", getRemark())
            .append("intro", getIntro())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
