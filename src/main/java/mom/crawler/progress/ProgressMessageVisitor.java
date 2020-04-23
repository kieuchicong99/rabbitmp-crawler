package mom.crawler.progress;

public interface ProgressMessageVisitor {
    void visitNewTaskAppeared(NewTaskAppeared message);

    void visitTaskDone(TaskDone message);
}