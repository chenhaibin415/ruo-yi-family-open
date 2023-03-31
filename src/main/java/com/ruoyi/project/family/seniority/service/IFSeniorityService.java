package com.ruoyi.project.family.seniority.service;

import java.util.List;
import com.ruoyi.project.family.seniority.domain.FSeniority;

/**
 * 辈分管理Service接口
 * 
 * @author haibin
 * @date 2022-08-02
 */
public interface IFSeniorityService 
{
    /**
     * 查询辈分管理
     * 
     * @param sId 辈分管理主键
     * @return 辈分管理
     */
    public FSeniority selectFSeniorityBySId(Integer sId);

    /**
     * 查询辈分管理列表
     * 
     * @param fSeniority 辈分管理
     * @return 辈分管理集合
     */
    public List<FSeniority> selectFSeniorityList(FSeniority fSeniority);

    /**
     * 查询辈分管理列表(根据家族编号)
     *
     * @param surnameId 家族编号
     * @return 辈分管理集合
     */
    public List<FSeniority> selectFSeniorityListBySurnameId(Integer surnameId);

    /**
     * 查询未删除信息中的最大排序号
     * @param surnameId 家族编号
     * @return 最大排序号
     */
    public int selectMaxNumSortBySurnameId(Integer surnameId);

    /**
     * 新增辈分管理
     * 
     * @param fSeniority 辈分管理
     * @return 结果
     */
    public int insertFSeniority(FSeniority fSeniority);

    /**
     * 修改辈分管理
     * 
     * @param fSeniority 辈分管理
     * @return 结果
     */
    public int updateFSeniority(FSeniority fSeniority);

    /**
     * 批量删除辈分管理
     * 
     * @param sIds 需要删除的辈分管理主键集合
     * @return 结果
     */
    public int deleteFSeniorityBySIds(String sIds);

    /**
     * 删除辈分管理信息
     * 
     * @param sId 辈分管理主键
     * @return 结果
     */
    public int deleteFSeniorityBySId(Integer sId);

    /**
     * 删除辈分管理信息(逻辑删除)
     *
     * @param sId 辈分管理主键
     * @return 结果
     */
    public int updateFSeniorityDelBySId(Integer sId);

    /**
     * 批量删除辈分管理(逻辑删除)
     *
     * @param sIds 需要删除的辈分管理主键集合
     * @return 结果
     */
    public int updateFSeniorityDelBySIds(String sIds);
}
