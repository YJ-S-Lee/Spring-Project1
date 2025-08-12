package login.login.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime creatDate;

    private LocalDateTime modifyDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private EzenMember author;  //글쓴이

    //하나의 질문에 여러개의 답을 할 수 있다.
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;
    //cascade = CascadeType.REMOVE : 질문을 지우면 그 답변들도 모두 사라진다.
}