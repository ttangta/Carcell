function checkWrite() {
	var frm = document.form1;
	
	if(!frm.subject.value.trim()) {
		alert("제목을 입력하세요");
		frm.subject.focus();
	} else if(!frm.content.value.trim()) {
		alert("내용을 입력하세요");
		frm.content.focus();
	} else {
		frm.submit();
	}
}