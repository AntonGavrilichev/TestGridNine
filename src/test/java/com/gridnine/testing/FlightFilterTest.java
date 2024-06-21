package com.gridnine.testing;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlightFilterTest {

    private static FilterInterface flightFilter;
    private static final LocalDateTime TIME_IS_NOW = LocalDateTime.now().plusDays(3);
    private static List<Flight> allFlight;
    private static List<Flight> flightsRemoveWithSegmentsArrivalsBeforeNow;
    private static List<Flight> flightRemoveDepartureBeforeArrival;
    private static List<Flight> flightWhereRemoveTransferMoreTwoHours;

    @BeforeAll
    public static void allFlights(){

        flightFilter = new FlightFilter();

        //Разные варианты рейсов:
        Flight standardFlight = new Flight(List.of(
                new Segment(TIME_IS_NOW, TIME_IS_NOW.plusHours(2))
        ));

        Flight standardFlightWithTwoSegments = new Flight(List.of(
                new Segment(TIME_IS_NOW.minusDays(4).minusHours(1), TIME_IS_NOW.minusDays(4)),
                new Segment(TIME_IS_NOW.minusDays(3), TIME_IS_NOW.minusDays(2).minusHours(8))
        ));

        Flight completedFlight = new Flight(List.of(
                new Segment(TIME_IS_NOW.minusDays(4).minusHours(2), TIME_IS_NOW.minusDays(4)),
                new Segment(TIME_IS_NOW.minusDays(3).minusHours(5), TIME_IS_NOW.minusDays(3))
        ));

        Flight incorrectFlight = new Flight(List.of(
                new Segment(TIME_IS_NOW, TIME_IS_NOW.minusHours(2)),
                new Segment(TIME_IS_NOW.minusHours(1), TIME_IS_NOW.minusHours(3))
        ));

        Flight moreThanTwoHours = new Flight(List.of(
                new Segment(TIME_IS_NOW, TIME_IS_NOW.plusHours(2)),
                new Segment(TIME_IS_NOW.plusHours(5), TIME_IS_NOW.plusHours(8))
        ));

        //Списки всех рейсов
        allFlight = Arrays.asList(standardFlight, standardFlightWithTwoSegments,completedFlight, incorrectFlight, moreThanTwoHours);
        flightsRemoveWithSegmentsArrivalsBeforeNow = Arrays.asList(standardFlight, incorrectFlight, moreThanTwoHours);
        flightRemoveDepartureBeforeArrival = Arrays.asList( standardFlight,standardFlightWithTwoSegments, completedFlight, moreThanTwoHours);
        flightWhereRemoveTransferMoreTwoHours = Arrays.asList(incorrectFlight);
    }


    //Тест на завершенные рейсы
    @Test
    public void shouldBeRemovedDepartureBeforeCurrentTime () {
        List<Flight> expectedResult = flightFilter.RemoveDepartureBeforeCurrentTime(allFlight);
        assertEquals(expectedResult, flightsRemoveWithSegmentsArrivalsBeforeNow);
    }

    //Тест на отбор корректных рейсов
    @Test
    public void shouldBeRemoveArrivalsBeforeDeparture() {
        List<Flight> expectedResult = flightFilter.RemoveArrivalsBeforeDeparture(allFlight);
        assertEquals(expectedResult, flightRemoveDepartureBeforeArrival);
    }

    //Тест на отбор рейсов, у которых время пересадки меньше двух часов
    @Test
    public void shouldBeRemoveTotalTimeEarthTwoHoursMore(){
        List<Flight> expectedResult = flightFilter.RemoveTotalTimeEarthTwoHoursMore(allFlight);
        assertEquals(expectedResult, flightWhereRemoveTransferMoreTwoHours);
    }
}
