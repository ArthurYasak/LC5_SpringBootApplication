package ru.yasak.springcourse.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "lots")
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lot_id")
    private Integer lotId;

    @ManyToOne(optional = false, cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER) // NO CascadeType.PERSIST,REMOVE
    @JoinColumn(name = "user_owner_id")
    private User userOwner;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "lot", fetch = FetchType.EAGER, orphanRemoval = true) // was LAZY
    // @JoinColumn(name = "property_id") // несет ответственность за отношения
    private LotProperty property;

    @Column(name = "sold_until")
    private LocalDateTime soldUntil;

    @Column(name = "min_price")
    private Double minPrice;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.EAGER) //NOT CascadeType.PERSIST, REMOVE
    @JoinColumn(name = "last_customer_id")
    private User lastCustomer;

    @Column(name = "current_price")
    private Double currentPrice;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "lot", fetch = FetchType.EAGER, orphanRemoval = true)
    private Commission commission;

    public Lot() {
        this.property = new LotProperty(this);
    }

    public Integer getLotId() {
        return lotId;
    }

    public User getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(User userOwner) {
        this.userOwner = userOwner;
        // userOwner.addUserLot(this);
    }

    public LotProperty getProperty() {
        return property;
    }
    public void setProperty(LotProperty property) {
        this.property = property;
        property.setLot(this);
    }

    public LocalDateTime getSoldUntil() {
        return soldUntil;
    }

    public void setSoldUntil(LocalDateTime soldUntil) {
        this.soldUntil = soldUntil;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public User getLastCustomer() {
        return lastCustomer;
    }

    public void setLastCustomer(User lastCustomer) {
        if (this.lastCustomer != null) {
            this.lastCustomer.stopLastCustomerIn(this);
        }
        this.lastCustomer = lastCustomer;
        // lastCustomer.stayLastCustomerIn(this);
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Commission getCommission() {
        return commission;
    }

    public void setCommission(Commission commission) {
        this.commission = commission;
        commission.setLot(this);
    }

    @Override
    public String toString() {
        return "Lot{" +
                "lotId=" + lotId +
                ", userOwnerId=" + userOwner.getUserId() +
                ", propertyId=" + property.getPropertyId() +
                ", property description=" + (property == null ? "not" : property.getDescription()) +
                ", property weight=" + (property == null ? "not" : property.getWeight()) +
                // ", soldUntil=" + soldUntil +
                ", minPrice=" + minPrice +
                ", lastCustomerId=" + (lastCustomer == null ? "not" : lastCustomer.getUserId()) +
                ", currentPrice=" + currentPrice +
                ", commission percent=" + (commission == null ? "not" : commission.getCommissionPercent()) +
        '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lot lot = (Lot) o;
        return Objects.equals(getLotId(), lot.getLotId()) && Objects.equals(getUserOwner(), lot.getUserOwner()) && Objects.equals(getProperty(), lot.getProperty()) && Objects.equals(getSoldUntil(), lot.getSoldUntil()) && Objects.equals(getMinPrice(), lot.getMinPrice()) && Objects.equals(getLastCustomer(), lot.getLastCustomer()) && Objects.equals(getCurrentPrice(), lot.getCurrentPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLotId(), getSoldUntil(), getMinPrice());
    }
}