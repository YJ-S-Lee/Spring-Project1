package login.login.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AnswerForm {

    @NotEmpty(message = "내용을 입력해주세요.")
    private String content;

}