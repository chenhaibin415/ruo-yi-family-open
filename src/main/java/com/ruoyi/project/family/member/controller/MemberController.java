package com.ruoyi.project.family.member.controller;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.project.family.clan.domain.FClan;
import com.ruoyi.project.family.clan.service.IFClanService;
import com.ruoyi.project.family.marry.domain.FMarry;
import com.ruoyi.project.family.marry.service.IFMarryService;
import com.ruoyi.project.family.mate.domain.FMate;
import com.ruoyi.project.family.mate.service.IFMateService;
import com.ruoyi.project.family.member.domain.ClanChangeData;
import com.ruoyi.project.family.seniority.domain.FSeniority;
import com.ruoyi.project.family.seniority.service.IFSeniorityService;
import com.ruoyi.project.system.user.controller.ProfileController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.family.member.domain.Member;
import com.ruoyi.project.family.member.service.IMemberService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 家族成员Controller
 * 
 * @author haibin
 * @date 2022-09-04
 */
@Controller
@RequestMapping("/family/member")
public class MemberController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);
    private String prefix = "family/member";

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IFClanService fClanService;

    @Autowired
    private IFMateService fMateService;

    @Autowired
    private IFMarryService fMarryService;

    //当前修改头像的家族成员对象
    private Member curHeadUrlMember;

    @Autowired
    private IFSeniorityService fSeniorityService;

    @RequiresPermissions("family:member:view")
    @GetMapping()
    public String member(ModelMap mmap)
    {
        //查询家族列表数据并传入变量备用
        List<FClan> fcanList = fClanService.selectFClanList();
        mmap.put("fcanList", fcanList);
        return prefix + "/member";
    }

    /**
     * 查询家族成员列表
     */
    @RequiresPermissions("family:member:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Member member)
    {
        startPage();
        List<Member> list = memberService.selectMemberList(member);
        //遍历数据集合查询出配偶（家族成员姓名）集合
        for(Member member1 : list) {
            StringBuilder mateNamesSB = new StringBuilder();
            //如果家族成员是男性才查询
            if(member1.getSex().equals("0")) {
                FMarry fMarry = new FMarry();
                fMarry.setMemberId(member1.getmId());
                List<FMarry> fMarryList = fMarryService.selectFMarryList(fMarry);
                int fMarryListSize = fMarryList.size();
                if (fMarryListSize > 0) {
                    for (int i = 0; i < fMarryListSize; i++) {
                        FMarry fMarry1 = fMarryList.get(i);
                        if (i == (fMarryListSize - 1)) {
                            //最后一个不拼接“、”
                            mateNamesSB.append(fMarry1.getMateFullName());
                        } else {
                            mateNamesSB.append(fMarry1.getMateFullName() + "、");
                        }
                    }
                } else {
                    mateNamesSB.append("-");
                }
            }else{
                mateNamesSB.append("-");
            }
            member1.setMateNames(mateNamesSB.toString());
        }
        return getDataTable(list);
    }

    /**
     * 导出家族成员列表
     */
    @RequiresPermissions("family:member:export")
    @Log(title = "家族成员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Member member)
    {
        List<Member> list = memberService.selectMemberList(member);
        ExcelUtil<Member> util = new ExcelUtil<Member>(Member.class);
        return util.exportExcel(list, "家族成员数据");
    }

    /**
     * 新增家族成员
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
     * 新增保存家族成员
     */
    @RequiresPermissions("family:member:add")
    @Log(title = "家族成员", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Member member)
    {
        //设置创建者和修改者
        member.setCreateBy(ShiroUtils.getLoginName());
        member.setUpdateBy(ShiroUtils.getLoginName());
        int insertResult = memberService.insertMember(member);
        //解析配偶编号集合字符串
        String mateIdsStr = member.getMateIds();
        if(mateIdsStr != null && !("").equals(mateIdsStr) && insertResult > 0)
        {
            List<Integer> mateIdList = getMateIdListByStr(mateIdsStr);
            for(Integer mateId : mateIdList){
                //构造数据并把数据插入家族婚配管理信息表
                FMarry fMarry = new FMarry();
                fMarry.setMateId(mateId);
                fMarry.setMemberId(member.getmId());
                fMarry.setRemark("");
                fMarryService.insertFMarry(fMarry);
            }
        }
        return toAjax(insertResult);
    }

    /**
     * 修改家族成员
     */
    @RequiresPermissions("family:member:edit")
    @GetMapping("/edit/{mId}")
    public String edit(@PathVariable("mId") Integer mId, ModelMap mmap)
    {
        Member member = memberService.selectMemberByMId(mId);
        mmap.put("member", member);
        //查询家族列表数据并传入变量备用
        List<FClan> fcanList = fClanService.selectFClanList();
        mmap.put("fcanList", fcanList);
        //查询辈分列表
        List<FSeniority> fSeniorityList = fSeniorityService.selectFSeniorityListBySurnameId(member.getSurnameId());
        mmap.put("fSeniorityList", fSeniorityList);
        return prefix + "/edit";
    }

    /**
     * 修改保存家族成员
     */
    @RequiresPermissions("family:member:edit")
    @Log(title = "家族成员", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Member member)
    {
        member.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(memberService.updateMember(member));
    }

    /**
     * 删除家族成员
     */
    @RequiresPermissions("family:member:remove")
    @Log(title = "家族成员", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        //return toAjax(memberService.deleteMemberByMIds(ids));//物理删除代码
        return toAjax(memberService.updateMemberDelByMIds(ids));
    }

    /**
     * 家族列表选项值改变发起联动请求
     */
    @GetMapping("/clanSelectChange")
    @ResponseBody
    public TableDataInfo getClanChangeData(Integer surnameId, Integer sId, Integer mId)
    {
        ClanChangeData cData = new ClanChangeData();
        cData.setfSeniorityList(fSeniorityService.selectFSeniorityListBySurnameId(surnameId));
        List<FMate> mateList = fMateService.selectFMateListBySurnameId(surnameId);
        //遍历数据集合查询出配偶（家族成员姓名）集合
        for(FMate fMate1 : mateList) {
            StringBuilder memberNamesSB = new StringBuilder();
            FMarry fMarry = new FMarry();
            fMarry.setMateId(fMate1.getmId());
            List<FMarry> fMarryList = fMarryService.selectFMarryList(fMarry);
            int fMarryListSize = fMarryList.size();
            if(fMarryListSize > 0){
                for(int i = 0; i < fMarryListSize; i++) {
                    FMarry fMarry1 = fMarryList.get(i);
                    if(i == (fMarryListSize - 1)){
                        //最后一个不拼接“,”
                        memberNamesSB.append(fMarry1.getMemFullName());
                    }else{
                        memberNamesSB.append(fMarry1.getMemFullName() + "、");
                    }
                }
            }else{
                memberNamesSB.append("-");
            }
            fMate1.setMemberNames(memberNamesSB.toString());
        }
        cData.setfMateList(mateList);
        cData.setMemberList(getEligibleMemberList(surnameId, sId, mId));
        List<ClanChangeData> list = new ArrayList<>();
        list.add(cData);
        return getDataTable(list);
    }

    /**
     * 辈分列表选项值改变发起联动请求
     */
    @GetMapping("/senioritySelectChange")
    @ResponseBody
    public TableDataInfo getSeniorityIdChangeData(Integer surnameId, Integer sId, Integer mId)
    {
        List<Member> memberList = getEligibleMemberList(surnameId, sId, mId);
        return getDataTable(memberList);
    }

    /**
     * 查询符合条件的家族成员集合
     * @param surnameId 家族编号
     * @param sId 辈分编号
     * @param mId 家族成员编号
     * @return 符合条件的家族成员集合
     */
    private List<Member> getEligibleMemberList(Integer surnameId, Integer sId, Integer mId)
    {
        //父亲列表中不能包含自己
        List<Integer> notInMIds = null;
        if(mId != null)
        {
            notInMIds = new ArrayList<>();
            notInMIds.add(mId);
        }
        //查询选择辈分编号的辈分序号编号(用来查询父亲列表的辈分必须大于当前辈分)
        Integer ltNumSort = null;
        if(sId != null){
            FSeniority fSeniority = fSeniorityService.selectFSeniorityBySId(sId);
            ltNumSort = fSeniority.getNumSort();
        }
        List<Member> memberList = memberService.selectManMemberListBySurnameId(surnameId, notInMIds, ltNumSort);
        return memberList;
    }

    /**
     * 修改头像
     */
    @GetMapping("/avatar/{mId}")
    public String avatar(@PathVariable("mId") Integer mId, ModelMap mmap)
    {
        curHeadUrlMember = memberService.selectMemberByMId(mId);
        mmap.put("cur_headUrl", curHeadUrlMember.getHeadUrl());
        return prefix + "/avatar";
    }

    /**
     * 保存头像
     */
    @Log(title = "家族成员头像", businessType = BusinessType.UPDATE)
    @PostMapping("/updateAvatar")
    @ResponseBody
    public AjaxResult updateAvatar(@RequestParam("avatarfile") MultipartFile file)
    {
        try
        {
            if (!file.isEmpty())
            {
                String avatar = FileUploadUtils.upload(RuoYiConfig.getAvatarPath(), file, MimeTypeUtils.IMAGE_EXTENSION);
                if(curHeadUrlMember != null) {
                    curHeadUrlMember.setHeadUrl(avatar);
                    curHeadUrlMember.setUpdateBy(ShiroUtils.getLoginName());
                    toAjax(memberService.updateMember(curHeadUrlMember));
                    return success();
                }else{
                    return error();
                }
            }
            return error();
        }
        catch (Exception e)
        {
            log.error("修改头像失败！", e);
            return error(e.getMessage());
        }
    }

    /**
     * 根据配偶成员编号集合字符串拆分出数组
     * @param mateIdsStr 配偶编号集合字符串
     * @return 配偶成员编号数组
     */
    private List<Integer> getMateIdListByStr(String mateIdsStr)
    {
        List<Integer> list = new ArrayList<>();
        String [] mateIdArr = mateIdsStr.split(",");
        for (String item: mateIdArr) {
            if(!item.equals("")){
                list.add(Integer.parseInt(item));
            }
        }
        return list;
    }
}
