package common.utils.utilcode.util


import android.util.Log

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class JsonUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    companion object {

        private val TYPE_BOOLEAN : Byte = 0x00
        private val TYPE_INT : Byte = 0x01
        private val TYPE_LONG : Byte = 0x02
        private val TYPE_DOUBLE : Byte = 0x03
        private val TYPE_STRING : Byte = 0x04
        private val TYPE_JSON_OBJECT : Byte = 0x05
        private val TYPE_JSON_ARRAY : Byte = 0x06

        @JvmOverloads
        fun getBoolean(jsonObject : JSONObject , key : String , defaultValue : Boolean = false) : Boolean {
            return getValueByType(jsonObject , key , defaultValue , TYPE_BOOLEAN)
        }

        @JvmOverloads
        fun getBoolean(json : String , key : String , defaultValue : Boolean = false) : Boolean {
            return getValueByType(json , key , defaultValue , TYPE_BOOLEAN)
        }

        @JvmOverloads
        fun getInt(jsonObject : JSONObject , key : String , defaultValue : Int = - 1) : Int {
            return getValueByType(jsonObject , key , defaultValue , TYPE_INT)
        }

        @JvmOverloads
        fun getInt(json : String , key : String , defaultValue : Int = - 1) : Int {
            return getValueByType(json , key , defaultValue , TYPE_INT)
        }

        @JvmOverloads
        fun getLong(jsonObject : JSONObject , key : String , defaultValue : Long = - 1) : Long {
            return getValueByType(jsonObject , key , defaultValue , TYPE_LONG)
        }

        @JvmOverloads
        fun getLong(json : String , key : String , defaultValue : Long = - 1) : Long {
            return getValueByType(json , key , defaultValue , TYPE_LONG)
        }

        @JvmOverloads
        fun getDouble(jsonObject : JSONObject , key : String , defaultValue : Double = - 1.0) : Double {
            return getValueByType(jsonObject , key , defaultValue , TYPE_DOUBLE)
        }

        @JvmOverloads
        fun getDouble(json : String , key : String , defaultValue : Double = - 1.0) : Double {
            return getValueByType(json , key , defaultValue , TYPE_DOUBLE)
        }

        @JvmOverloads
        fun getString(jsonObject : JSONObject , key : String , defaultValue : String = "") : String {
            return getValueByType(jsonObject , key , defaultValue , TYPE_STRING)
        }

        @JvmOverloads
        fun getString(json : String , key : String , defaultValue : String = "") : String {
            return getValueByType(json , key , defaultValue , TYPE_STRING)
        }

        fun getJSONObject(jsonObject : JSONObject , key : String , defaultValue : JSONObject) : JSONObject {
            return getValueByType(jsonObject , key , defaultValue , TYPE_JSON_OBJECT)
        }

        fun getJSONObject(json : String , key : String , defaultValue : JSONObject) : JSONObject {
            return getValueByType(json , key , defaultValue , TYPE_JSON_OBJECT)
        }

        fun getJSONArray(jsonObject : JSONObject , key : String , defaultValue : JSONArray) : JSONArray {
            return getValueByType(jsonObject , key , defaultValue , TYPE_JSON_ARRAY)
        }

        fun getJSONArray(json : String , key : String , defaultValue : JSONArray) : JSONArray {
            return getValueByType(json , key , defaultValue , TYPE_JSON_ARRAY)
        }

        private fun <T> getValueByType(jsonObject : JSONObject? , key : String? , defaultValue : T , type : Byte) : T {
            if (jsonObject == null || key == null || key.length == 0) {
                return defaultValue
            }
            try {
                val ret : Any
                if (type == TYPE_BOOLEAN) {
                    ret = jsonObject.getBoolean(key)
                } else if (type == TYPE_INT) {
                    ret = jsonObject.getInt(key)
                } else if (type == TYPE_LONG) {
                    ret = jsonObject.getLong(key)
                } else if (type == TYPE_DOUBLE) {
                    ret = jsonObject.getDouble(key)
                } else if (type == TYPE_STRING) {
                    ret = jsonObject.getString(key)
                } else if (type == TYPE_JSON_OBJECT) {
                    ret = jsonObject.getJSONObject(key)
                } else if (type == TYPE_JSON_ARRAY) {
                    ret = jsonObject.getJSONArray(key)
                } else {
                    return defaultValue
                }

                return ret as T
            } catch (e : JSONException) {
                Log.e("JsonUtils" , "getValueByType: " , e)
                return defaultValue
            }

        }

        private fun <T> getValueByType(json : String? , key : String? , defaultValue : T , type : Byte) : T {
            if (json == null || json.length == 0 || key == null || key.length == 0) {
                return defaultValue
            }
            try {
                return getValueByType(JSONObject(json) , key , defaultValue , type)
            } catch (e : JSONException) {
                Log.e("JsonUtils" , "getValueByType: " , e)
                return defaultValue
            }

        }

        @JvmOverloads
        fun formatJson(json : String , indentSpaces : Int = 4) : String {
            try {
                var i = 0
                val len = json.length
                while (i < len) {
                    val c = json[i]
                    if (c == '{') {
                        return JSONObject(json).toString(indentSpaces)
                    } else if (c == '[') {
                        return JSONArray(json).toString(indentSpaces)
                    } else if (! Character.isWhitespace(c)) {
                        return json
                    }
                    i ++
                }
            } catch (e : JSONException) {
                e.printStackTrace()
            }

            return json
        }
    }
}
