package shop.online.web;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.online.domain.Address;
import shop.online.domain.Member;
import shop.online.dto.MemberForm;
import shop.online.service.MemberService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("member",new MemberForm());
        return "members/join-form";
    }
    @PostMapping("/members/new")
    public String addMember(@ModelAttribute MemberForm form){
        Address address = new Address(form.getCity(),
                form.getStreet(),
                form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);
        memberService.join(member);
        return "redirect:/";
    }
    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        List<MemberForm> memberList = members.stream()
                .map(m->new MemberForm(m))
                .collect(Collectors.toList());
        model.addAttribute("members",memberList);
        return "members/member-list";
    }
}
