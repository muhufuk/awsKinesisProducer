package com.aws.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public final class Json
{
    private static final Gson GSON = new GsonBuilder().create();

    private JsonObject json = null;

    private Json()
    {
        this.json = new JsonObject();
    }

    public Json prop(final String name, final JsonObject prop)
    {
        if (prop != null)
        {
            json.add(name, prop);
        }
        return this;
    }

    public JsonObject build()
    {
        return json;
    }

    @Override
    public String toString()
    {
        return print(json);
    }

    public static Json of()
    {
        return new Json();
    }

    public static String print(final JsonObject json)
    {
        return GSON.toJson(json);
    }
}
