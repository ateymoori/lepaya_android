package com.lepaya.data.utils

import com.google.gson.Gson
import com.lepaya.data.models.TrainerResponse
import com.lepaya.data.utils.GsonUtils.toObjectByGson
import org.junit.Before
import org.mockito.MockitoAnnotations
import org.junit.Assert.*
import org.junit.Test
import java.io.InputStreamReader

class GsonUtilsTest {

    lateinit var gson: Gson

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        gson = GsonUtils.gson
    }

    @Test
    fun `Convert String to Json`() {
        val profileString =
            InputStreamReader(this.javaClass.classLoader?.getResourceAsStream("trainer_sample_api.json"))
        val jsonObject = profileString.toObjectByGson<TrainerResponse>()

        assertEquals(
            jsonObject.name.first,
            "Bertie"
        )
        assertEquals(
            jsonObject.name.last,
            "Williamson"
        )
        assertEquals(
            jsonObject.picture,
            "https://upload.wikimedia.org/wikipedia/commons/f/f7/Henry_Winkler_Fonzie_1977.JPG"
        )
    }

}