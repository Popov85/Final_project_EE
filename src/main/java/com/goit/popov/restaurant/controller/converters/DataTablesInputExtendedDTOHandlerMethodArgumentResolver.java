package com.goit.popov.restaurant.controller.converters;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.service.dataTables.DataTablesInputExtendedDTO;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrey on 1/3/2017.
 */
public class DataTablesInputExtendedDTOHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(DataTablesInputExtendedDTOHandlerMethodArgumentResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(DataTablesInputExtendedDTO.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        int draw = Integer.parseInt(webRequest.getParameter("draw"));
        int start= Integer.parseInt(webRequest.getParameter("start"));
        int length = Integer.parseInt(webRequest.getParameter("length"));
        int column = Integer.parseInt(webRequest.getParameter("order[0][column]"));
        String columnName = webRequest.getParameter("columns["+column+"][name]");
        String dir = webRequest.getParameter("order[0][dir]");
        String search = webRequest.getParameter("search[value]");
        Map<String, String> columnSearch = new HashMap<>();
        try {
            int counter = 0;
            while (webRequest.getParameterMap().containsKey("columns["+counter+"][search][value]")) {
                String nextColumnName = webRequest.getParameter("columns["+counter+"][name]");
                String nextSearchWord = webRequest.getParameter("columns["+counter+"][search][value]");
                if (!nextSearchWord.equals("")) columnSearch.put(nextColumnName, nextSearchWord);
                counter++;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info("Map size is: "+columnSearch.size());
        DataTablesInputExtendedDTO dto = new DataTablesInputExtendedDTO();
        dto.setDraw(draw);
        dto.setStart(start);
        dto.setLength(length);
        dto.setColumn(column);
        dto.setColumnName(columnName);
        dto.setDir(dir);
        dto.setSearch(search);
        dto.setColumnSearch(columnSearch);
        return dto;
    }
}
