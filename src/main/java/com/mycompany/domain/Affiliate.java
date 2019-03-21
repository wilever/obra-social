package com.mycompany.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Affiliate.
 */
@Entity
@Table(name = "affiliate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Affiliate implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @NotNull
    @Column(name = "jhi_number", nullable = false)
    private Integer number;

    @NotNull
    @Column(name = "pay", nullable = false)
    private Boolean pay;

    @Column(name = "remark")
    private String remark;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("affiliates")
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Affiliate startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Affiliate endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getNumber() {
        return number;
    }

    public Affiliate number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Boolean isPay() {
        return pay;
    }

    public Affiliate pay(Boolean pay) {
        this.pay = pay;
        return this;
    }

    public void setPay(Boolean pay) {
        this.pay = pay;
    }

    public String getRemark() {
        return remark;
    }

    public Affiliate remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Company getCompany() {
        return company;
    }

    public Affiliate company(Company company) {
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
        Affiliate affiliate = (Affiliate) o;
        if (affiliate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), affiliate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Affiliate{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", number=" + getNumber() +
            ", pay='" + isPay() + "'" +
            ", remark='" + getRemark() + "'" +
            "}";
    }
}
