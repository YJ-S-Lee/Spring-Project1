package login.login.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {

    private Long id;

    private String grade;

    @NotEmpty(message = "아이디는 필수 입력사항입니다.")
    private String loginId;

    @NotEmpty(message = "이름은 필수 입력사항입니다.")
    private String name;

    @NotEmpty(message = "비밀번호는 필수 입력사항입니다.")
    private String password;
}