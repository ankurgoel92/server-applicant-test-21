package com.freenow.service.search;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.freenow.datatransferobject.FiqlDTO;
import com.freenow.domainobject.DriverDO;
import com.freenow.exception.InvalidQueryStringException;
import com.freenow.service.car.CarService;
import com.freenow.service.driver.DriverService;
import com.google.common.collect.Sets;

/**
 * Service having business logic for searching drivers.
 * <p/>
 */
@Service
public class DefaultSearchService implements SearchService
{
    private static final String AND = ";";

    private final CarService carService;
    private final DriverService driverService;


    public DefaultSearchService(CarService carService, DriverService driverService)
    {
        this.carService = carService;
        this.driverService = driverService;
    }


    /**
     * Searches for all the drivers matching the search query.
     * Query Format: driver(<driver characteristics>)<Joining operator: , or ;>car(<car characteristics>)
     */
    @Override
    public Set<DriverDO> searchByQuery(final String queryString) throws InvalidQueryStringException
    {
        final FiqlDTO fiql = new FiqlDTO(queryString);

        Set<DriverDO> driversFromDriverQuery = getFromDriverQuery(fiql.getDriverQuery());
        Set<DriverDO> driversFromCarQuery = getFromCarQuery(fiql.getCarQuery());

        return StringUtils.equals(fiql.getOperator(), AND)
            ? Sets.intersection(driversFromDriverQuery, driversFromCarQuery)
            : Sets.union(driversFromDriverQuery, driversFromCarQuery);

    }


    private Set<DriverDO> getFromCarQuery(String carQuery) throws InvalidQueryStringException
    {

        if (StringUtils.isNotBlank(carQuery))
        {
            return carService.searchByQuery(carQuery).stream().collect(Collectors.toSet());
        }

        return Collections.emptySet();
    }


    private Set<DriverDO> getFromDriverQuery(final String driverQuery) throws InvalidQueryStringException
    {

        if (StringUtils.isNotBlank(driverQuery))
        {
            return driverService.searchByQuery(driverQuery).stream().collect(Collectors.toSet());
        }

        return Collections.emptySet();
    }

}
