package cn.com.ttblog.sssbootstrap_table;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestZkClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestZkClient.class);

    @Test
    public void testZk() throws IOException, InterruptedException, KeeperException {
        String addr = "localhost:2181";
        ZooKeeper zooKeeper = new ZooKeeper(addr, 3000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                LOGGER.info("watchedEvent:{}", watchedEvent);
            }
        });
        LOGGER.info("client-config:{}", ToStringBuilder.reflectionToString(zooKeeper.getClientConfig()));
        TimeUnit.SECONDS.sleep(3);
        LOGGER.info("session time out:{}",zooKeeper.getSessionTimeout());
        String createPath="/"+RandomStringUtils.random(8,true,true);
        zooKeeper.create(createPath ,RandomStringUtils.random(8,true,true).getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        LOGGER.info("get {} data:{}",createPath,new String(zooKeeper.getData(createPath,false,null)));
    }
}
