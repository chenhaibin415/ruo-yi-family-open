package com.ruoyi.project.family.seniority.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.family.seniority.mapper.FSeniorityMapper;
import com.ruoyi.project.family.seniority.domain.FSeniority;
import com.ruoyi.project.family.seniority.service.IFSeniorityService;
import com.ruoyi.common.utils.text.Convert;

/**
 * 辈分管理Service业务层处理
 * 
 * @author haibin
 * @date 2022-08-02
 */
@Service
public class FSeniorityServiceImpl implements IFSeniorityService 
{
    @Autowired
    private FSeniorityMapper fSeniorityMapper;

    /**
     * 查询辈分管理
     * 
     * @param sId 辈分管理主键
     * @return 辈分管理
     */
    @Override
    public FSeniority selectFSeniorityBySId(Integer sId)
    {
        return fSeniorityMapper.selectFSeniorityBySId(sId);
    }

    /**
     * 查询辈分管理列表
     * 
     * @param fSeniority 辈分管理
     * @return 辈分管理
     */
    @Override
    public List<FSeniority> selectFSeniorityList(FSeniority fSeniority)
    {
        return fSeniorityMapper.selectFSeniorityList(fSeniority);
    }

    /**
     * 查询辈分管理列表(根据家族编号)
     *
     * @param surnameId 家族编号
     * @return 辈分管理
     */
    @Override
    public List<FSeniority> selectFSeniorityListBySurnameId(Integer surnameId)
    {
        FSeniority fSeniority = new FSeniority();
        fSeniority.setSurnameId(surnameId);
        return fSeniorityMapper.selectFSeniorityList(fSeniority);
    }

    /**
     * 查询未删除信息中的最大排序号
     * @param surnameId 家族编号
     * @return 最大排序号
     */
    @Override
    public int selectMaxNumSortBySurnameId(Integer surnameId)
    {
        return fSeniorityMapper.selectMaxNumSortBySurnameId(surnameId);
    }

    /**
     * 新增辈分管理
     * 
     * @param fSeniority 辈分管理
     * @return 结果
     */
    @Override
    public int insertFSeniority(FSeniority fSeniority)
    {
        fSeniority.setCreateTime(DateUtils.getNowDate());
        fSeniority.setUpdateTime(DateUtils.getNowDate());
        return fSeniorityMapper.insertFSeniority(fSeniority);
    }

    /**
     * 修改辈分管理
     * 
     * @param fSeniority 辈分管理
     * @return 结果
     */
    @Override
    public int updateFSeniority(FSeniority fSeniority)
    {
        fSeniority.setUpdateTime(DateUtils.getNowDate());
        return fSeniorityMapper.updateFSeniority(fSeniority);
    }

    /**
     * 批量删除辈分管理
     * 
     * @param sIds 需要删除的辈分管理主键
     * @return 结果
     */
    @Override
    public int deleteFSeniorityBySIds(String sIds)
    {
        return fSeniorityMapper.deleteFSeniorityBySIds(Convert.toStrArray(sIds));
    }

    /**
     * 删除辈分管理信息
     * 
     * @param sId 辈分管理主键
     * @return 结果
     */
    @Override
    public int deleteFSeniorityBySId(Integer sId)
    {
        return fSeniorityMapper.deleteFSeniorityBySId(sId);
    }

    /**
     * 批量删除辈分管理(逻辑删除)
     *
     * @param sIds 需要删除的辈分管理主键
     * @return 结果
     */
    @Override
    public int updateFSeniorityDelBySIds(String sIds)
    {
        return fSeniorityMapper.updateFSeniorityDelBySIds(Convert.toStrArray(sIds));
    }

    /**
     * 删除辈分管理信息(逻辑删除)
     *
     * @param sId 辈分管理主键
     * @return 结果
     */
    @Override
    public int updateFSeniorityDelBySId(Integer sId)
    {
        return fSeniorityMapper.updateFSeniorityDelBySId(sId);
    }
}
