package webframework.finalproject.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String userid;
    private String password;
    private String nickname;
    private String tel;
    private String address;

    @Column
    private int emotionCnt;

    @CreatedDate
    private LocalDateTime joindate;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<Post> post = new HashSet<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<Like> likes = new HashSet<>();

}
