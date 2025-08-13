package login.login.service;

import login.login.domain.EzenMember;
import login.login.domain.Question;
import login.login.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    //질문 생성 시 저장하기(create)
    public void create(Question question) {
        questionRepository.save(question);
    }

    //질문 전체 조회(getList)
    public Page<Question> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("creatDate"));
        PageRequest pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return questionRepository.findAll(pageable);
    }

    //질문 하나 조회(getQuestionOne)
    public Optional<Question> getQuestionOne(Long id) {
        return questionRepository.findById(id);
    }
}