package com.example.demo.validation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Map;

@Component
public class SchemaRegistry {

    private final Map<String, JsonSchema> schemas;

    public SchemaRegistry(ObjectMapper mapper) {
        try {
            JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012);

            JsonSchema invoicePayment = load(factory, mapper, "schema/InvoicePayment.schema.json");

            this.schemas = Map.of(
                "InvoicePayment", invoicePayment
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to load JSON Schemas", e);
        }
    }

    private JsonSchema load(JsonSchemaFactory factory, ObjectMapper mapper, String classpathPath) throws Exception {
        ClassPathResource res = new ClassPathResource(classpathPath);
        try (InputStream in = res.getInputStream()) {
            JsonNode node = mapper.readTree(in);   // read to JsonNode
            return factory.getSchema(node);        // correct overload
        }
    }

    public JsonSchema get(String name) {
        return schemas.get(name);
    }
}
