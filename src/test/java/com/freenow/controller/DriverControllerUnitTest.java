package com.freenow.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.GeoCoordinate;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.service.driver.DriverService;
import com.freenow.service.search.SearchService;
import com.freenow.utils.CarDOBuilder;
import com.freenow.utils.DriverDOBuilder;

@RunWith(SpringRunner.class)
@WebMvcTest(DriverController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DriverControllerUnitTest
{

    private static final String SEARCH = "/search/";

    private static final String QUERY_STRING = "driver(username==driver01);car(colour==red)";

    private static final String EXPECTED_CARDTO = "{\"licensePlate\":\"LICENSE-01\"}";

    private static final String BAD_REQUEST = "BAD_REQUEST_BODY";

    private static final String DRIVERDTO_JSON =
        "{\"username\":\"driver01\",\"password\":\"driver01pw\",\"coordinate\":{\"latitude\":0.0,\"longitude\":0.0}}";

    private static final String BASE_URL = "/v1/drivers/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DriverService driverService;

    @MockBean
    private SearchService searchService;


    @Test
    public void testGetDriver() throws Exception
    {
        when(driverService.find(1l)).thenReturn(getDriverDO());

        mockMvc
            .perform(get(BASE_URL + 1l))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().json(DRIVERDTO_JSON));

        verify(driverService, times(1)).find(1L);
    }


    @Test
    public void testGetDriverWhenNotExistsThenThrowsException() throws Exception
    {
        when(driverService.find(1l)).thenThrow(new EntityNotFoundException("Entity Not Found"));

        mockMvc
            .perform(get(BASE_URL + 1l))
            .andExpect(status().isNotFound());

        verify(driverService, times(1)).find(1L);
    }


    @Test
    public void testCreateDriverWithGoodRequest() throws Exception
    {
        when(driverService.create(any(DriverDO.class))).thenReturn(getDriverDO());
        mockMvc
            .perform(
                post(BASE_URL)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(DRIVERDTO_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().json(DRIVERDTO_JSON));

        verify(driverService).create(any(DriverDO.class));
    }


    @Test
    public void testCreateDriverWithBadRequest() throws Exception
    {
        mockMvc
            .perform(
                post(BASE_URL)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

    }


    @Test
    public void testDeleteDriverWithGoodRequest() throws Exception
    {
        doNothing().when(driverService).delete(1l);

        mockMvc
            .perform(delete(BASE_URL + 1l))
            .andExpect(status().isOk());

        verify(driverService, times(1)).delete(1l);
    }


    @Test
    public void testDeleteDriverWhenDriverDoesNotExist() throws Exception
    {
        doThrow(new EntityNotFoundException("Entity Not Found")).when(driverService).delete(1l);

        mockMvc
            .perform(delete(BASE_URL + 1l))
            .andExpect(status().isNotFound());

        verify(driverService, times(1)).delete(1l);
    }


    @Test
    public void testSelectCar() throws Exception
    {
        when(driverService.selectCar(1l, 1l)).thenReturn(CarDOBuilder.builder().withLicensePlate("LICENSE-01").withManufacturer(1l).build());

        mockMvc
            .perform(put(BASE_URL + 1l + "/select-car/" + 1l))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().json(EXPECTED_CARDTO));

        verify(driverService, times(1)).selectCar(1l, 1l);
    }


    @Test
    public void testDeselectCar() throws Exception
    {
        doNothing().when(driverService).deselectCar(1l);

        mockMvc
            .perform(put(BASE_URL + 1l + "/deselect-car/"))
            .andExpect(status().isOk());

        verify(driverService, times(1)).deselectCar(1l);
    }


    @Test
    public void testSearchDriver() throws Exception
    {
        when(searchService.searchByQuery(QUERY_STRING)).thenReturn(Collections.singleton(getDriverDO()));

        mockMvc
            .perform(get(BASE_URL + SEARCH).param("_q", QUERY_STRING))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().json("[" + DRIVERDTO_JSON + "]"));

        verify(searchService, times(1)).searchByQuery(QUERY_STRING);
    }


    private DriverDO getDriverDO()
    {
        DriverDO driverDO =
            DriverDOBuilder
                .builder()
                .withCoordinate(new GeoCoordinate(0, 0))
                .withOnlineStatus(OnlineStatus.ONLINE)
                .withUsername("driver01")
                .withPassword("driver01pw")
                .build();

        return driverDO;
    }

}
