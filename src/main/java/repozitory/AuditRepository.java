package repozitory;

import model.Audit;

import java.util.List;
import java.util.Optional;

public interface AuditRepository {
boolean save(Audit audit);
Optional<List<Audit>> getById(Long playerId);

}
