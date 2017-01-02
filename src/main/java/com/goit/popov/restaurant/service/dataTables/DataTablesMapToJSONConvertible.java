package com.goit.popov.restaurant.service.dataTables;

import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.Map;

/**
 * Created by Andrey on 12/25/2016.
 */
public interface DataTablesMapToJSONConvertible<T, Q> {

    /**
     * Converts Map object into a an appropriate form for Data Tables front-end
     * @param items
     * @return
     */
    ArrayNode toJSON(Map<T, Q> items);
}
