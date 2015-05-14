package nl.tudelft.ewi.devhub.server.database.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.google.common.collect.Sets;

@Data
@Entity
@Table(name = "groups")
@IdClass(Group.GroupId.class)
@ToString(of = { "course", "groupNumber" })
@EqualsAndHashCode(of = { "course", "groupNumber" })
public class Group {

	@Data
	@EqualsAndHashCode
	public static class GroupId implements Serializable {

		private Course course;
		private Long groupNumber;

	}

	@Id
	@NotNull
	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;

	@Id
	@NotNull
	@Column(name = "group_number")
	private Long groupNumber;
	
	@Column(name = "build_timeout")
	private Integer buildTimeout;

	@OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
	private Set<GroupMembership> memberships;

	@NotNull
	@Column(name = "repository_name")
	private String repositoryName;
	
	public Set<User> getMembers() {
		Set<User> members = Sets.newHashSet();
		for (GroupMembership membership : memberships) {
			members.add(membership.getUser());
		}
		return members;
	}

	public String getGroupName() {
		StringBuilder builder = new StringBuilder();
		builder.append(course.getCode());
		builder.append(" - ");
		builder.append(course.getName());
		builder.append(" (Group #");
		builder.append(groupNumber);
		builder.append(")");
		return builder.toString();
	}

	public Integer getBuildTimeout() {
		return buildTimeout != null ? buildTimeout : course.getBuildTimeout();
	}
	
}
