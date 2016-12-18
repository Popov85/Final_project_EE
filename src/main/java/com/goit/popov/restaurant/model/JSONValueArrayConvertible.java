package com.goit.popov.restaurant.model;

import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * Created by Andrey on 12/17/2016.
 */
public interface JSONValueArrayConvertible {
    /**
     * Creates a JSON String array out of the filed data of an object
     * @See https://datatables.net/examples/server_side/simple.html
     * @return
     */
    ArrayNode toJSONArray();
}
