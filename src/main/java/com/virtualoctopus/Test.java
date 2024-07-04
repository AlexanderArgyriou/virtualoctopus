package com.virtualoctopus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtualoctopus.annotations.bean.definitions.VirtualOctopusController;
import com.virtualoctopus.annotations.dinjetion.VirtualOctopusInject;
import com.virtualoctopus.annotations.resource.mappings.VirtualOctopusGetMapping;
import com.virtualoctopus.annotations.resource.mappings.VirtualOctopusPostMapping;

@VirtualOctopusController
public class Test {
    @VirtualOctopusInject
    TestRepo testRepo;

    @VirtualOctopusGetMapping(path = "/x")
    public Object get() {
        return testRepo.getAll();
    }

    @VirtualOctopusPostMapping(path = "/y")
    public Object post(String jsonBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Book book = objectMapper.readValue(jsonBody, Book.class);

        return testRepo.add(book);
    }
}
