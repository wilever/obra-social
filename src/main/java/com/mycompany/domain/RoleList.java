package com.mycompany.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A RoleList.
 */
@Entity
@Table(name = "role_list")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RoleList implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_role", nullable = false)
    private String role;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "jhi_resource", nullable = false)
    private String resource;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("roleLists")
    private User user;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("roleLists")
    private RoleType roleType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public RoleList role(String role) {
        this.role = role;
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public RoleList description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResource() {
        return resource;
    }

    public RoleList resource(String resource) {
        this.resource = resource;
        return this;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public User getUser() {
        return user;
    }

    public RoleList user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public RoleList roleType(RoleType roleType) {
        this.roleType = roleType;
        return this;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoleList roleList = (RoleList) o;
        if (roleList.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), roleList.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RoleList{" +
            "id=" + getId() +
            ", role='" + getRole() + "'" +
            ", description='" + getDescription() + "'" +
            ", resource='" + getResource() + "'" +
            "}";
    }
}
