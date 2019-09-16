package com.freenow.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.freenow.datatransferobject.FiqlDTO;
import com.freenow.domainobject.DriverDO;
import com.freenow.service.car.CarService;
import com.freenow.service.driver.DriverService;
import com.freenow.service.search.DefaultSearchService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DefaultSearchService.class})
public class SearchServiceTest
{
    private static final String DRIVER_CAR_QUERY = "driver(username==driver01);car(colour==red)";
    private static final String DRIVER_QUERY = "driver(username==driver01)";
    private static final String CAR_QUERY = "car(colour==red)";

    @Autowired
    private DefaultSearchService searchService;

    @MockBean
    private CarService carService;

    @MockBean
    private DriverService driverService;

    @Captor
    private ArgumentCaptor<String> captor;


    @Test
    public void testSearchByQuery() throws Exception
    {

        final FiqlDTO fiqlDTO = new FiqlDTO(DRIVER_CAR_QUERY);

        when(driverService.searchByQuery(fiqlDTO.getDriverQuery())).thenReturn(Collections.singletonList(new DriverDO()));
        when(carService.searchByQuery(fiqlDTO.getCarQuery())).thenReturn(Collections.emptyList());

        final Set<DriverDO> drivers = searchService.searchByQuery(DRIVER_CAR_QUERY);

        assertNotNull(drivers);

        verify(driverService, times(1)).searchByQuery(captor.capture());
        verify(carService, times(1)).searchByQuery(captor.capture());

        List<String> allValues = captor.getAllValues();
        assertEquals(Arrays.asList(new String[] {fiqlDTO.getDriverQuery(), fiqlDTO.getCarQuery()}), allValues);

    }
    
    @Test
    public void testSearchByQueryWithDriverQueryOnly() throws Exception
    {

        final FiqlDTO fiqlDTO = new FiqlDTO(DRIVER_QUERY);

        when(driverService.searchByQuery(fiqlDTO.getDriverQuery())).thenReturn(Collections.singletonList(new DriverDO()));

        final Set<DriverDO> drivers = searchService.searchByQuery(DRIVER_QUERY);

        assertNotNull(drivers);

        verify(driverService, times(1)).searchByQuery(captor.capture());
        verify(carService, times(0)).searchByQuery(captor.capture());

        List<String> allValues = captor.getAllValues();
        assertEquals(Collections.singletonList(fiqlDTO.getDriverQuery()), allValues);

    }
    
    @Test
    public void testSearchByQueryWithCarQueryOnly() throws Exception
    {

        final FiqlDTO fiqlDTO = new FiqlDTO(CAR_QUERY);

        when(carService.searchByQuery(fiqlDTO.getCarQuery())).thenReturn(Collections.singletonList(new DriverDO()));

        final Set<DriverDO> drivers = searchService.searchByQuery(CAR_QUERY);

        assertNotNull(drivers);

        verify(driverService, times(0)).searchByQuery(captor.capture());
        verify(carService, times(1)).searchByQuery(captor.capture());

        List<String> allValues = captor.getAllValues();
        assertEquals(Collections.singletonList(fiqlDTO.getCarQuery()), allValues);

    }
}
