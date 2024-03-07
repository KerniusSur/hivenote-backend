package app.hivenote.notebook.mapper;

import app.hivenote.account.mapper.AccountMapper;
import app.hivenote.note.mapper.NoteMapper;
import app.hivenote.notebook.dto.response.NotebookResponse;
import app.hivenote.notebook.entity.NotebookEntity;
import java.util.stream.Collectors;

public class NotebookMapper {
  public static NotebookResponse toResponse(NotebookEntity notebook) {
    return new NotebookResponse()
        .setId(notebook.getId())
        .setName(notebook.getName())
        .setIsArchived(notebook.getIsArchived())
        .setIsDeleted(notebook.getIsDeleted())
        .setAccount(AccountMapper.toPublicResponse(notebook.getAccount()))
        .setNotes(
            notebook.getNotes() == null
                ? null
                : notebook.getNotes().stream()
                    .map(NoteMapper::toMinResponse)
                    .collect(Collectors.toList()));
  }
}
