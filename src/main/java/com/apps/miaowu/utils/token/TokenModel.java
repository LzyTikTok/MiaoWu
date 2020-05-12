package com.apps.miaowu.utils.token;

public class TokenModel {

	private String userId;

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "TokenModel [userId=" + userId + ", token=" + token + "]";
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public TokenModel(String userId, String token) {
		this.userId = userId;
		this.token = token;
	}

}
