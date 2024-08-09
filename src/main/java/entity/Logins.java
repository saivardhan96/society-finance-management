package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "logins")
public class Logins {
    @Id
    private String uname;

    @OneToOne(mappedBy = "logins" , cascade = CascadeType.ALL, orphanRemoval = true)
    private FamilyDetails familyDetails;

    @OneToOne(mappedBy = "logins", cascade = CascadeType.ALL, orphanRemoval = true)
    private FinanceDetails financeDetails;

    @Column
    private String password;

}
