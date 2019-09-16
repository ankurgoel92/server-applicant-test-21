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

import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.CarType;
import com.freenow.domainvalue.EngineType;
import com.freenow.domainvalue.Transmission;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.service.car.CarService;
import com.freenow.utils.CarDOBuilder;

@RunWith(SpringRunner.class)
@WebMvcTest(CarController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CarControllerUnitTest
{

    private static final String BAD_REQUEST = "BAD_REQUEST_BODY";

    private static final String CARDTO_JSON =
        "{\"model\":\"VXI\",\"color\":\"Red\",\"licensePlate\":\"License-01\",\"convertible\":false,\"engineType\":\"PETROL\",\"transmission\":\"MANUAL\",\"carType\":\"SMALL\"}";

    private static final String BASE_URL = "/v1/cars/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;


    @Test
    public void testGetCar() throws Exception
    {

        when(carService.find(1l)).thenReturn(getCarDO());

        mockMvc
            .perform(get(BASE_URL + 1l))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().json(CARDTO_JSON));

        verify(carService, times(1)).find(1L);
    }


    @Test
    public void testGetCarWhenNotExistsThenThrowsException() throws Exception
    {
        when(carService.find(1l)).thenThrow(new EntityNotFoundException("Entity Not Found"));

        mockMvc
            .perform(get(BASE_URL + 1l))
            .andExpect(status().isNotFound());

        verify(carService, times(1)).find(1L);
    }


    @Test
    public void testGetAllCars() throws Exception
    {

        when(carService.findAll()).thenReturn(Collections.singletonList(getCarDO()));

        mockMvc
            .perform(get(BASE_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().json("[" + CARDTO_JSON + "]"));

        verify(carService, times(1)).findAll();
    }


    @Test
    public void testCreateCarWithGoodRequest() throws Exception
    {
        CarDO carDO = getCarDO();
        when(carService.createCar(any(CarDO.class))).thenReturn(carDO);
        mockMvc
            .perform(
                post(BASE_URL)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(CARDTO_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().json(CARDTO_JSON));

        verify(carService).createCar(any(CarDO.class));
    }


    @Test
    public void testCreateCarWithBadRequest() throws Exception
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
    public void testDeleteCarWithGoodRequest() throws Exception
    {
        doNothing().when(carService).delete(1l);

        mockMvc
            .perform(delete(BASE_URL + 1l))
            .andExpect(status().isOk());

        verify(carService, times(1)).delete(1l);
    }


    @Test
    public void testDeleteCarWhenCarDoesNotExist() throws Exception
    {
        doThrow(new EntityNotFoundException("Entity Not Found")).when(carService).delete(1l);

        mockMvc
            .perform(delete(BASE_URL + 1l))
            .andExpect(status().isNotFound());

        verify(carService, times(1)).delete(1l);
    }


    private CarDO getCarDO()
    {
        CarDO carDO =
            CarDOBuilder
                .builder()
                .withCarType(CarType.SMALL)
                .withColor("Red")
                .withConvertible(false)
                .withDriverDO(new DriverDO())
                .withManufacturer(1L)
                .withEngineType(EngineType.PETROL)
                .withModel("VXI")
                .withLicensePlate("License-01")
                .withTransmission(Transmission.MANUAL)
                .build();

        return carDO;
    }

}
