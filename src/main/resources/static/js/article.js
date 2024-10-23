const deleteBtn = document.getElementById('delete-btn');
const modifyBtn = document.getElementById('modify-btn');
const createBtn = document.getElementById('create-btn');

if (deleteBtn) {
  deleteBtn.addEventListener('click', (e) => {
    let id = document.getElementById('article-id').value;
    fetch(`/api/article/${id}`, {
      method: 'DELETE'
    }).then((response) => {
      if (response.ok) {
        alert('삭제가 완료되었습니다.');
        location.replace('/articles');
      } else {
        alert('이미 삭제된 글이거나, 게시물을 찾을 수 없습니다.');
      }
    })
  })
}

if (modifyBtn) {
  modifyBtn.addEventListener('click', (e) => {
    // let params = new URLSearchParams(location.search);
    let id = document.getElementById('article-id').value;
    fetch(`/api/article/${id}`, {
      method: 'PUT',
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        title: document.getElementById('title').value,
        content: document.getElementById('content').value
      })
    }).then((response) => {
      if (response.ok) {
        alert('수정이 완료되었습니다.');
        location.replace('/articles');
      }
    })
  })
}

if (createBtn) {
  createBtn.addEventListener('click', (e) => {
    fetch(`/api/article`, {
      method: 'POST',
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        title: document.getElementById('title').value,
        content: document.getElementById('content').value
      })
    }).then((response) => {
      if (response.ok) {
        alert('작성이 완료되었습니다.');
        location.replace('/articles');
      }
    })
  })
}