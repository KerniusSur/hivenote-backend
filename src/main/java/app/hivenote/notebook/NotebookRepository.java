package app.hivenote.notebook;

import app.hivenote.notebook.entity.NotebookEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotebookRepository extends JpaRepository<NotebookEntity, UUID> {}
