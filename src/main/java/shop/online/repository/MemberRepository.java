package shop.online.repository;

import shop.online.domain.Member;

import java.util.List;

public interface MemberRepository {
    void save(Member member);
    Member findOne(Long id);
    List<Member> findAll();
    List<Member> findByName(String name);

}
