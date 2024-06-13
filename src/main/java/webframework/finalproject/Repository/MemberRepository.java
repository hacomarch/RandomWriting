package webframework.finalproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webframework.finalproject.Model.Member;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member findById(int id);
    List<Member> findByUserid(String userid);
    List<Member> findByNickname(String nickname);
}
