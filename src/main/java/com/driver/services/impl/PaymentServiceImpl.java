package com.driver.services.impl;

import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.model.Spot;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.driver.model.Payment;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {
        Reservation reservation = reservationRepository2.findById(reservationId).get();
        Spot spot = reservation.getSpot();

        int price = spot.getPricePerHour();

        int hours = reservation.getNumberOfHours();

        int bill = price * hours;

        if(amountSent < bill){
            throw new RuntimeException("Insufficient Amount");
        }

        if(!mode.equals(PaymentMode.CARD) && !mode.equals(PaymentMode.UPI) && !mode.equals(PaymentMode.CASH)){
            throw new Exception("fail");
        }


        Payment payment = reservation.getPayment();
        payment.setPaymentCompleted(true);
        if(mode.equals("CASH")){
            payment.setPaymentMode(PaymentMode.CASH);
        }
        else if(mode.equals("CARD")){
            payment.setPaymentMode(PaymentMode.CARD);
        }
        else {
            payment.setPaymentMode(PaymentMode.UPI);
        }


        paymentRepository2.save(payment);

        spot.setOccupied(false);

        reservationRepository2.save(reservation);

        return payment;
    }
}
