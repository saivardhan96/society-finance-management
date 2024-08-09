package entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="finance")
public class FinanceDetails {
    @Id
    private String uname;
    @Column
    private String request = "0";
    @Column
    private int due_amount;
    private int paid_amount;
    private int fine;
    private int due_times;
    private int req_amount;
    private String req_status = "0";
    private String req_items = "0";
    private String last_paid = "0";

    @OneToOne
    @MapsId
    @JoinColumn(name = "uname")
    private Logins logins;

}
