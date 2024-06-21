package com.gridnine.testing;

import java.sql.SQLOutput;

public class Main {
    public static void main(String[] args) {
        System.out.println("Список всех рейсов:");
        FlightBuilder.createFlights().forEach(System.out::println);
        System.out.println("\n");
        System.out.println("1.Исключены рейсы с вылетом до текущего времени:");
        new FlightFilter().RemoveDepartureBeforeCurrentTime(FlightBuilder.createFlights()).forEach(System.out::println);
        System.out.println("\n");
        System.out.println("2.Исключены рейсы, в которых есть сегменты с датой прилёта раньше даты вылета:");
        new FlightFilter().RemoveArrivalsBeforeDeparture(FlightBuilder.createFlights()).forEach(System.out::println);
        System.out.println("\n");
        System.out.println("3.Исключены рейсы без пересадки и рейсы, в которых проведенное время на земле превышает два часа: ");
        new FlightFilter().RemoveTotalTimeEarthTwoHoursMore(FlightBuilder.createFlights()).forEach(System.out::println);
    }
}