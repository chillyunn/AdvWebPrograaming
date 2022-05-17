package shop.online.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NamedEntityGraphs(
        {
                @NamedEntityGraph(name = "member_comment", attributeNodes = {
                        @NamedAttributeNode("comments")
                }),
                @NamedEntityGraph(name = "member_order", attributeNodes = {
                        @NamedAttributeNode("orders")
                })
        }
)


@Getter
@Setter
@Entity
public class Member {
    @Id @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<Order>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
}
