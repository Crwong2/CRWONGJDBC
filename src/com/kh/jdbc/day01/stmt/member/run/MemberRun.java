package com.kh.jdbc.day01.stmt.member.run;

import com.kh.jdbc.day01.stmt.member.view.MemberView;

public class MemberRun {
	public static void main(String[] args) {
		MemberView view = new MemberView(); // MemberView 클래스의 인스턴스 생성
		view.startProgram();                // 프로그램 실행을 위해 startProgram 메서드 호출
	}
}
