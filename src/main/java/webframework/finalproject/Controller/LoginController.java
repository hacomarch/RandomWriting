package webframework.finalproject.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import webframework.finalproject.Model.Login;
import webframework.finalproject.Model.Member;
import webframework.finalproject.Repository.MemberRepository;
import webframework.finalproject.sessionManager.SessionName;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final MemberRepository memberRepository;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginForm", new Login());
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") Login login,
                        BindingResult bindingResult,
                        HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMember = memberRepository.findByUserid(login.getUserid())
                .stream()
                .filter(m -> m.getPassword().equals(login.getPassword()))
                .findAny()
                .orElse(null);

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionName.LOGIN_MEMBER, loginMember);    //세션에 로그인 회원 정보 보관

        log.info("login");

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null)
            session.invalidate();

        log.info("logout");
        return "redirect:/";
    }

    @GetMapping("/isLogin")
    public String loginCheck() {
        return "login/loginCheck";
    }
}
