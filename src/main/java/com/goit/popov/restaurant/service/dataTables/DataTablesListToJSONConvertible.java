package com.goit.popov.restaurant.service.dataTables;

import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 1/2/2017.
 */
public interface DataTablesListToJSONConvertible<T> {
    /**
     *
     * @param items
     * @return
     */
    ArrayNode toJSON(List<T> items);
}
