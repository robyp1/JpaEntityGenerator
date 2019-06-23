package it.gen.formats;

import java.util.Objects;

public class FieldType {

    private final Class fieldType;
    private final String fieldName;
    private final String columnName; //da passare oppure usare ColumnExtractor.extractMatches(fieldName)

    public FieldType(Class fieldType, String fieldName, String columnName) {
        this.fieldType = fieldType;
        this.fieldName = fieldName;
        this.columnName = columnName;
    }

    public Class getFieldType() {
        return fieldType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getColumnName() {
        return columnName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldType fieldType1 = (FieldType) o;
        return Objects.equals(fieldType, fieldType1.fieldType) &&
                Objects.equals(fieldName, fieldType1.fieldName) &&
                Objects.equals(columnName, fieldType1.columnName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldType, fieldName, columnName);
    }

    @Override
    public String toString() {
        return "FieldType{" +
                "fieldType=" + fieldType +
                ", fieldName='" + fieldName + '\'' +
                ", columnName='" + columnName + '\'' +
                '}';
    }
}
