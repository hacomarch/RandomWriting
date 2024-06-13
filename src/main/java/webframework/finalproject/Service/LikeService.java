package webframework.finalproject.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webframework.finalproject.Model.Like;
import webframework.finalproject.Model.Member;
import webframework.finalproject.Model.Post;
import webframework.finalproject.Repository.LikeRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikeService {
    @Autowired
    LikeRepository likeRepository;

    @Transactional
    public Like save(Like like) {
        Like save = likeRepository.save(like);
        return save;
    }

    @Modifying
    @Transactional
    public void delete(int id) {
        likeRepository.deleteById(id);
    }

    public List<Like> findByPost(Post post) {
        return likeRepository.findByPost(post);
    }

    public List<Like> findByMember(Member member) {
        return likeRepository.findByMember(member);
    }

    public Like findById(int id) {
        return likeRepository.findById(id);
    }
}
