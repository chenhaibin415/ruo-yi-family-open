package com.ruoyi.project.family.clan.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.family.clan.mapper.FClanMapper;
import com.ruoyi.project.family.clan.domain.FClan;
import com.ruoyi.project.family.clan.service.IFClanService;
import com.ruoyi.common.utils.text.Convert;

/**
 * 家族Service业务层处理
 * 
 * @author haibin
 * @date 2022-08-03
 */
@Service
public class FClanServiceImpl implements IFClanService 
{
    @Autowired
    private FClanMapper fClanMapper;

    /**
     * 根据家族编号查询家族
     * 
     * @param cId 家族编号
     * @return 家族信息
     */
    @Override
    public FClan selectFClanByCId(Integer cId)
    {
        return fClanMapper.selectFClanByCId(cId);
    }

    /**
     * 根据家族编码查询家族
     *
     * @param cCode 家族编号
     * @return 家族信息
     */
    @Override
    public FClan selectFClanByCCode(String cCode)
    {
        FClan resultFClan = null;
        FClan selectFClan = new FClan();
        selectFClan.setcCode(cCode);
        List<FClan> list = fClanMapper.selectFClanList(selectFClan);
        if(list.size() >= 0){
            resultFClan = list.get(0);
        }
        return resultFClan;
    }

    /**
     * 查询家族列表
     * 
     * @param fClan 家族
     * @return 家族
     */
    @Override
    public List<FClan> selectFClanList(FClan fClan)
    {
        return fClanMapper.selectFClanList(fClan);
    }

    /**
     * 查询家族列表(返回所有数据)
     *
     * @return 家族
     */
    @Override
    public List<FClan> selectFClanList()
    {
        //构造此对象是为了查询所有数据
        FClan fClan = new FClan();
        fClan.setcCode("");
        fClan.setSurname("");
        fClan.setName("");
        return fClanMapper.selectFClanList(fClan);
    }

    /**
     * 新增家族
     * 
     * @param fClan 家族
     * @return 结果
     */
    @Override
    public int insertFClan(FClan fClan)
    {
        fClan.setCreateTime(DateUtils.getNowDate());
        fClan.setUpdateTime(DateUtils.getNowDate());
        return fClanMapper.insertFClan(fClan);
    }

    /**
     * 修改家族
     * 
     * @param fClan 家族
     * @return 结果
     */
    @Override
    public int updateFClan(FClan fClan)
    {
        fClan.setUpdateTime(DateUtils.getNowDate());
        return fClanMapper.updateFClan(fClan);
    }

    /**
     * 批量删除家族
     * 
     * @param cIds 需要删除的家族主键
     * @return 结果
     */
    @Override
    public int deleteFClanByCIds(String cIds)
    {
        return fClanMapper.deleteFClanByCIds(Convert.toStrArray(cIds));
    }

    /**
     * 删除家族信息
     * 
     * @param cId 家族主键
     * @return 结果
     */
    @Override
    public int deleteFClanByCId(Integer cId)
    {
        return fClanMapper.deleteFClanByCId(cId);
    }

    /**
     * 批量删除家族(逻辑删除)
     *
     * @param cIds 需要删除的家族主键
     * @return 结果
     */
    @Override
    public int updateFClanDelByCIds(String cIds)
    {
        return fClanMapper.updateFClanDelByCIds(Convert.toStrArray(cIds));
    }

    /**
     * 删除家族信息(逻辑删除)
     *
     * @param cId 家族主键
     * @return 结果
     */
    @Override
    public int updateFClanDelByCId(Integer cId)
    {
        return fClanMapper.updateFClanDelByCId(cId);
    }
}
