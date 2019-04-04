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
 * Criteria class for the ColumnModule entity. This class is used in ColumnModuleResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /column-modules?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ColumnModuleCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tableName;

    private StringFilter columnName;

    private BooleanFilter activeInd;

    private StringFilter dataType;

    private StringFilter description;

    private StringFilter defaultValue;

    private LongFilter moduleId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTableName() {
        return tableName;
    }

    public void setTableName(StringFilter tableName) {
        this.tableName = tableName;
    }

    public StringFilter getColumnName() {
        return columnName;
    }

    public void setColumnName(StringFilter columnName) {
        this.columnName = columnName;
    }

    public BooleanFilter getActiveInd() {
        return activeInd;
    }

    public void setActiveInd(BooleanFilter activeInd) {
        this.activeInd = activeInd;
    }

    public StringFilter getDataType() {
        return dataType;
    }

    public void setDataType(StringFilter dataType) {
        this.dataType = dataType;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(StringFilter defaultValue) {
        this.defaultValue = defaultValue;
    }

    public LongFilter getModuleId() {
        return moduleId;
    }

    public void setModuleId(LongFilter moduleId) {
        this.moduleId = moduleId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ColumnModuleCriteria that = (ColumnModuleCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(tableName, that.tableName) &&
            Objects.equals(columnName, that.columnName) &&
            Objects.equals(activeInd, that.activeInd) &&
            Objects.equals(dataType, that.dataType) &&
            Objects.equals(description, that.description) &&
            Objects.equals(defaultValue, that.defaultValue) &&
            Objects.equals(moduleId, that.moduleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        tableName,
        columnName,
        activeInd,
        dataType,
        description,
        defaultValue,
        moduleId
        );
    }

    @Override
    public String toString() {
        return "ColumnModuleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tableName != null ? "tableName=" + tableName + ", " : "") +
                (columnName != null ? "columnName=" + columnName + ", " : "") +
                (activeInd != null ? "activeInd=" + activeInd + ", " : "") +
                (dataType != null ? "dataType=" + dataType + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (defaultValue != null ? "defaultValue=" + defaultValue + ", " : "") +
                (moduleId != null ? "moduleId=" + moduleId + ", " : "") +
            "}";
    }

}
