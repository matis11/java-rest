package models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.bson.types.ObjectId;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;

@Entity("students")
@XmlRootElement
public class Student {
    @XmlTransient
    @Id
    private ObjectId id;

    @Indexed(name = "index", unique = true)
    private long index;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd", timezone = "CET")
    private Date dateOfBirth;

    @InjectLinks({
            @InjectLink(resource = rest.StudentEndpoint.class, method = "get", style = InjectLink.Style.ABSOLUTE,
                    bindings = @Binding(name = "index", value = "${instance.index}"), rel = "self")
    })
    @XmlElement(name = "link")
    @XmlElementWrapper(name = "links")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    private List<Link> links;

    public Student() {
    }

    public Student(String name, String surname, Date dateOfBirth) {
        initializeIndex();
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
    }

    public void initializeIndex() {
        Datastore datastore = DatastoreHandlerUtil.getInstance().getDatastore();
        Query<Counter> query = datastore.find(Counter.class, "_id", "studentIndex");
        UpdateOperations<Counter> operation = datastore.createUpdateOperations(Counter.class).inc("seq");
        this.index = datastore.findAndModify(query, operation).getSeq();
    }

    @XmlTransient
    public ObjectId getId() {
        return id;
    }

    public long getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
