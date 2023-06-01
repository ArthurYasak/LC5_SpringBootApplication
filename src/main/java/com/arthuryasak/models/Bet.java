package ru.yasak.springcourse.models;

import jakarta.persistence.*;

@Entity
@Table(name = "bets")
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bet_id")
    private Integer betId;

    @ManyToOne(optional = false, cascade = CascadeType.ALL) // fetch type
    @JoinColumn(name = "user_owner_id")
    private User userOwner;

    @Column(name = "bet_price")
    private Double betPrice;

    public Bet() {
        this.betPrice = 0.0;
    }

    public Bet(Double betPrice) {
        this.betPrice = betPrice;
    }

    public Integer getBetId() {
        return betId;
    }

    public User getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(User userOwner) {
        if (this.userOwner != null) {
            this.userOwner.removeUserBet(this);
        }
        this.userOwner = userOwner;
        // userOwner.addUserBet(this);
    }

    public Double getBetPrice() {
        return betPrice;
    }

    public void setBetPrice(Double betPrice) {
        this.betPrice = betPrice;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "betId=" + betId +
                ", userOwnerId=" + userOwner.getUserId() +
                ", betPrice=" + betPrice +
                '}';
    }
}
