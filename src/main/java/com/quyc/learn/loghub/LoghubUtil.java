package com.quyc.learn.loghub;

import com.aliyun.openservices.log.Client;
import com.aliyun.openservices.log.common.*;
import com.aliyun.openservices.log.exception.LogException;
import com.aliyun.openservices.log.request.*;
import com.aliyun.openservices.log.response.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * loghub SDK封装工具类
 *
 * @author quyc
 */
public class LoghubUtil {

    private Logger logger = LoggerFactory.getLogger(LoghubUtil.class);

    private String accessKey;
    private String accessKeyId;
    private String endpoint;
    private String logstore;
    private String projectName;
    private String source;
    private Client client;

    public LoghubUtil(String accessKey, String accessKeyId, String endpoint, String logstore, String projectName, String source) {
        this.accessKey = accessKey;
        this.accessKeyId = accessKeyId;
        this.endpoint = endpoint;
        this.logstore = logstore;
        this.projectName = projectName;
        this.source = source;
    }

    /**
     * 获取loghub client
     *
     * @return
     */
    public Client getClient() {
        if (client == null) {
            client = new Client(endpoint, accessKeyId, accessKey);
        }
        return client;
    }

    /**
     * 获取 project 列表
     *
     * @param projectName 根据projectName进行模糊匹配
     * @param offset
     * @param size
     * @return
     */
    public List<Project> listProjects(String projectName, int offset, int size) {
        try {
            if (offset < 0) {
                offset = 0;
            }
            if (size <= 0) {
                size = 100;
            }
            ListProjectResponse listProjectResponse = getClient().ListProject(projectName, offset, size);
            return listProjectResponse.getProjects();
        } catch (LogException e) {
            logger.error("LoghubUtil listProjects exception", e);
        }
        return null;
    }

    /**
     * 获取当前project下所有 logStore
     *
     * @param offset       默认0
     * @param size         默认100
     * @param logStoreName 根据logStoreName进行模糊查询
     * @return
     */
    public List<String> listLogStores(int offset, int size, String logStoreName) {
        try {
            if (offset < 0) {
                offset = 0;
            }
            if (size <= 0) {
                size = 100;
            }
            if (StringUtils.isBlank(logStoreName)) {
                logStoreName = "";
            }
            ListLogStoresRequest req = new ListLogStoresRequest(projectName, offset, size, logStoreName);
            ListLogStoresResponse listLogStoresResponse = getClient().ListLogStores(req);
            return listLogStoresResponse.GetLogStores();
        } catch (LogException e) {
            logger.error("LoghubUtil listLogStores exception", e);
        }
        return null;
    }

    /**
     * 获取当前 project，当前logstore下所有的topic
     *
     * @return
     */
    public List<String> listTopics() {
        try {
            ListTopicsRequest listTopicsRequest = new ListTopicsRequest(projectName, logstore);
            ListTopicsResponse listTopicsResponse = getClient().ListTopics(listTopicsRequest);
            return listTopicsResponse.GetTopics();
        } catch (LogException e) {
            logger.error("LoghubUtil listTopics exception", e);
        }
        return null;
    }

    /**
     * 获取当前project，当前logStore下面的所有shard
     *
     * @return
     */
    public List<Shard> listShards() {
        try {
            ListShardResponse listShardResponse = getClient().ListShard(projectName, logstore);
            return listShardResponse.GetShards();
        } catch (LogException e) {
            logger.error("LoghubUtil listShards exception", e);
        }
        return null;
    }

    /**
     * 向loghub 写入日志
     *
     * @param logs  日志内容部分由一个或多个内容项组成，每一个内容项为一个Key-Value对
     * @param topic 日志主题，默认为""，查询所有topic
     */
    public void writeLog(Map<String, String> logs, String topic) {
        if (StringUtils.isBlank(topic)) {
            topic = "";
        }
        ArrayList<LogItem> logGroup = new ArrayList<>();
        try {
            for (Map.Entry<String, String> entry : logs.entrySet()) {
                LogItem logItem = new LogItem();
                logItem.PushBack(entry.getKey(), entry.getValue());
                logGroup.add(logItem);
            }
            PutLogsRequest req = new PutLogsRequest(projectName, logstore, topic, source, logGroup);
            getClient().PutLogs(req);
        } catch (LogException e) {
            logger.error("LoghubUtil writeLog exception", e);
        }
    }

    /**
     * 发送日志到一个特定的 shard，只要设置 shard 的 hashkey，则数据会写入包含该hashkey 的 range 所对应的 shard，
     * 具体 API 参考以下接口：
     * public PutLogsResponse PutLogs( String project, String logStore, String topic,List<LogItem> logItems, String source,
     * String shardHash // 根据 hashkey 确定写入 shard，hashkey 可以是 MD5(ip) 或 MD5(id) 等 )
     *
     * @param logs    日志内容部分由一个或多个内容项组成，每一个内容项为一个Key-Value对
     * @param hashKey 用于确定分区
     * @param topic   日志主题，默认为""，查询所有topic
     */
    public void writeLog(Map<String, String> logs, String topic, String hashKey) {
        if (StringUtils.isBlank(topic)) {
            topic = "";
        }
        ArrayList<LogItem> logGroup = new ArrayList<>();
        try {
            for (Map.Entry<String, String> entry : logs.entrySet()) {
                LogItem logItem = new LogItem();
                logItem.PushBack(entry.getKey(), entry.getValue());
                logGroup.add(logItem);
            }
            PutLogsRequest req = new PutLogsRequest(projectName, logstore, topic, source, logGroup, hashKey);
            getClient().PutLogs(req);
        } catch (LogException e) {
            logger.error("LoghubUtil writeLogHash exception", e);
        }
    }

    /**
     * 打印一条日志
     * 发送日志到一个特定的 shard，只要设置 shard 的 hashkey，则数据会写入包含该hashkey 的 range 所对应的 shard，
     * 具体 API 参考以下接口：
     * public PutLogsResponse PutLogs( String project, String logStore, String topic,List<LogItem> logItems, String source,
     * String shardHash // 根据 hashkey 确定写入 shard，hashkey 可以是 MD5(ip) 或 MD5(id) 等 )
     *
     * @param log    日志内容部分由一个或多个内容项组成，每一个内容项为一个Key-Value对
     * @param topic   日志主题，默认为""，查询所有topic
     */
    public void writeOneLog(Map<String, String> log, String topic) {
        if (StringUtils.isBlank(topic)) {
            topic = "";
        }
        ArrayList<LogItem> logGroup = new ArrayList<>();
        LogItem logItem = new LogItem();
        try {
            log.entrySet().stream().forEach(entry -> logItem.PushBack(entry.getKey(), entry.getValue()));
            logGroup.add(logItem);
            PutLogsRequest req = new PutLogsRequest(projectName, logstore, topic, source, logGroup);
            getClient().PutLogs(req);
        } catch (LogException e) {
            logger.error("LoghubUtil writeLogHash exception", e);
        }
    }

    /**
     * 查询指定时间内日志分布情况
     *
     * @param query 日志查询语句，需要符合查询语法，详情可见：https://help.aliyun.com/document_detail/29060.html?spm=5176.2020520112.0.0.13dc34c0h2Fjmf
     * @param from  起始时间：System.currentTimeMillis()/1000 - 60
     * @param to    结束时间：System.currentTimeMillis()/1000
     * @param topic 日志主题，默认为""，查询所有topic
     * @return
     */
    public Map<String, Object> listHistograms(String query, int from, int to, String topic) {
        if (StringUtils.isBlank(topic)) {
            topic = "";
        }
        HashMap<String, Object> result = new HashMap<>(2);
        // 查询日志分布情况
        try {
            GetHistogramsResponse res = null;
            // 最多查询三次，若都查询失败则返回null
            for (int retryTime = 0; retryTime < 3; retryTime++) {
                GetHistogramsRequest req = new GetHistogramsRequest(projectName, logstore, topic, query, from, to);
                res = getClient().GetHistograms(req);
                // IsCompleted() 返回true，表示查询结果是准确的，如果返回false，则重复查询
                if (res != null && res.IsCompleted()) {
                    break;
                }
                Thread.sleep(100);
            }
            if (res == null) {
                return null;
            }
            ArrayList<Histogram> histograms = res.GetHistograms();
            result.put("histograms", histograms);
            result.put("totalCount", res.GetTotalCount());
        } catch (LogException | InterruptedException e) {
            logger.error("LoghubUtil listHistograms exception", e);
        }
        return result;
    }

    /**
     * 查询指定时间内日志列表
     *
     * @param query 日志查询语句，需要符合查询语法，详情可见：https://help.aliyun.com/document_detail/29060.html?spm=5176.2020520112.0.0.13dc34c0h2Fjmf
     * @param from  起始时间：System.currentTimeMillis()/1000
     * @param to    结束时间：System.currentTimeMillis()/1000
     * @param topic 日志主题，默认为""，查询所有topic
     * @return
     */
    public List<QueriedLog> listLogs(String query, String topic, int from, int to) {
        try {
            if (StringUtils.isBlank(topic)) {
                topic = "";
            }
            GetLogsResponse res = null;
            // 对于每个 log 如果读取失败，最多重复读取 3 次。
            for (int retryTime = 0; retryTime < 3; retryTime++) {
                GetLogsRequest req = new GetLogsRequest(projectName, logstore, from, to, topic, query);
                res = getClient().GetLogs(req);
                if (res != null && res.IsCompleted()) {
                    break;
                }
                Thread.sleep(100);
            }
            if (res == null) {
                return null;
            }
            return res.GetLogs();
        } catch (LogException | InterruptedException e) {
            logger.error("LoghubUtil listLogs exception",e);
        }
        return null;
    }

    /**
     * 分页查询指定时间内日志列表
     *
     * @param query    日志查询语句，需要符合查询语法，详情可见：https://help.aliyun.com/document_detail/29060.html?spm=5176.2020520112.0.0.13dc34c0h2Fjmf
     * @param from     起始时间（秒）：System.currentTimeMillis()/1000 - 60
     * @param to       结束时间（秒）：System.currentTimeMillis()/1000
     * @param pageNo   页码
     * @param pageSize 每页条数 最大值为100，每次获取100行数据。若需要读取更多数据，请使用 pageNo 翻页
     * @param topic    日志主题，默认为""，查询所有topic
     * @return
     */
    public List<QueriedLog> listLogsPage(String query, String topic, int from, int to, int pageNo, int pageSize) {
        if (StringUtils.isBlank(topic)) {
            topic = "";
        }
        if (pageNo < 0) {
            pageNo = 0;
        }
        if (pageSize < 0) {
            pageSize = 10;
        }
        int logOffset = (pageNo - 1) * pageSize;
        try {
            GetLogsResponse res = null;
            // 对于每个 log offset,如果读取失败，最多重复读取 3 次。
            for (int retryTime = 0; retryTime < 3; retryTime++) {
                // pageSize 最大值为100，每次获取100行数据。若需要读取更多数据，请使用 offset 翻页。offset和lines只对关键字查询有效，若使用SQL查询，则无效。在SQL查询中返回更多数据，请使用limit语法。
                GetLogsRequest req = new GetLogsRequest(projectName, logstore, from, to, topic, query, logOffset,
                        pageSize, false);
                res = getClient().GetLogs(req);
                if (res != null && res.IsCompleted()) {
                    break;
                }
                Thread.sleep(100);
            }
            if (res == null) {
                return null;
            }
            return res.GetLogs();
        } catch (LogException | InterruptedException e) {
            logger.error("LoghubUtil listLogsPage exception", e);
        }
        return null;
    }


    public static void main(String[] args) {
//        -Dloghub.projectName=weierai \
//        -Dloghub.logstore=ai_online \
//        -Dloghub.endpoint=cn-hangzhou-vpc.log.aliyuncs.com \
//        -Dloghub.accessKeyId=LTAIjGZl6C3VCSrv \
//        -Dloghub.accessKey=jodNX1Tl70qcAndqcC23teCOAbJU2E \
//        -Dloghub.source=$LOCAL_IP_ADDR \
        String projectName = "";
        String logstore = "";
        String accessKey = "";
        String accessKeyId = "";
        String endpoint = "";
        LoghubUtil loghubUtil = new LoghubUtil(accessKey, accessKeyId, endpoint, logstore, projectName, "");
        List<QueriedLog> queriedLogList = loghubUtil.listLogs("BusinessExecutor error", "",
                Long.valueOf(System.currentTimeMillis() / 1000 - 60 * 60 * 24).intValue(),
                Long.valueOf(System.currentTimeMillis() / 1000).intValue());
        for (QueriedLog queriedLog : queriedLogList) {
            LogItem logItem = queriedLog.GetLogItem();
            ArrayList<LogContent> logContents = logItem.GetLogContents();
            for (LogContent logContent : logContents) {
                System.out.println(logContent.GetKey());
                System.out.println(logContent.GetValue());
            }
        }
    }


}

