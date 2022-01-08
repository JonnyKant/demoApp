package com.modeling.demo.configs;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.modeling.demo.domains.Product;
import com.modeling.demo.repos.ProductRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.regex.Matcher;

@Component
public class ProductDeserializer extends JsonDeserializer<Product> {
    @Autowired
    ProductRepos productRepos;

    @Override
    public Product deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if(jsonParser == null )return null;
        return productRepos.findById(jsonParser.getNumberValue().longValue()).get();

    }
}
