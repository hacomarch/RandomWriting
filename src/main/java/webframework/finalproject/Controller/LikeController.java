package webframework.finalproject.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;
import webframework.finalproject.Model.Like;
import webframework.finalproject.Model.Member;
import webframework.finalproject.Model.Post;
import webframework.finalproject.Service.LikeService;
import webframework.finalproject.Service.MemberService;
import webframework.finalproject.Service.PostService;
import webframework.finalproject.sessionManager.SessionName;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LikeController {
    private final LikeService likeService;
    private final PostService postService;

    @GetMapping("/like/{postId}")
    public String saveLike(Model model,
                           @PathVariable("postId") int postId,
                           @SessionAttribute(name = SessionName.LOGIN_MEMBER, required = false) Member loginMember) {
        Post postServiceById = postService.findById(postId);

        Like like = new Like();
        like.setMember(loginMember);
        like.setPost(postServiceById);

        int byId = likeService.save(like).getId();
        model.addAttribute("likeId", byId);

        return "redirect:/postList/" + postId;
    }

    @GetMapping("/likeCancel/{postId}/{likeId}")
    public String cancelLike(@PathVariable("likeId") int likeId,
                             @PathVariable("postId") int postId) {
        likeService.delete(likeId);
        return "redirect:/postList/" + postId;
    }
}
