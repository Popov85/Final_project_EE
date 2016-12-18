package com.goit.popov.restaurant.model;

import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.List;

/**
 * Created by Andrey on 12/17/2016.
 */
public interface JSONArrayOfArraysConvertible<T> {
    /**
     * Creates a JSON array or arrays of strings according to DataTables specifications
     * @See https://datatables.net/examples/server_side/simple.html
     * @return
     */
    ArrayNode toJSONArray(List<T> list);
}
