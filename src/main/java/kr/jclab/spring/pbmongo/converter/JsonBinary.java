package kr.jclab.spring.pbmongo.converter;

import com.google.gson.JsonElement;

public class JsonBinary extends JsonElement {
    private final byte[] value;

    public JsonBinary(byte[] bytes) {
        this.value = bytes;
    }

    /**
     * Returns the same value as primitives are immutable.
     * @since 2.8.2
     */
    @Override
    public JsonBinary deepCopy() {
        return this;
    }

    public byte[] getValue() {
        return value;
    }
}
