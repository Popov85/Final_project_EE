package com.goit.popov.restaurant.controller.converters;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.service.dataTables.DataTablesInputDTO;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by Andrey on 12/17/2016.
 */
public class DataTablesInputDTOHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(DataTablesInputDTOHandlerMethodArgumentResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(DataTablesInputDTO.class);
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
        return new DataTablesInputDTO()
                .setDraw(draw)
                .setStart(start)
                .setLength(length)
                .setColumn(column)
                .setColumnName(columnName)
                .setDir(dir)
                .setSearch(search);
    }
}
