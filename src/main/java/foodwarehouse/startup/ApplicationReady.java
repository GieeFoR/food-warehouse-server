package foodwarehouse.startup;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationReady {

    @EventListener(ApplicationEvent.class)
    public void readBatchesExpiredAlerts() {

    }
}
