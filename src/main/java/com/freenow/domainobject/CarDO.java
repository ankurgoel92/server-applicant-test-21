package com.freenow.domainobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.freenow.domainvalue.CarType;
import com.freenow.domainvalue.EngineType;
import com.freenow.domainvalue.Transmission;

@Entity
@Table(
    name = "car",
    uniqueConstraints = @UniqueConstraint(name = "uc_licenseplate", columnNames = {"licensePlate"}))
public class CarDO extends BaseDO
{

    @Column(nullable = false)
    @NotNull(message = "Name can not be null!")
    private String model;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String licensePlate;

    @Column(nullable = false)
    private Integer seatCount;

    @Column(nullable = false)
    private Boolean convertible;

    @Column(nullable = false)
    private Float rating;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EngineType engineType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Transmission transmission;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarType carType;

    @ManyToOne
    private ManufacturerDO manufacturer;

    @OneToOne
    @JoinColumn(name = "driver_id")
    private DriverDO driverDO;


    public CarDO()
    {

    }


    public CarDO(
        String model, String color, String licensePlate, Integer seatCount, Boolean convertible, Float rating, EngineType engineType, Transmission transmission, CarType carType,
        Long manufacturerId)
    {

        this.model = model;
        this.color = color;
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.convertible = convertible;
        this.rating = rating;
        this.engineType = engineType;
        this.transmission = transmission;
        this.carType = carType;
        this.manufacturer = new ManufacturerDO(manufacturerId);
        this.setDeleted(false);
    }


    public String getModel()
    {
        return model;
    }


    public void setModel(String model)
    {
        this.model = model;
    }


    public String getColor()
    {
        return color;
    }


    public void setColor(String color)
    {
        this.color = color;
    }


    public String getLicensePlate()
    {
        return licensePlate;
    }


    public void setLicensePlate(String licensePlate)
    {
        this.licensePlate = licensePlate;
    }


    public Integer getSeatCount()
    {
        return seatCount;
    }


    public void setSeatCount(Integer seatCount)
    {
        this.seatCount = seatCount;
    }


    public Boolean getConvertible()
    {
        return convertible;
    }


    public void setConvertible(Boolean convertible)
    {
        this.convertible = convertible;
    }


    public Float getRating()
    {
        return rating;
    }


    public void setRating(Float rating)
    {
        this.rating = rating;
    }


    public EngineType getEngineType()
    {
        return engineType;
    }


    public void setEngineType(EngineType engineType)
    {
        this.engineType = engineType;
    }


    public Transmission getTransmission()
    {
        return transmission;
    }


    public void setTransmission(Transmission transmission)
    {
        this.transmission = transmission;
    }


    public CarType getCarType()
    {
        return carType;
    }


    public void setCarType(CarType carType)
    {
        this.carType = carType;
    }


    public ManufacturerDO getManufacturer()
    {
        return manufacturer;
    }


    public void setManufacturer(ManufacturerDO manufacturer)
    {
        this.manufacturer = manufacturer;
    }


    public DriverDO getDriverDO()
    {
        return driverDO;
    }


    public void setDriverDO(DriverDO driverDO)
    {
        this.driverDO = driverDO;
    }

}
