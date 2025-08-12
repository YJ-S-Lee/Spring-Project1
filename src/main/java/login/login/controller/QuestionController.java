package login.login.controller;

import jakarta.validation.Valid;
import login.login.domain.EzenMember;
import login.login.domain.Question;
import login.login.dto.AnswerForm;
import login.login.dto.QuestionForm;
import login.login.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class QuestionController {

    public final QuestionService questionService;

    //질문 등록 페이지 이동
    @GetMapping("/question/create")
    public String questionCreateForm(@SessionAttribute(name = SessionConst.LOGIN_MEMBER)EzenMember loginMember, Model model) {
        model.addAttribute("questionForm", new QuestionForm());
        model.addAttribute("loginMember", loginMember);
        return "user/questionForm";
    }

    @PostMapping("/question/create")
    public String questionCreate(@Valid @ModelAttribute("questionForm") QuestionForm questionForm , BindingResult bindingResult, @SessionAttribute(name = SessionConst.LOGIN_MEMBER)EzenMember loginMember, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("loginMember", loginMember);
            return "user/questionForm";
        }
        Question question = new Question();
        question.setSubject(questionForm.getSubject());
        question.setContent(questionForm.getContent());
        question.setCreatDate(LocalDateTime.now());
        question.setAuthor(loginMember);

        questionService.create(question);
        model.addAttribute("loginMember", loginMember);

        return "redirect:/question/list";
    }

    //질문 목록 페이지 이동
    @GetMapping("/question/list")
    public String list(@SessionAttribute(name = SessionConst.LOGIN_MEMBER)EzenMember loginMember, Model model) {

        List<Question> questionLists = questionService.getList();
        model.addAttribute("questionList", questionLists);
        model.addAttribute("loginMember", loginMember);
        return "user/questionList";
    }

    //질문 상세 보기
    @GetMapping("/question/detail/{id}")
    public String detail(@PathVariable("id") Long id, @SessionAttribute(name = SessionConst.LOGIN_MEMBER)EzenMember loginMember, AnswerForm answerForm, Model model) {

        Question question = questionService.getQuestionOne(id).orElseThrow();

        model.addAttribute("question", question);
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("answerForm", answerForm);
        return "user/questionDtail";
    }
}