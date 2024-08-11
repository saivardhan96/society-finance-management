package entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@Table(name = "family")
public class FamilyDetails {

    @Id
    private String uname;

    @Column(name = "phone_number", columnDefinition = "char(10)")
    private String phoneNumber;
    @Column(name = "email_id")
    private String email;
    @Column(name = "flat")
    private String flat;
    @Column(name = "name")
    private String name;

    @OneToOne
    @MapsId
    @JoinColumn(name = "uname")
    private Logins logins;



}
