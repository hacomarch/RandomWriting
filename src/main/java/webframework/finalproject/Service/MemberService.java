package webframework.finalproject.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webframework.finalproject.Model.Member;
import webframework.finalproject.Model.Post;
import webframework.finalproject.Repository.MemberRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    @Autowired
    MemberRepository memberRepository;

    @Transactional
    public void save(Member member) {
        memberRepository.save(member);
    }

    @Transactional
    public void update(int id, Member member) {
        Member memberById = memberRepository.findById(id);
        memberById.setPassword(member.getPassword());
        memberById.setName(member.getName());
        memberById.setAddress(member.getAddress());
        memberById.setTel(member.getTel());
        memberById.setNickname(member.getNickname());
    }

    @Transactional
    @Modifying
    public void delete(Member member) {
        memberRepository.delete(member);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member findById(int id) {
        return memberRepository.findById(id);
    }

    public List<Member> findByNickname(String nickname) {
        return memberRepository.findByNickname(nickname);
    }

}
