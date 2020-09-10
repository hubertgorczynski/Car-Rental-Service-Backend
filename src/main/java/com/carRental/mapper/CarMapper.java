package com.carRental.mapper;

import com.carRental.domain.Car;
import com.carRental.domain.dto.CarDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarMapper {

    public Car mapToCar(final CarDto carDto) {
        return new Car(
                carDto.getVin(),
                carDto.getBrand(),
                carDto.getModel(),
                carDto.getProductionYear(),
                carDto.getFuelType(),
                carDto.getEngineCapacity(),
                carDto.getBodyClass(),
                carDto.getMileage(),
                carDto.getCostPerDay());
    }

    public CarDto mapToCarDto(final Car car) {
        return new CarDto(
                car.getId(),
                car.getVin(),
                car.getBrand(),
                car.getModel(),
                car.getProductionYear(),
                car.getFuelType(),
                car.getEngineCapacity(),
                car.getBodyClass(),
                car.getMileage(),
                car.getCostPerDay(),
                car.getStatus());
    }

    public List<CarDto> mapToCarDtoList(final List<Car> carList) {
        return carList.stream()
                .map(car -> new CarDto(
                        car.getId(),
                        car.getVin(),
                        car.getBrand(),
                        car.getModel(),
                        car.getProductionYear(),
                        car.getFuelType(),
                        car.getEngineCapacity(),
                        car.getBodyClass(),
                        car.getMileage(),
                        car.getCostPerDay(),
                        car.getStatus()))
                .collect(Collectors.toList());
    }
}
