package com.jusmore.trading.platform.steadforce;

import java.time.Year;

public abstract class Vehicle {
  private final String licensePlate;
  private final int year;

  protected Vehicle(String licensePlate, int year) {
    this.licensePlate = licensePlate;
    this.year = year;
  }

  public String getLicensePlate() {
    return licensePlate;
  }

  public int getYear() {
    return year;
  }

  public int getAge() {
    return Year.now().getValue() - year;
  }

  public abstract double calculateMaintenanceCost();

  @Override
  public String toString() {
    return "Vehicle{" +
          "licensePlate='" + licensePlate + '\'' +
          ", year=" + year +
          '}';
  }
}
