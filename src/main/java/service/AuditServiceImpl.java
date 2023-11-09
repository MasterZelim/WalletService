package service;

import model.Audit;
import model.Player;
import repozitory.AuditRepository;

public class AuditServiceImpl implements AuditService {
    private final AuditRepository auditRepository;


    public AuditServiceImpl(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Override
    public void saveAuditUserHistory(Audit audit) {
        auditRepository.save(audit);
    }

    @Override
    public void printAuditUserHistory(Player player) {
        var actions = auditRepository.getById(player.getId());
        if (actions.isEmpty()){
            System.out.println("История действий для имени: " + player.getName()+ " не найдено");
            return;
        }
        actions.get().forEach(System.out::println);
    }
}
