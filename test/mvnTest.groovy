import com.lesfurets.jenkins.unit.BasePipelineTest
import org.junit.Before
import org.junit.Test

class MvnTest extends BasePipelineTest {

    @Override
    @Before
    void setUp() throws Exception {
        super.setUp()
        // When the mvn step calls the tool step, just return the name of the tool
        helper.registerAllowedMethod("tool", [String.class], { paramString -> paramString })
    }

    @Test
    void mvn() {
        def shParams = ""
        def withEnvParams = []
        helper.registerAllowedMethod("sh", [String.class], { paramString -> shParams = paramString })
        helper.registerAllowedMethod("withEnv", [List.class, Closure.class], { paramList, closure ->
            withEnvParams = paramList
            closure.call()
            })

        def script = loadScript('vars/mvn.groovy')
        script.env = new Object() {
            String JAVA_HOME = "javaHome"
        }
        script.call('clean install')

        assert shParams.contains('clean install')
        assert withEnvParams.size() == 2
        assert withEnvParams[0].contains('JDK8')
        assert withEnvParams[1].contains('M3')
        assert withEnvParams[1].contains('javaHome')
    }
}