package login.login.service;

import login.login.domain.EzenMember;
import login.login.dto.MemberForm;
import login.login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //회원 가입
    public Long join(EzenMember member) {

        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    //회원 수정
    public Long update(EzenMember member) {
        memberRepository.save(member);
        return member.getId();
    }

    //중복 회원 검증
    private void validateDuplicateMember(EzenMember member) {

        Optional<EzenMember> byLoginId = memberRepository.findByLoginId(member.getLoginId());
        if(byLoginId.isPresent()){
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }

    //전체 회원 조회
    public List<EzenMember> findMemberList() {
        return memberRepository.findAll();
    }

    //회원 한 명 조회
    public Optional<EzenMember> findOneMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    //회원 삭제
    public void deleteMember(Long memberId) {
        this.memberRepository.deleteById(memberId);
    }
}