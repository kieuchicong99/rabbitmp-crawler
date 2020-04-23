package mom.crawler;

import com.rabbitmq.client.Channel;

public abstract class CrawlerProtocol {
    public final static String TASK_PROGRESS_QUEUE_NAME = "task-progress-queue";
    public final static String TASK_QUEUE_NAME = "task-queue";
    public final static String RESULT_QUEUE_NAME = "result-queue";

    public static void initialize(final Channel channel) {
        try {
            channel.queueDeclare(CrawlerProtocol.TASK_QUEUE_NAME, false, false, false, null);
            channel.queueDeclare(CrawlerProtocol.TASK_PROGRESS_QUEUE_NAME, false, false, false, null);
            channel.queueDeclare(CrawlerProtocol.RESULT_QUEUE_NAME, false, false, false, null);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void reset(final Channel channel) {
        try {
            channel.queuePurge(CrawlerProtocol.TASK_QUEUE_NAME);
            channel.queuePurge(CrawlerProtocol.TASK_PROGRESS_QUEUE_NAME);
            channel.queuePurge(CrawlerProtocol.RESULT_QUEUE_NAME);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}