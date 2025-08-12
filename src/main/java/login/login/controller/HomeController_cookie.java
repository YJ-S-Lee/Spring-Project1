package login.login.controller;

import login.login.domain.EzenMember;
import login.login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

//@Controller
@RequiredArgsConstructor
public class HomeController_cookie {

    private final MemberRepository memberRepository;

    //required = false 를 줘야 비회원, 로그인하지 않은 사용자도 홈에 접속할 수 있다.
    @GetMapping("/")
    public String home(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {
        if(memberId == null) {
            return "home";
        }
        
        //로그인, 저장소에 가서 아이디를 찾아 꺼낸
        Optional<EzenMember> loginMember = memberRepository.findById(memberId);

        if(loginMember.isPresent()) {
            EzenMember findEzenMember = loginMember.get();
            model.addAttribute("loginMember", findEzenMember);
            return "loginHome";
        }
        return "home";
    }
}