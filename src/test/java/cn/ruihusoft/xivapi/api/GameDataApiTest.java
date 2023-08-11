package cn.ruihusoft.xivapi.api;

import cn.ruihusoft.xivapi.starter.AbstractTest;
import cn.ruihusoft.xviapi.api.GameDataApi;
import cn.ruihusoft.xviapi.core.ContentType;
import cn.ruihusoft.xviapi.pojo.ContentResponse;
import cn.ruihusoft.xviapi.pojo.common.QueryParam;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Slf4j
public class GameDataApiTest extends AbstractTest {

    @Autowired
    private GameDataApi gameDataApi;

    @Test
    public void testServers() {
        List<String> allServers = gameDataApi.servers();
        Assert.assertTrue("所有服务器为空", allServers != null && !allServers.isEmpty());
        log.info("allServers is {}", allServers);
    }

    @Test
    public void testDc() {
        Map<String, List<String>> dc = gameDataApi.dc();

        Assert.assertTrue("数据为空", dc != null && !dc.isEmpty());

        log.info("dc list:");
        log.info("---------------------------------");
        for (Map.Entry<String, List<String>> entry : dc.entrySet()) {
            log.info("dc:" + entry.getKey());
            for (String server : entry.getValue()) {
                log.info("----" + server);
            }
        }
    }

    @Test
    public void testContent() {
        List<String> allContent = gameDataApi.content();
        Assert.assertTrue("content can not be null",
                allContent != null && !allContent.isEmpty()
        );

        log.info("all content is : {}", allContent);
    }

    @Test
    public void testContentList1() {
    }

    @Test
    public void testContentList2() {
        QueryParam param = new QueryParam();
        param.setLimit(5);
        testContentList(ContentType.Item, param);
    }

    private void testContentList(ContentType content, QueryParam param) {
        ContentResponse response = gameDataApi.contentList(content, param);

        Assert.assertTrue("数据为空", response != null);
        log.info("response is {}", response);
    }
}