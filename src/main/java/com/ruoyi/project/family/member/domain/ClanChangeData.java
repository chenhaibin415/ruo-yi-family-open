package com.ruoyi.project.family.member.domain;

import com.ruoyi.project.family.mate.domain.FMate;
import com.ruoyi.project.family.seniority.domain.FSeniority;

import java.util.List;

/**
 * 根据家族id改变联动返回的data数据集
 *
 * @author haibin
 * @date 2022-09-06
 */

public class ClanChangeData{

    //根据家族id改变联动的辈分列表
    private List<FSeniority> fSeniorityList;
    //根据家族id改变联动的配偶成员列表
    private List<FMate> fMateList;
    //根据家族id改变联动的家族成员列表
    private List<Member> memberList;

    public List<FSeniority> getfSeniorityList() {
        return fSeniorityList;
    }

    public void setfSeniorityList(List<FSeniority> fSeniorityList) {
        this.fSeniorityList = fSeniorityList;
    }

    public List<FMate> getfMateList() {
        return fMateList;
    }

    public void setfMateList(List<FMate> fMateList) {
        this.fMateList = fMateList;
    }

    public List<Member> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }

}
