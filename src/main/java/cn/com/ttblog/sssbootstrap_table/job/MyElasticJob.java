package cn.com.ttblog.sssbootstrap_table.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyElasticJob implements SimpleJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyElasticJob.class);

    @Override
    public void execute(ShardingContext context) {
        LOGGER.info("MyElasticJob定时任务执行:{}", context);
        switch (context.getShardingItem()) {
            case 0:
                // do something by sharding item 0
                LOGGER.info("execute 0");
                break;
            case 1:
                // do something by sharding item 1
                LOGGER.info("execute 1");
                break;
            case 2:
                // do something by sharding item 2
                LOGGER.info("execute 2");
                break;
            default:
                LOGGER.info("nothing");
                // case n: ...
        }
    }
}