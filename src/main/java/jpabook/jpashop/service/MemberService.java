package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 가입
    @Transactional
    public Long join(Member member) {
        // 중복 여부 확인
        validateDuplicateMember(member.getName());
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(String name) {
        List<Member> membersWithSameName = memberRepository.findByName(name);
        if (!membersWithSameName.isEmpty()) {
            throw new IllegalStateException("중복된 이름이 있습니다");
        }
    }

    // 회원 조회(단건)
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    // 회원 조회(모두)
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

}
