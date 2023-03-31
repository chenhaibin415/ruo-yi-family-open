package com.ruoyi.project.family.mate.service;

import java.util.List;
import com.ruoyi.project.family.mate.domain.FMate;

/**
 * 配偶成员Service接口
 * 
 * @author haibin
 * @date 2022-08-13
 */
public interface IFMateService 
{
    /**
     * 查询配偶成员
     * 
     * @param mId 配偶成员主键
     * @return 配偶成员
     */
    public FMate selectFMateByMId(Integer mId);

    /**
     * 查询配偶成员列表
     * 
     * @param fMate 配偶成员
     * @return 配偶成员集合
     */
    public List<FMate> selectFMateList(FMate fMate);

    /**
     * 查询配偶成员列表(根据家族编号)
     *
     * @param surnameId 家族编号
     * @return 配偶成员集合
     */
    public List<FMate> selectFMateListBySurnameId(Integer surnameId);

    /**
     * 查询匹配条件的配偶成员列表
     *
     * @param surnameId 家族编号
     * @param notInMIds 不需要匹配的配偶id集合
     * @return 配偶成员集合
     */
    public List<FMate> selectFMateListByNotInMIds(Integer surnameId, List<Integer> notInMIds);

    /**
     * 新增配偶成员
     * 
     * @param fMate 配偶成员
     * @return 结果
     */
    public int insertFMate(FMate fMate);

    /**
     * 修改配偶成员
     * 
     * @param fMate 配偶成员
     * @return 结果
     */
    public int updateFMate(FMate fMate);

    /**
     * 批量删除配偶成员
     * 
     * @param mIds 需要删除的配偶成员主键集合
     * @return 结果
     */
    public int deleteFMateByMIds(String mIds);

    /**
     * 删除配偶成员信息
     * 
     * @param mId 配偶成员主键
     * @return 结果
     */
    public int deleteFMateByMId(Integer mId);

    /**
     * 删除配偶成员信息(逻辑删除)
     *
     * @param mId 配偶成员主键
     * @return 结果
     */
    public int updateFMateDelByMId(Integer mId);

    /**
     * 批量删除配偶成员(逻辑删除)
     *
     * @param mIds 需要删除的配偶成员主键集合
     * @return 结果
     */
    public int updateFMateDelByMIds(String mIds);
}
