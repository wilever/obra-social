package com.mycompany.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Module entity. This class is used in ModuleResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /modules?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ModuleCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private LongFilter moduleId;

    private LongFilter moduleId;

    private LongFilter moduleId;

    private LongFilter moduleTypeId;

    private LongFilter tagId;

    private LongFilter companyId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getModuleId() {
        return moduleId;
    }

    public void setModuleId(LongFilter moduleId) {
        this.moduleId = moduleId;
    }

    public LongFilter getModuleId() {
        return moduleId;
    }

    public void setModuleId(LongFilter moduleId) {
        this.moduleId = moduleId;
    }

    public LongFilter getModuleId() {
        return moduleId;
    }

    public void setModuleId(LongFilter moduleId) {
        this.moduleId = moduleId;
    }

    public LongFilter getModuleTypeId() {
        return moduleTypeId;
    }

    public void setModuleTypeId(LongFilter moduleTypeId) {
        this.moduleTypeId = moduleTypeId;
    }

    public LongFilter getTagId() {
        return tagId;
    }

    public void setTagId(LongFilter tagId) {
        this.tagId = tagId;
    }

    public LongFilter getCompanyId() {
        return companyId;
    }

    public void setCompanyId(LongFilter companyId) {
        this.companyId = companyId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ModuleCriteria that = (ModuleCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(moduleId, that.moduleId) &&
            Objects.equals(moduleId, that.moduleId) &&
            Objects.equals(moduleId, that.moduleId) &&
            Objects.equals(moduleTypeId, that.moduleTypeId) &&
            Objects.equals(tagId, that.tagId) &&
            Objects.equals(companyId, that.companyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        moduleId,
        moduleId,
        moduleId,
        moduleTypeId,
        tagId,
        companyId
        );
    }

    @Override
    public String toString() {
        return "ModuleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (moduleId != null ? "moduleId=" + moduleId + ", " : "") +
                (moduleId != null ? "moduleId=" + moduleId + ", " : "") +
                (moduleId != null ? "moduleId=" + moduleId + ", " : "") +
                (moduleTypeId != null ? "moduleTypeId=" + moduleTypeId + ", " : "") +
                (tagId != null ? "tagId=" + tagId + ", " : "") +
                (companyId != null ? "companyId=" + companyId + ", " : "") +
            "}";
    }

}
