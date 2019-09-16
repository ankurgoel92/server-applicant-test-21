package com.freenow.service.driver;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freenow.dataaccessobject.DriverRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.GeoCoordinate;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.CarSelectDeselectException;
import com.freenow.exception.DriverNotOnlineException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.exception.InvalidQueryStringException;
import com.freenow.service.BaseServiceImpl;
import com.freenow.service.car.CarService;
import com.github.tennaito.rsql.jpa.JpaCriteriaQueryVisitor;

import cz.jirutka.rsql.parser.ast.RSQLVisitor;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some driver specific things.
 * <p/>
 */
/**
 * @author ergoe
 *
 */
@Service
public class DefaultDriverService extends BaseServiceImpl<DriverDO, Long> implements DriverService
{

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

    private final DriverRepository driverRepository;

    private final CarService carService;


    public DefaultDriverService(final DriverRepository driverRepository, final CarService carService)
    {
        this.driverRepository = driverRepository;
        this.carService = carService;
    }


    @Override
    protected JpaRepository<DriverDO, Long> getRepository()
    {
        return driverRepository;
    }


    /**
     * Update the location for a driver.
     *
     * @param driverId
     * @param longitude
     * @param latitude
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException
    {
        DriverDO driverDO = find(driverId);
        driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
    }


    /**
     * Find all drivers by online state.
     *
     * @param onlineStatus
     */
    @Override
    public List<DriverDO> find(OnlineStatus onlineStatus)
    {
        return driverRepository.findByOnlineStatus(onlineStatus);
    }


    /**
     * Drivers can select a car they are driving with.
     * Driver needs to be online and not have a car already. Also the car selected should not have a driver associated.
     * Exceptions are thrown if the above condition is not met.
     */
    @Override
    @Transactional
    public CarDO selectCar(long driverId, long carId) throws EntityNotFoundException, DriverNotOnlineException, CarAlreadyInUseException, CarSelectDeselectException
    {
        LOG.info("Driver with id {} is trying to select car with id {}", driverId, carId);
        
        DriverDO driverDO = find(driverId);
        validateNoCarSelectedAndOnlineStatus(driverDO);

        CarDO carDO = carService.find(carId);
        validateNoDriverAssociated(carDO, driverDO);

        carDO.setDriverDO(driverDO);

        return carDO;

    }


    /**
     * Drivers can deselect a car they are driving.
     * The car being tried to deselect should have the same driver id else an exception is raised.
     */
    @Override
    @Transactional
    public void deselectCar(long driverId) throws EntityNotFoundException, CarSelectDeselectException
    {
        LOG.info("Driver with id {} is trying to deselect the alloted car", driverId);
        
        DriverDO driverDO = find(driverId);
        CarDO carDO = carService.findByDriver(driverDO);
        validateCarSelected(carDO);
        carDO.setDriverDO(null);
    }


    /**
     * Searches the drivers matching the search query having driver characteristics.
     * If the query does not adhere to FIQL format an exception is raised.
     */
    @Override
    public List<DriverDO> searchByQuery(String queryString) throws InvalidQueryStringException
    {
        LOG.info("Finding drivers with search string {}", queryString);
        
        RSQLVisitor<CriteriaQuery<DriverDO>, EntityManager> visitor = new JpaCriteriaQueryVisitor<>();
        CriteriaQuery<DriverDO> query =  getCriteriaQuery(queryString, visitor);
        List<DriverDO> resultList = entityManager.createQuery(query).getResultList();
        if (resultList == null || resultList.isEmpty())
        {
            return Collections.emptyList();
        }
        return resultList;
    }


    private boolean validateNoCarSelectedAndOnlineStatus(DriverDO driverDO) throws DriverNotOnlineException, CarSelectDeselectException
    {
        if (Objects.nonNull(driverDO.getCarDO()))
        {
            throw new CarSelectDeselectException("Driver has already a car associated with it.");
        }

        if (!OnlineStatus.ONLINE.equals(driverDO.getOnlineStatus()))
        {
            throw new DriverNotOnlineException("Driver with id:" + driverDO.getId() + " is not ONLINE");
        }

        return true;
    }


    private boolean validateNoDriverAssociated(CarDO carDO, DriverDO driverDO) throws CarAlreadyInUseException
    {
        DriverDO associatedDriver = carDO.getDriverDO();

        if (Objects.nonNull(associatedDriver) && !associatedDriver.getId().equals(driverDO.getId()))
        {
            throw new CarAlreadyInUseException("Requested Car with id: " + carDO.getId() + " is already in use by driver: " + associatedDriver.getId());
        }

        return true;
    }


    private boolean validateCarSelected(CarDO carDO) throws CarSelectDeselectException
    {
        if (Objects.isNull(carDO))
        {
            throw new CarSelectDeselectException("No car has been allocated to the driver");
        }

        return true;
    }

}
