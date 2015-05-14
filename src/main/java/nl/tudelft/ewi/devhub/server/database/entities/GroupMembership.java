package nl.tudelft.ewi.devhub.server.database.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@SuppressWarnings("serial")
@Table(name = "group_memberships")
@ToString(of={"group", "user"})
@EqualsAndHashCode(of={"group", "user"})
public class GroupMembership implements Serializable {

	@Id
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "course_id"),
		@JoinColumn(name = "group_number")
	})
	private Group group;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
}
