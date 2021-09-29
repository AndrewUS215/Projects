import java.util.ArrayList;
import java.util.List;

public class NodeRoot {
    private String nodeName;
    private final List<NodeRoot> children;
    private int code;
    private String content;

    public NodeRoot(String nodeName, String content, int code) {
        this.nodeName = nodeName;
        this.content = content;
        this.code = code;
        this.children = new ArrayList<>();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public NodeRoot addChild(NodeRoot child) {
        children.add(child);
        return child;
    }

    public List<NodeRoot> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        if (children.isEmpty()) {
            return "\t\n" + nodeName;
        }
        return "\t\n" + nodeName + "\t\n" + children;
    }
}
