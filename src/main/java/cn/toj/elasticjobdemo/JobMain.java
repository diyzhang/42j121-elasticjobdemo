package cn.toj.elasticjobdemo;

import cn.toj.elasticjobdemo.job.JobConfig;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

/**
 * 启动任务
 * @author Administrator
 * @version 1.0
 **/
public class JobMain {

    //zookeeper端口
    private static final int ZOOKEEPER_PORT = 2181;
    //zookeeper链接字符串 localhost:2181
    private static final String ZOOKEEPER_CONNECTION_STRING = "localhost:" + ZOOKEEPER_PORT;
    //定时任务命名空间
    private static final String JOB_NAMESPACE = "elastic-job-example-java-test";


    //执行启动任务
    public static void main(String[] args) {
        //配置注册中心
        CoordinatorRegistryCenter registryCenter = setUpRegistryCenter();
        //启动任务
        startJob(registryCenter);
    }

    //zk的配置及创建注册中心
    private static CoordinatorRegistryCenter setUpRegistryCenter(){
        //zk的配置
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(ZOOKEEPER_CONNECTION_STRING, JOB_NAMESPACE);

        //创建注册中心
        CoordinatorRegistryCenter zookeeperRegistryCenter = new ZookeeperRegistryCenter(zookeeperConfiguration);
        zookeeperRegistryCenter.init();
        return zookeeperRegistryCenter;
    }

    //任务的配置和启动
    private static void startJob(CoordinatorRegistryCenter registryCenter){
        //String jobName 任务名称, String cron 调度表达式, int shardingTotalCount 作业分片数量
        JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder("job-test", "0/3 * * * * ?", 3).build();
        //创建SimpleJobConfiguration
        SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(jobCoreConfiguration, JobConfig.class.getCanonicalName());
        //创建new JobScheduler
        new JobScheduler(registryCenter, LiteJobConfiguration.newBuilder(simpleJobConfiguration).overwrite(true).build()).init();

    }
}
