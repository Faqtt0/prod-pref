package prefrest.com.prod.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

import javax.servlet.http.HttpServletResponse;

public class RecursoEvent extends ApplicationEvent {

    private HttpServletResponse response;
    private Long codigo;

    public RecursoEvent(Object source, HttpServletResponse response, Long codigo) {
        super(source);
        this.response = response;
        this.codigo = codigo;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public Long getCodigo() {
        return codigo;
    }
}
