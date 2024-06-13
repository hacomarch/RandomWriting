package webframework.finalproject.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import webframework.finalproject.Model.Member;
import webframework.finalproject.Repository.TitleRepository;
import webframework.finalproject.sessionManager.SessionName;

import java.util.Random;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final TitleRepository titleRepository;

    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionName.LOGIN_MEMBER, required = false) Member loginMember,
                       Model model) {
        model.addAttribute("loginMember", loginMember);
        String randomTitle = getRandomTitle();

        model.addAttribute("randomTitle", randomTitle);
        return "index";
    }

    private String getRandomTitle() {
        int titleIndex = Long.valueOf(titleRepository.count()).intValue();

        Random random = new Random();
        int randomInt = random.nextInt(titleIndex);

        return titleRepository.findAll().get(randomInt).getTitle();
    }
}
