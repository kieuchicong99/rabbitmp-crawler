package mom.crawler;

import mom.crawler.progress.NewTaskAppeared;
import mom.crawler.progress.ProgressMessage;
import mom.crawler.progress.TaskDone;
import mom.crawler.tasks.Task;

public class WorkerService {
    private final MessageDestination<Task> taskDestination;
    private final MessageDestination<ProgressMessage> taskProgressDestination;
    private final MessageDestination<String> resultDestination;
    private final MessageSource<Task> taskSource;

    public WorkerService(MessageDestination<Task> taskDestination,
            MessageDestination<ProgressMessage> taskProgressDestination, MessageDestination<String> resultDestination,
            MessageSource<Task> taskSource) {
        this.taskDestination = taskDestination;
        this.taskProgressDestination = taskProgressDestination;
        this.resultDestination = resultDestination;
        this.taskSource = taskSource;
    }

    public void submitTask(Task task) {
        taskDestination.putMessage(task);
        taskProgressDestination.putMessage(new NewTaskAppeared());
    }

    public void submitTaskDone() {
        taskProgressDestination.putMessage(new TaskDone());
    }

    public Task consumeTask() {
        return taskSource.getMessage();
    }

    public void submitResult(String result) {
        resultDestination.putMessage(result);
    }
}