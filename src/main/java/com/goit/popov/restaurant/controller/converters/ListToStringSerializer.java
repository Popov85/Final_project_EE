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
                try {
                        if (!value.isEmpty()) {
                                // TODO {insert a link to a list of menus}
                                if (value.size()>3) {gen.writeString("Many"); return;}
                                StringBuilder sb = new StringBuilder();
                                for (Object o : value) {
                                        int id = ((Menu) o).getId();
                                        String name = ((Menu) o).getName();
                                        String url = "<a href='/all/show_dishes?id=" + id + "'>" + name + "</a>";
                                        sb.append(url);
                                        sb.append("<br>");
                                }
                                gen.writeString(sb.toString());
                        } else {
                                gen.writeString("No");
                        }
                } catch (IOException e) {
                        logger.error("ERROR: "+e.getMessage()+" cause: "+e.getClass());
                }

        }
}
