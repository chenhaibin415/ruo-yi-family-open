package com.ruoyi.project.family.member.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.utils.DateUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 家族成员对象 f_member
 * 
 * @author haibin
 * @date 2022-09-04
 */
public class Member extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    private Integer mId;

    /** 家族编号 */
    @Excel(name = "家族编号")
    private Integer surnameId;

    /** 名字 */
    @Excel(name = "名字")
    private String name;

    /** 字 */
    private String wordName;

    /** 号 */
    private String numName;

    /** 用户性别（0男 1女 2未知） */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

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
    @Excel(name = "头像url")
    private String headUrl;

    /** 辈分编号 */
    private Integer seniorityId;

    /** 父亲编号 */
    private Integer fatherId;

    /** 母亲编号 */
    private Integer motherId;

    /** 家中数字排行 */
    private Integer sortNum;

    /** 家中文字排行 */
    private String sortTag;

    /** 家庭地址 */
    private String address;

    /** 手机号 */
    private String phoneNum;

    /** 微信号 */
    private String wechatNum;

    /** 状态标识（0代表未登记 1代表已登记） */
    @Excel(name = "状态标识", readConverterExp = "0=未登记,1=已登记")
    private String statusFlag;

    /** 死亡标识（0代表健在 1代表死亡） */
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

    /**      ******************多表连接新增的字段******************    */

    /** 家族成员姓名 */
    @Excel(name = "家族成员姓名")
    private String fullName;

    /** 家族名称 */
    @Excel(name = "家族名称")
    private String surnameName;

    /** 家族姓 */
    @Excel(name = "家族姓")
    private String surname;

    /** 辈分序号 */
    @Excel(name = "辈分序号")
    private Integer numSort;

    /** 辈分世 */
    @Excel(name = "辈分世")
    private String numTag;

    /** 字辈 */
    private String cnTag;

    /** 父亲名字 */
    @Excel(name = "父亲名字")
    private String fatherName;

    /** 父亲姓名 */
    @Excel(name = "父亲姓名")
    private String fatherFullName;

    /** 母亲姓 */
    @Excel(name = "母亲姓")
    private String montherSurName;

    /** 母亲名字 */
    @Excel(name = "母亲名字")
    private String montherName;

    /** 母亲姓名 */
    @Excel(name = "母亲姓名")
    private String montherFullName;

    /** 配偶（妻子）编号集合字符串 */
    private String mateIds;

    /** 配偶（妻子）姓名字符串 */
    private String mateNames;

    /** 动态计算年龄(异常时返回字符串空) */
    public String getAge(){
        String ageStr = "";
        try{
            ageStr = DateUtils.getUserAge(birthday) + "";
        }catch (Exception ex){

        }
        return ageStr;
    }

    /**************************扩展查询属性********************************/

    /** 不匹配编号 */
    private List<Integer> notInMIdList;

    /** 按姓名完全匹配 */
    private String selectName;

    /** 是否按照辈分升序处理（1为升序，0则按照默认排序） */
    private String orderByNumSort;



    public void setmId(Integer mId)
    {
        this.mId = mId;
    }

    public Integer getmId()
    {
        return mId;
    }
    public void setSurnameId(Integer surnameId)
    {
        this.surnameId = surnameId;
    }

    public Integer getSurnameId()
    {
        return surnameId;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setWordName(String wordName)
    {
        this.wordName = wordName;
    }

    public String getWordName()
    {
        return wordName;
    }
    public void setNumName(String numName)
    {
        this.numName = numName;
    }

    public String getNumName()
    {
        return numName;
    }
    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getSex()
    {
        return sex;
    }

    public String getLunarFlag() {
        return lunarFlag;
    }

    public void setLunarFlag(String lunarFlag) {
        this.lunarFlag = lunarFlag;
    }
    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
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
    public void setSeniorityId(Integer seniorityId)
    {
        this.seniorityId = seniorityId;
    }

    public Integer getSeniorityId()
    {
        return seniorityId;
    }
    public void setFatherId(Integer fatherId)
    {
        this.fatherId = fatherId;
    }

    public Integer getFatherId()
    {
        return fatherId;
    }
    public void setMotherId(Integer motherId)
    {
        this.motherId = motherId;
    }

    public Integer getMotherId()
    {
        return motherId;
    }
    public void setSortNum(Integer sortNum)
    {
        this.sortNum = sortNum;
    }

    public Integer getSortNum()
    {
        return sortNum;
    }
    public void setSortTag(String sortTag)
    {
        this.sortTag = sortTag;
    }

    public String getSortTag()
    {
        return sortTag;
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

    /**             ******************多表连接新增的属性方法******************        */
    public String getSurnameName() {
        return surnameName;
    }

    public void setSurnameName(String surnameName) {
        this.surnameName = surnameName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getNumSort() {
        return numSort;
    }

    public void setNumSort(Integer numSort) {
        this.numSort = numSort;
    }
    public String getNumTag() {
        return numTag;
    }

    public void setNumTag(String numTag) {
        this.numTag = numTag;
    }

    public String getCnTag() {
        return cnTag;
    }

    public void setCnTag(String cnTag) {
        this.cnTag = cnTag;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMontherSurName() {
        return montherSurName;
    }

    public void setMontherSurName(String montherSurName) {
        this.montherSurName = montherSurName;
    }

    public String getMontherName() {
        return montherName;
    }

    public void setMontherName(String montherName) {
        this.montherName = montherName;
    }

    public String getMontherFullName() {
        String montherFullName1 = "-";
        if(this.montherSurName != null && this.montherName != null)
        {
            if(!"".equals(this.montherName)){
                montherFullName1 = this.montherSurName + this.montherName;
            }else{
                montherFullName1 = this.montherSurName + "氏";
            }
        }
        return montherFullName1;
    }

    public String getFullName() {
        String fullName1 = "-";
        if(this.surname != null && this.name != null)
        {
            fullName1 = this.surname + this.name;
        }
        return fullName1;
    }
    public String getFatherFullName() {
        String fatherFullName1 = "-";
        if(this.surname != null && this.fatherName != null)
        {
            fatherFullName1 = this.surname + this.fatherName;
        }
        return fatherFullName1;
    }

    public String getMateIds() {
        return mateIds;
    }

    public void setMateIds(String mateIds) {
        this.mateIds = mateIds;
    }

    public String getMateNames() {
        return mateNames;
    }

    public void setMateNames(String mateNames) {
        this.mateNames = mateNames;
    }

    /**************************扩展查询属性方法********************************/
    public List<Integer> getNotInMIdList() {
        return notInMIdList;
    }

    public void setNotInMIdList(List<Integer> notInMIdList) {
        this.notInMIdList = notInMIdList;
    }

    public String getSelectName() {
        return selectName;
    }

    public void setSelectName(String selectName) {
        this.selectName = selectName;
    }

    public String getOrderByNumSort() {
        return orderByNumSort;
    }

    public void setOrderByNumSort(String orderByNumSort) {
        this.orderByNumSort = orderByNumSort;
    }

    /** 出生日期格式化用于解决前端表格显示相差一个小时的问题 */
    public String getBirthdayStr(){
        return DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, birthday);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("mId", getmId())
            .append("surnameId", getSurnameId())
            .append("name", getName())
            .append("wordName", getWordName())
            .append("numName", getNumName())
            .append("sex", getSex())
            .append("birthday", getBirthday())
            .append("birthdayMark", getBirthdayMark())
            .append("barthHour", getBarthHour())
            .append("headUrl", getHeadUrl())
            .append("seniorityId", getSeniorityId())
            .append("fatherId", getFatherId())
            .append("motherId", getMotherId())
            .append("sortNum", getSortNum())
            .append("sortTag", getSortTag())
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
            .append("surnameName", getSurnameName())
            .append("surname", getSurname())
            .append("numTag", getNumTag())
            .append("fatherName", getFatherName())
            .append("montherSurName", getMontherSurName())
            .append("montherName", getMontherName())
            .append("montherFullName", getMontherFullName())
            .toString();
    }
}
