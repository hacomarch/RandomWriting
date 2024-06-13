package webframework.finalproject.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webframework.finalproject.Model.Member;
import webframework.finalproject.Model.Post;
import webframework.finalproject.Repository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Transactional
    public void save(Post post) {
        postRepository.saveAndFlush(post);
    }

    @Transactional
    public void update(int id, Post post) {
        Post postById = postRepository.findById(id);
        postById.setContent(post.getContent());
        postById.setOpen(post.getOpen());
    }

    @Transactional
    @Modifying
    public void deleteById(int id) {
        postRepository.deleteById(id);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(int id) {
        return postRepository.findById(id);
    }

    public List<Post> findByOpen(String isOpen) {
        return postRepository.findByOpen(isOpen);
    }

}
