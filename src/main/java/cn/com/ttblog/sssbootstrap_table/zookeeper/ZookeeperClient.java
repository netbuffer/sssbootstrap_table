package cn.com.ttblog.sssbootstrap_table.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class ZookeeperClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperClient.class);

    @Value("${zk.addr}")
    private String addr;
    @Value("${zk.sessiontimeout}")
    private Integer sessionTimeout;

    public ZkClient zkClient;

    public void init() {
        LOGGER.info("connection to addr:{},timeout:{}", addr, sessionTimeout);
        zkClient = new ZkClient(addr, sessionTimeout, sessionTimeout, new SerializableSerializer());
    }
}
