package com.goit.popov.restaurant.service.dataTables;

import java.util.Collection;

/**
 * Created by Andrey on 12/22/2016.
 */
public class DataTablesOutputDTOListWrapper<T> {

    private Collection<T> data;

    public Collection<T> getData() {
        return data;
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }
}
