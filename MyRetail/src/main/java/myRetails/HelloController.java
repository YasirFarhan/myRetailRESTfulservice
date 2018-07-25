package myRetails;

import dao.FilePersistanceException;
import java.io.IOException;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
@Component
@Configuration
@ComponentScan
@ComponentScan({"myRetails"})
@Controller
 */
@RequestMapping({"/hello"})

public class HelloController {

    public HelloController() {
    }

    @RequestMapping(value = "/sayhi", method = RequestMethod.GET)
    public String sayHi(Map<String, Object> model) throws FilePersistanceException, IOException {
        model.put("message", "Please use a HTTP client to test myRetail REST API");
        return "hello";
    }

}
