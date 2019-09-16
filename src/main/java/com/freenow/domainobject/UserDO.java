package com.freenow.domainobject;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public class UserDO extends BaseDO implements UserDetails
{

    private static final long serialVersionUID = 1954740989798652773L;

    @Column(nullable = false, unique = true)
    @NotNull(message = "Username can not be null!")
    protected String username;

    @Column(nullable = false)
    @NotNull(message = "Password can not be null!")
    protected String password;

    @Column(columnDefinition = "boolean default false")
    private boolean accountExpired = false;

    @Column(columnDefinition = "boolean default false")
    private boolean accountLocked = false;

    @Column(columnDefinition = "boolean default false")
    private boolean credentialsExpired = false;

    @Column(columnDefinition = "boolean default true")
    private boolean accountEnabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    protected Set<RoleDO> roles = new HashSet<>();


    public UserDO(String userName, Set<RoleDO> authorityList)
    {
        this.username = userName;
        this.roles = authorityList;
    }


    public UserDO()
    {

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return getRoles();
    }


    @Override
    public String getPassword()
    {
        return this.password;
    }


    @Override
    public String getUsername()
    {
        return this.username;
    }


    @Override
    public boolean isAccountNonExpired()
    {
        return !accountExpired;
    }


    @Override
    public boolean isAccountNonLocked()
    {
        return !accountLocked;
    }


    @Override
    public boolean isCredentialsNonExpired()
    {
        return !credentialsExpired;
    }


    @Override
    public boolean isEnabled()
    {
        return accountEnabled;
    }


    public boolean isAccountExpired()
    {
        return accountExpired;
    }


    public void setAccountExpired(boolean accountExpired)
    {
        this.accountExpired = accountExpired;
    }


    public boolean isAccountLocked()
    {
        return accountLocked;
    }


    public void setAccountLocked(boolean accountLocked)
    {
        this.accountLocked = accountLocked;
    }


    public boolean isCredentialsExpired()
    {
        return credentialsExpired;
    }


    public void setCredentialsExpired(boolean credentialsExpired)
    {
        this.credentialsExpired = credentialsExpired;
    }


    public boolean isAccountEnabled()
    {
        return accountEnabled;
    }


    public void setAccountEnabled(boolean accountEnabled)
    {
        this.accountEnabled = accountEnabled;
    }


    public Set<RoleDO> getRoles()
    {
        return roles;
    }


    public void setRoles(Set<RoleDO> roles)
    {
        this.roles = roles;
    }


    public void setUsername(String username)
    {
        this.username = username;
    }


    public void setPassword(String password)
    {
        this.password = password;
    }

}
