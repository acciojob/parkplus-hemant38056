package com.driver.model;

import javax.persistence.*;

@Entity
@Table
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int numberOfHours;

    public Reservation(){

    }
    public Reservation(int numberOfHours){
        this.numberOfHours = numberOfHours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfHours() {
        return numberOfHours;
    }

    public void setNumberOfHours(int numberOfHours) {
        this.numberOfHours = numberOfHours;
    }

    @ManyToOne
    @JoinColumn
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @ManyToOne
    @JoinColumn
    private Spot spot;

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }


    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL)
    private Payment payment;

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
