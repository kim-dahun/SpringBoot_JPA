package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member.Member;
import jpabook.jpashop.domain.Member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional // 좀 더 긴 패키지의 스프링의 트랜잭셔널을 사용하는 것을 권장함.
public class MemberService {


    private final MemberRepository memberRepository;
    @Autowired
    public MemberService(MemberRepository memberRepository){

        this.memberRepository = memberRepository;

    }

    /**
     * 회원가입
     */
    @Transactional // 좀 더 긴 패키지의 스프링의 트랜잭셔널을 사용하는 것을 권장함.
    public Long join(Member member){

        validateDuplicateMember(member);

        memberRepository.save(member);

        return member.getId();
    }

    private void validateDuplicateMember(Member member){

        List<Member> findMembers =  memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){

            throw new IllegalStateException("이미 존재하는 회원입니다");

        }

    }

    @Transactional(readOnly = true)
    // 회원 전체 조회
    public List<Member> findMembers(){

        return memberRepository.findAll();

    }

    @Transactional(readOnly = true)
    public Member findOne(Long memberId){

        return memberRepository.findOne(memberId);

    }

}
