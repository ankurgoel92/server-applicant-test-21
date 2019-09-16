package com.freenow.dataaccessobject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.freenow.domainobject.ManufacturerDO;

@Repository
public interface ManufacturerRepository extends JpaRepository<ManufacturerDO, Long> {

}
