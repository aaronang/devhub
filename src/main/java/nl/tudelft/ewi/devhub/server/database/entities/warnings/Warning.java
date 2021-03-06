package nl.tudelft.ewi.devhub.server.database.entities.warnings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nl.tudelft.ewi.devhub.server.database.entities.Group;
import nl.tudelft.ewi.devhub.server.web.templating.Translator;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The base entity for group warnings.
 *
 * @author Jan-Willem Gmelig Meyling
 */
@Data
@Entity
@Table(name = "group_warning")
@EqualsAndHashCode(of={"repository"})
@DiscriminatorColumn(name = "warning_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Warning {

    @Id
    @Column(name = "id")
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "repository_name", referencedColumnName = "repository_name")
    private Group repository;

    /**
     * Retrieve a descriptive message for the warning
     *
     * @param translator translation context
     * @return the warning message
     */
    @JsonProperty("message")
    public abstract String getMessage(Translator translator);

}
