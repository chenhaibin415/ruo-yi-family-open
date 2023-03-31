package com.ruoyi.project.family.member.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.family.member.mapper.MemberMapper;
import com.ruoyi.project.family.member.domain.Member;
import com.ruoyi.project.family.member.service.IMemberService;
import com.ruoyi.common.utils.text.Convert;

/**
 * 家族成员Service业务层处理
 * 
 * @author haibin
 * @date 2022-09-04
 */
@Service
public class MemberServiceImpl implements IMemberService 
{
    @Autowired
    private MemberMapper memberMapper;

    /**
     * 查询家族成员
     * 
     * @param mId 家族成员主键
     * @return 家族成员
     */
    @Override
    public Member selectMemberByMId(Integer mId)
    {
        return memberMapper.selectMemberByMId(mId);
    }

    /**
     * 查询家族成员列表
     * 
     * @param member 家族成员
     * @return 家族成员集合
     */
    @Override
    public List<Member> selectMemberList(Member member)
    {
        return memberMapper.selectMemberList(member);
    }

    /**
     * 查询家族成员列表(根据家族编号)
     *
     * @param surnameId 家族编号
     * @return 家族成员集合
     */
    @Override
    public List<Member> selectMemberListBySurnameId(Integer surnameId)
    {
        Member member = new Member();
        member.setSurnameId(surnameId);
        return memberMapper.selectMemberList(member);
    }

    /**
     * 根据匹配条件查询家族男性成员列表
     *
     * @param surnameId 家族编号
     * @param notInMIds 不需要匹配的家族成员id集合
     * @param ltNumSort 小于辈分序号条件
     * @return 男性家族成员集合
     */
    @Override
    public List<Member> selectManMemberListBySurnameId(Integer surnameId, List<Integer> notInMIds, Integer ltNumSort)
    {
        Member member = new Member();
        member.setSurnameId(surnameId);
        member.setSex("0");//男性
        member.setNotInMIdList(notInMIds);
        member.setNumSort(ltNumSort);
        return memberMapper.selectMemberList(member);
    }

    /**
     * 新增家族成员
     * 
     * @param member 家族成员
     * @return 结果
     */
    @Override
    public int insertMember(Member member)
    {
        member.setCreateTime(DateUtils.getNowDate());
        member.setUpdateTime(DateUtils.getNowDate());
        return memberMapper.insertMember(member);
    }

    /**
     * 修改家族成员
     * 
     * @param member 家族成员
     * @return 结果
     */
    @Override
    public int updateMember(Member member)
    {
        member.setUpdateTime(DateUtils.getNowDate());
        return memberMapper.updateMember(member);
    }

    /**
     * 批量删除家族成员
     * 
     * @param mIds 需要删除的家族成员主键
     * @return 结果
     */
    @Override
    public int deleteMemberByMIds(String mIds)
    {
        return memberMapper.deleteMemberByMIds(Convert.toStrArray(mIds));
    }

    /**
     * 删除家族成员信息
     * 
     * @param mId 家族成员主键
     * @return 结果
     */
    @Override
    public int deleteMemberByMId(Integer mId)
    {
        return memberMapper.deleteMemberByMId(mId);
    }

    /**
     * 批量删除家族成员(逻辑删除)
     *
     * @param mIds 需要删除的家族成员主键
     * @return 结果
     */
    @Override
    public int updateMemberDelByMIds(String mIds)
    {
        return memberMapper.updateMemberDelByMIds(Convert.toStrArray(mIds));
    }

    /**
     * 删除家族成员信息(逻辑删除)
     *
     * @param mId 家族成员主键
     * @return 结果
     */
    @Override
    public int updateMemberDelByMId(Integer mId)
    {
        return memberMapper.updateMemberDelByMId(mId);
    }
}
