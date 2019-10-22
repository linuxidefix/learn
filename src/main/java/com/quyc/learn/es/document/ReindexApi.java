package com.quyc.learn.es.document;

import com.quyc.learn.es.EsClientUtil;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.elasticsearch.index.reindex.ScrollableHitSource;

import java.io.IOException;
import java.util.List;

/**
 * @author: andy
 * @create: 2019/7/1 17:24
 * @description: ElasticSearch Reindex Api
 */
public class ReindexApi {

    private static RestHighLevelClient client = EsClientUtil.getClient();

    public static void main(String[] args) throws IOException {
        reindex();
    }

    public static void reindex() throws IOException {
        ReindexRequest request = new ReindexRequest();
        request.setSourceIndices("posts");
        request.setDestIndex("copy_posts");
        // 重新建一个index，保护原始index不被修改
        request.setDestVersionType(VersionType.EXTERNAL);
        // 设置操作类型为"create"会只在目的index创建缺失的文档，默认为Index，覆盖已存在的文档。
        request.setDestOpType(DocWriteRequest.OpType.CREATE.name());
        // 出现版本冲突的时候不作处理，只统计数量
        request.setConflicts("proceed");
        // 对源index进行过滤
        // 只传递type为doc的文档
        request.setSourceDocTypes("doc");
        // 通过查询过滤文档
        request.setSourceQuery(new TermQueryBuilder("user", "aaa"));
        BulkByScrollResponse bulkResponse = client.reindex(request, RequestOptions.DEFAULT);
        TimeValue timeTaken = bulkResponse.getTook();
        boolean timedOut = bulkResponse.isTimedOut();
        long totalDocs = bulkResponse.getTotal();
        long updatedDocs = bulkResponse.getUpdated();
        long createdDocs = bulkResponse.getCreated();
        long deletedDocs = bulkResponse.getDeleted();
        long batches = bulkResponse.getBatches();
        long noops = bulkResponse.getNoops();
        long versionConflicts = bulkResponse.getVersionConflicts();
        long bulkRetries = bulkResponse.getBulkRetries();
        long searchRetries = bulkResponse.getSearchRetries();
        TimeValue throttledMillis = bulkResponse.getStatus().getThrottled();
        TimeValue throttledUntilMillis = bulkResponse.getStatus().getThrottledUntil();
        List<ScrollableHitSource.SearchFailure> searchFailures = bulkResponse.getSearchFailures();
        List<BulkItemResponse.Failure> bulkFailures = bulkResponse.getBulkFailures();
        System.out.println("bulkResponse = " + bulkResponse);

    }

}
