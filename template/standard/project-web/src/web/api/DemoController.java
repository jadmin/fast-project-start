package ${packagePrefix}.web.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.javaclub.sword.web.HttpResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/demo")
@Api(tags = "示例api")
public class DemoController {

    @GetMapping(value = "/info")
    @ApiOperation(value = "示例信息")
    public HttpResult<String> info() {
        return HttpResult.success("Hello, World!");
    }
    
}
