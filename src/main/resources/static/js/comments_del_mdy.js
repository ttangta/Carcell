document.addEventListener('DOMContentLoaded', function() {

	// 모든 폼 숨기기 (댓글 수정, 대댓글 수정, 답글 폼 포함)
	function hideAllForms() {
		document.querySelectorAll('.comment-edit').forEach(editForm => {
			editForm.style.display = 'none';
		});
		document.querySelectorAll('.comment-content').forEach(content => {
			content.style.display = 'block';
		});
		
		document.querySelectorAll('.reply-edit').forEach(editForm => {
			editForm.style.display = 'none';
		});
		document.querySelectorAll('.reply-content').forEach(content => {
			content.style.display = 'block';
		});

		document.querySelectorAll('.reply-form').forEach(form => {
			form.style.display = 'none';
		});
	}

	// 댓글 수정 시작 함수
	function startEditingComment(commentNum) {
		hideAllForms();
		document.getElementById('comment-content-' + commentNum).style.display = 'none';
		document.getElementById('comment-edit-' + commentNum).style.display = 'block';
	}

	// 댓글 수정 취소 함수
	function cancelEditComment(commentNum) {
		document.getElementById('comment-edit-' + commentNum).style.display = 'none';
		document.getElementById('comment-content-' + commentNum).style.display = 'block';
	}

	// 댓글 수정 저장 함수 (수정 후 해당 위치로 이동)
	function updateComment(commentNum) {
		var textarea = document.getElementById('edit-textarea-' + commentNum);
		var updatedContent = textarea.value;

		fetch('/QnA/CommentUpdate', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify({
				num: commentNum,
				cmtcontent: updatedContent
			})
		})
			.then(response => response.json())
			.then(data => {
				if (data.success) {
					document.getElementById('comment-content-' + commentNum).innerHTML = updatedContent;
					cancelEditComment(commentNum);

					// 수정 완료 후 해당 댓글로 이동
					document.getElementById('comment-' + commentNum).scrollIntoView({ behavior: 'smooth' });
				} else {
					alert('내용을 입력해주세요!');
				}
			})
			.catch(error => {
				console.error('Error:', error);
				alert('수정 성공');
			});
	}

	// 대댓글 수정 시작 함수
	function startEditingReply(replyNum) {
		hideAllForms();
		document.getElementById('reply-content-' + replyNum).style.display = 'none';
		document.getElementById('reply-edit-' + replyNum).style.display = 'block';
	}

	// 대댓글 수정 취소 함수
	function cancelEditReply(replyNum) {
		document.getElementById('reply-edit-' + replyNum).style.display = 'none';
		document.getElementById('reply-content-' + replyNum).style.display = 'block';
	}

	// 대댓글 수정 저장 함수 (수정 후 해당 위치로 이동)
	function updateReply(replyNum) {
		var textarea = document.getElementById('reply-edit-textarea-' + replyNum);
		var replyContent = textarea.value;

		fetch('/QnA/CommentUpdate', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify({
				num: replyNum,
				cmtcontent: replyContent
			})
		})
			.then(response => response.json())
			.then(data => {
				if (data.success) {
					document.getElementById('reply-content-' + replyNum).innerHTML = replyContent;
					cancelEditReply(replyNum);

					// 수정 완료 후 해당 대댓글로 이동
					document.getElementById('reply-' + replyNum).scrollIntoView({ behavior: 'smooth' });
				} else {
					alert('내용을 입력해주세요!');
				}
			})
			.catch(error => {
				console.error('Error:', error);
				alert('수정 성공');
			});
	}

	// 답글 폼 토글 함수
	function toggleReplyForm(commentNum) {
		hideAllForms();
		var form = document.getElementById('reply-form-' + commentNum);
		form.style.display = (form.style.display === 'block') ? 'none' : 'block';
	}

	// 답글 추가 함수 (추가 후 해당 위치로 이동)
	function addReply(commentNum) {
	    var textarea = document.getElementById('reply-comment-' + commentNum);
	    var replyContent = textarea.value; // 사용자 입력 그대로 가져오기

	    // 여기서 replyContent의 \n은 그대로 유지
	    fetch('/QnA/addReply', {
	        method: 'POST',
	        headers: {
	            'Content-Type': 'application/x-www-form-urlencoded',
	        },
	        body: new URLSearchParams({
	            parentCommentId: commentNum,
	            replyContent: replyContent, // 이 부분은 그대로 사용
	            qnaSeq: document.getElementById('qna-seq').value
	        })
	    })
	    .then(response => response.json())
	    .then(data => {
	        if (data.success) {
	            // 페이지 리로드 후 스크롤 이동
	            location.reload();
	            // 새로고침 후 해당 답글 위치로 이동
	            setTimeout(function() {
	                var newReply = document.getElementById('reply-' + data.replyId);
	                if (newReply) {
	                    newReply.scrollIntoView({ behavior: 'smooth' });
	                }
	            }, 1000);  // 타이밍 조절
	        } else {
	            alert('답글 제출에 실패했습니다.');
	        }
	    })
	    .catch(error => {
	        console.error('Error:', error);
	        alert('답글 제출에 실패했습니다.');
	    });
	}



	// 댓글 수정 버튼 클릭 이벤트
	document.querySelectorAll('[data-edit]').forEach(button => {
		button.addEventListener('click', function() {
			startEditingComment(this.getAttribute('data-edit'));
		});
	});

	// 댓글 수정 취소 버튼 클릭 이벤트
	document.querySelectorAll('[data-cancel]').forEach(button => {
		button.addEventListener('click', function() {
			cancelEditComment(this.getAttribute('data-cancel'));
		});
	});

	// 댓글 업데이트 버튼 클릭 이벤트
	document.querySelectorAll('[data-update]').forEach(button => {
		button.addEventListener('click', function() {
			updateComment(this.getAttribute('data-update'));
		});
	});

	// 답글 달기 버튼 클릭 이벤트
	document.querySelectorAll('[data-reply]').forEach(button => {
		button.addEventListener('click', function() {
			toggleReplyForm(this.getAttribute('data-reply'));
		});
	});

	// 대댓글 수정 버튼 클릭 이벤트
	document.querySelectorAll('[data-reply-edit]').forEach(button => {
		button.addEventListener('click', function() {
			startEditingReply(this.getAttribute('data-reply-edit'));
		});
	});

	// 대댓글 수정 취소 버튼 클릭 이벤트
	document.querySelectorAll('[data-reply-cancel]').forEach(button => {
		button.addEventListener('click', function() {
			cancelEditReply(this.getAttribute('data-reply-cancel'));
		});
	});

	// 대댓글 업데이트 버튼 클릭 이벤트
	document.querySelectorAll('[data-reply-update]').forEach(button => {
		button.addEventListener('click', function() {
			updateReply(this.getAttribute('data-reply-update'));
		});
	});
});
