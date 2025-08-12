package login.login.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class EzenMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String grade;
    private String loginId;
    private String name;
    private String password;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Question> questionList;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Answer> answerList;
    //cascade = CascadeType.ALL : 회원이 삭제되면 해당 회원이 작성한 질문과 답변도 모두 삭제된다.
}