package com.freenow.utils;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Set;

import javax.annotation.Generated;

import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainobject.RoleDO;
import com.freenow.domainvalue.GeoCoordinate;
import com.freenow.domainvalue.OnlineStatus;

public class DriverDOBuilder
{
    /**
     * Creates builder to build {@link DriverDO}.
     * @return created builder
     */
    @Generated("SparkTools")
    public static Builder builder()
    {
        return new Builder();
    }

    /**
     * Builder to build {@link DriverDO}.
     */
    @Generated("SparkTools")
    public static final class Builder
    {
        private String username;
        private String password;
        private Set<RoleDO> roles = Collections.emptySet();
        private GeoCoordinate coordinate;
        private ZonedDateTime dateCoordinateUpdated;
        private OnlineStatus onlineStatus;
        private CarDO carDO;


        private Builder()
        {
            
        }


        public Builder withUsername(String username)
        {
            this.username = username;
            return this;
        }


        public Builder withPassword(String password)
        {
            this.password = password;
            return this;
        }


        public Builder withRoles(Set<RoleDO> roles)
        {
            this.roles = roles;
            return this;
        }


        public Builder withCoordinate(GeoCoordinate coordinate)
        {
            this.coordinate = coordinate;
            return this;
        }


        public Builder withDateCoordinateUpdated(ZonedDateTime dateCoordinateUpdated)
        {
            this.dateCoordinateUpdated = dateCoordinateUpdated;
            return this;
        }


        public Builder withOnlineStatus(OnlineStatus onlineStatus)
        {
            this.onlineStatus = onlineStatus;
            return this;
        }


        public Builder withCarDO(CarDO carDO)
        {
            this.carDO = carDO;
            return this;
        }


        public DriverDO build()
        {
            DriverDO driverDO = new DriverDO(username, password);
            driverDO.setOnlineStatus(onlineStatus);
            driverDO.setCarDO(carDO);
            driverDO.setCoordinate(coordinate);
            return driverDO;
        }
    }
}
