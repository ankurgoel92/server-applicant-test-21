package com.freenow.service.car;

import java.util.List;

import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.exception.InvalidQueryStringException;
import com.freenow.service.BaseService;

public interface CarService extends BaseService<CarDO, Long>
{

    CarDO createCar(CarDO carDO) throws ConstraintsViolationException, EntityNotFoundException;


    CarDO findByDriver(DriverDO driverDO);


    List<DriverDO> searchByQuery(String queryString) throws InvalidQueryStringException;

}
