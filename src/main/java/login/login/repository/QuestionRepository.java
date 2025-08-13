package login.login.repository;

import login.login.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    //findAll매서드 추가
    Page<Question> findAll(Pageable pageable);
}