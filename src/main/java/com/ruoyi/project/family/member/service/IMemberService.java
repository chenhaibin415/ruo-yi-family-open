package com.ruoyi.project.family.member.service;

import java.util.List;
import com.ruoyi.project.family.member.domain.Member;

/**
 * 家族成员Service接口
 * 
 * @author haibin
 * @date 2022-09-04
 */
public interface IMemberService 
{
    /**
     * 查询家族成员
     * 
     * @param mId 家族成员主键
     * @return 家族成员
     */
    public Member selectMemberByMId(Integer mId);

    /**
     * 查询家族成员列表
     * 
     * @param member 家族成员
     * @return 家族成员集合
     */
    public List<Member> selectMemberList(Member member);

    /**
     * 查询家族成员列表(根据家族编号)
     *
     * @param surnameId 家族编号
     * @return 家族成员集合
     */
    public List<Member> selectMemberListBySurnameId(Integer surnameId);

    /**
     * 根据匹配条件查询家族男性成员列表
     *
     * @param surnameId 家族编号
     * @param notInMIds 不需要匹配的家族成员id集合
     * @param ltNumSort 小于辈分序号条件
     * @return 男性家族成员集合
     */
    public List<Member> selectManMemberListBySurnameId(Integer surnameId, List<Integer> notInMIds, Integer ltNumSort);

    /**
     * 新增家族成员
     * 
     * @param member 家族成员
     * @return 结果
     */
    public int insertMember(Member member);

    /**
     * 修改家族成员
     * 
     * @param member 家族成员
     * @return 结果
     */
    public int updateMember(Member member);

    /**
     * 批量删除家族成员
     * 
     * @param mIds 需要删除的家族成员主键集合
     * @return 结果
     */
    public int deleteMemberByMIds(String mIds);

    /**
     * 删除家族成员信息
     * 
     * @param mId 家族成员主键
     * @return 结果
     */
    public int deleteMemberByMId(Integer mId);

    /**
     * 批量删除家族成员(物理删除)
     *
     * @param mIds 需要删除的家族成员主键集合
     * @return 结果
     */
    public int updateMemberDelByMIds(String mIds);

    /**
     * 删除家族成员信息(物理删除)
     *
     * @param mId 家族成员主键
     * @return 结果
     */
    public int updateMemberDelByMId(Integer mId);
}
