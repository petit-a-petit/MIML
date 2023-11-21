package com.petitapetit.miml.domain.friendship.exception;

public class FriendshipNotFoundException extends RuntimeException {
	public FriendshipNotFoundException(){
		super("존재하지 않는 관계입니다.");
	}
}
