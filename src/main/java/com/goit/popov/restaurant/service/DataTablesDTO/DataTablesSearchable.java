package com.goit.popov.restaurant.service.dataTablesDTO;

/**
 * Created by Andrey on 21.12.2016.
 */
public interface DataTablesSearchable {
        /**
         * Filtering, sorting and paging for items based on search criteria from Data Tables - based front-end
         * @param dt
         * @return adapted for Data Tables output object
         */
        DataTablesOutputDTO getAll(DataTablesInputDTO dt);
}
