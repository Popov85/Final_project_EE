package com.goit.popov.restaurant.service.dataTables;

import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * Created by Andrey on 16.01.2017.
 */
public interface DataTablesObjectToJSONConvertible<T> {

        ArrayNode toJSON(T t);
}
