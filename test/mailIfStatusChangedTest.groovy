import org.junit.Before
import org.junit.Test

import com.lesfurets.jenkins.unit.BasePipelineTest

class MailIfStatusChangedTest extends BasePipelineTest {

    @Override
    @Before
    void setUp() throws Exception {
        super.setUp()
    }

    @Test
    void mailIfStatusChangedTest() {
        def stepParams = [:]
        helper.registerAllowedMethod("step", [Map.class], {paramMap -> stepParams = paramMap})

        def script = loadScript('vars/mailIfStatusChanged.groovy')
        script.call('a@b.cd')
        assert stepParams.recipients == 'a@b.cd'
        assert stepParams.$class == 'Mailer'
    }
}