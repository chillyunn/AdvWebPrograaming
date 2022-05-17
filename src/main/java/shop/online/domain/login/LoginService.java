package shop.online.domain.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.online.domain.Member;
import shop.online.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;
    public Member login(Long loginId, String password){
        Member findMember = memberRepository.findOne(loginId);
        if(findMember !=null && findMember.getName().equals(password)){
            return findMember;
        }else{
            return null;
        }
    }
}
