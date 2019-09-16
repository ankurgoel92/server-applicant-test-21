package com.freenow.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freenow.domainobject.BaseDO;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.exception.InvalidQueryStringException;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;

/**
 * Encapsulates common logic
 */
@Service
public abstract class BaseServiceImpl<T extends BaseDO, ID extends Serializable> implements BaseService<T, ID>
{

    private static final Logger LOG = LoggerFactory.getLogger(BaseServiceImpl.class);

    @PersistenceContext
    protected EntityManager entityManager;


    protected abstract JpaRepository<T, ID> getRepository();


    @Override
    public T create(T entity) throws ConstraintsViolationException
    {
        try
        {
            entity = getRepository().save(entity);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("Some constraints are thrown due to Entity creation: " + entity.getClass(), e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return entity;
    }


    @Override
    @Transactional
    public void delete(ID id) throws EntityNotFoundException
    {
        T entity = find(id);
        entity.setDeleted(true);
    }


    @Override
    public T find(ID id) throws EntityNotFoundException
    {
        return getRepository()
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + id));
    }


    @Override
    public List<T> findAll()
    {
        return getRepository().findAll();
    }


    protected CriteriaQuery<T> getCriteriaQuery(String queryString, RSQLVisitor<CriteriaQuery<T>, EntityManager> visitor) throws InvalidQueryStringException
    {
        Node rootNode;
        CriteriaQuery<T> query;
        try
        {
            rootNode = new RSQLParser().parse(queryString);
            query = rootNode.accept(visitor, entityManager);
        }
        catch (Exception e)
        {
            LOG.error("An error happened while executing RSQL query {}", queryString, e);
            throw new InvalidQueryStringException("Query String: " + queryString + " in not valid");
        }
        return query;
    }

}
