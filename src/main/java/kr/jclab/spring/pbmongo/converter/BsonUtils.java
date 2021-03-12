package kr.jclab.spring.pbmongo.converter;

import com.google.gson.*;
import com.google.gson.stream.JsonToken;
import org.bson.types.Binary;

import java.util.List;
import java.util.Map;

public class BsonUtils {
    public static JsonToken findTypeAdapter(Object o) {
        if (o == null) {
            return JsonToken.NULL;
        } else if (o instanceof String) {
            return JsonToken.STRING;
        }else if (o instanceof Number) {
            return JsonToken.NUMBER;
        } if (o instanceof Boolean) {
            return JsonToken.BOOLEAN;
        } if (o instanceof List) {
            return JsonToken.BEGIN_ARRAY;
        }
        return JsonToken.BEGIN_OBJECT;
    }

    public static JsonElement toJsonElement(Object o) {
        if (o instanceof Binary) {
            Binary v = (Binary)o;
            return new JsonBinary(v.getData());
        }
        switch (findTypeAdapter(o)) {
            case STRING:
                return new JsonPrimitive((String)o);
            case NUMBER:
                return new JsonPrimitive((Number)o);
            case BOOLEAN:
                return new JsonPrimitive((Boolean)o);
            case NULL:
                return JsonNull.INSTANCE;
            case BEGIN_ARRAY:
                JsonArray array = new JsonArray();
                for (Object item : (List)o) {
                    array.add(toJsonElement(item));
                }
                return array;
            case BEGIN_OBJECT:
                JsonObject object = new JsonObject();
                for (Object item : ((Map)o).entrySet()) {
                    Map.Entry<?, ?> entry = (Map.Entry<?, ?>)item;
                    object.add(String.valueOf(entry.getKey()), toJsonElement(entry.getValue()));
                }
                return object;
            case END_DOCUMENT:
            case NAME:
            case END_OBJECT:
            case END_ARRAY:
            default:
                throw new IllegalArgumentException();
        }
    }
}
