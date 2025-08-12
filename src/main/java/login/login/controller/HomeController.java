package login.login.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import login.login.domain.EzenMember;
import login.login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class HomeController {

    //@SessionAttribute 스프링이 제공하는 세션, 이미 로그인 된 사용자를 찾을 때 사용, 세션을 생성하지 않음.
    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)EzenMember loginMember, Model model) {

        //세션에 회원데이터가 없으면 home으로
        if(loginMember == null) {
            return "home";
        }

        //세션이 유지되면 로그인으로 이동
        model.addAttribute("loginMember", loginMember);
        return "loginHome";
    }
}