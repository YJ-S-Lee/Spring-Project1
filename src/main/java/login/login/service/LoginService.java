package login.login.service;

import login.login.domain.EzenMember;
import login.login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    //로그인
    public EzenMember login(String loginId, String password) {
        Optional<EzenMember> findMember = memberRepository.findByLoginId(loginId);
        if (findMember.isPresent()) {
            EzenMember ezenMember = findMember.get();
            if (ezenMember.getPassword().equals(password)) {
                return ezenMember;
            }
            else return null;
        }
        return null;
    }
}