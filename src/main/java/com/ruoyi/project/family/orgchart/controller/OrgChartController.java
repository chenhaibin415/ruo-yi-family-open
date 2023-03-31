package com.ruoyi.project.family.orgchart.controller;

import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.family.clan.domain.FClan;
import com.ruoyi.project.family.clan.service.IFClanService;
import com.ruoyi.project.family.front.domain.FamilyRelations;
import com.ruoyi.project.family.marry.domain.FMarry;
import com.ruoyi.project.family.marry.service.IFMarryService;
import com.ruoyi.project.family.mate.domain.FMate;
import com.ruoyi.project.family.member.domain.ClanChangeData;
import com.ruoyi.project.family.member.domain.Member;
import com.ruoyi.project.family.orgchart.domain.OrgChartInfo;
import com.ruoyi.project.system.user.controller.ProfileController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.project.family.member.service.IMemberService;
import com.ruoyi.framework.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 家族结构图Controller
 * 
 * @author haibin
 * @date 2022-09-04
 */
@Controller
@RequestMapping("/family/orgchart")
public class OrgChartController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);
    private String prefix = "family/orgchart";

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IFMarryService fMarryService;
    @Autowired
    private IFClanService fClanService;

    private int forNum = 0;//遍历次数

    @RequiresPermissions("family:orgchart:view")
    @GetMapping()
    public String orgchart(ModelMap mmap)
    {
        //查询家族列表数据并传入变量备用
        List<FClan> fcanList = fClanService.selectFClanList();
        mmap.put("fcanList", fcanList);
        return prefix + "/orgchart";
    }

    /**
     * 家族列表选项值改变发起联动请求
     */
    @GetMapping("/clanSelectChange")
    @ResponseBody
    public TableDataInfo getClanChangeData(Integer surnameId)
    {
        ClanChangeData cData = new ClanChangeData();
        Member member = new Member();
        member.setSurnameId(surnameId);
        member.setSex("0");//男性
        member.setOrderByNumSort("1");//按照辈分升序
        cData.setMemberList(memberService.selectMemberList(member));
        List<ClanChangeData> list = new ArrayList<>();
        list.add(cData);
        return getDataTable(list);
    }

    /**
     * 根据家族成员编号查询对应的配偶姓名或集合
     * @param mId 家族成员编号
     * @return
     */
    private String getMateNamesByMemberId(Integer mId)
    {
        StringBuilder mateNamesSB = new StringBuilder();
        FMarry fMarry = new FMarry();
        fMarry.setMemberId(mId);
        List<FMarry> fMarryList = fMarryService.selectFMarryList(fMarry);
        int fMarryListSize = fMarryList.size();
        if(fMarryListSize > 0){
            for(int i = 0; i < fMarryListSize; i++) {
                FMarry fMarry1 = fMarryList.get(i);
                if(i == (fMarryListSize - 1)){
                    //最后一个不拼接“,”
                    mateNamesSB.append(fMarry1.getMateFullName());
                }else{
                    mateNamesSB.append(fMarry1.getMateFullName() + "、");
                }
            }
        }else{
            mateNamesSB.append("");
        }
        return mateNamesSB.toString();
    }

    /**
     * 根据家族成员编号查询对应信息对象
     * @param mId 家族成员编号
     * @return
     */
    private OrgChartInfo getChartInfoByMId(Integer mId)
    {
        OrgChartInfo orgChartInfo = new OrgChartInfo();
        Member member = memberService.selectMemberByMId(mId);
        if(member != null){
            forNum++;
            //获取到配偶姓名集合
            String mateNames = "";
            orgChartInfo.setName(member.getFullName() + "（第" + member.getNumSort() + "世）");
            //设置样式
            if(forNum == 1){
                orgChartInfo.setClassName(OrgChartInfo.ROSEREDCLS);
            }else{
                //男性
                if(member.getSex().equals("0")){
                    orgChartInfo.setClassName(OrgChartInfo.BLUECLS);
                }else{
                    orgChartInfo.setClassName(OrgChartInfo.BRIGHTREDCLS);
                }
            }
            //如果是男性才查询配偶
            if(member.getSex().equals("0")){
                mateNames = getMateNamesByMemberId(mId);
                orgChartInfo.setTitle(mateNames);
                //查询家族成员本人名下儿女
                Member memberZNSelectMember = new Member();
                memberZNSelectMember.setFatherId(mId);
                List<Member> memberZNList = memberService.selectMemberList(memberZNSelectMember);
                if(memberZNList != null && memberZNList.size() > 0){
                    List<OrgChartInfo> children = new ArrayList<>();
                    for(int i = 0; i < memberZNList.size(); i++){
                        children.add(getChartInfoByMId(memberZNList.get(i).getmId()));
                    }
                    orgChartInfo.setChildren(children);
                }
            }
        }
        return orgChartInfo;
    }

    /**
     * 根据家族成员编号返回json对象给请求
     * @param mId 家族成员编号
     * @return
     */
    @GetMapping("/getOrgChartByMId")
    @ResponseBody
    public OrgChartInfo getOrgChartByMId(Integer mId)
    {
        forNum = 0;
        OrgChartInfo rootInfo = getChartInfoByMId(mId);
        return rootInfo;
    }
}
