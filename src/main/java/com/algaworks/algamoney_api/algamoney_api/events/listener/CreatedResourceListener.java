package com.algaworks.algamoney_api.algamoney_api.events.listener;

import com.algaworks.algamoney_api.algamoney_api.events.CreatedResourceEvent;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Component
public class CreatedResourceListener implements ApplicationListener<CreatedResourceEvent> {
    @Override
    public void onApplicationEvent(CreatedResourceEvent event) {
        HttpServletResponse response = event.getResponse();
        Long id = event.getId();
        addLocationHeader(id, response);
    }

    private static void addLocationHeader(Long id, HttpServletResponse response) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(id).toUri();
        response.setHeader("Location", uri.toASCIIString());
    }
}
