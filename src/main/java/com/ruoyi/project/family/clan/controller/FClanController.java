package com.ruoyi.project.family.clan.controller;

import java.util.List;

import com.ruoyi.common.utils.security.ShiroUtils;
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
import com.ruoyi.project.family.clan.domain.FClan;
import com.ruoyi.project.family.clan.service.IFClanService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 家族Controller
 * 
 * @author haibin
 * @date 2022-08-03
 */
@Controller
@RequestMapping("/family/clan")
public class FClanController extends BaseController
{
    private String prefix = "family/clan";

    @Autowired
    private IFClanService fClanService;

    @RequiresPermissions("family:clan:view")
    @GetMapping()
    public String clan()
    {
        return prefix + "/clan";
    }

    /**
     * 查询家族列表
     */
    @RequiresPermissions("family:clan:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FClan fClan)
    {
        startPage();
        List<FClan> list = fClanService.selectFClanList(fClan);
        return getDataTable(list);
    }

    /**
     * 导出家族列表
     */
    @RequiresPermissions("family:clan:export")
    @Log(title = "家族", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FClan fClan)
    {
        List<FClan> list = fClanService.selectFClanList(fClan);
        ExcelUtil<FClan> util = new ExcelUtil<FClan>(FClan.class);
        return util.exportExcel(list, "家族数据");
    }

    /**
     * 新增家族
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存家族
     */
    @RequiresPermissions("family:clan:add")
    @Log(title = "家族", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(FClan fClan)
    {
        //设置创建者和修改者
        fClan.setCreateBy(ShiroUtils.getLoginName());
        fClan.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(fClanService.insertFClan(fClan));
    }

    /**
     * 修改家族
     */
    @RequiresPermissions("family:clan:edit")
    @GetMapping("/edit/{cId}")
    public String edit(@PathVariable("cId") Integer cId, ModelMap mmap)
    {
        FClan fClan = fClanService.selectFClanByCId(cId);
        mmap.put("fClan", fClan);
        return prefix + "/edit";
    }

    /**
     * 修改保存家族
     */
    @RequiresPermissions("family:clan:edit")
    @Log(title = "家族", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FClan fClan)
    {
        fClan.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(fClanService.updateFClan(fClan));
    }

    /**
     * 删除家族
     */
    @RequiresPermissions("family:clan:remove")
    @Log(title = "家族", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        //return toAjax(fClanService.deleteFClanByCIds(ids));//物理删除备份，启用以下逻辑删除
        return toAjax(fClanService.updateFClanDelByCIds(ids));
    }
}
