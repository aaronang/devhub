package nl.tudelft.ewi.devhub.server.database.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Jan-Willem Gmelig Meyling
 */
@Data
@Entity
@Table(name= "assignments")
@EqualsAndHashCode(of={"course", "assignmentId"})
public class Assignment implements Serializable {

    @Id
    @NotNull
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Id
    @NotNull(message = "assignment.number.should-be-given")
    @Column(name = "assignment_id")
    private Long assignmentId;

    @NotEmpty(message = "assignment.name.should-be-given")
    @Column(name = "name")
    private String name;

    @Lob
    @Nullable
    @Column(name = "summary")
    private String summary;

    @Nullable
    @Column(name="due_date")
    private String dueDate;

}
