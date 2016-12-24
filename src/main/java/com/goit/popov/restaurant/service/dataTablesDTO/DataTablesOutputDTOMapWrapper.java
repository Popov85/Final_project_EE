package com.goit.popov.restaurant.service.dataTablesDTO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.Map;

/**
 * Created by Andrey on 12/24/2016.
 */
public class DataTablesOutputDTOMapWrapper {

    private ArrayNode data;

    public ArrayNode getData() {
        return data;
    }

    public void setData(ArrayNode data) {
        this.data = data;
    }
}
