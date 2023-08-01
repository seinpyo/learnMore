// axios
const posts = [
  { id: 1, title: '제목1', contents: '내용1', createdAt: '2023-08-01' },
  { id: 2, title: '제목2', contents: '내용2', createdAt: '2023-08-01' },
  { id: 3, title: '제목3', contents: '내용3', createdAt: '2023-08-01' },
  { id: 4, title: '제목4', contents: '내용4', createdAt: '2023-08-01' },
  { id: 5, title: '제목5', contents: '내용5', createdAt: '2023-08-01' },
  { id: 6, title: '제목6', contents: '내용6', createdAt: '2023-08-01' },
  { id: 7, title: '제목7', contents: '내용7', createdAt: '2023-08-01' },
];

export function getPosts() {
  return posts;
}

export function getPostById(id) {
  const numberId = parseInt(id);
  return posts.find(item => item.id === numberId);
}
