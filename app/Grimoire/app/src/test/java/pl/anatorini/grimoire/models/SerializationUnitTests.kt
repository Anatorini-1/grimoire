package pl.anatorini.grimoire.models

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

class SerializationUnitTests {
    @Test
    fun serialize_spell() {
        val spell: Spell = Spell(
            url = "",
            name = "Name",
            school = "School",
            duration = "Duration",
            level = 1,
            description = "Description",
            range = "",
            ritual = false
        )
        val serialized: String = Json.encodeToString(spell)
        val expected =
            "{\"url\":\"\",\"name\":\"Name\",\"description\":\"Description\",\"level\":1,\"ritual\":false,\"range\":1,\"duration\":\"Duration\",\"school\":\"School\"}"
        assertEquals(expected, serialized)
    }

    @Test
    fun deserialize_spell() {
        val spell: Spell = Spell(
            url = "",
            name = "Name",
            school = "School",
            duration = "Duration",
            level = 1,
            description = "Description",
            range ="",
            ritual = false
        )
        val serialized =
            "{\"url\":\"\",\"name\":\"Name\",\"description\":\"Description\",\"level\":1,\"ritual\":false,\"range\":1,\"duration\":\"Duration\",\"school\":\"School\"}"
        val deserialized = Json.decodeFromString<Spell>(serialized)
        assertEquals(spell, deserialized)
    }

    @Test
    fun serialize_paginated_response() {
        val item = Item(
            url = "",
            name = "name",
            value = 1f,
            weight = 1f,
            attackBonus = 0,
            damage = "damage",
            description = "description"
        )
        val paginatedResponse = PaginatedResponse<Item>(
            next = "next",
            previous = "previous",
            count = 1,
            results = arrayListOf(item)
        )
        val serialized = Json.encodeToString(paginatedResponse)
        val expected =
            "{\"count\":1,\"previous\":\"previous\",\"next\":\"next\",\"results\":[{\"url\":\"\",\"name\":\"name\",\"weight\":1.0,\"value\":1.0,\"attackBonus\":0,\"damage\":\"damage\",\"description\":\"description\"}]}"
        assertEquals(expected, serialized)
    }


    @Test
    fun deserialize_paginated_response() {
        val serialized =
            "{\"count\":1,\"previous\":\"previous\",\"next\":\"next\",\"results\":[{\"url\":\"\",\"name\":\"name\",\"weight\":1.0,\"value\":1.0,\"attackBonus\":0,\"damage\":\"damage\",\"description\":\"description\"}]}"

        val expected = PaginatedResponse<Item>(
            next = "next",
            previous = "previous",
            count = 1,
            results = arrayListOf(
                Item(
                    url = "",
                    name = "name",
                    value = 1f,
                    weight = 1f,
                    attackBonus = 0,
                    damage = "damage",
                    description = "description"
                )
            )
        )
        val deserialized = Json.decodeFromString<PaginatedResponse<Item>>(serialized)
        assertEquals(expected, deserialized)
    }
}