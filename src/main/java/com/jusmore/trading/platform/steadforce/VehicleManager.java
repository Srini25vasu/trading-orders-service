package com.jusmore.trading.platform.steadforce;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class VehicleManager {

  private static List<Vehicle> vehicles = new ArrayList<>();

  public static void main(String[] args) {
    Vehicle vehicle1 = new Car("ahah25", 2025);
    Vehicle vehicle2 = new Car("ahah25", 2023);
    Vehicle vehicle3 = new Car("ahah25", 2025);

    vehicles.add(vehicle1);
    vehicles.add(vehicle2);
    vehicles.add(vehicle3);

    vehicles.stream().forEach (out::println);
  }
}
