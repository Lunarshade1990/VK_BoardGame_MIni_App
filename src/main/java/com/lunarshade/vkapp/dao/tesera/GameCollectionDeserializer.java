package com.lunarshade.vkapp.dao.tesera;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class GameCollectionDeserializer extends StdDeserializer<Map<String, Integer>> {

    public GameCollectionDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    @Override
    public Map<String, Integer> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        JsonNode collections = node.get("collections");
        Map<String, Integer> collectionsInfo = new HashMap<>();
        if (collections.isArray()) {
            collections.forEach(collection -> {
                collectionsInfo.put(collection.get("collectionType").asText(), collection.get("gamesTotal").asInt());
            });
        }
        return collectionsInfo;
    }
}
