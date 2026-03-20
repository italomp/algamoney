package com.algaworks.algamoney_api.algamoney_api.events;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class CreatedResourceEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private HttpServletResponse response;
    private Long id;

    public CreatedResourceEvent(Object object, HttpServletResponse response, Long id) {
        super(object);
        this.response = response;
        this.id = id;
    }
}
