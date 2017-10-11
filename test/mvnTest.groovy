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
        def imageParams = ""
        def shParams = ""
        helper.registerAllowedMethod("sh", [String.class], { paramString -> shParams = paramString })

        def script = loadScript('vars/mvn.groovy')
        script.docker = new Object() {
            def image(String imgName) {
                imageParams = imgName
                return new Object() {
                    def inside(Closure closure) {
                        closure.call()
                    }
                }
            }
        }

        script.call('clean install')

        assert imageParams == 'maven:3.5.0-jdk-8'
        assert shParams.contains('clean install')
    }
}