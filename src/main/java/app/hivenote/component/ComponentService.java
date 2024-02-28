package app.hivenote.component;

import org.springframework.stereotype.Service;

@Service
public class ComponentService {
    private final ComponentRepository componentRepository;

    public ComponentService(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }
}
