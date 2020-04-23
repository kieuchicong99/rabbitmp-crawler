package mom.crawler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import mom.crawler.tasks.ProcessAbcTask;
import mom.crawler.tasks.ProcessLetterTask;
import mom.crawler.tasks.ProcessPlayerTask;
import mom.crawler.tasks.Task;
import mom.crawler.tasks.TaskVisitor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WorkerApp implements TaskVisitor {
    private final WorkerService workerService;

    public WorkerApp(WorkerService workerService) {
        this.workerService = workerService;
    }

    public void run() {
        while (true) {
            Task task = workerService.consumeTask();
            if (task == null) {
                continue;
            }

            while (true) {
                try {
                    task.accept(this);
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            workerService.submitTaskDone();
        }
    }

    @Override
    public void visitAbcTask(ProcessAbcTask task) throws IOException {
        Document doc = Jsoup.connect(task.url).get();
        Elements elements = doc.select("#playerSearch > .lastInitial > a");
        for (Element e : elements) {
            ProcessLetterTask processLetterTask = new ProcessLetterTask();
            processLetterTask.url = e.attr("abs:href");
            processLetterTask.processPagination = true;
            workerService.submitTask(processLetterTask);
        }
    }

    @Override
    public void visitLetterTask(ProcessLetterTask task) throws IOException {
        Document doc = Jsoup.connect(task.url).get();
        Elements elements = doc.select("table.data > tbody > tr a[href*=player]");
        for (Element e : elements) {
            ProcessPlayerTask processPlayerTask = new ProcessPlayerTask();
            processPlayerTask.url = e.attr("abs:href");
            workerService.submitTask(processPlayerTask);
        }

        if (!task.processPagination) {
            return;
        }

        elements = doc.select(".pageNumbers > a");
        Set<String> urls = new HashSet<String>();
        for (Element e : elements) {
            urls.add(e.attr("abs:href"));
        }

        for (String url : urls) {
            ProcessLetterTask processLetterTask = new ProcessLetterTask();
            processLetterTask.url = url;
            processLetterTask.processPagination = false;
            workerService.submitTask(processLetterTask);
        }
    }

    @Override
    public void visitPlayerTask(ProcessPlayerTask task) throws IOException {
        Document doc = Jsoup.connect(task.url).get();
        Element nameElement = doc.select("#tombstone h1 *").first();
        String playerName = fixString(nameElement.ownText());
        workerService.submitResult(playerName);
    }

    private static String fixString(String s) {
        return s.replace(String.valueOf((char) 160), " ").trim();
    }
}