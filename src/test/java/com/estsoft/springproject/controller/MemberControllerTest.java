package com.estsoft.springproject.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.estsoft.springproject.entity.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

  @Autowired
  WebApplicationContext context;

  @Autowired
  MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
  }

  @Test
  void testGetAllMember() throws Exception {
    ResultActions resultActions = mockMvc.perform(get("/members")
        .accept(MediaType.APPLICATION_JSON));

    resultActions.andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[1].id").value(2));
  }

  @Test
  void testSaveMemberWithValidFields() throws Exception {
    Member validMember = new Member();
    validMember.setName("John Doe");

    ResultActions resultActions = mockMvc.perform(post("/members")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(validMember))
        .accept(MediaType.APPLICATION_JSON));

    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("John Doe"));
  }

}