/**
 * 게시글 등록 요청 함수
 */
async function submitPost() {
    // const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    // const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    // 1. 데이터 수집
    const data = {
        title: document.getElementById('title').value,
        content: document.getElementById('content').value
    };

    try {
        // 2. 서버 API 호출 (PostController의 createPost 메서드 실행)
        const response = await fetch('/api/posts', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                // [header]: token
            },
            body: JSON.stringify(data), // @RequestBody 매핑을 위해 JSON 직렬화
            credentials: 'include'
        });

        // 3. 응답 처리
        if (response.ok) {
            alert('글이 등록되었습니다!');
            location.href = '/posts'; // 목록 페이지로 이동
        } else {
            // PostService의 검증 실패 메시지 또는 GlobalExceptionHandler의 에러 메시지 출력
            const msg = await response.text();
            alert('등록 실패: ' + msg);
        }
    } catch (error) {
        console.error('Error:', error);
        alert('서버와 통신 중 에러가 발생했습니다.');
    }
}

// board.js 하단에 추가
async function deletePost(id) {
    // const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    // const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    if (!confirm('정말로 이 글을 삭제하시겠습니까?')) return;

    try {
        const response = await fetch(`/api/posts/${id}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                // [header]: token
            },
            credentials: 'include'
        });

        if (response.ok) {
            alert('글이 삭제되었습니다.');
            location.href = '/posts';
        } else {
            const msg = await response.text();
            alert('삭제 실패: ' + msg); // "삭제 권한이 없습니다" 등의 메시지 출력
        }
    } catch (error) {
        console.error('Error:', error);
        alert('삭제 중 오류가 발생했습니다.');
    }
}

// board.js 하단에 추가
async function updatePost() {
    // const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    // const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    const id = document.getElementById('postId').value;
    const data = {
        title: document.getElementById('title').value,
        content: document.getElementById('content').value
    };

    if (!confirm('수정하시겠습니까?')) return;

    try {
        // PostController의 @PutMapping("/{id}") 호출
        const response = await fetch(`/api/posts/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                // [header]: token
            },
            body: JSON.stringify(data),
            credentials: 'include'
        });

        if (response.ok) {
            alert('수정되었습니다.');
            location.href = `/posts/${id}`; // 수정 후 상세 페이지로 이동
        } else {
            const msg = await response.text();
            alert('수정 실패: ' + msg); // "수정 권한이 없습니다" 등의 메시지 처리
        }
    } catch (error) {
        console.error('Error:', error);
        alert('수정 중 오류가 발생했습니다.');
    }
}