package com.ruoyi.project.family.clan.service;

import java.util.List;
import com.ruoyi.project.family.clan.domain.FClan;

/**
 * 家族Service接口
 * 
 * @author haibin
 * @date 2022-08-03
 */
public interface IFClanService 
{
    /**
     * 根据编号查询家族
     * 
     * @param cId 家族编号
     * @return 家族信息
     */
    public FClan selectFClanByCId(Integer cId);

    /**
     * 根据编码查询家族
     *
     * @param cCode 家族编码
     * @return 家族信息
     */
    public FClan selectFClanByCCode(String cCode);

    /**
     * 查询家族列表
     * 
     * @param fClan 家族
     * @return 家族集合
     */
    public List<FClan> selectFClanList(FClan fClan);

    /**
     * 查询家族列表(返回所有数据)
     *
     * @return 家族集合
     */
    public List<FClan> selectFClanList();

    /**
     * 新增家族
     * 
     * @param fClan 家族
     * @return 结果
     */
    public int insertFClan(FClan fClan);

    /**
     * 修改家族
     * 
     * @param fClan 家族
     * @return 结果
     */
    public int updateFClan(FClan fClan);

    /**
     * 批量删除家族
     * 
     * @param cIds 需要删除的家族主键集合
     * @return 结果
     */
    public int deleteFClanByCIds(String cIds);

    /**
     * 删除家族信息
     * 
     * @param cId 家族主键
     * @return 结果
     */
    public int deleteFClanByCId(Integer cId);

    /**
     * 批量删除家族(逻辑删除)
     *
     * @param cIds 需要删除的家族主键集合
     * @return 结果
     */
    public int updateFClanDelByCIds(String cIds);

    /**
     * 删除家族信息(逻辑删除)
     *
     * @param cId 家族主键
     * @return 结果
     */
    public int updateFClanDelByCId(Integer cId);
}
