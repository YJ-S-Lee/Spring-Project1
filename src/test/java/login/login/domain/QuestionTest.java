package login.login.domain;

import login.login.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionTest {

    @Autowired //테스트에서는 이걸 사용해야 등록이 된다.
    private QuestionRepository questionRepository;

    @Test
    void 질문1() {
        Question question1 = new Question();
        question1.setSubject("제목입니다.");
        question1.setContent("내용입니다.");
        question1.setCreatDate(LocalDateTime.now());
        questionRepository.save(question1);
    }

    @Test
    void 질문2() {
        Question question2 = new Question();
        question2.setSubject("제목입니다.");
        question2.setContent("내용입니다.");
        question2.setCreatDate(LocalDateTime.now());
        questionRepository.save(question2);
    }

}