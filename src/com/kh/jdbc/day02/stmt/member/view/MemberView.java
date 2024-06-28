package com.kh.jdbc.day02.stmt.member.view;

import java.util.List;
import java.util.Scanner;

import com.kh.jdbc.day02.stmt.member.model.vo.Member;
import com.kh.jdbc.day02.stmt.member.controller.MemberController;

public class MemberView {

	// View 클래스에서 계속 쓸거니
	// 필드로 두고
	MemberController mController; // 5.

	public MemberView() { // 6.
		// 생성자에서 초기화 해줌
		mController = new MemberController(); // 7.
	}

	public void startProgram() { // 3.
//		System.out.println("호출됐네~"); // 4.
//		mController.insertMember(); // 8.
		end: // 34.
		while (true) { // 33.
			int value = this.printMainMenu(); // 32.
			switch (value) {
			case 1:
				// 1을 눌렀다면 회원의 정보를 입력받아야 함.
//				this.inputMember(); // 37. inputMember()를 실행시켜 정보 받기
				Member member = this.inputMember(); // 42.
				// ID부터 취미까지 저장된 member 객체를 컨트롤러로 전달
				mController.insertMember(member); // 43.
				break; // 35.
			case 2:
				// 2를 눌렀다면 회원의 전체정보를 출력해야 함.
				// 1. 디비에서 데이터 가져오기, 전체 회원 정보니까 여러개, 여러개니까 리스트 List, 멤버니까 List<Member>
				List<Member> mList = mController.printAllMember(); // 48. //db에서 가져온 값이 여기로 옴.
				// 2. view의 메소드를 이용해서 출력하기
				this.printAllMembers(mList); // 64
				break;
			case 3:
				// 3을 눌렀다면 회원의 정보를 검색해야 함.(아이디로 검색)
				// 사용자가 검색한 아이디 입력받아야 되니까 inputMember();
				String memberId = this.inputMemberId(); // 68
				// 입력받은 아이디로 디비에서 검색해 와야되니까 printMember()
				// 컨트롤러로 전달해야 되니까 printOneMember(memberId)
				member = mController.printOneMember(memberId); // 71
				// 디비에서 가져온 값을 출력해야되니까 printOneMember
				this.printOneMember(member); // 87
				break;
			case 4:  // 95
				// 4를 눌렀다면 회원의 정보를 수정해야 함(아이디로 정보가 존재하는지 확인 후 있으면 수정 없으면 안함)
				// 사용자가 수정할 아이디 입력받아야 되니까 inputMember();
				memberId = inputMemberId();
				// 존재하는 정보만 수정로직을 타야되니까, printOneMember() 호출
				// DB에서 가져온 값 저장해야되니까 member = mController.printOneMember(memberId);
				member = mController.printOneMember(memberId);
				// DB에서 데이터를 가져왔는지 체크해야되니까 if(member != null)
				// 데이터가 없다면 member는 null일 것임.
				if(member != null) {
					// 수정할 때에는 수정할 정보를 입력해야되니까 nodifyMember(member);
					// 즉 수정할 정보를 가지고 있는 member 객체가 필요함.
					Member modifyInfo = this.inputModifyInfo();
					// UPDATE 할때는 가장 중요한 것이 WHERE 조건절이니까, WHERE에 들어갈 데이터를 전달 해줘야 함.
					// modifyMember(modifyInfo)에서 modifyInfo에 memberId를 꼭 넣어줘야 하니까 modifyInfo.setMemberId(memberId);
					modifyInfo.setMemberId(memberId); // 97
					// DML의 결과는 int니까 int result;
					int result = mController.modifyMember(modifyInfo); 
					if(result > 0) { // 101
						this.displayMessage("수정 성공!");
					}else {
						this.displayMessage("수정 실패!");
					}
				}else {
					this.displayMessage("존재하지 않는 정보입니다");
				}
				break;
			case 5:  // 89
				// 5를 눌렀다면 회원의 정보를 삭제해야함(아이디로 삭제)
				// 사용자가 삭제할 아이디 입력받아야 되니까 inputMember();
				memberId = this.inputMemberId();
				// 존재하는 정보만 삭제로직을 타야되니까, printOneMember() 호출
				member = mController.printOneMember(memberId); // 93
				// DB에서 데이터를 가져왔는지 체크해야되니까 if(member != null)
				// 데이터가 없다면 member는 null일 것임.
				if(member != null) { // 94
					// 입력받은 아이디로 디비에서 삭제해야되니까 removeMember();
					// 컨트롤러로 전달해야되니까 removeMember(memberId);
					// DML의 결과는 int니까 int result
					int result = mController.removeMember(memberId); 
					if(result > 0) { // 92
						this.displayMessage("삭제 성공!");
					}else {
						this.displayMessage("삭제 실패!");
					}
					break;
				}else {
					this.displayMessage("존재하지 않는 정보입니다");
				}
				break;
			case 0:
				break end; // 36.
			}
		}
	}
	
	private Member inputModifyInfo() { // 96
		Scanner sc = new Scanner(System.in);
		System.out.println("====== 회원 정보 등록 ======");
		System.out.print("비밀번호 : ");
		String memberPw = sc.next(); 
		System.out.print("이메일 : ");
		String email = sc.next(); 
		System.out.print("전화번호 : ");
		String phone = sc.next();
		System.out.print("주소 : ");
		sc.nextLine(); 
		String address = sc.nextLine(); 
		System.out.print("취미 : ");
		String hobby = sc.next(); 
		Member member = new Member();
		member.setMemberPw(memberPw);
		member.setEmail(email);
		member.setPhone(phone);
		member.setAddress(address);
		member.setHobby(hobby); 
		return member; 
	}

	//MemberView:57,59
	private void displayMessage(String msg) { // 93
		System.out.println("[서비스 결과] : " + msg);
	}

	// MemberView:41
	private String inputMemberId() { // 69
		Scanner sc = new Scanner(System.in);
		System.out.print("아이디 입력 : ");
		String memberId = sc.next();
		// 호출한 곳에서 써야되니까 return memberId, MemberView:41
		return memberId; // 70
	}
	
	private void printOneMember(Member member) { // 88
		System.out.println("====== 회원 정보 전체 출력 ======");
		System.out.printf(
				"이름 : %s, 나이 : %d" + ", 아이디 : %s, 성별 : %s, 이메일 : %s" + ", 전화번호 : %s, 주소 : %s, 취미 : %s"
						+ ", 가입날짜 : %s\n",
				member.getMemberName(), member.getAge(), member.getMemberId(), member.getGender(), member.getEmail(),
				member.getPhone(), member.getAddress(), member.getHobby(), member.getRegDate());
	}

	// MemberView:46
	private void printAllMembers(List<Member> mList) { // 65
		System.out.println("====== 회원 정보 전체 출력 ======");
		for (Member member : mList) { // 66
			System.out.printf(
					"이름 : %s, 나이 : %d" + ", 아이디 : %s, 성별 : %s, 이메일 : %s" + ", 전화번호 : %s, 주소 : %s, 취미 : %s"
							+ ", 가입날짜 : %s\n",
					member.getMemberName(), member.getAge(), member.getMemberId(), member.getGender(),
					member.getEmail(), member.getPhone(), member.getAddress(), member.getHobby(), member.getRegDate()); // 67
		}
	}

	private Member inputMember() { // 38. // 리턴타입에 의거해서 void -> Member
		Scanner sc = new Scanner(System.in);
		System.out.println("====== 회원 정보 등록 ======");
		System.out.print("아이디 : ");
		String memberId = sc.next(); // 39
		System.out.print("비밀번호 : ");
		String memberPw = sc.next(); // 39
		System.out.print("이름 : ");
		String memberName = sc.next(); // 39
		System.out.print("성별 : ");
		String gender = sc.next(); // 39
		System.out.print("나이 : ");
		int age = sc.nextInt(); // 39
		System.out.print("이메일 : ");
		String email = sc.next(); // 39
		System.out.print("전화번호 : ");
		String phone = sc.next(); // 39
		System.out.print("주소 : ");
		sc.nextLine(); // 39
		String address = sc.nextLine(); // 39
		System.out.print("취미 : ");
		String hobby = sc.next(); // 39
		Member member = new Member(); // 40
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		member.setMemberName(memberName);
		member.setGender(gender);
		member.setAge(age);
		member.setEmail(email);
		member.setPhone(phone);
		member.setAddress(address);
		member.setHobby(hobby); // 41
		return member; // 40
	}

	public int printMainMenu() { // 30. 리턴타입 int 주의
		Scanner sc = new Scanner(System.in);
		System.out.println("====== 회원 관리 프로그램 ======");
		System.out.println("1. 회원가입");
		System.out.println("2. 전체 회원 조회");
		System.out.println("3. 회원 검색(아이디로 조회)");
		System.out.println("4. 회원 정보 수정");
		System.out.println("5. 회원 탈퇴");
		System.out.println("0. 종료");
		System.out.print("메뉴 선택 : ");
		int choice = sc.nextInt();
		return choice; // 31. 다른 메소드에서 쓸 수 있도록 리턴
	}
}
