package com.freenow.dataaccessobject;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.OnlineStatus;

/**
 * Database Access Object for driver table.
 * <p/>
 */
@Repository
public interface DriverRepository extends JpaRepository<DriverDO, Long>
{

    List<DriverDO> findByOnlineStatus(OnlineStatus onlineStatus);
}
