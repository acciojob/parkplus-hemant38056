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

        Reservation reservation = new Reservation();

        if(!userRepository3.findById(userId).isPresent()){
            throw new Exception("Cannot make reservation");
        }

        if(!parkingLotRepository3.findById(parkingLotId).isPresent()){
            throw new Exception("Cannot make reservation");
        }

        User user1 = userRepository3.findById(userId).get();
        ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();

        List<Spot> spotList = parkingLot.getSpotList();

        int allocatedWheels = 0;

        Spot reservedSpot = null;

        for (Spot spot : spotList){
            int spotWheels = 0;
            if(spot.getOccupied() == false){
                if(spot.getSpotType().equals(SpotType.TWO_WHEELER)){
                    spotWheels = 2;
                }
                else if(spot.getSpotType().equals(SpotType.FOUR_WHEELER)){
                    spotWheels = 4;
                }
                else if(spot.getSpotType().equals(SpotType.OTHERS)){
                    spotWheels = 9999;
                }

                if((reservedSpot == null && spotWheels >= numberOfWheels) || (spotWheels < allocatedWheels && spotWheels >= numberOfWheels)){
                    reservedSpot = spot;
                    allocatedWheels = spotWheels;
                }
            }
        }

        if(reservedSpot == null){
            throw new Exception("Cannot make reservation");
        }

        reservedSpot.setOccupied(true);

        reservation.setNumberOfHours(timeInHours);

        reservation.setSpot(reservedSpot);
        reservation.setUser(user1);

        List<Reservation> spotReservations = reservedSpot.getReservationList();
        spotReservations.add(reservation);
        reservedSpot.setReservationList(spotReservations);
//        spotRepository3.save(reservedSpot);


        List<Reservation> userReservations = user1.getReservationList();
        userReservations.add(reservation);
        user1.setReservationList(userReservations);
//        userRepository3.save(user1);

//        Payment payment = new Payment();
//        payment.setPaymentCompleted(false);
//        payment.setReservation(reservation);
//        reservation.setPayment(payment);
        reservationRepository3.save(reservation);

        return reservation;

//        User user = userRepository3.findById(userId).get();
//        if(user == null){
//            throw new Exception("Cannot make reservation");
//        }
//
//        ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();
//        if(parkingLot == null){
//            throw new Exception("Cannot make reservation");
//        }
//
//        List<Spot> spotList = parkingLot.getSpotList();
//        if(spotList.size() == 0){
//            return null;
//        }
//
//        Spot allocatedSpot = null;
//
//        int allocatedWheels = 0;
//
//        for(Spot spot : spotList){
//            int spotWheels = 2;
//            if(spot.getSpotType().equals(SpotType.TWO_WHEELER)){
//                spotWheels = 2;
//            }
//            else if(spot.getSpotType().equals(SpotType.FOUR_WHEELER)){
//                spotWheels = 4;
//            }
//            else if(spot.getSpotType().equals(SpotType.OTHERS)){
//                spotWheels = 9999;
//            }
//
//            if(spot.getOccupied() == false){
//                if((spot == null && spotWheels >= numberOfWheels) || (spotWheels >= numberOfWheels && spotWheels <= allocatedWheels)){
//                    allocatedSpot = spot;
//                    allocatedWheels = spotWheels;
//                }
//            }
//        }
//
//        if(allocatedSpot == null){
//            throw new Exception("Cannot make reservation");
//        }
//
//        allocatedSpot.setOccupied(true);
//
//        Reservation reservation = new Reservation();
//        reservation.setNumberOfHours(timeInHours);
//        reservation.setSpot(allocatedSpot);
//        List<Reservation> spotReservations = allocatedSpot.getReservationList();
//        spotReservations.add(reservation);
//        allocatedSpot.setReservationList(spotReservations);
//
//        spotRepository3.save(allocatedSpot);
//
//
//        reservation.setUser(user);
//        List<Reservation> userReservations = user.getReservationList();
//        userReservations.add(reservation);
//        user.setReservationList(userReservations);
//
//        userRepository3.save(user);
//
//
//        Payment payment = new Payment();
//        payment.setPaymentCompleted(false);
//
//        payment.setReservation(reservation);
//        reservation.setPayment(payment);
//
//        reservationRepository3.save(reservation);
//
//        return reservation;




//        User user = userRepository3.findById(userId).get();
//        if(user == null){
//            throw new Exception("Cannot make reservation");
//            return null;
//        }
//
//        ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();
//        if(parkingLot == null){
//            throw new Exception("Cannot make reservation");
//            return null;
//        }
//
//        Spot actualSpot = null;
//        List<Spot> spotList = parkingLot.getSpotList();
//        int size = 5;
//        if(numberOfWheels <= 2){
//            size = 2;
//        }
//        else if(numberOfWheels > 2 && numberOfWheels <= 4){
//            size = 4;
//        }
//
//        for(Spot spot : spotList){
//            int spotsize = 2;
//            if(spot.getSpotType().equals(SpotType.TWO_WHEELER)){
//                if(spotsize >= size && spot.getOccupied() == false){
//                    actualSpot = spot;;
//                }
//            }
//        }
//
//        if(actualSpot == null){
//            for (Spot spot : spotList){
//                int spotsize = 4;
//                if(spot.getSpotType().equals(SpotType.FOUR_WHEELER)){
//                    if(spotsize >= size && spot.getOccupied() == false){
//                        actualSpot = spot;
//                    }
//                }
//            }
//        }
//
//        if(actualSpot == null){
//            for (Spot spot : spotList){
//                int spotsize = 5;
//                if(spot.getSpotType().equals(SpotType.OTHERS)){
//                    if(spotsize >= size && spot.getOccupied() == false){
//                        actualSpot = spot;
//                    }
//                }
//            }
//        }
//        if(actualSpot == null){
//            throw  new Exception("Cannot make reservation");
//            return null;
//        }
//
//        actualSpot.setOccupied(true);
//        spotRepository3.save(actualSpot);
//
//
//        Reservation reservation = new Reservation(timeInHours);
//
//        List<Reservation> userReservationList = user.getReservationList();
//        userReservationList.add(reservation);
//
//        userRepository3.save(user);
//
//        List<Reservation> actualSpotReservationList = actualSpot.getReservationList();
//        actualSpotReservationList.add(reservation);
//
//        spotRepository3.save(actualSpot);
//
//        Payment payment = new Payment();
//        payment.setPaymentCompleted(false);
//        reservation.setPayment(payment);
//        reservationRepository3.save(reservation);
//
//
//
//        return reservation;
    }
}
