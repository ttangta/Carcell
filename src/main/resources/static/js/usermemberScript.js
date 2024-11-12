function inputCheck(){
		var frm = document.form1;
		var writeEmail = document.getElementById("writeEmail");
		var style = getComputedStyle(writeEmail);
		if(style.display == "block"){
			if(!frm.writeEmail.value.trim()){
				alert("이메일을 작성해주세요");
				frm.writeEmail.focus();
				return false;
			}
		}
		
		if(!frm.id.value.trim()){
			alert("아이디를 입력해주세요");
			frm.id.focus();
			return false;
		}else if(!frm.pw.value.trim()){
			alert("비밀번호를 입력해주세요");
			frm.pw.focus();
			return false;
		}else if(!frm.re_pw.value.trim()){
			alert("비밀번호 재확인을 입력해주세요");
			frm.re_pw.focus();
			return false;
		}else if(!frm.name.value.trim()){
			alert("이름을 입력해주세요")
			frm.name.focus();
			return false;
		}else if(!isNaN(frm.name.value)){
			alert("이름을 다시 한 번 확인해주세요");
			frm.name.value = "";
			frm.name.focus();
			return false;
		}else if(!frm.email2.value.trim()){
			alert("이메일을 입력해주세요");
			frm.email2.focus();
			return false;
		}else if(!frm.tel2.value.trim()){
			alert("전화번호를 입력해주세요");
			frm.tel2.focus();
			return false;
		}else if(isNaN(frm.tel2.value)){
			alert("전화번호는 숫자로 입력해주세요");
			frm.tel2.value="";
			frm.tel2.focus();
			return false;
		}else if(!frm.tel3.value.trim()){
			alert("전화번호를 입력해주세요");
			frm.tel3.focus();
			return false;
		}else if(isNaN(frm.tel3.value)){
			alert("전화번호는 숫자로 입력해주세요");
			frm.tel3.value="";
			frm.tel3.focus();
			return false;
		}else if(!frm.addr.value.trim()){
			alert("주소를 입력해주세요");
			frm.addr.focus();
			return false;
		}else if(frm.idChecknum.value == 0){
			alert("아이디 중복검사를 해주세요");
			return false;
		}
		frm.submit();
	}
function idCheck(){
	var id = document.getElementById("id").value;
	if(id==""){
		alert("아이디를 먼저 입력해주세요");
		return false;
	}else{
		window.open("/member/idCheck?id=" + id ,"","width=350 height=130");
	}
}