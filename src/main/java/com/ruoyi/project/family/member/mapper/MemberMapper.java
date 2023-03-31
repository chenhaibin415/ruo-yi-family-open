package com.ruoyi.project.family.member.mapper;

import java.util.List;
import com.ruoyi.project.family.member.domain.Member;

/**
 * 家族成员Mapper接口
 * 
 * @author haibin
 * @date 2022-09-04
 */
public interface MemberMapper 
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
     * 删除家族成员
     * 
     * @param mId 家族成员主键
     * @return 结果
     */
    public int deleteMemberByMId(Integer mId);

    /**
     * 批量删除家族成员
     * 
     * @param mIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMemberByMIds(String[] mIds);

    /**
     * 删除家族成员(逻辑删除)
     *
     * @param mId 家族成员主键
     * @return 结果
     */
    public int updateMemberDelByMId(Integer mId);

    /**
     * 批量删除家族成员(逻辑删除)
     *
     * @param mIds 需要删除的数据主键集合
     * @return 结果
     */
    public int updateMemberDelByMIds(String[] mIds);
}
