package com.freenow.service.manufacturer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.freenow.dataaccessobject.ManufacturerRepository;
import com.freenow.domainobject.ManufacturerDO;
import com.freenow.service.BaseServiceImpl;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some manufacturer specific things.
 * <p/>
 */
@Service
public class DefaultManufacturerService extends BaseServiceImpl<ManufacturerDO, Long> implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    @Autowired
    public DefaultManufacturerService(final ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }


    @Override
    protected JpaRepository<ManufacturerDO, Long> getRepository() {
        return manufacturerRepository;
    }
}
