package nl.tudelft.ewi.devhub.server.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@Entity
@Table(name = "build_results")
public class BuildResult {

	public static BuildResult newBuildResult(Group group, String commit) {
		BuildResult result = new BuildResult();
		result.setRepository(group);
		result.setCommitId(commit);
		result.setSuccess(null);
		result.setLog(null);
		return result;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "course_id", referencedColumnName = "course_id", insertable = false, updatable = false),
		@JoinColumn(name = "group_number", referencedColumnName = "group_number", insertable = false, updatable = false)
	})
	private Group repository;

	@NotEmpty
	@Column(name = "commit_id")
	private String commitId;

	@Column(name = "success")
	private Boolean success;

	@Type(type = "org.hibernate.type.TextType")
	@Column(name = "log")
	private String log;

}
