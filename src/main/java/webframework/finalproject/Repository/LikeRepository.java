package webframework.finalproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webframework.finalproject.Model.Like;
import webframework.finalproject.Model.Member;
import webframework.finalproject.Model.Post;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
    List<Like> findByPost(Post post);
    List<Like> findByMember(Member member);
    Like findById(int id);
}
