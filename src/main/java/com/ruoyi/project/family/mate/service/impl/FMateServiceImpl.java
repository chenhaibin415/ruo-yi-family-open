package com.ruoyi.project.family.mate.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.family.mate.mapper.FMateMapper;
import com.ruoyi.project.family.mate.domain.FMate;
import com.ruoyi.project.family.mate.service.IFMateService;
import com.ruoyi.common.utils.text.Convert;

/**
 * 配偶成员Service业务层处理
 * 
 * @author haibin
 * @date 2022-08-13
 */
@Service
public class FMateServiceImpl implements IFMateService 
{
    @Autowired
    private FMateMapper fMateMapper;

    /**
     * 查询配偶成员
     * 
     * @param mId 配偶成员主键
     * @return 配偶成员
     */
    @Override
    public FMate selectFMateByMId(Integer mId)
    {
        return fMateMapper.selectFMateByMId(mId);
    }

    /**
     * 查询配偶成员列表
     * 
     * @param fMate 配偶成员
     * @return 配偶成员
     */
    @Override
    public List<FMate> selectFMateList(FMate fMate)
    {
        return fMateMapper.selectFMateList(fMate);
    }

    /**
     * 查询配偶成员列表(根据家族编号)
     *
     * @param surnameId 家族编号
     * @return 配偶成员列表
     */
    @Override
    public List<FMate> selectFMateListBySurnameId(Integer surnameId)
    {
        FMate fMate = new FMate();
        fMate.setSurnameId(surnameId);
        return fMateMapper.selectFMateList(fMate);
    }

    /**
     * 查询匹配条件的配偶成员列表
     *
     * @param surnameId 家族编号
     * @param notInMIds 不需要匹配的配偶id集合
     * @return 配偶成员集合
     */
    @Override
    public List<FMate> selectFMateListByNotInMIds(Integer surnameId, List<Integer> notInMIds)
    {
        FMate fMate = new FMate();
        fMate.setSurnameId(surnameId);
        fMate.setNotInMIdList(notInMIds);
        return fMateMapper.selectFMateList(fMate);
    }

    /**
     * 新增配偶成员
     * 
     * @param fMate 配偶成员
     * @return 结果
     */
    @Override
    public int insertFMate(FMate fMate)
    {
        fMate.setCreateTime(DateUtils.getNowDate());
        fMate.setUpdateTime(DateUtils.getNowDate());
        return fMateMapper.insertFMate(fMate);
    }

    /**
     * 修改配偶成员
     * 
     * @param fMate 配偶成员
     * @return 结果
     */
    @Override
    public int updateFMate(FMate fMate)
    {
        fMate.setUpdateTime(DateUtils.getNowDate());
        return fMateMapper.updateFMate(fMate);
    }

    /**
     * 批量删除配偶成员
     * 
     * @param mIds 需要删除的配偶成员主键
     * @return 结果
     */
    @Override
    public int deleteFMateByMIds(String mIds)
    {
        return fMateMapper.deleteFMateByMIds(Convert.toStrArray(mIds));
    }

    /**
     * 删除配偶成员信息
     * 
     * @param mId 配偶成员主键
     * @return 结果
     */
    @Override
    public int deleteFMateByMId(Integer mId)
    {
        return fMateMapper.deleteFMateByMId(mId);
    }

    /**
     * 批量删除配偶成员(逻辑删除)
     *
     * @param mIds 需要删除的配偶成员主键
     * @return 结果
     */
    @Override
    public int updateFMateDelByMIds(String mIds)
    {
        return fMateMapper.updateFMateDelByMIds(Convert.toStrArray(mIds));
    }

    /**
     * 删除辈分管理信息(逻辑删除)
     *
     * @param mId 配偶成员主键
     * @return 结果
     */
    @Override
    public int updateFMateDelByMId(Integer mId)
    {
        return fMateMapper.updateFMateDelByMId(mId);
    }
}
