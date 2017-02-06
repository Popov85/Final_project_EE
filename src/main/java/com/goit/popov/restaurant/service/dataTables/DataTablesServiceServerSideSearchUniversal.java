package com.goit.popov.restaurant.service.dataTables;

import java.util.List;

/**
 * Created by Andrey on 2/4/2017.
 */
public abstract class DataTablesServiceServerSideSearchUniversal<T> implements DataTablesSearchableUniversal {

        protected abstract long count(String[] params);

        protected abstract List<T> getAllItems(DataTablesInputExtendedDTO dt, String[] params);

        @Override
        public DataTablesOutputDTOUniversal getAll(DataTablesInputExtendedDTO dt, String[] params) {
                long recordsTotal = count(params);
                long recordsFiltered;
                List<T> data = getAllItems(dt, params);
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
