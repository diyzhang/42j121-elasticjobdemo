package cn.toj.elasticjobdemo.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

import java.time.LocalDateTime;

/**
 * @author Carlos
 * @description
 * @Date 2020/8/13
 */

public class JobConfig implements SimpleJob {

    //任务执行代码逻辑
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("当前分片：" + shardingContext.getShardingItem());
        System.out.printf("当前时间：%s \n", LocalDateTime.now());
    }

}
