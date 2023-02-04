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
        String payMode = mode.toUpperCase();
        if(!payMode.equals("CARD") && !payMode.equals("UPI") && !payMode.equals("CASH")){
            throw new Exception("Payment mode not detected");
        }

//        if(!mode.equals(PaymentMode.CARD) && !mode.equals(PaymentMode.UPI) && !mode.equals(PaymentMode.CASH)){
//            throw new Exception("fail");
//        }

        Payment payment = reservation.getPayment();
        payment.setPaymentCompleted(true);
        if(payMode.equals("CASH")){
            payment.setPaymentMode(PaymentMode.CASH);
        }
        else if(payMode.equals("CARD")){
            payment.setPaymentMode(PaymentMode.CARD);
        }
        else if(payMode.equals("UPI")){
            payment.setPaymentMode(PaymentMode.UPI);
        }

        spot.setOccupied(false);

        reservationRepository2.save(reservation);


//        Payment payment = new Payment();
//        payment.setPaymentCompleted(true);
//        if(payMode.equals("CASH")){
//            payment.setPaymentMode(PaymentMode.CASH);
//        }
//        else if(payMode.equals("CARD")){
//            payment.setPaymentMode(PaymentMode.CARD);
//        }
//        else if(payMode.equals("UPI")){
//            payment.setPaymentMode(PaymentMode.UPI);
//        }




//        paymentRepository2.save(payment);

//        spot.setOccupied(false);
//
//        reservation.setPayment(payment);
//        reservationRepository2.save(reservation);



        return payment;
    }
}
