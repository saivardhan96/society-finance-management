package entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "payments")
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sno;
    private String services;
    private int amount;
    private int users_paid;

    // used lombok library for getters and setters
}
