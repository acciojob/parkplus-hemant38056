package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        User user = userRepository3.findById(userId).get();
        if(user == null){
//            throw new Exception("Cannot make reservation");
            return null;
        }

        ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();
        if(parkingLot == null){
//            throw new Exception("Cannot make reservation");
            return null;
        }

        Spot actualSpot = null;
        List<Spot> spotList = parkingLot.getSpotList();
        int size = 5;
        if(numberOfWheels <= 2){
            size = 2;
        }
        else if(numberOfWheels > 2 && numberOfWheels <= 4){
            size = 4;
        }

        for(Spot spot : spotList){
            int spotsize = 2;
            if(spot.getSpotType().equals(SpotType.TWO_WHEELER)){
                if(spotsize >= size && spot.getOccupied() == false){
                    actualSpot = spot;;
                }
            }
        }

        if(actualSpot == null){
            for (Spot spot : spotList){
                int spotsize = 4;
                if(spot.getSpotType().equals(SpotType.FOUR_WHEELER)){
                    if(spotsize >= size && spot.getOccupied() == false){
                        actualSpot = spot;
                    }
                }
            }
        }

        if(actualSpot == null){
            for (Spot spot : spotList){
                int spotsize = 5;
                if(spot.getSpotType().equals(SpotType.OTHERS)){
                    if(spotsize >= size && spot.getOccupied() == false){
                        actualSpot = spot;
                    }
                }
            }
        }
        if(actualSpot == null){
//            throw  new Exception("Cannot make reservation");
            return null;
        }

        actualSpot.setOccupied(true);
        spotRepository3.save(actualSpot);


        Reservation reservation = new Reservation(timeInHours);

        List<Reservation> userReservationList = user.getReservationList();
        userReservationList.add(reservation);

        userRepository3.save(user);

        List<Reservation> actualSpotReservationList = actualSpot.getReservationList();
        actualSpotReservationList.add(reservation);

        spotRepository3.save(actualSpot);

        Payment payment = new Payment();
        payment.setPaymentCompleted(false);
        reservation.setPayment(payment);
        reservationRepository3.save(reservation);



        return reservation;
    }
}
