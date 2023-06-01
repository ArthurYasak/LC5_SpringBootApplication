package ru.yasak.springcourse.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
    private AuthorizationData authorizationData;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;

    @Column(name = "user_balance")
    private Double userBalance;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH}, // NO CascadeType.PERSIST
            mappedBy = "user", fetch = FetchType.EAGER)  // , orphanRemoval = true
    @JoinColumn(name = "user_data_id")
    private UserData userData;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, // NO CascadeType.REMOVE,PERSIST
            orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_owner_id")
    private List<Lot> userLots;

    @OneToMany( cascade = {CascadeType.MERGE,  CascadeType.REFRESH, CascadeType.PERSIST}, // NO CascadeType.REMOVE,PERSIST
            orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "last_customer_id")
    private List<Lot> lastCustomerIn;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_owner_id")
    private List<Bet> userBets;

    public User() {
        this(UserType.GUEST);
    }

    public User(UserType userType) {
        this.userType = userType;
        userLots = new ArrayList<>();
        lastCustomerIn = new ArrayList<>();
        userBets = new ArrayList<>();
    }

    public void addUserLot(Lot lot) {
        userLots.add(lot);
        lot.setUserOwner(this);
    }

    public void removeUserLot(Lot lot) {
        userLots.remove(lot);
    }

    public void stayLastCustomerIn(Lot lot) {
        lastCustomerIn.add(lot);
        lot.setLastCustomer(this);
    }

    public void stopLastCustomerIn(Lot lot) {
        lastCustomerIn.remove(lot);
    }

    public void addUserBet(Bet bet) {
        userBets.add(bet);
        bet.setUserOwner(this);
    }

    public void removeUserBet(Bet bet) {
        userBets.remove(bet);
    }

    public Integer getUserId() {
        return userId;
    }

    public AuthorizationData getAuthorizationData() {
        return authorizationData;
    }

    public void setAuthorizationData(AuthorizationData authorizationData) {
        this.authorizationData = authorizationData;
        authorizationData.setUser(this);
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Double getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(Double userBalance) {
        this.userBalance = userBalance;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
        userData.setUser(this);
    }

    public List<Lot> getUserLots() {
        return userLots;
    }

    public void setUserLots(List<Lot> userLots) {
        this.userLots = userLots;
    }

    public List<Lot> getLastCustomerIn() {
        return lastCustomerIn;
    }

    public void setLastCustomerIn(List<Lot> lastCustomerIn) {
        this.lastCustomerIn = lastCustomerIn;
    }

    public List<Bet> getUserBets() {
        return userBets;
    }

    public void setUserBets(List<Bet> userBets) {
        this.userBets = userBets;
    }

    @Override
    public String toString() {
        return "User{" + '\n' +
                "userId=" + userId + '\n' +
                ", authorization data=" + authorizationData + '\n' +
                ", userType=" + userType + '\n' +
                ", userBalance=" + userBalance + '\n' +
                ", userName=" + (userData == null ? "null" : userData.getName()) + '\n' +
                ", userSurname=" + (userData == null ? "null" : userData.getSurname()) + '\n' +
                ", userLots=" + userLots + '\n' +
                ", lastCustomerIn=" + lastCustomerIn + '\n' +
                ", userBets=" + userBets + '\n' +
                '}' + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getUserId(), user.getUserId()) && getUserType() == user.getUserType() && Objects.equals(getUserBalance(), user.getUserBalance()) && Objects.equals(getUserLots(), user.getUserLots()) && Objects.equals(getLastCustomerIn(), user.getLastCustomerIn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getUserType(), getUserBalance());
    }
}
