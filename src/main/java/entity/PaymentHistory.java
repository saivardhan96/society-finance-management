package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "history")
public class PaymentHistory {

    @Id
    private String uname;

    private String paid_date;
    private String Services;
    private int amount;

}
