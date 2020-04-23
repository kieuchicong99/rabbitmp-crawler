package mom.crawler;

import mom.crawler.progress.NewTaskAppeared;
import mom.crawler.progress.ProgressMessageVisitor;
import mom.crawler.progress.TaskDone;

public class ProgressDeltasProgressMessageVisitor implements ProgressMessageVisitor {
    private int newTaskCount;
    private int finishedTaskCount;

    @Override
    public void visitNewTaskAppeared(NewTaskAppeared message) {
        newTaskCount = 1;
    }

    @Override
    public void visitTaskDone(TaskDone message) {
        finishedTaskCount = 1;
    }

    public int getNewTask() {
        return newTaskCount;
    }

    public int getFinishedTaskCount() {
        return finishedTaskCount;
    }
}