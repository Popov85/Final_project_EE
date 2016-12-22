package com.goit.popov.restaurant.service.dataTablesDTO;

import java.util.List;

/**
 * Created by Andrey on 12/22/2016.
 */
public class DataTablesOutputDTOArrayWrapper<T> {

    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
