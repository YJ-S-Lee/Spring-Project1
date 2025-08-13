package login.login.service;

import login.login.domain.Answer;
import login.login.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    //답변 생성 시 저장하기(create)
    public void create(Answer answer) {
        answerRepository.save(answer);
    }

    //답글 조회
    public Optional<Answer> getAnswer(Long id) {
        return answerRepository.findById(id);
    }
}