package com.goit.popov.restaurant.service.dataTables;

import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * Created by Andrey on 12/24/2016.
 */
public class DataTablesOutputDTOCollectionWrapper {

    private ArrayNode data;

    public ArrayNode getData() {
        return data;
    }

    public void setData(ArrayNode data) {
        this.data = data;
    }
}
