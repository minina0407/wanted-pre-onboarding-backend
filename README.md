# wanted-pre-onboarding-backend
## 성명: 민인아
## 데이터베이스 테이블 구조
![image](https://github.com/minina0407/wanted-pre-onboarding-backend/assets/83204216/7d2e3c5e-44fd-457f-9af5-9a9694cd32e4)

## API 명세서

## 사용자 관련

### 1. 사용자 회원가입

**endpoint**

```javascript
POST /api/v1/users
```

**request 파라미터**

| Parameter | Type   | Description |
| --------- | ------ | ----------- |
| email     | String | 사용자 이메일 주소  |
| password  | String | 사용자 비밀번호    |

**request 예시**

json

```json
{
    "email": "example@example.com",
    "password": "password1234"
}
```

**response**

```javascript
HTTP 200 OK
```

---

### 2. 사용자 로그인

**endpoint**

```javascript
POST /api/v1/auth/login
```

**request 파라미터**

| Parameter | Type   | Description |
| --------- | ------ | ----------- |
| email     | String | 사용자 이메일 주소  |
| password  | String | 사용자 비밀번호    |

**request 예시**

json

```json
{
    "email": "example@example.com",
    "password": "password1234"
}
```

**response**

json

```json
{
    "accessToken": "{JWT 액세스 토큰}",
    "refreshToken": "{JWT 리프래쉬 토큰}"
}
```

## 게시글 관련

### 3. 특정 게시글 조회

**endpoint**

```javascript
GET /api/v1/posts/{postId}
```

**response**

| Parameter | Type   | Description |
| --------- | ------ | ----------- |
| Id    | Long   | 게시글 ID      |
| title     | String | 게시글 제목      |
| userId  | Long |  유저 아이디      |
| content   | String | 게시글 내용      |
| createdAt   | LocalDateTime | 게시글 작성 시각      |
| updateddAt   | LocalDateTime | 게시글 수정 시각      |


**response 예시**

json

```json
{
    "id": 1,
    "userId": 1,
    "title": "test",
    "content": "content",
    "createdAt": "2023-08-02T14:49:53",
    "updatedAt": "2023-08-02T14:55:05"
}
```

---

### 4. 게시글 목록 조회

**endpoint**

```javascript
GET /api/v1/posts
```

**response**

json

```json
{
    "posts": [
        {
            "id": 1,
            "userId": 1,
            "title": "test",
            "content": "test",
            "createdAt": "2023-08-02T14:49:53",
            "updatedAt": "2023-08-02T14:55:05"
        },
        {
            "id": 2,
            "userId": 1,
            "title": "test2",
            "content": "test2",
            "createdAt": "2023-08-02T15:13:28",
            "updatedAt": "2023-08-02T15:13:28"
        },
        {
            "id": 3,
            "userId": 1,
            "title": "test3",
            "content": "test3",
            "createdAt": "2023-08-02T15:15:02",
            "updatedAt": "2023-08-02T15:15:02"
        },
        {
            "id": 4,
            "userId": 1,
            "title": "test4",
            "content": "test4",
            "createdAt": "2023-08-02T15:15:39",
            "updatedAt": null
        }
    ],
    "total": 4,
    "totalPages": 1,
    "totalElements": 4
}
```

---

### 5. 새로운 게시글 생성

**endpoint**

```javascript
POST /api/v1/posts
```

**request 파라미터**

| Parameter | Type   | Description |
| --------- | ------ | ----------- |
| title     | String | 게시글 제목      |
| content   | String | 게시글 내용      |

**request 예시**

json

```json
{
    "title": "새로운 게시글",
    "content": "이것은 새로운 게시글입니다."
}
```

**response**

```javascript
HTTP 200 OK
```

---

### 6. 특정 게시글 수정

**endpoint**

```javascript
PUT /api/v1/posts/{postId}
```

**request 파라미터**

| Parameter | Type   | Description |
| --------- | ------ | ----------- |
| title     | String | 게시글 제목      |
| content   | String | 게시글 내용      |

**request 예시**

json

```json
{
    "title": "수정된 제목",
    "content": "수정된 내용"
}
```

**response**

```javascript
HTTP 200 OK
```

---

### 7. 특정 게시글 삭제

**endpoint**

```javascript
DELETE /api/v1/posts/{postId}
```

**response**

```javascript
HTTP 200 OK
```

