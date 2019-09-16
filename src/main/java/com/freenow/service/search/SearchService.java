package com.freenow.service.search;

import java.util.Set;

import javax.validation.constraints.NotEmpty;

import com.freenow.domainobject.DriverDO;
import com.freenow.exception.InvalidQueryStringException;

public interface SearchService
{
    Set<DriverDO> searchByQuery(@NotEmpty String queryString) throws InvalidQueryStringException;
}
