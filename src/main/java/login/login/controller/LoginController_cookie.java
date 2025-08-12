package login.login.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import login.login.domain.EzenMember;
import login.login.dto.LoginForm;
import login.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

//@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController_cookie {

    public final LoginService loginService;

    //로그인 폼을 보여주는 로직
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        model.addAttribute("page", "login");
        return "user/loginForm";
    }

    //로그인이 되는 로직
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "user/loginForm";
        }

        EzenMember loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());

        //log.info("로그인한 맴버의 아이디는? {}", loginMember.getLoginId());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "user/loginForm";
        }

        //쿠키에 시간 정보를 주지 않으면 세션 쿠키는 브라우저 종료 시 모두 종료
        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
        response.addCookie(idCookie);
        log.info("로그인한 회원은? {} {}", loginMember.getLoginId(), loginMember.getName());
        return "redirect:/";
    }

    //로그아웃 기능
    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        expireCookie(response, "memberId");
        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse response, String memberId) {
        Cookie cookie = new Cookie(memberId, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}