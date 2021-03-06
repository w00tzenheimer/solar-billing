/*
    jBilling - The Enterprise Open Source Billing System
    Copyright (C) 2003-2009 Enterprise jBilling Software Ltd. and Emiliano Conde

    This file is part of jbilling.

    jbilling is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    jbilling is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with jbilling.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.sapienter.jbilling.server.user.permisson.db;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OrderBy;

import com.google.common.collect.ImmutableSet;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.db.AbstractDescription;

@Entity
@Table(name="role")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class RoleDTO extends AbstractDescription implements java.io.Serializable, Role {
	private static final long serialVersionUID = 1L;
	private int id;
    private ImmutableSet<UserDTO> baseUsers;
    private ImmutableSet<PermissionDTO> permissions;

    // bean methods:
    // these are not called directly from any of our code, however they are called when instantiating this class as a spring or Hibernate bean 
    
    public RoleDTO() {}
    
    public void setBaseUsers(final Set<UserDTO> baseUsers)
    {
    	this.baseUsers = ImmutableSet.copyOf(baseUsers);
    }
    
    public void setPermissions(final Set<PermissionDTO> permissions)
    {
    	this.permissions = ImmutableSet.copyOf(permissions);
    }
    
    public void setId(int id) {
        this.id = id;
    }
     

    
    
     @Override public String toString()
     {
    	 return "RoleDTO " + id;
     }
 
    public RoleDTO(int id) {
        this.id = id;
        this.baseUsers = ImmutableSet.of();
        this.permissions = ImmutableSet.of();
    }
    public RoleDTO(int id, Set<UserDTO> baseUsers, Set<PermissionDTO> permissions) {
       this.id = id;
       this.baseUsers = ImmutableSet.copyOf(baseUsers);
       this.permissions = ImmutableSet.copyOf(permissions);
    }
    
    @Transient
    protected String getTable() {
        return Constants.TABLE_ROLE;
    }

    @Transient
    @Override public String getTitle(int languageId) {
        return getDescription(languageId, "title");
    }
    
    @Id 
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinTable(name="user_role_map", joinColumns = { 
        @JoinColumn(name="role_id", updatable=false) }, inverseJoinColumns = { 
        @JoinColumn(name="user_id", updatable=false) })
    public Set<UserDTO> getBaseUsers() {
        return this.baseUsers;
    }
    
    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinTable(name="permission_role_map", joinColumns = { 
        @JoinColumn(name="role_id", updatable=false) }, inverseJoinColumns = { 
        @JoinColumn(name="permission_id", updatable=false) })
    @OrderBy(clause = "permission_id")
    public Set<PermissionDTO> getPermissions() {
        return this.permissions;
    }
    
/*
    private void setPermissions(Set<PermissionDTO> permissions) {
        this.permissions = ImmutableSet.copyOf(permissions);
    }
*/    
}


