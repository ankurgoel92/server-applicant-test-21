package com.freenow.datatransferobject;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.freenow.domainvalue.CarType;
import com.freenow.domainvalue.EngineType;
import com.freenow.domainvalue.Transmission;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO
{
    @JsonIgnore
    private Long id;

    @NotNull(message = "Model can not be null!")
    private String model;

    private String color;

    @NotNull(message = "License Number can not be null!")
    private String licensePlate;

    private Integer seatCount;

    private Boolean convertible;

    private Float rating;

    @NotNull(message = "Engine Type can not be null!")
    private EngineType engineType;

    private Transmission transmission;

    private CarType carType;

    private String manufacturer;

    private Long manufacturerId;


    private CarDTO()
    {

    }


    private CarDTO(
        Long id, String model, String color, String licensePlate, Integer seatCount, Boolean convertible, Float rating, EngineType engineType, Transmission transmission,
        CarType carType, String manufacturer)
    {
        this.id = id;
        this.model = model;
        this.color = color;
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.convertible = convertible;
        this.rating = rating;
        this.engineType = engineType;
        this.transmission = transmission;
        this.carType = carType;
        this.manufacturer = manufacturer;
    }


    public static CarDTOBuilder newBuilder()
    {
        return new CarDTOBuilder();
    }


    @JsonProperty
    public Long getId()
    {
        return id;
    }


    public String getModel()
    {
        return model;
    }


    public String getColor()
    {
        return color;
    }


    public String getLicensePlate()
    {
        return licensePlate;
    }


    public Integer getSeatCount()
    {
        return seatCount;
    }


    public Boolean getConvertible()
    {
        return convertible;
    }


    public Float getRating()
    {
        return rating;
    }


    public EngineType getEngineType()
    {
        return engineType;
    }


    public Transmission getTransmission()
    {
        return transmission;
    }


    public CarType getCarType()
    {
        return carType;
    }


    public String getManufacturer()
    {
        return manufacturer;
    }


    public Long getManufacturerId()
    {
        return manufacturerId;
    }

    public static class CarDTOBuilder
    {
        private Long id;
        private String model;
        private String color;
        private String licensePlate;
        private Integer seatCount;
        private Boolean convertible;
        private Float rating;
        private EngineType engineType;
        private Transmission transmission;
        private CarType carType;
        private String manufacturer;


        public CarDTOBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }


        public CarDTOBuilder setModel(String model)
        {
            this.model = model;
            return this;
        }


        public CarDTOBuilder setColor(String color)
        {
            this.color = color;
            return this;
        }


        public CarDTOBuilder setSeatCount(Integer seatCount)
        {
            this.seatCount = seatCount;
            return this;
        }


        public CarDTOBuilder setLicensePlate(String licensePlate)
        {
            this.licensePlate = licensePlate;
            return this;
        }


        public CarDTOBuilder setConvertible(Boolean convertible)
        {
            this.convertible = convertible;
            return this;
        }


        public CarDTOBuilder setRating(Float rating)
        {
            this.rating = rating;
            return this;
        }


        public CarDTOBuilder setEngineType(EngineType engineType)
        {
            this.engineType = engineType;
            return this;
        }


        public CarDTOBuilder setTransmission(Transmission transmission)
        {
            this.transmission = transmission;
            return this;
        }


        public CarDTOBuilder setCarType(CarType carType)
        {
            this.carType = carType;
            return this;
        }


        public CarDTOBuilder setManufacturer(String manufacturer)
        {
            this.manufacturer = manufacturer;
            return this;
        }


        public CarDTO createCarDTO()
        {
            return new CarDTO(id, model, color, licensePlate, seatCount, convertible, rating, engineType, transmission, carType, manufacturer);
        }
    }
}
