package com.goit.popov.restaurant.service.dataTables;

import java.util.Map;

/**
 * Class DTO extends DataTablesInputDTO and allows to pass a map with names
 * of columns and search words associated with those names
 * Created by Andrey on 1/3/2017.
 */
public class DataTablesInputExtendedDTO extends DataTablesInputDTO {

    private Map<String, String> columnSearch;

    public Map<String, String> getColumnSearch() {
        return columnSearch;
    }

    public DataTablesInputExtendedDTO setColumnSearch(Map<String, String> columnSearch) {
        this.columnSearch = columnSearch;
        return this;
    }

    @Override
    public String toString() {
        return "DataTablesInputExtendedDTO{" +
                "draw=" + this.getDraw() +
                ", start=" + this.getStart() +
                ", length=" + this.getLength() +
                ", column=" + this.getColumn() +
                ", columnName='" + this.getColumnName() + '\'' +
                ", dir='" + this.getDir() + '\'' +
                ", search='" + this.getSearch() + '\'' +
                ", columnSearch=" + columnSearch +
                '}';
    }
}
