package models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;

@XmlRootElement
@Embedded
public class Grade {
    private static final double[] noteScale = new double[]{2.0, 3.0, 3.5, 4.0, 4.5, 5.0};

    private long id;
    @NotNull
    private double value;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd", timezone = "CET")
    private Date date;

    @Reference
    private Student student;
    private long subjectId;

    @InjectLinks({
            @InjectLink(resource = rest.GradeForStudentEndpoint.class, method = "get", style = InjectLink.Style.ABSOLUTE,
                    bindings = {
                            @Binding(name = "index", value = "${instance.student.index}"),
                            @Binding(name = "subjectId", value = "${instance.subjectId}"),
                            @Binding(name = "id", value = "${instance.id}")
                    }, rel = "self"),
            @InjectLink(resource = rest.SubjectEndpoint.class, method = "get", style = InjectLink.Style.ABSOLUTE,
                    bindings = @Binding(name = "id", value = "${instance.subjectId}"), rel = "course"),
            @InjectLink(resource = rest.StudentEndpoint.class, method = "get", style = InjectLink.Style.ABSOLUTE,
                    bindings = @Binding(name = "index", value = "${instance.student.index}"), rel = "student")
    })
    @XmlElement(name = "link")
    @XmlElementWrapper(name = "links")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    private List<Link> links;

    public Grade() {
    }

    public Grade(double value, Date date, Student student, long subjectId) {
        initId();
        this.value = value;
        this.date = date;
        this.student = student;
        this.subjectId = subjectId;
    }

    public void initId() {
        Datastore datastore = DatastoreHandlerUtil.getInstance().getDatastore();
        Query<Counter> query = datastore.find(Counter.class, "_id", "gradeId");
        UpdateOperations<Counter> operation = datastore.createUpdateOperations(Counter.class).inc("seq");
        this.id = datastore.findAndModify(query, operation).getSeq();
    }

    public long getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    public Date getDate() {
        return date;
    }

    public Student getStudent() {
        return student;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setValue(double note) {
        this.value = note;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public boolean validateNote() {
        boolean result = false;

        for (double element : noteScale) {
            if (this.value == element) {
                result = true;
            }
        }

        return result;
    }

    public static boolean validateGivenNote(double note) {
        boolean result = false;

        for (double element : noteScale) {
            if (note == element) {
                result = true;
            }
        }

        return result;
    }
}
