package app.hivenote.event;

import org.springframework.stereotype.Service;

@Service
public class EventService {
  private final EventRepository eventRepository;

  public EventService(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }
}
