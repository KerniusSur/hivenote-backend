package app.hivenote.note;

import app.hivenote.account.entity.AccountEntity;
import app.hivenote.exception.ApiException;
import app.hivenote.note.dto.request.NoteCreateRequest;
import app.hivenote.note.dto.request.NoteUpdateRequest;
import app.hivenote.note.entity.NoteAccessEntity;
import app.hivenote.note.entity.NoteAccessType;
import app.hivenote.note.entity.NoteEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
  private final NoteRepository noteRepository;
  private final String ERROR_PREFIX = "err.note.";

  public NoteService(NoteRepository noteRepository) {
    this.noteRepository = noteRepository;
  }

  public NoteEntity findById(UUID id) {
    return noteRepository
        .findById(id)
        .orElseThrow(() -> ApiException.notFound(ERROR_PREFIX + "notFound"));
  }

  public List<NoteEntity> findByIdAndAccountId(UUID noteId, UUID accountId) {
    return noteRepository.findByAccountIdAndNoteId(accountId, noteId);
  }

  public List<NoteEntity> findByAccountAccessAndAccountId(
      NoteAccessType accessType, UUID accountId) {
    return noteRepository.findByAccountAccessAndAccountId(accessType, accountId);
  }

  public NoteEntity create(NoteCreateRequest request, UUID accountId) {
    NoteEntity noteEntity =
        new NoteEntity()
            .setType(request.getType())
            .setTitle(request.getTitle())
            .setCoverUrl(request.getCoverUrl());

    NoteAccessEntity noteAccessEntity =
        new NoteAccessEntity()
            .setAccount(new AccountEntity().setId(accountId))
            .setNote(noteEntity)
            .setAccessType(NoteAccessType.OWNER);

    noteEntity.getAccountAccess().add(noteAccessEntity);
    return noteRepository.save(noteEntity);
  }

  public NoteEntity update(NoteUpdateRequest request, UUID accountId) {
    NoteEntity noteEntity = findById(request.getId());

    if (noteEntity.getAccountAccess().stream()
        .noneMatch(
            access ->
                access.getAccount().getId().equals(accountId)
                    && (access.getAccessType() == NoteAccessType.OWNER
                        || access.getAccessType() == NoteAccessType.EDITOR))) {
      throw ApiException.unauthorized(ERROR_PREFIX + "unauthorized");
    }

    noteEntity
        .setTitle(request.getTitle())
        .setCoverUrl(request.getCoverUrl())
        .setIsArchived(request.getIsArchived())
        .setIsDeleted(request.getIsDeleted());

    return noteRepository.save(noteEntity);
  }

  public void delete(UUID id, UUID accountId) {
    NoteEntity noteEntity = findById(id);

    if (noteEntity.getAccountAccess().stream()
        .noneMatch(
            access ->
                access.getAccount().getId().equals(accountId)
                    && access.getAccessType() == NoteAccessType.OWNER)) {
      throw ApiException.unauthorized(ERROR_PREFIX + "unauthorized");
    }

    noteEntity.setIsDeleted(true);
    noteRepository.save(noteEntity);
  }

   public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 }

 public void test () {
    ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3)));
    ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4)));
    ListNode result = addTwoNumbers(l1, l2);

    while (result != null) {
      System.out.println(result.val);
      result = result.next;
    }
  }

  public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0);
    ListNode current = dummy;

    int carry = 0;
    while (l1 != null || l2 != null) {
      int x = (l1 != null) ? l1.val : 0;
      int y = (l2 != null) ? l2.val : 0;
      int sum = carry + x + y;
      carry = sum / 10;
      current.next = new ListNode(sum % 10);
      current = current.next;
      if (l1 != null) l1 = l1.next;
      if (l2 != null) l2 = l2.next;
    }
    if (carry > 0) {
      current.next = new ListNode(carry);
    }
    return dummy.next;
 }
}
