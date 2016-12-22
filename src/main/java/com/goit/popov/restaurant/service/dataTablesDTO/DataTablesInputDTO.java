package com.goit.popov.restaurant.service.dataTablesDTO;

/**
 * Created by Andrey on 12/17/2016.
 */
public class DataTablesInputDTO {

    private int draw;
    private int start;
    private int length;
    private int column;
    private String columnName;
    private String dir;
    private String search;

    public int getDraw() {
        return draw;
    }

    public DataTablesInputDTO setDraw(int draw) {
        this.draw = draw;
        return this;
    }

    public int getStart() {
        return start;
    }

    public DataTablesInputDTO setStart(int start) {
        this.start = start;
        return this;
    }

    public int getLength() {
        return length;
    }

    public DataTablesInputDTO setLength(int length) {
        this.length = length;
        return this;
    }

    public int getColumn() {
        return column;
    }

    public DataTablesInputDTO setColumn(int column) {
        this.column = column;
        return this;
    }

    public String getDir() {
        return dir;
    }

    public DataTablesInputDTO setDir(String dir) {
        this.dir = dir;
        return this;
    }

    public String getSearch() {
        return search;
    }

    public DataTablesInputDTO setSearch(String search) {
        this.search = search;
        return this;
    }

    public String getColumnName() {
        return columnName;
    }

    public DataTablesInputDTO setColumnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    @Override
    public String toString() {
        return "DataTablesInputDTO{" +
                "draw=" + draw +
                ", start=" + start +
                ", length=" + length +
                ", column=" + column +
                ", columnName='" + columnName + '\'' +
                ", dir='" + dir + '\'' +
                ", search='" + search + '\'' +
                '}';
    }
}
