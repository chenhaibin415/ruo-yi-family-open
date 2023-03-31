package com.ruoyi.project.family.mate.controller;

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
import com.ruoyi.project.family.member.domain.ClanChangeData;
import com.ruoyi.project.family.member.domain.Member;
import com.ruoyi.project.family.member.service.IMemberService;
import com.ruoyi.project.system.user.controller.ProfileController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.family.mate.domain.FMate;
import com.ruoyi.project.family.mate.service.IFMateService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 配偶成员Controller
 * 
 * @author haibin
 * @date 2022-08-13
 */
@Controller
@RequestMapping("/family/mate")
public class FMateController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);
    private String prefix = "family/mate";

    @Autowired
    private IFMateService fMateService;

    @Autowired
    private IFClanService fClanService;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IFMarryService fMarryService;

    //当前修改头像的配偶成员对象
    private FMate curHeadUrlFmate;


    @RequiresPermissions("family:mate:view")
    @GetMapping()
    public String mate(ModelMap mmap)
    {
        //查询家族列表数据并传入变量备用
        List<FClan> fcanList = fClanService.selectFClanList();
        mmap.put("fcanList", fcanList);
        return prefix + "/mate";
    }

    /**
     * 查询配偶成员列表
     */
    @RequiresPermissions("family:mate:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FMate fMate)
    {
        startPage();
        List<FMate> list = fMateService.selectFMateList(fMate);
        //遍历数据集合查询出配偶（家族成员姓名）集合
        for(FMate fMate1 : list) {
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
        return getDataTable(list);
    }

    /**
     * 导出配偶成员列表
     */
    @RequiresPermissions("family:mate:export")
    @Log(title = "配偶成员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FMate fMate)
    {
        List<FMate> list = fMateService.selectFMateList(fMate);
        ExcelUtil<FMate> util = new ExcelUtil<FMate>(FMate.class);
        return util.exportExcel(list, "配偶成员数据");
    }

    /**
     * 新增配偶成员
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
     * 新增保存配偶成员
     */
    @RequiresPermissions("family:mate:add")
    @Log(title = "配偶成员", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(FMate fMate)
    {
        //设置创建者和修改者
        fMate.setCreateBy(ShiroUtils.getLoginName());
        fMate.setUpdateBy(ShiroUtils.getLoginName());
        int insertResult = fMateService.insertFMate(fMate);
        //解析丈夫编号集合字符串
        String memIdsStr = fMate.getMemberIds();
        if(memIdsStr != null && !("").equals(memIdsStr) && insertResult > 0)
        {
            List<Integer> memList = getMemberIdListByStr(memIdsStr);
            for(Integer memId : memList){
                //构造数据并把数据插入家族婚配管理信息表
                FMarry fMarry = new FMarry();
                fMarry.setMateId(fMate.getmId());
                fMarry.setMemberId(memId);
                fMarry.setRemark("");
                fMarryService.insertFMarry(fMarry);
            }
        }
        return toAjax(insertResult);
    }

    /**
     * 修改配偶成员
     */
    @RequiresPermissions("family:mate:edit")
    @GetMapping("/edit/{mId}")
    public String edit(@PathVariable("mId") Integer mId, ModelMap mmap)
    {
        FMate fMate = fMateService.selectFMateByMId(mId);
        mmap.put("fMate", fMate);
        //查询家族列表数据并传入变量备用
        List<FClan> fcanList = fClanService.selectFClanList();
        mmap.put("fcanList", fcanList);
        return prefix + "/edit";
    }

    /**
     * 修改保存配偶成员
     */
    @RequiresPermissions("family:mate:edit")
    @Log(title = "配偶成员", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FMate fMate)
    {
        fMate.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(fMateService.updateFMate(fMate));
    }

    /**
     * 删除配偶成员
     */
    @RequiresPermissions("family:mate:remove")
    @Log(title = "配偶成员", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        //return toAjax(fMateService.deleteFMateByMIds(ids));//原始的物理删除方法
        return toAjax(fMateService.updateFMateDelByMIds(ids));
    }

    /**
     * 修改头像
     */
    @GetMapping("/avatar/{mId}")
    public String avatar(@PathVariable("mId") Integer mId, ModelMap mmap)
    {
        curHeadUrlFmate = fMateService.selectFMateByMId(mId);
        mmap.put("cur_headUrl", curHeadUrlFmate.getHeadUrl());
        return prefix + "/avatar";
    }

    /**
     * 保存头像
     */
    @Log(title = "配偶成员头像", businessType = BusinessType.UPDATE)
    @PostMapping("/updateAvatar")
    @ResponseBody
    public AjaxResult updateAvatar(@RequestParam("avatarfile") MultipartFile file)
    {
        try
        {
            if (!file.isEmpty())
            {
                String avatar = FileUploadUtils.upload(RuoYiConfig.getAvatarPath(), file, MimeTypeUtils.IMAGE_EXTENSION);
                if(curHeadUrlFmate != null) {
                    curHeadUrlFmate.setHeadUrl(avatar);
                    curHeadUrlFmate.setUpdateBy(ShiroUtils.getLoginName());
                    toAjax(fMateService.updateFMate(curHeadUrlFmate));
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
     * 家族列表选项值改变
     */
    @GetMapping("/clanSelectChange")
    @ResponseBody
    public TableDataInfo getClanChangeData(Integer surnameId)
    {
        List<Member> list = memberService.selectManMemberListBySurnameId(surnameId, null, null);
        return getDataTable(list);
    }

    /**
     * 根据家族成员编号集合字符串拆分出数组
     * @param memberIdsStr 成员编号集合字符串
     * @return 家族成员编号数组
     */
    private List<Integer> getMemberIdListByStr(String memberIdsStr)
    {
        List<Integer> list = new ArrayList<>();
        String [] memberIdArr = memberIdsStr.split(",");
        for (String item: memberIdArr) {
            if(!item.equals("")){
                list.add(Integer.parseInt(item));
            }
        }
        return list;
    }
}
