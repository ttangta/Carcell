function checkWrite() {
	var frm = document.forms['form1']; 

	if(!frm.eventsubject.value) {
		alert("제목을 입력하세요.");
		frm.eventsubject.focus();
		return false;
	}
	
	if(!frm.eventsubject2.value) {
		alert("서브제목을 입력하세요.");
		frm.eventsubject2.focus();
		return false;
	}

	if(!frm.eventcontent.value) {
		alert("내용을 입력하세요.");
		frm.eventcontent.focus();
		return false;
	}

	if(!frm.img.value) {
		alert("파일을 선택하세요.");
		frm.img.focus();
		return false;
	}
	
	frm.submit();
}