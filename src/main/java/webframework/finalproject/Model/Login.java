package webframework.finalproject.Model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class Login {

    @NotEmpty String userid;
    @NotEmpty String password;
}
