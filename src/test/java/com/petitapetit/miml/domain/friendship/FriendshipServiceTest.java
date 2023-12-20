package com.petitapetit.miml.domain.friendship;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.petitapetit.miml.domain.auth.oauth.CustomOAuth2User;
import com.petitapetit.miml.domain.auth.oauth.OAuth2Provider;
import com.petitapetit.miml.domain.auth.oauth.userinfo.SpotifyUserInfo;
import com.petitapetit.miml.domain.friendship.dto.FriendshipDto;
import com.petitapetit.miml.domain.friendship.model.Friendship;
import com.petitapetit.miml.domain.friendship.repository.FriendshipRepository;
import com.petitapetit.miml.domain.friendship.service.FriendshipService;
import com.petitapetit.miml.domain.member.dto.MemberDto;
import com.petitapetit.miml.domain.member.model.Member;
import com.petitapetit.miml.domain.member.model.RoleType;
import com.petitapetit.miml.domain.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
public class FriendshipServiceTest {
	@InjectMocks
	FriendshipService friendshipService;
	@Mock
	FriendshipRepository friendshipRepository;
	@Mock
	MemberRepository memberRepository;

	@Test
	public void 친구_신청에_성공한다() {
		// given
		FriendshipDto.CreateRequest request = createRequest();
		CustomOAuth2User customOAuth2User = createCustomOAuth2User();
		Member toMember = mock(Member.class); // Member toMember = createMember();
		given(memberRepository.findById(any())).willReturn(Optional.ofNullable(toMember));

		// when
		friendshipService.createFriendship(request, customOAuth2User);

		// then
		verify(friendshipRepository).saveAll(any());
	}

	@Test
	public void 친구_신청을_수락한다() {
		// given
		FriendshipDto.AcceptRequest request = acceptRequest();
		CustomOAuth2User customOAuth2User = createCustomOAuth2User();
		Friendship friendship = createFriendship1(); // 객체의 실제 동작을 테스트하고 싶을 때 사용
		Member fromMember = mock(Member.class); // 특정 메서드 호출에 대한 반환값을 명시하거나 메서드 호출을 검증하고 싶을 때 사용
		given(memberRepository.findById(any())).willReturn(Optional.ofNullable(fromMember));
		given(friendshipRepository.findByFromMemberAndToMemberAndIsFriend(any(), any(), any()))
			.willReturn(Optional.ofNullable(friendship));

		// when
		friendshipService.acceptFriendship(request, customOAuth2User);

		// then
		assertNotNull(friendship);
		assertEquals(Boolean.TRUE, friendship.getIsFriend());
	}

	@Test
	public void 내가_친구_신청_보낸_회원을_조회한다() {
		// 가짜 Friendship 객체 생성
		Friendship fakeFriendship1 = createFriendship1();
		Friendship fakeFriendship2 = createFriendship2();

		// friendshipRepository가 가짜 객체를 반환하도록 설정
		when(friendshipRepository.findFriendshipsByToMemberAndIsFriend(any(), anyBoolean()))
			.thenReturn(Arrays.asList(fakeFriendship1, fakeFriendship2));

		// 테스트할 서비스 메소드 호출
		List<FriendshipDto.NotFriendResponse> result = friendshipService.getToMemberList(createCustomOAuth2User());

		// 결과 검증
		assertEquals(2, result.size());

		assertEquals(1L, result.get(0).getMemberId());
		assertEquals("Friend1", result.get(0).getMemberName());
		assertEquals(2L, result.get(1).getMemberId());
		assertEquals("Friend2", result.get(1).getMemberName());

		// friendshipRepository 메소드가 호출되었는지 검증
		verify(friendshipRepository, times(1)).findFriendshipsByToMemberAndIsFriend(any(), anyBoolean());
	}

	@Test
	public void 나에게_친구_신청_보낸_회원을_조회한다() {
		Friendship fakeFriendship = createFriendship3();

		when(friendshipRepository.findFriendshipsByFromMemberAndIsFriend(any(), anyBoolean()))
			.thenReturn(Arrays.asList(fakeFriendship));

		List<FriendshipDto.NotFriendResponse> result = friendshipService.getFromMemberList(createCustomOAuth2User());

		assertEquals(1, result.size());
		assertEquals(1L, result.get(0).getMemberId());
		assertEquals("Friend1", result.get(0).getMemberName());
	}

	@Test
	public void 나와_서로_친구인_회원을_조회한다() {
		Member friend1 = mock(Member.class);
		Member friend2 = mock(Member.class);
		CustomOAuth2User loginMember = createCustomOAuth2User();

		when(friendshipRepository.findFriends(loginMember.getUser()))
			.thenReturn(Arrays.asList(friend1, friend2));

		List<MemberDto.BriefInfoResponse> result = friendshipService.getFriendList(loginMember);
		assertEquals(2, result.size());
	}

	private Friendship createFriendship1() { // '내가 보낸 친구 신청'의 역방향 레코드 1
		return Friendship.builder()
			.fromMember(createMember1())
			.isFriend(Boolean.FALSE)
			.build();
	}

	private Friendship createFriendship2() { // '내가 보낸 친구 신청'의 역방향 레코드 2
		return Friendship.builder()
			.fromMember(createMember2())
			.isFriend(Boolean.FALSE)
			.build();
	}

	private Friendship createFriendship3() { // '내가 받은 친구 신청'의 역방향 레코드
		return Friendship.builder()
			.toMember(createMember1())
			.fromMember(createCustomOAuth2User().getUser())
			.isFriend(Boolean.FALSE)
			.build();
	}

	private FriendshipDto.AcceptRequest acceptRequest() {
		return FriendshipDto.AcceptRequest.builder()
			.fromMemberId(2L)
			.build();
	}

	private FriendshipDto.CreateRequest createRequest() {
		return FriendshipDto.CreateRequest.builder()
			.toMemberId(2L)
			.build();
	}

	private CustomOAuth2User createCustomOAuth2User() { // 로그인한 회원
		Member member = Member.builder()
			.memberId(1000L)
			.name("name")
			.email("email")
			.image("image")
			.role(RoleType.ROLE_USER)
			.provider(OAuth2Provider.SPOTIFY)
			.providerId("provider_id")
			.build();
		String accessToken = "1234";
		Map<String, Object> attribute = Map.of(
			"id", "spotify",
			"accessToken", accessToken
		);
		return new CustomOAuth2User(new SpotifyUserInfo(attribute), member, accessToken);
	}

	private Member createMember1() {
		return Member.builder()
			.memberId(1L)
			.name("Friend1")
			.build();
	}

	private Member createMember2() {
		return Member.builder()
			.memberId(2L)
			.name("Friend2")
			.build();
	}
}
