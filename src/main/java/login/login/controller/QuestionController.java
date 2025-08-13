package login.login.controller;

import jakarta.validation.Valid;
import login.login.domain.EzenMember;
import login.login.domain.Question;
import login.login.dto.AnswerForm;
import login.login.dto.QuestionForm;
import login.login.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    //목록 보기
    //http://localhost:8080/question/list?page=1
    @GetMapping("/question/list")
    public String list(@SessionAttribute(name = SessionConst.LOGIN_MEMBER)EzenMember loginMember, @RequestParam(value = "page", defaultValue = "0") int page, Model model) {

        Page<Question> paging = questionService.getList(page);
        model.addAttribute("paging", paging);
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
        return "user/questionDetail";
    }

    //수정 페이지 열기
    @GetMapping("/question/modify/{questionId}")
    public String questionModify(@PathVariable("questionId") Long questionId, QuestionForm questionForm, @SessionAttribute(name = SessionConst.LOGIN_MEMBER)EzenMember loginMember, Model model ) {

        Optional<Question> question = questionService.getQuestionOne(questionId);
        questionForm.setSubject(question.get().getSubject());
        questionForm.setContent(question.get().getContent());

        model.addAttribute("questionForm", questionForm);
        model.addAttribute("loginMember", loginMember);
        return "user/questionForm";
    }

    //질문 수정 로직
    @PostMapping("/question/modify/{questionId}")
    public String questionModify(@Valid @ModelAttribute("questionForm") QuestionForm questionForm,BindingResult bindingResult, @SessionAttribute(name = SessionConst.LOGIN_MEMBER)EzenMember loginMember, @PathVariable("questionId") Long questionId, Model model) {

        if(bindingResult.hasErrors()) {
            model.addAttribute("loginMember", loginMember);
            return "user/questionForm";
        }

        //질문 생성 날짜 가져오기(질문을 수정하면 생성날짜가 없으므로 게시물 맨 마지막 페이지로 이동해 버린다. 그걸 방지)
        Optional<Question> findQuestion = this.questionService.getQuestionOne(questionId);

        Question question = new Question();
        question.setId(questionId);
        question.setSubject(questionForm.getSubject());
        question.setContent(questionForm.getContent());
        question.setCreatDate(findQuestion.get().getCreatDate());
        question.setModifyDate(LocalDateTime.now());
        question.setAuthor(loginMember);

        questionService.create(question);
        model.addAttribute("loginMember", loginMember);

        return String.format("redirect:/question/detail/%s", questionId);
    }
}