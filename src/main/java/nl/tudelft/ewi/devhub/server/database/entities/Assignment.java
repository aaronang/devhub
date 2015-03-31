package nl.tudelft.ewi.devhub.server.database.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by jgmeligmeyling on 04/03/15.
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
