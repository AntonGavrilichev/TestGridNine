package com.gridnine.testing;

import java.util.List;

public interface FilterInterface {
    List<Flight> RemoveDepartureBeforeCurrentTime(List<Flight> flights);
    List<Flight> RemoveArrivalsBeforeDeparture(List<Flight> flights);
    List<Flight> RemoveTotalTimeEarthTwoHoursMore(List<Flight> flights);
}
