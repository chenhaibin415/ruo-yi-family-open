package com.ruoyi.project.family.seniority.controller;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.family.clan.domain.FClan;
import com.ruoyi.project.family.clan.service.IFClanService;
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
import com.ruoyi.project.family.seniority.domain.FSeniority;
import com.ruoyi.project.family.seniority.service.IFSeniorityService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 辈分管理Controller
 * 
 * @author haibin
 * @date 2022-08-02
 */
@Controller
@RequestMapping("/family/seniority")
public class FSeniorityController extends BaseController
{
    private String prefix = "family/seniority";

    @Autowired
    private IFSeniorityService fSeniorityService;

    @Autowired
    private IFClanService fClanService;

    @RequiresPermissions("family:seniority:view")
    @GetMapping()
    public String seniority(ModelMap mmap)
    {
        //查询家族列表数据并传入变量备用
        List<FClan> fcanList = fClanService.selectFClanList();
        mmap.put("fcanList", fcanList);
        return prefix + "/seniority";
    }

    /**
     * 查询辈分管理列表
     */
    @RequiresPermissions("family:seniority:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FSeniority fSeniority)
    {
        startPage();
        List<FSeniority> list = fSeniorityService.selectFSeniorityList(fSeniority);
        return getDataTable(list);
    }

    /**
     * 导出辈分管理列表
     */
    @RequiresPermissions("family:seniority:export")
    @Log(title = "辈分管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FSeniority fSeniority)
    {
        List<FSeniority> list = fSeniorityService.selectFSeniorityList(fSeniority);
        ExcelUtil<FSeniority> util = new ExcelUtil<FSeniority>(FSeniority.class);
        return util.exportExcel(list, "辈分管理数据");
    }

    /**
     * 新增辈分管理
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
     * 新增保存辈分管理
     */
    @RequiresPermissions("family:seniority:add")
    @Log(title = "辈分管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(FSeniority fSeniority)
    {
        //设置创建者和修改者
        fSeniority.setCreateBy(ShiroUtils.getLoginName());
        fSeniority.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(fSeniorityService.insertFSeniority(fSeniority));
    }

    /**
     * 修改辈分管理
     */
    @RequiresPermissions("family:seniority:edit")
    @GetMapping("/edit/{sId}")
    public String edit(@PathVariable("sId") Integer sId, ModelMap mmap)
    {
        FSeniority fSeniority = fSeniorityService.selectFSeniorityBySId(sId);
        mmap.put("fSeniority", fSeniority);
        //查询家族列表数据并传入变量备用
        List<FClan> fcanList = fClanService.selectFClanList();
        mmap.put("fcanList", fcanList);
        return prefix + "/edit";
    }

    /**
     * 修改保存辈分管理
     */
    @RequiresPermissions("family:seniority:edit")
    @Log(title = "辈分管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FSeniority fSeniority)
    {
        fSeniority.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(fSeniorityService.updateFSeniority(fSeniority));
    }

    /**
     * 删除辈分管理
     */
    @RequiresPermissions("family:seniority:remove")
    @Log(title = "辈分管理", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        //return toAjax(fSeniorityService.deleteFSeniorityBySIds(ids));//原始物理删除方法
        return toAjax(fSeniorityService.updateFSeniorityDelBySIds(ids));
    }

    /**
     * 家族列表选项值改变发起联动请求
     */
    @GetMapping("/getMaxNumSort")
    @ResponseBody
    public JSONObject getMaxNumSort(Integer surnameId)
    {
        int maxNumSortVal = fSeniorityService.selectMaxNumSortBySurnameId(surnameId) + 1;
        String jsonStr = "{\"maxNumSort\":" + maxNumSortVal + ",\"code\":0,\"msg\":null}";
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        return jsonObject;
    }
}
