package service;

import model.Audit;
import model.Player;

public interface AuditService {

    void saveAuditUserHistory(Audit audit);
    void printAuditUserHistory(Player player);
}
