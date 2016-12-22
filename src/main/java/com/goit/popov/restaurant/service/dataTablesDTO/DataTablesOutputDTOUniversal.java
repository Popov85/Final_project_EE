package com.goit.popov.restaurant.service.dataTablesDTO;

import java.util.List;

/**
 * Created by Andrey on 12/17/2016.
 */
public class DataTablesOutputDTOUniversal<T> {

    private int draw;
    private long recordsTotal;
    private long recordsFiltered;
    private List<T> data;

    public int getDraw() {
        return draw;
    }

    public DataTablesOutputDTOUniversal<T> setDraw(int draw) {
        this.draw = draw;
        return this;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public DataTablesOutputDTOUniversal<T> setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
        return this;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public DataTablesOutputDTOUniversal<T> setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
        return this;
    }

    public List<T> getData() {
        return data;
    }

    public DataTablesOutputDTOUniversal<T> setData(List<T> data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "DataTablesOutputDTOUniversal{" +
                "draw=" + draw +
                ", recordsTotal=" + recordsTotal +
                ", recordsFiltered=" + recordsFiltered +
                ", data=" + data +
                '}';
    }
}
