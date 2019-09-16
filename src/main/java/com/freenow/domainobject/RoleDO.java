package com.freenow.domainobject;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Table(name = "role")
@Entity
public class RoleDO extends BaseDO implements GrantedAuthority
{

    private static final long serialVersionUID = -6193485444874165977L;

    private String authority;

    @ManyToMany(mappedBy = "roles")
    private Set<UserDO> users;


    public RoleDO(String authority)
    {
        this.authority = authority;
    }


    public RoleDO()
    {

    }


    @Override
    public String getAuthority()
    {
        return authority;
    }


    public Set<UserDO> getUsers()
    {
        return users;
    }


    public void setUsers(Set<UserDO> users)
    {
        this.users = users;
    }


    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }


    public void setAuthority(String authority)
    {
        this.authority = authority;
    }

}
