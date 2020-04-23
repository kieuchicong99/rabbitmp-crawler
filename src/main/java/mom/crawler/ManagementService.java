package mom.crawler;

import mom.crawler.progress.NewTaskAppeared;
import mom.crawler.progress.ProgressMessage;
import mom.crawler.tasks.Task;

public class ManagementService {
    private final MessageDestination<Task> taskDestination;
    private final MessageDestination<ProgressMessage> taskProgressDestination;
    private final MessageSource<String> resultSource;
    private final MessageSource<ProgressMessage> taskProgressSource;

    public ManagementService(MessageDestination<Task> taskDestination,
            MessageDestination<ProgressMessage> taskProgressDestination, MessageSource<String> resultSource,
            MessageSource<ProgressMessage> taskProgressSource) {

        this.taskDestination = taskDestination;
        this.taskProgressDestination = taskProgressDestination;
        this.resultSource = resultSource;
        this.taskProgressSource = taskProgressSource;
    }

    public void submitTask(Task task) {
        taskDestination.putMessage(task);
        taskProgressDestination.putMessage(new NewTaskAppeared());
    }

    public String consumeResult() {
        return resultSource.getMessage();
    }

    public ProgressMessage consumeProgressMessage() {
        return taskProgressSource.getMessage();
    }
}