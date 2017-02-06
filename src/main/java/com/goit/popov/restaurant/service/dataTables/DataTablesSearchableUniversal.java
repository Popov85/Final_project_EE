package com.goit.popov.restaurant.service.dataTables;

/**
 * Created by Andrey on 2/4/2017.
 */
public interface DataTablesSearchableUniversal<T> {

        DataTablesOutputDTOUniversal<T> getAll(DataTablesInputExtendedDTO dt, String[] params);

}
