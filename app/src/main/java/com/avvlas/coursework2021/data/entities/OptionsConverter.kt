package com.avvlas.coursework2021.data.entities

import androidx.room.TypeConverter
import com.avvlas.coursework2021.model.options.Option
import com.avvlas.coursework2021.model.options.actions.Action
import com.avvlas.coursework2021.model.options.triggers.Trigger
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class OptionsConverter {

    private val gson = GsonBuilder()
        .registerTypeAdapter(Trigger::class.java, OptionAdapter<Trigger>())
        .registerTypeAdapter(Action::class.java, OptionAdapter<Action>())
        .create()

    @TypeConverter
    fun fromTriggers(triggers: ArrayList<Trigger>): String =
        gson.toJson(triggers, object : TypeToken<ArrayList<Trigger>>() {}.type)


    @TypeConverter
    fun toTriggers(json: String): ArrayList<Trigger> =
        gson.fromJson(json, object : TypeToken<ArrayList<Trigger>>() {}.type)

    @TypeConverter
    fun fromActions(actions: ArrayList<Action>): String =
        gson.toJson(actions, object : TypeToken<ArrayList<Action>>() {}.type)


    @TypeConverter
    fun toActions(json: String): ArrayList<Action> =
        gson.fromJson(json, object : TypeToken<ArrayList<Action>>() {}.type)
}

class OptionAdapter<T : Option> : JsonSerializer<T>,
    JsonDeserializer<T> {

    override fun serialize(
        src: T,
        typeOfSrc: Type?,
        context: JsonSerializationContext
    ): JsonElement {
        val result = JsonObject()
        result.add("type", JsonPrimitive(src.javaClass.canonicalName))
        result.add("properties", context.serialize(src, src.javaClass))
        return result
    }

    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext
    ): T {
        val jsonObject: JsonObject = json.asJsonObject
        val type: String = jsonObject["type"].asString
        val element = jsonObject["properties"]
        try {
            return context.deserialize(element, Class.forName(type))
        } catch (cnfe: ClassNotFoundException) {
            throw JsonParseException("Unknown element type: $type", cnfe)
        }
    }
}