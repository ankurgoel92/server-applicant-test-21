package com.freenow.dataaccessobject;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;

/**
 * Database Access Object for car table.
 * <p/>
 */
@Repository
public interface CarRepository extends JpaRepository<CarDO, Long> {

    CarDO findByDriverDO(DriverDO driverDO);
}
