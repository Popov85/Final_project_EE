package com.goit.popov.restaurant.service.dataTables;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        String data="";
        try {
            data = mapper.writeValueAsString(this.data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Exception in DataTablesOutputDTOCollectionWrapper class occurred...");
        }
        return "DataTablesOutputDTOCollectionWrapper{" +
                "data=" + data +
                '}';
    }
}
