package it.gen.formats;

public enum SourceTypeEnum {

    FIELD_LIST("_field.list"),
    TYPE_LIST("_type.list"),
    COLUMN_LIST("_column.list");


    private final String fileSuffix;

    SourceTypeEnum(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }
}
