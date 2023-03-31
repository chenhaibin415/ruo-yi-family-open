package com.ruoyi.project.family.front.controller;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.family.clan.domain.FClan;
import com.ruoyi.project.family.clan.service.IFClanService;
import com.ruoyi.project.family.front.domain.FamilyRelations;
import com.ruoyi.project.family.front.domain.SimpleMember;
import com.ruoyi.project.family.marry.domain.FMarry;
import com.ruoyi.project.family.marry.service.IFMarryService;
import com.ruoyi.project.family.mate.domain.FMate;
import com.ruoyi.project.family.mate.service.IFMateService;
import com.ruoyi.project.family.member.domain.Member;
import com.ruoyi.project.family.member.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 前台显示Controller
 *
 * @author haibin
 * @date 2022-09-19
 */
@Controller
@RequestMapping("/family/front")
public class FrontController  extends BaseController {

    private String prefix = "family/front";

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IFMateService fMateService;

    @Autowired
    private IFClanService fClanService;

    @Autowired
    private IFMarryService fMarryService;

    @GetMapping("/index/{clanCode}")
    public String front(@PathVariable("clanCode") String clanCode)
    {
        return prefix + "/index";
    }

    @GetMapping("/mateInfo/{mId}")
    public String mateInfo(@PathVariable("mId") Integer mId, String clanCode, String selectName, ModelMap mmap)
    {
        FMate fMate = fMateService.selectFMateByMId(mId);
        this.selectMemberByMate(fMate);//查询配偶成员
        mmap.put("fMate", fMate);
        mmap.put("clanCode", clanCode);
        mmap.put("selectName", selectName);
        return prefix + "/mateInfo";
    }

    @GetMapping("/memberInfo/{mId}")
    public String memberInfo(@PathVariable("mId") Integer mId, String clanCode, String selectName, ModelMap mmap)
    {
        Member member = memberService.selectMemberByMId(mId);
        this.selectMateByMember(member);//查询配偶
        mmap.put("member", member);
        mmap.put("clanCode", clanCode);
        mmap.put("selectName", selectName);
        //处理出生日期时分部分
        String birthdayTime = DateUtils.parseDateToStr(DateUtils.HH_MM, member.getBirthday());
        if("00时00分".equals(birthdayTime)){
            birthdayTime = "";
        }
        mmap.put("birthdayTime", birthdayTime);
        return prefix + "/memberInfo";
    }

    /**
     * 根据家族编码和姓名查询人员列表
     */
    @RequestMapping("/getListByName")
    @ResponseBody
    public TableDataInfo getListByName(String name, String clanCode)
    {
        List<SimpleMember> list = new ArrayList<>();
        //首先根据家族编码查询家族信息
        FClan fClan = fClanService.selectFClanByCCode(clanCode);
        if(fClan != null && name != null && !name.equals("") && name.length() > 1){
            name = name.trim();//去除空格
            //根据姓名拆分出姓和名字
            String selectSurname = name.substring(0, 1);
            String selectName = name.substring(1);
            SimpleMember ownSimpleMember = null;
            Member ownMember = null;
            //判断姓是否家族成员
            if(fClan.getSurname().equals(selectSurname)){
                Member selectMember = new Member();
                selectMember.setSurnameId(fClan.getcId());
                selectMember.setSelectName(selectName);
                List<Member> memberList = memberService.selectMemberList(selectMember);
                if(memberList != null && memberList.size() > 0){
                    ownMember = memberList.get(0);
                    ownSimpleMember = convertSimpleMemberByMember(ownMember, FamilyRelations.OWN);
                }else{
                    //如果家族成员中没有找到则在配偶成员中找，避免姓一样
                    ownSimpleMember = getMateSimpleMember(selectSurname, selectName, fClan.getcId());
                }
            }else{
                //配偶成员中查找
                ownSimpleMember = getMateSimpleMember(selectSurname, selectName, fClan.getcId());
            }
            //如果找到本人
            if(ownSimpleMember != null){
                //将本人添加到集合当中
                this.addSimpleMember(list, ownSimpleMember);
                //如果本人是家族成员（查询爷爷、奶奶、父母、兄弟姐妹、儿女）
                if(ownSimpleMember.getMemberType().equals(SimpleMember.MEMBERTYPE)){
                    //如果是男的，从婚配表中查询妻子可能是多个
                    if(ownMember.getSex().equals("0")){
                        FMarry selectQZFmarry = new FMarry();
                        selectQZFmarry.setSurnameId(fClan.getcId());
                        selectQZFmarry.setMemberId(ownSimpleMember.getmId());
                        List<FMarry> fMarryQZList = fMarryService.selectFMarryList(selectQZFmarry);
                        for(int k = 0; k < fMarryQZList.size(); k++){
                            FMate qzFMate = fMateService.selectFMateByMId(fMarryQZList.get(k).getMateId());
                            SimpleMember qzSM = convertSimpleMemberByMate(qzFMate, FamilyRelations.WIFE);
                            //将妻子添加到集合当中
                            this.addSimpleMember(list, qzSM);
                        }
                    }
                    //查询本人名下儿女
                    Member memberZNSelectMember = new Member();
                    memberZNSelectMember.setSurnameId(fClan.getcId());
                    memberZNSelectMember.setFatherId(ownSimpleMember.getmId());
                    List<Member> memberZNList = memberService.selectMemberList(memberZNSelectMember);
                    for(int n = 0; n < memberZNList.size(); n++){
                        String relations = FamilyRelations.SON;
                        Member memberZNMember = memberZNList.get(n);
                        if(memberZNMember.getSex().equals("1")){
                            relations = FamilyRelations.DAUGHTER;
                        }
                        this.addSimpleMember(list, convertSimpleMemberByMember(memberZNMember, relations));
                    }
                    //查询本人父亲
                    Member fatherMember = memberService.selectMemberByMId(ownMember.getFatherId());
                    this.addSimpleMember(list, convertSimpleMemberByMember(fatherMember, FamilyRelations.FATHER));
                    //查询本人母亲
                    FMate motherFMate = fMateService.selectFMateByMId(ownMember.getMotherId());
                    this.addSimpleMember(list, convertSimpleMemberByMate(motherFMate, FamilyRelations.MOTHER));
                    //查询本人爷爷
                    Member grandpaMember = memberService.selectMemberByMId(fatherMember.getFatherId());
                    this.addSimpleMember(list, convertSimpleMemberByMember(grandpaMember, FamilyRelations.GRANDPA));
                    //查询本人奶奶
                    FMate grandmaFMate = fMateService.selectFMateByMId(fatherMember.getMotherId());
                    this.addSimpleMember(list, convertSimpleMemberByMate(grandmaFMate, FamilyRelations.GRANDMA));
                    //查询本人兄弟姐们
                    Member xdjmSelectMember = new Member();
                    xdjmSelectMember.setSurnameId(fClan.getcId());
                    xdjmSelectMember.setFatherId(ownMember.getFatherId());
                    List<Member> xdjmList = memberService.selectMemberList(xdjmSelectMember);
                    for(int n = 0; n < xdjmList.size(); n++){
                        Member xdjmMember = xdjmList.get(n);
                        //踢除自己
                        if(xdjmMember.getmId() != ownMember.getmId()) {
                            String relations = FamilyRelations.BROTHER;
                            //本人和比较者都有出生日期的情况
                            if(ownMember.getBirthday() != null && xdjmMember.getBirthday() != null) {
                                //如果本人的出生时间大(年龄反而小)
                                if (DateUtils.compareTo(ownMember.getBirthday(), xdjmMember.getBirthday())) {
                                    if (xdjmMember.getSex().equals("1")) {
                                        relations = FamilyRelations.SISTER;
                                    } else {
                                        relations = FamilyRelations.BROTHER;
                                    }
                                } else {
                                    if (xdjmMember.getSex().equals("1")) {
                                        relations = FamilyRelations.YOUNGERSISTER;
                                    } else {
                                        relations = FamilyRelations.YOUNGERBROTHER;
                                    }
                                }
                            }else{
                                //出生日期不全靠家中排行来比较大小(数字越大反而小)
                                if (ownMember.getSortNum() > xdjmMember.getSortNum()) {
                                    if (xdjmMember.getSex().equals("1")) {
                                        relations = FamilyRelations.SISTER;
                                    } else {
                                        relations = FamilyRelations.BROTHER;
                                    }
                                } else {
                                    if (xdjmMember.getSex().equals("1")) {
                                        relations = FamilyRelations.YOUNGERSISTER;
                                    } else {
                                        relations = FamilyRelations.YOUNGERBROTHER;
                                    }
                                }
                            }
                            this.addSimpleMember(list, convertSimpleMemberByMember(xdjmMember, relations));
                        }
                    }
                }else{
                    //如果本人是配偶成员则查询丈夫、儿女
                    //从婚配表查询丈夫可能多个
                    FMarry selectFmarry = new FMarry();
                    selectFmarry.setSurnameId(fClan.getcId());
                    selectFmarry.setMateId(ownSimpleMember.getmId());
                    List<FMarry> fMarryList = fMarryService.selectFMarryList(selectFmarry);
                    for(int i = 0; i < fMarryList.size(); i++){
                        Member zfMember = memberService.selectMemberByMId(fMarryList.get(i).getMemberId());
                        SimpleMember zfSM = convertSimpleMemberByMember(zfMember, FamilyRelations.HUSBAND);
                        //将老公添加到集合当中
                        this.addSimpleMember(list, zfSM);
                    }
                    //查询本人子女
                    Member mateZNSelectMember = new Member();
                    mateZNSelectMember.setSurnameId(fClan.getcId());
                    mateZNSelectMember.setMotherId(ownSimpleMember.getmId());
                    List<Member> mateZNList = memberService.selectMemberList(mateZNSelectMember);
                    for(int j = 0; j < mateZNList.size(); j++){
                        String relations = FamilyRelations.SON;
                        Member mateZNMember = mateZNList.get(j);
                        if(mateZNMember.getSex().equals("1")){
                            relations = FamilyRelations.DAUGHTER;
                        }
                        this.addSimpleMember(list, convertSimpleMemberByMember(mateZNMember, relations));
                    }
                }
            }
        }
        return getDataTable(list);
    }

    /**
     * 当家庭成员简易信息存在时添加
     * @param list 家庭成员对象集合
     * @param simpleMember 家庭成员对象
     */
    private void addSimpleMember(List<SimpleMember> list, SimpleMember simpleMember){
        if(simpleMember != null){
            list.add(simpleMember);
        }
    }

    /**
     * 根据姓和名字还有家族编号查询配偶成员信息
     * @param surname 姓
     * @param name 名字
     * @param surnameId 家族编号
     * @return
     */
    private SimpleMember getMateSimpleMember(String surname, String name, int surnameId){
        SimpleMember simpleMember = null;
        FMate selectFMate = new FMate();
        selectFMate.setSurnameId(surnameId);
        selectFMate.setSurname(surname);
        selectFMate.setSelectName(name);
        List<FMate> mateList = fMateService.selectFMateList(selectFMate);
        if(mateList != null && mateList.size() > 0){
            FMate resultFMate = mateList.get(0);
            simpleMember = convertSimpleMemberByMate(resultFMate, FamilyRelations.OWN);
        }
        return simpleMember;
    }

    /**
     * 把家族成员对象转换成家庭简易成员信息对象
     * @param member 家族成员
     * @param relations 家庭关系
     * @return
     */
    private SimpleMember convertSimpleMemberByMember(Member member, String relations){
        SimpleMember simpleMember = null;
        if(member != null){
            simpleMember = new SimpleMember();
            simpleMember.setFullName(member.getFullName());
            simpleMember.setmId(member.getmId());
            simpleMember.setMemberType(SimpleMember.MEMBERTYPE);
            simpleMember.setBirthday(member.getBirthdayStr());
            simpleMember.setRelations(relations);
            simpleMember.setHeadUrl(member.getHeadUrl());
        }
        return simpleMember;
    }

    /**
     * 把配偶成员对象转换成家庭简易成员信息对象
     * @param fMate 配偶成员
     * @param relations 家庭关系
     * @return
     */
    private SimpleMember convertSimpleMemberByMate(FMate fMate, String relations){
        SimpleMember simpleMember = null;
        if(fMate != null) {
            simpleMember = new SimpleMember();
            simpleMember.setFullName(fMate.getFullName());
            simpleMember.setmId(fMate.getmId());
            simpleMember.setMemberType(SimpleMember.MATETYPE);
            simpleMember.setBirthday(fMate.getBirthdayStr());
            simpleMember.setRelations(relations);
            simpleMember.setHeadUrl(fMate.getHeadUrl());
        }
        return simpleMember;
    }

    /**
     * 查询家族成员配偶
     * @param member1 家族成员
     */
    private void selectMateByMember(Member member1){
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

    /**
     * 查询配偶成员配偶
     * @param fMate1 配偶成员
     */
    private void selectMemberByMate(FMate fMate1){
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


}
