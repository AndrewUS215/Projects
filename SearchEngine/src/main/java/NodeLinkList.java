import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class NodeLinkList extends RecursiveTask<List<NodeLinkList>> {
    private final NodeRoot root;

    public NodeLinkList(NodeRoot root) {
        this.root = root;
    }

    public NodeRoot getRoot() {
        return root;
    }

    @Override
    protected List<NodeLinkList> compute() {
        DBConnection.getConnection();
        List<NodeLinkList> tasks = new ArrayList();

        root.getChildren().forEach(child -> {
                    NodeLinkList task = new NodeLinkList(child);
                    task.fork();
                    tasks.add(task);
                }
        );

        try {
            for (NodeLinkList nodeLinkList : tasks) {
                nodeLinkList.join();
                addResultsFromTasks(nodeLinkList);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return tasks;
    }

    private void addResultsFromTasks(NodeLinkList nodeLinkList) throws SQLException {
        String content = nodeLinkList.getRoot().getContent();
        int code = nodeLinkList.getRoot().getCode();
        String path = nodeLinkList.getRoot().getNodeName();
        DBConnection.executeMultiInsert(path, code, content);
    }


    @Override
    public String toString() {
        return root.getNodeName();
    }
}
