package com.kh.jdbc.day01.stmt.member.view;

import java.util.List;
import java.util.Scanner;

import com.kh.jdbc.day01.stmt.member.controller.MemberController;
import com.kh.jdbc.day01.stmt.member.model.vo.Member;

public class MemberView { // 이 필드는 Membercontroller를 사용해서 비즈니스로직을 처리함
	MemberController mController;
	
	public MemberView() {   // 객체 초기화
		mController = new MemberController();  // MemberView 객체가 생성될때 MemberController 객체도 함께 초기화됌.
	
	}
	
	public void startProgram() { // 프로그램의 메인 루프를 실행하여 사용자의 입력에 따라 적절한 작업을 수행
//		//mController.insertMember(); 
//		List<Member> mList = mController.listMember(); // void는 리턴값이 없으므로 오류발생, 즉 membercontroller클래스에서 void를 바꿔줘야함.
//		this.displayMemberList(mList);
		int choice = 0;
		끝 :
		while(true) { // 무한반복문
			choice = this.printMainMenu();
			switch(choice) {
			case 0 : 
				System.out.println("프로그램을 종료합니다.");
				break 끝;
			case 1 : 
				Member member = this.inputMember();
				mController.insertMember(member);
				break;
			case 2 : 
				List<Member> mList = mController.listMember();
				this.displayMemberList(mList);
				break;
			case 3 : 
				String memberId = this.inputMemberId();
				member = mController.printOneMember(memberId);
				this.displayOne(member);
				break;
			default : this.displayMessage("메뉴를 다시 선택해주세요.");
			}
		}

	}
	
	private String inputMemberId() {
		Scanner sc = new Scanner(System.in);
		System.out.print("아이디 입력 : ");
		String memberId = sc.next();
		return memberId;
	}

	private void displayOne(Member member) {
		System.out.printf("이름 : %s, 나이 : %d"
				+ ", 아이디 : %s, 성별 : %s, 이메일 : %s"
				+ ", 전화번호 : %s, 주소 : %s, 취미 : %s"
				+ ", 가입날짜 : %s\n"
				, member.getMemberName()
				, member.getAge()
				, member.getMemberId()
				, member.getGender()
				, member.getEmail()
				, member.getPhone()
				, member.getAddress()
				, member.getHobby()
				, member.getRegDate());
	}

	private Member inputMember() {
		Scanner sc = new Scanner(System.in);
		System.out.println("====== 회원 정보 입력 ======");
		System.out.print("아이디 : ");
		String memberId = sc.next();
		System.out.print("비밀번호 : ");
		String memberPw = sc.next();
		System.out.print("이름 : ");
		String memberName = sc.next();
		System.out.print("성별 : ");
		String gender = sc.next();
		System.out.print("나이 : ");
		int age = sc.nextInt();
		System.out.print("이메일 : ");
		String email = sc.next();
		System.out.print("전화번호 : ");
		String phone = sc.next();
		System.out.print("주소 : ");
		sc.nextLine();
		String address = sc.nextLine();
		System.out.print("취미 : ");
		String hobby = sc.nextLine();
		Member member = new Member();
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		member.setMemberName(memberName);
		member.setGender(gender);
		member.setAge(age);
		member.setEmail(email);
		member.setPhone(phone);
		member.setAddress(address);
		member.setHobby(hobby);
		return member;
	}

	private void displayMessage(String msg) {
		System.out.println(msg);
	}

	private int printMainMenu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("====== 회원 관리 프로그램 ======");
		System.out.println("1. 회원가입");
		System.out.println("2. 회원 전체 조회");
		System.out.println("3. 회원 검색(아이디로 조회)");
		System.out.println("0. 프로그램 종료");
		System.out.print("메뉴 선택 : ");
		int value = sc.nextInt();
		return value;
	}

	public void displayMemberList(List<Member> mList) {
		System.out.println("====== 회원 정보 전체 출력 ======");
		for(Member member : mList) {
			System.out.printf("이름 : %s\n", member.getMemberName());
		}
	}
}
