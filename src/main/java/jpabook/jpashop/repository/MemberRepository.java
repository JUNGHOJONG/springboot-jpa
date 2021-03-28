package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class MemberRepository {

    private final EntityManager entityManager;

    // 생성
    public Long save(Member member) {
        entityManager.persist(member);
        return member.getId();
    }

    // 단건 조회
    public Member findOne(Long memberId) {
        return entityManager.find(Member.class, memberId);
    }

    // 모두 조회
    public List<Member> findAll() {
        return entityManager.createQuery("select m from Member m", Member.class).getResultList();
    }

    // 이름으로 조회
    public List<Member> findByName(String name) {
        return entityManager.createQuery("select m from Member m where m.name = :name", Member.class)
                    .setParameter("name", name).getResultList();
    }
}
