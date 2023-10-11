package service;

import model.Action;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class AuditService {
    private final Map<String, List<Action>> actionHistory = new HashMap<>();
    public void saveAuditUserHistory(String name, Action action) {
        List<Action> actions = actionHistory.computeIfAbsent(name, k -> new ArrayList<>());
        actions.add(action);
    }
    public void printAuditUserHistory(String name) {
        List<Action> actions = actionHistory.get(name);
        if (actions == null) {
            System.out.println("История действий для: " + name + " не найдена");
            return;
        }
        actions.forEach(System.out::println);
    }
}
