package com.goit.popov.restaurant.service.dataTables;

/**
 * Created by Andrey on 21.12.2016.
 */
public interface DataTablesSearchable<T> {
        /**
         * Server-side filtering, sorting and paging for items based on search criteria from Data Tables - based front-end
         * @param dt
         * @return adapted for Data Tables output object
         */
        DataTablesOutputDTOUniversal<T> getAll(DataTablesInputDTO dt);
}
