package webframework.finalproject.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import webframework.finalproject.Model.Like;
import webframework.finalproject.Model.Login;
import webframework.finalproject.Model.Member;
import webframework.finalproject.Model.Post;
import webframework.finalproject.Service.MemberService;
import webframework.finalproject.Service.PostService;
import webframework.finalproject.sessionManager.SessionName;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PostService postService;

    @GetMapping("/signup")
    public String signUp(Model model) {
        model.addAttribute("newMember", new Member());
        return "member/signupForm";
    }

    @PostMapping("/signup")
    public String signUp(@Valid @ModelAttribute("newMember") Member newMember,
                         Model model,
                         BindingResult result) {
        Member findByNickname = memberService.findByNickname(newMember.getNickname()).stream().findAny().orElse(null);

        if (findByNickname != null) {
            result.reject("duplicateNickname", "닉네임이 중복됩니다.");
            return "member/signupForm";
        }

        Member member = new Member();
        member.setId(newMember.getId());
        member.setName(newMember.getName());
        member.setUserid(newMember.getUserid());
        member.setPassword(newMember.getPassword());
        member.setNickname(newMember.getNickname());
        member.setAddress(newMember.getAddress());
        member.setTel(newMember.getTel());
        member.setJoindate(LocalDateTime.now());
        member.setEmotionCnt(0);

        memberService.save(member);
        model.addAttribute("loginForm", new Login());

        return "login/loginForm";
    }

    @GetMapping("/quit/{id}")
    public String quit(@PathVariable("id") int id,
                       HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.removeAttribute(SessionName.LOGIN_MEMBER);

        Member findMember = memberService.findById(id);
        memberService.delete(findMember);

        return "redirect:/";
    }

    @GetMapping("/info/{id}")
    public String showMyInfo(@PathVariable("id") int id, Model model) {
        Member findMember = memberService.findById(id);
        Set<Post> postList = findMember.getPost();
        Set<Like> likes = findMember.getLikes();

        List<Post> likePostList = new ArrayList<>();
        for (Like like : likes) {
            Post byId = postService.findById(like.getPost().getId());
            likePostList.add(byId);
        }

        model.addAttribute("member", findMember);
        model.addAttribute("postList", postList);
        model.addAttribute("likeList", likePostList);
        return "member/myInfo";
    }

    @PostMapping("/update/member")
    public String updateMember(@ModelAttribute("member") Member updateMember, Model model) {
        memberService.update(updateMember.getId(), updateMember);

        model.addAttribute("member", updateMember);
        return "redirect:/info/" + updateMember.getId();
    }
}
