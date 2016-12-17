package com.goit.popov.restaurant.service.DataTablesDTO;

import com.fasterxml.jackson.databind.node.ArrayNode;
/**
 * Created by Andrey on 12/17/2016.
 */
public class DataTablesOutputDTO {

    private int draw;
    private long recordsTotal;
    private long recordsFiltered;
    private ArrayNode data;

    public int getDraw() {
        return draw;
    }

    public DataTablesOutputDTO setDraw(int draw) {
        this.draw = draw;
        return this;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public DataTablesOutputDTO setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
        return this;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public DataTablesOutputDTO setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
        return this;
    }

    public ArrayNode getData() {
        return data;
    }

    public DataTablesOutputDTO setData(ArrayNode data) {
        this.data = data;
        return this;
    }
}
