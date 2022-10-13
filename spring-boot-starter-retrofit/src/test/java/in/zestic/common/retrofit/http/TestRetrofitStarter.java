package in.zestic.common.retrofit.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;

@SpringBootTest(classes = RetrofitTestApplication.class)
@RunWith(SpringRunner.class)
public class TestRetrofitStarter {

    private static final Logger logger = LoggerFactory.getLogger(TestRetrofitStarter.class);

    @Autowired
    private TestRetrofitClient client;

}
