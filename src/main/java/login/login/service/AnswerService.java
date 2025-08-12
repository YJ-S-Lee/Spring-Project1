package login.login.service;

import login.login.domain.Answer;
import login.login.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    //답변 생성 시 저장하기(create)
    public void create(Answer answer) {

        answerRepository.save(answer);
    }
}