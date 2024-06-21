package com.gridnine.testing;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FlightFilter implements FilterInterface {

    @Override
    public List<Flight> RemoveDepartureBeforeCurrentTime(List<Flight> flights) {
        return flights.stream().filter
                (f -> f.getSegments().stream().anyMatch
                        (s -> s.getDepartureDate().isAfter(LocalDateTime.now()))).toList();
    }

    @Override
    public List<Flight> RemoveArrivalsBeforeDeparture(List<Flight> flights) {
        return flights.stream().filter
                (f -> f.getSegments().stream().anyMatch
                        (s -> s.getArrivalDate().isAfter(s.getDepartureDate()))).toList();
    }

    @Override
    public List<Flight> RemoveTotalTimeEarthTwoHoursMore(List<Flight> flights) {

        List<Flight> updatedFlightList = new ArrayList<>();

        for (int i = 0; i < flights.size(); i++) {
            List<Segment> segmentList = flights.get(i).getSegments();
            for (int j = 0; j < segmentList.size()-1; j++) {
                if (ChronoUnit.HOURS.between
                        (segmentList.get(j).getArrivalDate(),segmentList.get(j+1).getDepartureDate()) < 2){
                            updatedFlightList.add(flights.get(i));
                }
            }
        }
        return updatedFlightList;
    }
}
