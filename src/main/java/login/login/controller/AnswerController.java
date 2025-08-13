package login.login.controller;

import jakarta.validation.Valid;
import login.login.domain.Answer;
import login.login.domain.EzenMember;
import login.login.domain.Question;
import login.login.dto.AnswerForm;
import login.login.service.AnswerService;
import login.login.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AnswerController {

    public final AnswerService answerService;
    public final QuestionService questionService;

    //질문에 대한 답글 쓰기
    @PostMapping("/answer/create/{questionId}")
    public String createAnswer(@PathVariable("questionId") Long questionId, @SessionAttribute(name = SessionConst.LOGIN_MEMBER) EzenMember loginMember, @Valid AnswerForm answerForm, BindingResult bindingResult, Model model) {

        Optional<Question> question = questionService.getQuestionOne(questionId);

        if(bindingResult.hasErrors()) {
            model.addAttribute("question", question.get());
            model.addAttribute("loginMember", loginMember);
            return "user/questionDetail";
        }

        if(question.isPresent()) {
            Answer answer = new Answer();
            answer.setContent(answerForm.getContent());
            answer.setCreatDate(LocalDateTime.now());
            answer.setQuestion(question.get());
            answer.setAuthor(loginMember);
            answerService.create(answer);
        }

        model.addAttribute("loginMember", loginMember);
        return String.format("redirect:/question/detail/%s", questionId);
    }

    //답변 수정 페이지 열기
    @GetMapping("/answer/modify/{answerId}")
    public String answerModify(@PathVariable("answerId") Long answerId, AnswerForm answerForm, @SessionAttribute(name = SessionConst.LOGIN_MEMBER)EzenMember loginMember, Model model) {

        //아이디에 해당하는 답글 조회
        Optional<Answer> answer = answerService.getAnswer(answerId);
        answerForm.setContent(answer.get().getContent());

        model.addAttribute("answerForm", answerForm);
        model.addAttribute("loginMember", loginMember);
        return "user/answerForm";
    }

    //답변 수정 저장
    @PostMapping("/answer/modify/{answerId}")
    public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult, @PathVariable("answerId") Long answerId, @SessionAttribute(name = SessionConst.LOGIN_MEMBER)EzenMember loginMember, Model model) {

        if(bindingResult.hasErrors()) {
            model.addAttribute("loginMember", loginMember);
            return "user/answerForm";
        }

        //아이디에 해당하는 답글 조회
        Optional<Answer> findAnswer = answerService.getAnswer(answerId);

        //답글에 해당하는 질문 찾아오기
        Optional<Question> findQuestion = questionService.getQuestionOne(findAnswer.get().getQuestion().getId());

        Answer  answer = new Answer();
        answer.setId(answerId);
        answer.setContent(answerForm.getContent());
        answer.setCreatDate(findAnswer.get().getCreatDate());
        answer.setModifyDate(LocalDateTime.now());
        answer.setAuthor(loginMember);
        answer.setQuestion(findQuestion.get());

        answerService.create(answer);
        model.addAttribute("loginMember", loginMember);
        return String.format("redirect:/question/detail/%s", findQuestion.get().getId());
    }
}