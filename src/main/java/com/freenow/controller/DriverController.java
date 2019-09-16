package com.freenow.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.freenow.controller.mapper.CarMapper;
import com.freenow.controller.mapper.DriverMapper;
import com.freenow.datatransferobject.CarDTO;
import com.freenow.datatransferobject.DriverDTO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.CarSelectDeselectException;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.DriverNotOnlineException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.exception.InvalidQueryStringException;
import com.freenow.service.driver.DriverService;
import com.freenow.service.search.SearchService;

/**
 * All operations with a driver will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/drivers")
public class DriverController
{

    private final DriverService driverService;
    private final SearchService searchServie;


    @Autowired
    public DriverController(final DriverService driverService, final SearchService searchService)
    {
        this.driverService = driverService;
        this.searchServie = searchService;
    }


    @GetMapping("/{driverId}")
    public DriverDTO getDriver(@PathVariable long driverId) throws EntityNotFoundException
    {
        return DriverMapper.makeDriverDTO(driverService.find(driverId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDTO createDriver(@Valid @RequestBody DriverDTO driverDTO) throws ConstraintsViolationException
    {
        DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
        return DriverMapper.makeDriverDTO(driverService.create(driverDO));
    }


    @DeleteMapping("/{driverId}")
    public void deleteDriver(@PathVariable long driverId) throws EntityNotFoundException
    {
        driverService.delete(driverId);
    }


    @PutMapping("/{driverId}")
    public void updateLocation(
        @PathVariable long driverId, @RequestParam double longitude, @RequestParam double latitude)
        throws EntityNotFoundException
    {
        driverService.updateLocation(driverId, longitude, latitude);
    }


    @GetMapping
    public List<DriverDTO> findDrivers(@RequestParam OnlineStatus onlineStatus)
    {
        return DriverMapper.makeDriverDTOList(driverService.find(onlineStatus));
    }


    @PutMapping("/{driverId}/select-car/{carId}")
    public CarDTO selectCar(@PathVariable long driverId, @PathVariable Long carId)
        throws EntityNotFoundException, CarAlreadyInUseException, DriverNotOnlineException, CarSelectDeselectException
    {
        return CarMapper.makeCarDTO(driverService.selectCar(driverId, carId));
    }


    @PutMapping("/{driverId}/deselect-car")
    public void deselectCar(@PathVariable long driverId) throws EntityNotFoundException, CarSelectDeselectException
    {
        driverService.deselectCar(driverId);
    }


    @GetMapping("/search")
    public List<DriverDTO> search(@RequestParam(value = "_q") String query) throws EntityNotFoundException, InvalidQueryStringException
    {
        return DriverMapper.makeDriverDTOList(searchServie.searchByQuery(query));
    }
}
