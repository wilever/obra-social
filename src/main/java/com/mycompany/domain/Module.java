package com.mycompany.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Module.
 */
@Entity
@Table(name = "module")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Module implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JsonIgnoreProperties("names")
    private Module module;

    @OneToMany(mappedBy = "module")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Module> names = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("types")
    private ModuleType moduleType;

    @ManyToOne
    @JsonIgnoreProperties("names")
    private Tag tag;

    @ManyToOne
    @JsonIgnoreProperties("names")
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Module name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Module getModule() {
        return module;
    }

    public Module module(Module module) {
        this.module = module;
        return this;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Set<Module> getNames() {
        return names;
    }

    public Module names(Set<Module> modules) {
        this.names = modules;
        return this;
    }

    public Module addName(Module module) {
        this.names.add(module);
        module.setModule(this);
        return this;
    }

    public Module removeName(Module module) {
        this.names.remove(module);
        module.setModule(null);
        return this;
    }

    public void setNames(Set<Module> modules) {
        this.names = modules;
    }

    public ModuleType getModuleType() {
        return moduleType;
    }

    public Module moduleType(ModuleType moduleType) {
        this.moduleType = moduleType;
        return this;
    }

    public void setModuleType(ModuleType moduleType) {
        this.moduleType = moduleType;
    }

    public Tag getTag() {
        return tag;
    }

    public Module tag(Tag tag) {
        this.tag = tag;
        return this;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
    
    public Company getCompany() {
        return company;
    }

    public Module company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
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
        Module module = (Module) o;
        if (module.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), module.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Module{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
