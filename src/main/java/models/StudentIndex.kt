package models

import org.bson.types.ObjectId
import org.mongodb.morphia.annotations.*
import javax.xml.bind.annotation.XmlTransient

@Entity("student_index")
@Indexes(
        Index(value = "index", fields = [Field("id")])
)
data class StudentIndex(
        @Id
        @XmlTransient
        var id: ObjectId = ObjectId(),
        var index: Int = 0
)