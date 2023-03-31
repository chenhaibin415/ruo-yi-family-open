package com.ruoyi.project.family.marry.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.family.marry.mapper.FMarryMapper;
import com.ruoyi.project.family.marry.domain.FMarry;
import com.ruoyi.project.family.marry.service.IFMarryService;
import com.ruoyi.common.utils.text.Convert;

/**
 * 家族婚配管理Service业务层处理
 * 
 * @author haibin
 * @date 2022-09-12
 */
@Service
public class FMarryServiceImpl implements IFMarryService 
{
    @Autowired
    private FMarryMapper fMarryMapper;

    /**
     * 查询家族婚配管理
     * 
     * @param mId 家族婚配管理主键
     * @return 家族婚配管理
     */
    @Override
    public FMarry selectFMarryByMId(Integer mId)
    {
        return fMarryMapper.selectFMarryByMId(mId);
    }

    /**
     * 查询家族婚配管理列表
     * 
     * @param fMarry 家族婚配管理
     * @return 家族婚配管理
     */
    @Override
    public List<FMarry> selectFMarryList(FMarry fMarry)
    {
        return fMarryMapper.selectFMarryList(fMarry);
    }

    /**
     * 新增家族婚配管理
     * 
     * @param fMarry 家族婚配管理
     * @return 结果
     */
    @Override
    public int insertFMarry(FMarry fMarry)
    {
        fMarry.setCreateTime(DateUtils.getNowDate());
        fMarry.setUpdateTime(DateUtils.getNowDate());
        return fMarryMapper.insertFMarry(fMarry);
    }

    /**
     * 修改家族婚配管理
     * 
     * @param fMarry 家族婚配管理
     * @return 结果
     */
    @Override
    public int updateFMarry(FMarry fMarry)
    {
        fMarry.setUpdateTime(DateUtils.getNowDate());
        return fMarryMapper.updateFMarry(fMarry);
    }

    /**
     * 批量删除家族婚配管理
     * 
     * @param mIds 需要删除的家族婚配管理主键
     * @return 结果
     */
    @Override
    public int deleteFMarryByMIds(String mIds)
    {
        return fMarryMapper.deleteFMarryByMIds(Convert.toStrArray(mIds));
    }

    /**
     * 删除家族婚配管理信息
     * 
     * @param mId 家族婚配管理主键
     * @return 结果
     */
    @Override
    public int deleteFMarryByMId(Integer mId)
    {
        return fMarryMapper.deleteFMarryByMId(mId);
    }

    /**
     * 批量删除家族婚配管理(逻辑删除)
     *
     * @param mIds 需要删除的家族婚配管理主键
     * @return 结果
     */
    @Override
    public int updateFMarryDelByMIds(String mIds)
    {
        return fMarryMapper.updateFMarryDelByMIds(Convert.toStrArray(mIds));
    }

    /**
     * 删除家族婚配管理信息（逻辑删除）
     *
     * @param mId 家族婚配管理主键
     * @return 结果
     */
    @Override
    public int updateFMarryDelByMId(Integer mId)
    {
        return fMarryMapper.updateFMarryDelByMId(mId);
    }
}
