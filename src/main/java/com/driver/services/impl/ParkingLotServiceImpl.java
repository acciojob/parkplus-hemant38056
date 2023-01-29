package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkingLot = new ParkingLot(name, address);
        parkingLotRepository1.save(parkingLot);
        return parkingLot;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
//        Spot spot = new Spot();
        SpotType spotType = SpotType.TWO_WHEELER;
        if(numberOfWheels <= 2){
            spotType = SpotType.TWO_WHEELER;
        }
        else if(numberOfWheels > 2 && numberOfWheels <= 4){
            spotType = SpotType.FOUR_WHEELER;
        }
        else if(numberOfWheels > 4){
            spotType = SpotType.OTHERS;
        }

        Spot spot = new Spot(spotType, pricePerHour, false);
        spotRepository1.save(spot);

//        if(numberOfWheels <= 2){
//            spot.setSpotType(SpotType.TWO_WHEELER);
//        }
//        else if (numberOfWheels > 2 && numberOfWheels <= 4) {
//            spot.setSpotType(SpotType.FOUR_WHEELER);
//        }
//        else {
//            spot.setSpotType(SpotType.OTHERS);
//        }
//
//        spot.setPricePerHour(pricePerHour);
//        spot.setOccupied(false);

        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        List<Spot> listOfSpots = parkingLot.getSpotList();
        listOfSpots.add(spot);
        parkingLotRepository1.save(parkingLot);

        return spot;

    }

    @Override
    public void deleteSpot(int spotId) {

        Spot spot = spotRepository1.findById(spotId).get();
        ParkingLot parkingLot = spot.getParkingLot();
        List<Spot> listOfSpots = parkingLot.getSpotList();
        listOfSpots.remove(spot);

        spotRepository1.deleteById(spotId);
        spotRepository1.save(spot);

        parkingLotRepository1.save(parkingLot);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        List<Spot> listOfSpots = parkingLot.getSpotList();
        Spot updated = null;
        for(Spot spot : listOfSpots){
            if(spot.getId() == spotId){
                spot.setPricePerHour(pricePerHour);
                updated = spot;
                spotRepository1.save(spot);
            }
        }

        parkingLotRepository1.save(parkingLot);
        return updated;
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
        parkingLotRepository1.deleteById(parkingLotId);
    }
}
