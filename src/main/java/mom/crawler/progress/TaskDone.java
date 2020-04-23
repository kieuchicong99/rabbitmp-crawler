package mom.crawler.progress;

public class TaskDone implements ProgressMessage {
    @Override
    public void accept(ProgressMessageVisitor visitor) {
        visitor.visitTaskDone(this);
    }
}