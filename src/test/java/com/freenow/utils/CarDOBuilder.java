package com.freenow.utils;

import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.CarType;
import com.freenow.domainvalue.EngineType;
import com.freenow.domainvalue.Transmission;

public class CarDOBuilder
{

    public static Builder builder()
    {
        return new Builder();
    }

    public static final class Builder
    {
        private String model;
        private String color;
        private String licensePlate;
        private Integer seatCount;
        private Boolean convertible;
        private Float rating;
        private EngineType engineType;
        private Transmission transmission;
        private CarType carType;
        private Long manufacturerId;
        private DriverDO driverDO;


        private Builder()
        {}


        public Builder withModel(String model)
        {
            this.model = model;
            return this;
        }


        public Builder withColor(String color)
        {
            this.color = color;
            return this;
        }


        public Builder withLicensePlate(String licensePlate)
        {
            this.licensePlate = licensePlate;
            return this;
        }


        public Builder withSeatCount(Integer seatCount)
        {
            this.seatCount = seatCount;
            return this;
        }


        public Builder withConvertible(Boolean convertible)
        {
            this.convertible = convertible;
            return this;
        }


        public Builder withRating(Float rating)
        {
            this.rating = rating;
            return this;
        }


        public Builder withEngineType(EngineType engineType)
        {
            this.engineType = engineType;
            return this;
        }


        public Builder withTransmission(Transmission transmission)
        {
            this.transmission = transmission;
            return this;
        }


        public Builder withCarType(CarType carType)
        {
            this.carType = carType;
            return this;
        }


        public Builder withManufacturer(Long manufacturerId)
        {
            this.manufacturerId = manufacturerId;
            return this;
        }


        public Builder withDriverDO(DriverDO driverDO)
        {
            this.driverDO = driverDO;
            return this;
        }


        public CarDO build()
        {
            return new CarDO(model, color, licensePlate, seatCount, convertible, rating, engineType, transmission, carType, manufacturerId);
        }
    }

}
