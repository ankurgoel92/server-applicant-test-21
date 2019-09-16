package com.freenow.domainobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "manufacturer")
public class ManufacturerDO extends BaseDO
{

    @Column(nullable = false)
    @NotNull(message = "Manufacturer name can not be null!")
    private String name;


    public ManufacturerDO()
    {

    }


    public ManufacturerDO(Long id)
    {
        super.setId(id);
    }


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }

}
