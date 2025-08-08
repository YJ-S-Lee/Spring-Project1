package login.login.repository;

import login.login.domain.EzenMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<EzenMember, Long> {

    //로그인 아이디로 조회하는 기능을 추가
    Optional<EzenMember> findByLoginId(String loginId);
}