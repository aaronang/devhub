package nl.tudelft.ewi.devhub.server.backend.warnings;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlAnnotationIntrospector;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import nl.tudelft.ewi.devhub.server.database.controllers.Commits;
import nl.tudelft.ewi.devhub.server.database.embeddables.Source;
import nl.tudelft.ewi.devhub.server.database.entities.Commit;
import nl.tudelft.ewi.devhub.server.database.entities.Group;
import nl.tudelft.ewi.devhub.server.database.entities.warnings.CheckstyleWarning;
import nl.tudelft.ewi.git.client.GitClientException;
import nl.tudelft.ewi.git.client.GitServerClient;
import nl.tudelft.ewi.git.client.Repositories;
import nl.tudelft.ewi.git.client.Repository;
import nl.tudelft.ewi.git.models.BlameModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import nl.tudelft.ewi.devhub.server.backend.warnings.CheckstyleWarningGenerator.CheckStyleReport;

/**
 * @author Jan-Willem Gmelig Meyling
 */
@RunWith(MockitoJUnitRunner.class)
public class CheckstyleWarningGeneratorTest {

    private final static String EXPECTED_PATH = "src/test/java/nl/tudelft/jpacman/board/DirectionTest.java";
    private final static String COMMIT_ID = "234345345345";

    @Mock private Group group;
    @Mock private Commit commit;
    @InjectMocks private CheckstyleWarningGenerator checkstyleWarningGenerator;
    @Mock private Commits commits;
    @Mock private GitServerClient gitServerClient;
    @Mock private Repositories repositories;
    @Mock private Repository repository;
    @Mock private nl.tudelft.ewi.git.client.Commit repoCommit;
    @Mock private BlameModel blameModel;
    @Mock private BlameModel.BlameBlock blameBlock;

    @Before
    public void initializeMocks() throws GitClientException {
        when(commit.getCommitId()).thenReturn(COMMIT_ID);
        when(commit.getRepository()).thenReturn(group);
        when(group.getRepositoryName()).thenReturn("");
        when(commits.ensureExists(any(), any())).thenReturn(commit);

        when(gitServerClient.repositories()).thenReturn(repositories);
        when(repositories.retrieve(anyString())).thenReturn(repository);
        when(repository.retrieveCommit(COMMIT_ID)).thenReturn(repoCommit);
        blameCurrentCommit();
    }

    public void blameCurrentCommit() throws GitClientException {
        when(repoCommit.blame(EXPECTED_PATH)).thenReturn(blameModel);
        when(blameModel.getBlameBlock(anyInt())).thenReturn(blameBlock);
        when(blameBlock.getFromCommitId()).thenReturn(COMMIT_ID);
        when(blameBlock.getFromFilePath()).thenReturn(EXPECTED_PATH);
        when(blameBlock.getFromLineNumber(any())).thenAnswer(invocation ->
                invocation.getArguments()[0]);
    }

    @Test
    public void testBasicParse() throws IOException {
        ObjectMapper mapper = new XmlMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        AnnotationIntrospector introspector = new JacksonXmlAnnotationIntrospector();
        mapper.getDeserializationConfig().withAppendedAnnotationIntrospector(introspector);

        CheckstyleWarning expected = expectedWarning(EXPECTED_PATH, 27, "First sentence should end with a period.", "warning");

        try(InputStreamReader inputStreamReader = new InputStreamReader(CheckstyleWarningGeneratorTest.class.getResourceAsStream("/checkstyle-result.xml"))) {
            CheckStyleReport report = mapper.readValue(inputStreamReader, CheckStyleReport.class);
            Set<CheckstyleWarning> warnings = checkstyleWarningGenerator.generateWarnings(commit, report);
            assertThat(warnings, contains(expected));
        }
    }

    protected CheckstyleWarning expectedWarning(String path, int lineNumber, String message, String severity) {
        final Source bSource = new Source();
        bSource.setSourceLineNumber(lineNumber);
        bSource.setSourceFilePath(path);
        bSource.setSourceCommit(commit);

        final CheckstyleWarning b = new CheckstyleWarning();
        b.setCommit(commit);
        b.setSource(bSource);
        b.setSeverity(severity);
        b.setMessage(message);
        return b;
    }

}
