package cn.com.ttblog.sssbootstrap_table.controller;

import cn.com.ttblog.sssbootstrap_table.zookeeper.ZookeeperClient;
import org.I0Itec.zkclient.IZkDataListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zk")
public class ZookeeperController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperController.class);

    @Autowired
    private ZookeeperClient zkClient;

    @GetMapping(value = "/children")
    public Object getChildren(String path) {
        LOGGER.info("get children:{}", path);
        if (zkClient.zkClient.exists(path)) {
            return zkClient.zkClient.getChildren(path);
        }
        return null;
    }

    @GetMapping(value = "/get")
    public Object getData(String path) {
        LOGGER.info("get data");
        if (zkClient.zkClient.exists(path)) {
            zkClient.zkClient.subscribeDataChanges(path, new IZkDataListener() {
                @Override
                public void handleDataChange(String s, Object o) throws Exception {
                    LOGGER.info("data changed!path:{},value:{}", s, o);
                }

                @Override
                public void handleDataDeleted(String s) throws Exception {
                    LOGGER.info("data deleted!path:{}", s);
                }
            });
        }
        return zkClient.zkClient.readData(path, true);
    }

    @GetMapping(value = "/create")
    public String create(String path, String data) {
        LOGGER.info("create {}:{}", path, data);
        String result = zkClient.zkClient.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zkClient.zkClient.subscribeDataChanges(path, new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                LOGGER.info("data changed!path:{},value:{}", s, o);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                LOGGER.info("data deleted!path:{}", s);
            }
        });
        return result;
    }


    @GetMapping(value = "/update")
    public String update(String path, String data) {
        zkClient.zkClient.writeData(path, data);
        return data;
    }

    @GetMapping(value = "/delete")
    public boolean delete(String path) {
        return zkClient.zkClient.delete(path);
    }
}
