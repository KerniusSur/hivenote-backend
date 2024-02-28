package app.hivenote.notebook;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "notebook")
@Transactional
@RestController
@RequestMapping("/api/v1/user/notebook")
public class NotebookController {
  private final NotebookService notebookService;

  public NotebookController(NotebookService notebookService) {
    this.notebookService = notebookService;
  }
}
