package com.ruoyi.project.family.marry.service;

import java.util.List;
import com.ruoyi.project.family.marry.domain.FMarry;

/**
 * 家族婚配管理Service接口
 * 
 * @author haibin
 * @date 2022-09-12
 */
public interface IFMarryService 
{
    /**
     * 查询家族婚配管理
     * 
     * @param mId 家族婚配管理主键
     * @return 家族婚配管理
     */
    public FMarry selectFMarryByMId(Integer mId);

    /**
     * 查询家族婚配管理列表
     * 
     * @param fMarry 家族婚配管理
     * @return 家族婚配管理集合
     */
    public List<FMarry> selectFMarryList(FMarry fMarry);

    /**
     * 新增家族婚配管理
     * 
     * @param fMarry 家族婚配管理
     * @return 结果
     */
    public int insertFMarry(FMarry fMarry);

    /**
     * 修改家族婚配管理
     * 
     * @param fMarry 家族婚配管理
     * @return 结果
     */
    public int updateFMarry(FMarry fMarry);

    /**
     * 批量删除家族婚配管理
     * 
     * @param mIds 需要删除的家族婚配管理主键集合
     * @return 结果
     */
    public int deleteFMarryByMIds(String mIds);

    /**
     * 删除家族婚配管理信息
     * 
     * @param mId 家族婚配管理主键
     * @return 结果
     */
    public int deleteFMarryByMId(Integer mId);

    /**
     * 批量删除家族婚配管理(逻辑删除)
     *
     * @param mIds 需要删除的家族婚配管理主键集合
     * @return 结果
     */
    public int updateFMarryDelByMIds(String mIds);

    /**
     * 删除家族婚配管理信息（逻辑删除）
     *
     * @param mId 家族婚配管理主键
     * @return 结果
     */
    public int updateFMarryDelByMId(Integer mId);
}
