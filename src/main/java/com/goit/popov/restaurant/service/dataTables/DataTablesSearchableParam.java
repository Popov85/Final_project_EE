package com.goit.popov.restaurant.service.dataTables;

/**
 * Created by Andrey on 1/14/2017.
 */
public interface DataTablesSearchableParam<T> extends DataTablesSearchable {
        /**
         * Includes an additional filtering param: Id of an employee (for Orders, PreparedDishes,)
         * Server-side filtering, sorting and paging for items based on search criteria from Data Tables-based front-end
         * @param dt
         * @return adapted for Data Tables output object
         */
        DataTablesOutputDTOUniversal<T> getAll(DataTablesInputExtendedDTO dt, int empId);
}
