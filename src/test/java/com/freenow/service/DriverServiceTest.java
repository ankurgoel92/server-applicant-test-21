package com.freenow.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.freenow.dataaccessobject.DriverRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.CarSelectDeselectException;
import com.freenow.exception.DriverNotOnlineException;
import com.freenow.service.car.CarService;
import com.freenow.service.driver.DefaultDriverService;
import com.freenow.utils.CarDOBuilder;
import com.freenow.utils.DriverDOBuilder;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DefaultDriverService.class})
public class DriverServiceTest
{
    @Autowired
    private DefaultDriverService driverService;

    @MockBean
    private CarService carService;

    @MockBean
    private DriverRepository repository;

    @MockBean
    private EntityManager entityManager;

    @MockBean
    private EntityManagerFactory entityManagerFactory;


    @Test
    public void testUpdateLocation() throws Exception
    {

        Optional<DriverDO> driverDO = Optional.of(DriverDOBuilder.builder().build());

        when(repository.findById(1l)).thenReturn(driverDO);

        driverService.updateLocation(1l, 0, 0);

        assertNotNull(driverDO.get().getCoordinate());

        verify(repository, times(1)).findById(1l);

    }


    @Test
    public void testFind() throws Exception
    {

        DriverDO driverDO = DriverDOBuilder.builder().build();

        when(repository.findByOnlineStatus(OnlineStatus.ONLINE)).thenReturn(Collections.singletonList(driverDO));

        assertNotNull(driverService.find(OnlineStatus.ONLINE));

        verify(repository, times(1)).findByOnlineStatus(OnlineStatus.ONLINE);

    }


    @Test
    public void testSelectCar() throws Exception
    {
        Optional<DriverDO> driverDO = Optional.of(DriverDOBuilder.builder().withOnlineStatus(OnlineStatus.ONLINE).build());
        CarDO carDO = CarDOBuilder.builder().withManufacturer(1l).build();

        when(repository.findById(1l)).thenReturn(driverDO);
        when(carService.find(1l)).thenReturn(carDO);

        CarDO actualCarDO = driverService.selectCar(1l, 1l);

        assertNotNull(actualCarDO);
        assertNotNull(actualCarDO.getDriverDO());

        verify(repository, times(1)).findById(1l);
        verify(carService, times(1)).find(1l);

    }


    @Test(expected = DriverNotOnlineException.class)
    public void testSelectCarWithDriverOffline() throws Exception
    {
        Optional<DriverDO> driverDO = Optional.of(DriverDOBuilder.builder().withOnlineStatus(OnlineStatus.OFFLINE).build());

        when(repository.findById(1l)).thenReturn(driverDO);

        driverService.selectCar(1l, 1l);

        verify(repository, times(1)).findById(1l);
        verify(carService, times(0)).find(1l);

    }


    @Test(expected = CarSelectDeselectException.class)
    public void testSelectCarWithDriverHavingCar() throws Exception
    {
        Optional<DriverDO> driverDO = Optional.of(DriverDOBuilder.builder().withCarDO(new CarDO()).build());

        when(repository.findById(1l)).thenReturn(driverDO);

        driverService.selectCar(1l, 1l);

        verify(repository, times(1)).findById(1l);
        verify(carService, times(0)).find(1l);

    }


    @Test(expected = CarAlreadyInUseException.class)
    public void testSelectCarWithCarInUse() throws Exception
    {
        Optional<DriverDO> driverDO = Optional.of(DriverDOBuilder.builder().withOnlineStatus(OnlineStatus.ONLINE).build());
        CarDO carDO = CarDOBuilder.builder().build();
        DriverDO associatedDriver = new DriverDO();
        associatedDriver.setId(1L);
        carDO.setDriverDO(associatedDriver);

        when(repository.findById(1l)).thenReturn(driverDO);
        when(carService.find(1l)).thenReturn(carDO);

        driverService.selectCar(1l, 1l);

        verify(repository, times(1)).findById(1l);
        verify(carService, times(1)).find(1l);

    }
    
    
    @Test
    public void testDeselectCar() throws Exception
    {
        Optional<DriverDO> driverDO = Optional.of(DriverDOBuilder.builder().withOnlineStatus(OnlineStatus.ONLINE).build());
        CarDO carDO = CarDOBuilder.builder().withManufacturer(1l).build();

        when(repository.findById(1l)).thenReturn(driverDO);
        when(carService.findByDriver(driverDO.get())).thenReturn(carDO);

        driverService.deselectCar(1l);

        assertNull(carDO.getDriverDO());
        
        verify(repository, times(1)).findById(1l);
        verify(carService, times(1)).findByDriver(driverDO.get());

    }
    
    
    @Test(expected = CarSelectDeselectException.class)
    public void testDeselectCarWithDriverNotHavingCar() throws Exception
    {
        Optional<DriverDO> driverDO = Optional.of(DriverDOBuilder.builder().withOnlineStatus(OnlineStatus.ONLINE).build());
        CarDO carDO = CarDOBuilder.builder().withManufacturer(1l).build();

        when(repository.findById(1l)).thenReturn(driverDO);
        when(carService.findByDriver(driverDO.get())).thenReturn(null);

        driverService.deselectCar(1l);

        assertNull(carDO.getDriverDO());
        
        verify(repository, times(1)).findById(1l);
        verify(carService, times(1)).findByDriver(driverDO.get());

    }


}
