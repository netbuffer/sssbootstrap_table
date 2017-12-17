package cn.com.ttblog.sssbootstrap_table;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.zookeeper.*;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestZkClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestZkClient.class);
    private static final String ADDR = "localhost:2181";
    private static ZooKeeper zooKeeper = null;

    @Before
    public void before() throws IOException {
        zooKeeper = new ZooKeeper(ADDR, 3000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                LOGGER.info("watchedEvent:{}", watchedEvent);
            }
        });
    }

    @Test
    public void testCreate() throws KeeperException, InterruptedException {
        LOGGER.info("client-config:{}", ToStringBuilder.reflectionToString(zooKeeper.getClientConfig()));
        TimeUnit.SECONDS.sleep(3);
        LOGGER.info("session time out:{}", zooKeeper.getSessionTimeout());
        String createPath = "/" + RandomStringUtils.random(8, true, true);
        String data = RandomStringUtils.random(8, true, true);
        LOGGER.info("createPath:{},data:{}", createPath, data);
        /**
         * CreateMode
         * PERSISTENT：持久化目录节点，这个目录节点存储的数据不会丢失
         * PERSISTENT_SEQUENTIAL：顺序自动编号的目录节点，这种目录节点会根据当前已近存在的节点数自动加 1，然后返回给客户端已经成功创建的目录节点名
         * EPHEMERAL：临时目录节点，一旦创建这个节点的客户端与服务器端口也就是 session 超时，这种节点会被自动删除
         * EPHEMERAL_SEQUENTIAL：临时自动编号节点
         */
        zooKeeper.create(createPath, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    @Test
    public void testGet() throws KeeperException, InterruptedException {
        String createPath = "/xvmXOs6W";
        LOGGER.info("get {} data:{}", createPath, new String(zooKeeper.getData(createPath, true, null)));
        LOGGER.info("get / children:{}",zooKeeper.getChildren("/",true,null));
    }
}
