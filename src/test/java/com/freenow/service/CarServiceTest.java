package com.freenow.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.freenow.dataaccessobject.CarRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainobject.ManufacturerDO;
import com.freenow.service.car.DefaultCarService;
import com.freenow.service.manufacturer.ManufacturerService;
import com.freenow.utils.CarDOBuilder;
import com.freenow.utils.DriverDOBuilder;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DefaultCarService.class})
public class CarServiceTest
{
    @Autowired
    private DefaultCarService carService;

    @MockBean
    private ManufacturerService manufacturerService;

    @MockBean
    private CarRepository repository;

    @MockBean
    private EntityManager entityManager;

    @MockBean
    private EntityManagerFactory entityManagerFactory;

    @MockBean
    private TypedQuery<CarDO> typedQuery;


    @Test
    public void testUpdateLocation() throws Exception
    {

        CarDO carDO = CarDOBuilder.builder().withManufacturer(1l).build();

        when(manufacturerService.find(1l)).thenReturn(new ManufacturerDO(1l));
        when(repository.save(carDO)).thenReturn(carDO);

        final CarDO car = carService.createCar(carDO);

        assertNotNull(car);

        verify(manufacturerService, times(1)).find(1l);
        verify(repository, times(1)).save(carDO);

    }


    @Test
    public void testFindByDriver() throws Exception
    {

        DriverDO driverDO = DriverDOBuilder.builder().build();
        CarDO carDO = CarDOBuilder.builder().withManufacturer(1l).build();

        when(repository.findByDriverDO(driverDO)).thenReturn(carDO);

        final CarDO car = carService.findByDriver(driverDO);

        assertNotNull(car);

        verify(repository, times(1)).findByDriverDO(driverDO);

    }

}
