package mom.crawler.progress;

public class NewTaskAppeared implements ProgressMessage {
    @Override
    public void accept(ProgressMessageVisitor visitor) {
        visitor.visitNewTaskAppeared(this);
    }
}