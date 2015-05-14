package nl.tudelft.ewi.devhub.server.database.controllers;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import nl.tudelft.ewi.devhub.server.database.entities.Course;
import nl.tudelft.ewi.devhub.server.database.entities.Group;
import nl.tudelft.ewi.devhub.server.database.entities.GroupMembership;
import nl.tudelft.ewi.devhub.server.database.entities.User;
import org.jukito.JukitoRunner;
import org.jukito.UseModules;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;
import java.math.BigInteger;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.contains;

/**
 * @author Jan-Willem Gmelig Meyling
 */
@RunWith(JukitoRunner.class)
@UseModules(TestDatabaseModule.class)
public class GroupMembershipsTest {

    @Inject
    private Random random;

    @Inject
    private Groups groups;

    @Inject
    private Courses courses;

    @Inject
    private Users users;

    @Inject
    private GroupMemberships groupMemberships;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Group group;
    private User user;

    @Before
    public void setupTest() {
        group = createGroup();
        user = createUser();
    }

    @Test
    public void testPersistGroupMembership() {
        GroupMembership groupMembership = new GroupMembership();
        groupMembership.setGroup(group);
        groupMembership.setUser(user);
        groupMemberships.persist(groupMembership);
        assertThat(groupMemberships.listParticipating(user), contains(group));
        assertThat(groupMemberships.ofGroup(group), contains(groupMembership));
    }

    @Test
    public void testPersistDuplicateGroupMembership() {
        testPersistGroupMembership();
        expectedException.expect(EntityExistsException.class);
        testPersistGroupMembership();
    }

    @Test
    public void testPersistDuplicateCourseMembership() {
        testPersistGroupMembership();
        GroupMembership groupMembership = new GroupMembership();
        groupMembership.setUser(user);
        groupMembership.setGroup(createGroup());
        expectedException.expect(PersistenceException.class);
        groupMemberships.persist(groupMembership);
    }

    protected Group createGroup() {
        Group group = new Group();
        Course course = getTestCourse();
        group.setGroupNumber(random.nextLong());
        group.setCourse(course);
        group.setRepositoryName(String.format("courses/%s/group-%s", group.getGroupNumber(), course.getName()));
        return groups.persist(group);
    }

    protected Course getTestCourse() {
        return courses.find("TI1705");
    }

    protected User createUser() {
        User user = new User();
        user.setMemberOf(Lists.<GroupMembership> newArrayList());
        user.setNetId(randomString());
        users.persist(user);
        return user;
    }

    protected String randomString() {
        return new BigInteger(130, random).toString(32);
    }

}
