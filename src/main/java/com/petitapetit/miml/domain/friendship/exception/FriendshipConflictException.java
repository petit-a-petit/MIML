package com.petitapetit.miml.domain.friendship.exception;

public class FriendshipConflictException extends RuntimeException {
	public FriendshipConflictException(){
		super("이미 존재하는 관계입니다.");
	}
}
