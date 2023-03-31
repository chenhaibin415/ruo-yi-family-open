package com.ruoyi.project.family.marry.controller;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.family.clan.domain.FClan;
import com.ruoyi.project.family.clan.service.IFClanService;
import com.ruoyi.project.family.mate.domain.FMate;
import com.ruoyi.project.family.mate.service.IFMateService;
import com.ruoyi.project.family.member.domain.ClanChangeData;
import com.ruoyi.project.family.member.domain.Member;
import com.ruoyi.project.family.member.service.IMemberService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.family.marry.domain.FMarry;
import com.ruoyi.project.family.marry.service.IFMarryService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 家族婚配管理Controller
 * 
 * @author haibin
 * @date 2022-09-12
 */
@Controller
@RequestMapping("/family/marry")
public class FMarryController extends BaseController
{
    private String prefix = "family/marry";

    @Autowired
    private IFMarryService fMarryService;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IFClanService fClanService;

    @Autowired
    private IFMateService fMateService;

    @RequiresPermissions("family:marry:view")
    @GetMapping()
    public String marry(ModelMap mmap)
    {
        //查询家族列表数据并传入变量备用
        List<FClan> fcanList = fClanService.selectFClanList();
        mmap.put("fcanList", fcanList);
        return prefix + "/marry";
    }

    /**
     * 查询家族婚配管理列表
     */
    @RequiresPermissions("family:marry:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FMarry fMarry)
    {
        startPage();
        List<FMarry> list = fMarryService.selectFMarryList(fMarry);
        return getDataTable(list);
    }

    /**
     * 导出家族婚配管理列表
     */
    @RequiresPermissions("family:marry:export")
    @Log(title = "家族婚配管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FMarry fMarry)
    {
        List<FMarry> list = fMarryService.selectFMarryList(fMarry);
        ExcelUtil<FMarry> util = new ExcelUtil<FMarry>(FMarry.class);
        return util.exportExcel(list, "家族婚配管理数据");
    }

    /**
     * 新增家族婚配管理
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        //查询家族列表数据并传入变量备用
        List<FClan> fcanList = fClanService.selectFClanList();
        mmap.put("fcanList", fcanList);
        return prefix + "/add";
    }

    /**
     * 新增保存家族婚配管理
     */
    @RequiresPermissions("family:marry:add")
    @Log(title = "家族婚配管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(FMarry fMarry)
    {
        //设置创建者和修改者
        fMarry.setCreateBy(ShiroUtils.getLoginName());
        fMarry.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(fMarryService.insertFMarry(fMarry));
    }

    /**
     * 修改家族婚配管理
     */
    @RequiresPermissions("family:marry:edit")
    @GetMapping("/edit/{mId}")
    public String edit(@PathVariable("mId") Integer mId, ModelMap mmap)
    {
        FMarry fMarry = fMarryService.selectFMarryByMId(mId);
        mmap.put("fMarry", fMarry);
        //查询家族列表数据并传入变量备用
        List<FClan> fcanList = fClanService.selectFClanList();
        mmap.put("fcanList", fcanList);
        return prefix + "/edit";
    }

    /**
     * 修改保存家族婚配管理
     */
    @RequiresPermissions("family:marry:edit")
    @Log(title = "家族婚配管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FMarry fMarry)
    {
        fMarry.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(fMarryService.updateFMarry(fMarry));
    }

    /**
     * 删除家族婚配管理
     */
    @RequiresPermissions("family:marry:remove")
    @Log(title = "家族婚配管理", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        //return toAjax(fMarryService.deleteFMarryByMIds(ids));//屏蔽原有的物理删除方法
        return toAjax(fMarryService.updateFMarryDelByMIds(ids));
    }

    /**
     * 家族列表选项值改变发起联动请求
     */
    @GetMapping("/clanSelectChange")
    @ResponseBody
    public TableDataInfo getClanChangeData(Integer surnameId, Integer memberId, Integer mateId)
    {
        ClanChangeData cData = new ClanChangeData();
        cData.setfMateList(getEligibleFMateList(surnameId, memberId, mateId));
        cData.setMemberList(getEligibleMemberList(surnameId, memberId, mateId));
        List<ClanChangeData> list = new ArrayList<>();
        list.add(cData);
        return getDataTable(list);
    }

    /**
     * 家族成员下拉列表改变
     */
    @GetMapping("/memberSelectChange")
    @ResponseBody
    public TableDataInfo getMemberSelectChangeData(Integer surnameId, Integer memberId, Integer mateId)
    {
        List<FMate> mateList = getEligibleFMateList(surnameId, memberId, mateId);
        return getDataTable(mateList);
    }

    /**
     * 配偶成员下拉列表改变
     */
    @GetMapping("/mateSelectChange")
    @ResponseBody
    public TableDataInfo getMateSelectChangeData(Integer surnameId, Integer memberId, Integer mateId)
    {
        List<Member> memberList = getEligibleMemberList(surnameId, memberId, mateId);
        return getDataTable(memberList);
    }

    /**
     * 查询符合条件的配偶成员集合
     * @param surnameId 家族编号
     * @param memberId 家族成员编号
     * @param mateId 配偶成员编号
     * @return 符合条件的配偶成员集合
     */
    private List<FMate> getEligibleFMateList(Integer surnameId, Integer memberId, Integer mateId)
    {
        List<FMate> mateList = new ArrayList<>();
        if(memberId == null && mateId == null)
        {
            mateList = fMateService.selectFMateListBySurnameId(surnameId);
        }
        else
        {
            //首先查询此家族成员婚配列表
            FMarry fMarry = new FMarry();
            fMarry.setSurnameId(surnameId);
            fMarry.setMemberId(memberId);
            List<FMarry> marryList = fMarryService.selectFMarryList(fMarry);
            //构造not in条件字符串
            List<Integer> notInList = new ArrayList<>();
            if(marryList != null && marryList.size() > 0)
            {
                for (FMarry fmItem : marryList) {
                    //如果是编辑状态需要包含本人
                    if(fmItem.getMateId() != mateId)
                    {
                        notInList.add(fmItem.getMateId());
                    }
                }
            }
            //查询此家族成员的母亲（配偶排查母亲）
            Member member = memberService.selectMemberByMId(memberId);
            //如果母亲不为null则排除母亲
            if(member.getMotherId() != null){
                notInList.add(member.getMotherId());
            }
            System.out.println("notInList:" + notInList.toString());
            //查询最终符合条件的配偶成员
            mateList = fMateService.selectFMateListByNotInMIds(surnameId, notInList);
        }
        return mateList;
    }

    /**
     * 查询符合条件的家族成员集合
     * @param surnameId 家族编号
     * @param memberId 家族成员编号
     * @param mateId 配偶成员编号
     * @return 符合条件的家族成员集合
     */
    private List<Member> getEligibleMemberList(Integer surnameId, Integer memberId, Integer mateId)
    {
        List<Member> memberList = new ArrayList<>();
        if(memberId == null && mateId == null)
        {
            memberList = memberService.selectManMemberListBySurnameId(surnameId, null, null);
        }
        else
        {
            //首先查询此配偶成员婚配列表
            FMarry fMarry = new FMarry();
            fMarry.setSurnameId(surnameId);
            fMarry.setMateId(mateId);
            List<FMarry> marryList = fMarryService.selectFMarryList(fMarry);
            //构造not in条件字符串
            List<Integer> notInList = new ArrayList<>();
            if(marryList != null && marryList.size() > 0)
            {
                for (FMarry fmItem : marryList) {
                    //如果是编辑状态需要包含本人
                    if(fmItem.getMemberId() != memberId)
                    {
                        notInList.add(fmItem.getMemberId());
                    }
                }
            }
            System.out.println("notInList:" + notInList.toString());
            //查询最终符合条件的家族成员
            memberList = memberService.selectManMemberListBySurnameId(surnameId, notInList, null);
        }
        return memberList;
    }
}
