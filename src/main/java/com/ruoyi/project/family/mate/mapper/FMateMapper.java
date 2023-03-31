package com.ruoyi.project.family.mate.mapper;

import java.util.List;
import com.ruoyi.project.family.mate.domain.FMate;

/**
 * 配偶成员Mapper接口
 * 
 * @author haibin
 * @date 2022-08-13
 */
public interface FMateMapper 
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
     * 删除配偶成员
     * 
     * @param mId 配偶成员主键
     * @return 结果
     */
    public int deleteFMateByMId(Integer mId);

    /**
     * 批量删除配偶成员
     * 
     * @param mIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFMateByMIds(String[] mIds);

    /**
     * 删除配偶成员(逻辑删除)
     *
     * @param mId 配偶成员主键
     * @return 结果
     */
    public int updateFMateDelByMId(Integer mId);

    /**
     * 批量删除配偶成员(逻辑删除)
     *
     * @param mIds 需要删除的数据主键集合
     * @return 结果
     */
    public int updateFMateDelByMIds(String[] mIds);
}
