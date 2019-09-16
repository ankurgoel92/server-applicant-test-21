package com.freenow.service.car;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.freenow.dataaccessobject.CarRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainobject.ManufacturerDO;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.exception.InvalidQueryStringException;
import com.freenow.service.BaseServiceImpl;
import com.freenow.service.manufacturer.ManufacturerService;
import com.github.tennaito.rsql.jpa.JpaCriteriaQueryVisitor;

import cz.jirutka.rsql.parser.ast.RSQLVisitor;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some car specific things.
 * <p/>
 */
@Service
public class DefaultCarService extends BaseServiceImpl<CarDO, Long> implements CarService
{

    private static final Logger LOG = LoggerFactory.getLogger(DefaultCarService.class);

    private final CarRepository carRepository;
    private final ManufacturerService manufacturerService;


    @Autowired
    public DefaultCarService(final CarRepository carRepository, final ManufacturerService manufacturerService)
    {
        this.carRepository = carRepository;
        this.manufacturerService = manufacturerService;
    }


    @Override
    protected JpaRepository<CarDO, Long> getRepository()
    {
        return carRepository;
    }


    /**
     * Creates a new car.
     */
    @Override
    public CarDO createCar(CarDO carDO) throws ConstraintsViolationException, EntityNotFoundException
    {
        ManufacturerDO manufacturerDO = manufacturerService.find(carDO.getManufacturer().getId());
        carDO.setManufacturer(manufacturerDO);
        return create(carDO);
    }


    /**
     * Finds a car with a driver.
     */
    @Override
    public CarDO findByDriver(DriverDO driverDO)
    {
        return carRepository.findByDriverDO(driverDO);
    }


    /**
     * Searches the car matching the search query having car characteristics and returns the drivers associated with it.
     * If the query does not adhere to FIQL format an exception is raised.
     */
    @Override
    public List<DriverDO> searchByQuery(String queryString) throws InvalidQueryStringException
    {
        RSQLVisitor<CriteriaQuery<CarDO>, EntityManager> visitor = new JpaCriteriaQueryVisitor<>();
        CriteriaQuery<CarDO> query;
        query = getCriteriaQuery(queryString, visitor);
        List<CarDO> resultList = entityManager.createQuery(query).getResultList();
        if (resultList == null || resultList.isEmpty())
        {
            return Collections.emptyList();
        }

        return resultList
            .stream()
            .filter(car -> Objects.nonNull(car.getDriverDO()))
            .map(CarDO::getDriverDO)
            .collect(Collectors.toList());
    }

}
