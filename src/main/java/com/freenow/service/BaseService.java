package com.freenow.service;

import java.io.Serializable;
import java.util.List;

import com.freenow.domainobject.BaseDO;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;

public interface BaseService<T extends BaseDO, ID extends Serializable>
{

    T create(T entity) throws ConstraintsViolationException;


    void delete(ID id) throws EntityNotFoundException;


    T find(ID id) throws EntityNotFoundException;


    List<T> findAll();

}
