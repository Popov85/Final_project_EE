package com.goit.popov.restaurant.service.dataTables;

import java.util.List;

/**
 * Created by Andrey on 02.02.2017.
 */
public abstract class DataTablesServiceServerSideSearch<T> implements DataTablesSearchable {

        protected abstract long count();

        protected abstract List<T> getAllItems(DataTablesInputExtendedDTO dt);

        @Override
        public DataTablesOutputDTOUniversal<T> getAll(DataTablesInputExtendedDTO dt) {
                long recordsTotal = count();
                long recordsFiltered;
                List<T> data = getAllItems(dt);
                if (!dt.getColumnSearch().isEmpty()) {
                        recordsFiltered = data.size();
                } else {
                        recordsFiltered=recordsTotal;
                }
                return new DataTablesOutputDTOUniversal<T>()
                        .setDraw(dt.getDraw())
                        .setRecordsTotal(recordsTotal)
                        .setRecordsFiltered(recordsFiltered)
                        .setData(data);
        }
}
