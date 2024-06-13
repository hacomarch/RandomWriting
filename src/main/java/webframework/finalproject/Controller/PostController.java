package webframework.finalproject.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import webframework.finalproject.Model.*;
import webframework.finalproject.Repository.TitleRepository;
import webframework.finalproject.Service.EmotionService;
import webframework.finalproject.Service.LikeService;
import webframework.finalproject.Service.MemberService;
import webframework.finalproject.Service.PostService;
import webframework.finalproject.sessionManager.SessionName;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;
    private final EmotionService emotionService;
    private final MemberService memberService;
    private final LikeService likeService;
    private final TitleRepository titleRepository;

    @GetMapping("/post/{title}")
    public String write(Model model,
                        @PathVariable("title") String title,
                        @SessionAttribute(name = SessionName.LOGIN_MEMBER, required = false) Member loginMember) {
        if (loginMember == null) {
            return "redirect:/isLogin";
        }

        Post post = new Post();
        post.setTitle(title);

        model.addAttribute("post", post);
        return "post/postForm";
    }

    @PostMapping("/post")
    @Transactional
    public String write(@Valid @ModelAttribute("post") Post newPost,
                        Model model,
                        @SessionAttribute(name = SessionName.LOGIN_MEMBER, required = false) Member loginMember) throws JsonProcessingException {

        Member member = memberService.findById(loginMember.getId());
        int emotionCnt = member.getEmotionCnt();

        saveTitle(newPost.getTitle());

        Post post = new Post();
        post.setId(newPost.getId());
        post.setTitle(newPost.getTitle());
        post.setContent(newPost.getContent());
        post.setCreated(LocalDateTime.now());
        post.setMember(member);
        post.setLikes(null);

        isOpen(newPost, model, post);

        postService.save(post);

        int resultEmotion = emotionService.emotionCount(post.getId());
        if (resultEmotion == 1) {
            member.setEmotionCnt(++emotionCnt);
        } else {
            member.setEmotionCnt(--emotionCnt);
        }

        model.addAttribute("myPost", true);
        model.addAttribute("post", post);
        model.addAttribute("nickname", loginMember.getNickname());
        model.addAttribute("emotionCnt", member.getEmotionCnt());
        model.addAttribute("isLikeOk", false);

        return "post/showPost";
    }

    @GetMapping("/editPost/{id}")
    public String editPost(@PathVariable("id") int id, Model model) {
        Post findPost = postService.findById(id);

        model.addAttribute("editPost", findPost);
        return "post/updatePost";
    }

    @PostMapping("/editPost/{id}")
    public String editPost(@ModelAttribute("editPost") Post editPost,
                           Model model,
                           @SessionAttribute(name = SessionName.LOGIN_MEMBER, required = false) Member loginMember) {
        isLikeOk(editPost, loginMember, model);
        postService.update(editPost.getId(), editPost);

        model.addAttribute("myPost", true);
        model.addAttribute("post", editPost);
        model.addAttribute("nickname", loginMember.getNickname());

        if (editPost.getOpen().equals("OPEN")) {
            model.addAttribute("isOpen", "공개");
        } else {
            model.addAttribute("isOpen", "비공개");
        }

        return "post/showPost";
    }

    @GetMapping("/postList")
    public String postList(Model model) {
        List<Post> findPostByOpen = postService.findByOpen("OPEN");

        model.addAttribute("postList", findPostByOpen);
        return "post/postList";
    }

    @GetMapping("/postList/{postId}")
    public String postIdByList(Model model,
                               @PathVariable("postId") int postId,
                               @SessionAttribute(name = SessionName.LOGIN_MEMBER, required = false) Member loginMember) {
        Post findPostById = postService.findById(postId);

        isLikeOk(findPostById, loginMember, model);
        isMyPost(model, loginMember, findPostById);

        model.addAttribute("post", findPostById);
        model.addAttribute("nickname", findPostById.getMember().getNickname());
        model.addAttribute("isOpen", "공개");
        return "post/showPost";
    }

    @GetMapping("/deletePost/{postId}")
    public String deletePost(@PathVariable("postId") int postId) {
        postService.deleteById(postId);
        return "redirect:/postList";
    }

    private void saveTitle(String newTitle) {
        Title byTitle = titleRepository.findByTitle(newTitle);
        int titleIndex = Long.valueOf(titleRepository.count()).intValue();

        if (byTitle == null) {
            Title title = new Title();
            title.setId(titleIndex + 1);
            title.setTitle(newTitle);
            titleRepository.save(title);
        }
    }

    private void isLikeOk(Post post, Member member, Model model) {
        List<Like> byPost = likeService.findByPost(post);
        boolean isLikeOk = false;

        for (Like like : byPost) {
            if (like.getMember().getId() == member.getId()) {
                model.addAttribute("likeId", like.getId());
                isLikeOk = true;
                break;
            } else {
                isLikeOk = false;
            }
        }

        if (isLikeOk) {
            model.addAttribute("isLikeOk", true);
        } else {
            model.addAttribute("isLikeOk", false);
        }
    }

    private void isMyPost(Model model, Member loginMember, Post findPostById) {
        if (loginMember != null) {
            if (loginMember.getId() == findPostById.getMember().getId()) {
                model.addAttribute("myPost", true);
            } else {
                model.addAttribute("myPost", false);
            }
        } else {
            model.addAttribute("myPost", false);
        }
    }

    private void isOpen(Post newPost, Model model, Post post) {
        if (newPost.getOpen().equals("OPEN")) {
            post.setOpen("OPEN");
            model.addAttribute("isOpen", "공개");
        } else {
            post.setOpen("PRIVATE");
            model.addAttribute("isOpen", "비공개");
        }
    }
}
