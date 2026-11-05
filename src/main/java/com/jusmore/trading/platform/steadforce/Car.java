package com.jusmore.trading.platform.steadforce;

public class Car extends Vehicle {

  private static final double BASIS_RATE = 1000.0;

  public Car(String licensePlate, int year) {
    super(licensePlate, year);
  }

  @Override
  public double calculateMaintenanceCost() {
    return getAge() * BASIS_RATE;
  }
}
