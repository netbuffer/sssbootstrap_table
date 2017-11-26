package cn.com.ttblog.sssbootstrap_table;

import com.jayway.jsonpath.JsonPath;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * https://github.com/json-path/JsonPath
 */
public class TestJsonPath {

    private static final Logger LOGGER= LoggerFactory.getLogger(TestJsonPath.class);

    @Test
    public void testJsonPath() throws IOException {
        String json=FileUtils.readFileToString(new File(TestJsonPath.class.getResource("/json-path/data.json").getPath()),"UTF-8");
        LOGGER.info("JsonPath.read(json, \"$.store.book[*].author\"):{}",JsonPath.read(json, "$.store.book[*].author"));
    }
}
