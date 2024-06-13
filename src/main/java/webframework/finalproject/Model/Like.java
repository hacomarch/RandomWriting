package webframework.finalproject.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "postlike")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Transactional
    public void setMember(Member member) {
        this.member = member;
        member.getLikes().add(this);
    }

    @Transactional
    public Member getMember() {
        return member;
    }

    @Transactional
    public void setPost(Post post) {
        this.post = post;
        post.getLikes().add(this);
    }

    @Transactional
    public Post getPost(Post post) {
        return post;
    }
}
