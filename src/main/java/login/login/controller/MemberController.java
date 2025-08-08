package login.login.controller;

import jakarta.validation.Valid;
import login.login.domain.EzenMember;
import login.login.dto.MemberForm;
import login.login.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "user/addMemberForm";
    }

    //회원 가입
    @PostMapping("/add")
    public String create(@Valid @ModelAttribute("memberForm") MemberForm memberForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "user/addMemberForm";
        }

        EzenMember ezenMember = new EzenMember();
        ezenMember.setLoginId(memberForm.getLoginId());
        ezenMember.setPassword(memberForm.getPassword());
        ezenMember.setName(memberForm.getName());
        ezenMember.setGrade("user");

        memberService.join(ezenMember);
        return "redirect:/";
    }

    //회원 목록
    @GetMapping(value = "/members")
    public String list(Model model){
        List<EzenMember> members = memberService.findMemberList();
        model.addAttribute("members", members);
        return "admin/memberList";
    }

    //수정하기(수정폼 보여주기)
    @GetMapping("/members/{id}/edit")
    public String updateMemberForm(@PathVariable("id") Long id, Model model) {
        EzenMember ezenMember = memberService.findOneMember(id).get();

        MemberForm memberForm = new MemberForm();

        memberForm.setId(ezenMember.getId());
        memberForm.setLoginId(ezenMember.getLoginId());
        memberForm.setName(ezenMember.getName());
        memberForm.setPassword(ezenMember.getPassword());
        memberForm.setGrade(ezenMember.getGrade());

        model.addAttribute("memberForm", memberForm);

        return "admin/updateMemberForm";
    }

    //수정하기(수정폼 저장하기)
    @PostMapping("/members/{id}/edit")
    public String updateMemberFormSave(@ModelAttribute("memberForm") MemberForm memberForm) {
        EzenMember ezenMember = new EzenMember();

        ezenMember.setId(memberForm.getId());
        ezenMember.setLoginId(memberForm.getLoginId());
        ezenMember.setName(memberForm.getName());
        ezenMember.setPassword(memberForm.getPassword());
        ezenMember.setGrade(memberForm.getGrade());

        memberService.update(ezenMember);
        return "redirect:/members";
    }

    //삭제하기
    @GetMapping("/members/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        Optional<EzenMember> findMember = memberService.findOneMember(id);
        if(findMember.isPresent()) {
            EzenMember ezenMember = findMember.get();
            memberService.deleteMember(ezenMember.getId());
        }
        return "redirect:/members";
    }
    
}