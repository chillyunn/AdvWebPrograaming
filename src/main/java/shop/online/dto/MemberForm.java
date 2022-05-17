package shop.online.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.online.domain.Member;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberForm {
    private String name;
    private String city;
    private String street;
    private String zipcode;

    public MemberForm(Member member){
        this.name = member.getName();
        this.city = member.getAddress().getCity();
        this.street = member.getAddress().getStreet();
        this.zipcode = member.getAddress().getZipcode();
    }
}
