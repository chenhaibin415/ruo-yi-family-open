package com.ruoyi.project.family.front.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 用于前台简单成员信息对象
 *
 * @author haibin
 * @date 2022-09-19
 */
public class SimpleMember {

    public static final String MEMBERTYPE = "1";//家族成员
    public static final String MATETYPE = "2";//配偶成员

    /** 编号 */
    private Integer mId;

    /** 姓名 */
    private String fullName;

    /** 出生日期 */
    private String birthday;

    /** 成员类型（1家族成员 2配偶成员） */
    private String memberType;

    /** 家庭关系（本人、妻子、儿子、女儿、父亲、母亲、爷爷、奶奶等） */
    private String relations;

    /** 头像 */
    private String headUrl;

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getRelations() {
        return relations;
    }

    public void setRelations(String relations) {
        this.relations = relations;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

}
