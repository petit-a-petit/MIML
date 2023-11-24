package com.petitapetit.miml.domain.friendship;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petitapetit.miml.domain.friendship.api.FriendshipController;
import com.petitapetit.miml.domain.friendship.dto.FriendshipDto;
import com.petitapetit.miml.domain.friendship.service.FriendshipService;
import com.petitapetit.miml.global.WithMockCustomUser;

@WebMvcTest(FriendshipController.class)
public class FriendshipControllerTest {
	@MockBean
	FriendshipService friendshipService;
	@Autowired
	private MockMvc mockMvc;

	@DisplayName("친구 신청 성공")
	@Test
	@WithMockCustomUser
	void 친구_신청에_성공한다() throws Exception {
		FriendshipDto.CreateRequest request = createRequest();
		mockMvc.perform(post("/friendship")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectToJsonString(request))
			.with(SecurityMockMvcRequestPostProcessors.csrf())
		).andExpect(status().isCreated());
	}

	private FriendshipDto.CreateRequest createRequest() {
		return FriendshipDto.CreateRequest.builder()
			.toMemberId(2L)
			.build();
	}

	private String objectToJsonString(Object object) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
