package login.login.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter //원래는 Entity와 같이 쓰면 안된다. 필요한 부분에만 Setter를 따로 따로 줘야한다.
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime creatDate;

    private LocalDateTime modifyDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private EzenMember author;  //글쓴이

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}