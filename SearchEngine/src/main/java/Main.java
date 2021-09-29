import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.regex.Pattern;

public class Main {
    private static final String URL = "http://www.playback.ru/";
    private static NodeRoot firstNode;

    public static void main(String[] args) throws IOException {
        firstNode = createFirstNode(URL);
        createTreeNodeLink(firstNode);
        new ForkJoinPool().invoke(new NodeLinkList(firstNode));
    }

    public static NodeRoot createFirstNode(String path) throws IOException { //Метод для создания и инициализации первого узла
        Document document = createDocumentForUrl(path);
        String content = document.toString();
        int code = document.connection().response().statusCode();
        firstNode = new NodeRoot(path, content, code);
        return firstNode;
    }

    public static Document createDocumentForUrl(String path) throws IOException { //Метод для создания документа
        return Jsoup.connect(path)
                .userAgent("SearchEngineBot")
                .referrer("http://www.google.com")
                .timeout(3000)
                .get();
    }

    public static void createTreeNodeLink(NodeRoot nodeRoot) throws IOException {  //Рекурсивный метод для создания дерева узлов
        String nodeName = nodeRoot.getNodeName();
        Document document = createDocumentForUrl(nodeName);
        List<String> linkList = new ArrayList<>();
        Elements elements = document.select("a");
        for (Element el : elements) {
            String absUrl = el.absUrl("href");
            Pattern pattern = Pattern.compile(nodeName + ".+[^#]");
            if (absUrl.matches(pattern.pattern()) && !absUrl.contains("pdf")) {
                linkList.add(absUrl);
            }
        }
        for (String newLink : linkList) {
            Document newDocument = createDocumentForUrl(newLink);
            int code = newDocument.connection().response().statusCode();
            String newContent = newDocument.toString();
            NodeRoot newNodeRoot = new NodeRoot(newLink, newContent, code);
            nodeRoot.addChild(newNodeRoot);
            createTreeNodeLink(newNodeRoot);
        }
    }
}