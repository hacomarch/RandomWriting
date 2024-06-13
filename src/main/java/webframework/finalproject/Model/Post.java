package webframework.finalproject.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String content;
    private String open;

    @Column
    private LocalDateTime created;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "memberid")
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<Like> likes = new HashSet<>();

    @Transactional
    public void setMember(Member member) {
        this.member = member;
        member.getPost().add(this);
    }

    @Transactional
    public Member getMember() {
        return member;
    }


}
