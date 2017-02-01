package com.goit.popov.restaurant.controller.converters;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.goit.popov.restaurant.model.Menu;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by Andrey on 01.02.2017.
 */
public class ListToStringSerializer extends JsonSerializer<List<Object>> {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(ListToStringSerializer.class);

        @Override
        public void serialize(List<Object> value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
                //gen.writeObject(value.size());
                try {
                        for (Object o : value) {
                                int id = ((Menu) o).getId();
                                String name = ((Menu) o).getName();
                                String url = "<a href='/show_menu?id="+id+"'>"+name+"</a>";
                                gen.writeString(url);
                                String line = "&para;";
                                gen.writeString(line);
                        }
                } catch (IOException e) {
                        logger.error("ERROR: "+e.getMessage()+" cause: "+e.getClass());
                }

        }
}