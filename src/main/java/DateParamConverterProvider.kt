import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*
import javax.ws.rs.WebApplicationException
import javax.ws.rs.ext.ParamConverter
import javax.ws.rs.ext.ParamConverterProvider
import javax.ws.rs.ext.Provider

@Provider
class DateParamConverterProvider(private val format: String) : ParamConverterProvider {

    override fun <T> getConverter(rawType: Class<T>,
                                  genericType: Type,
                                  annotations: Array<Annotation>): ParamConverter<T>? {

        return if (rawType != Date::class.java) {
            null
        } else object : ParamConverter<Date> {

            override fun fromString(value: String?): Date? {
                if (value == null || value.isEmpty())
                    return null

                val formatter = SimpleDateFormat(format)
                try {
                    return formatter.parse(value)
                } catch (ex: Exception) {
                    throw WebApplicationException("Bad formatted date", 400)
                }

            }

            override fun toString(date: Date): String {
                return SimpleDateFormat(format).format(date)
            }
        } as ParamConverter<T>

    }
}