package in.zestic.springboot.common.activemq;

import javax.jms.JMSException;

/**
 * create an instance of complete application
 *
 * @SpringBootTest annotation loads whole application, but it is better to limit Application Context only
 * to a set of spring components that participate in test scenario.
 */
//@SpringBootTest(classes = {ActiveMQConsumer.class})
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestConsumer {

    /**
     *
     */
//    @BeforeAll
//    void setUp() throws JMSException {
//    }
//
//    @Test
//    void test() {
//        System.out.println("Test");
//    }
}
