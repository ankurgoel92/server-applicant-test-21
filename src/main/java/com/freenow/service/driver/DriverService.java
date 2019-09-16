package com.freenow.service.driver;

import java.util.List;

import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.CarSelectDeselectException;
import com.freenow.exception.DriverNotOnlineException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.exception.InvalidQueryStringException;
import com.freenow.service.BaseService;

public interface DriverService extends BaseService<DriverDO, Long>
{

    void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException;

    List<DriverDO> find(OnlineStatus onlineStatus);
    
    CarDO selectCar(long driverId, long carId) throws EntityNotFoundException, DriverNotOnlineException, CarAlreadyInUseException, CarSelectDeselectException;

    void deselectCar(long driverId) throws EntityNotFoundException, CarSelectDeselectException;

    List<DriverDO> searchByQuery(String queryString) throws InvalidQueryStringException;

}
