# wanted-pre-onboarding-backend
## 성명: 민인아
## 데이터베이스 테이블 구조
![image](https://github.com/minina0407/wanted-pre-onboarding-backend/assets/83204216/7d2e3c5e-44fd-457f-9af5-9a9694cd32e4)
## 애플리케이션 실행방법
1. Docker 설치되어있는지 확인 , 없다면 Docker 설치
2. 터미널에서 다음 명령어 실행해서 프로젝트 클론
 ```
git clone https://github.com/minina0407/wanted-pre-onboarding-backend.git
 ```
3. 클론한 디렉토리로 이동
 ```
cd wanted-pre-onboarding-backend
 ```
4. resources 폴더 생성 후 application.properties 파일 작성
### application.properties 
 ```
spring.profiles.active=local
spring.application.instance-id=${HOSTNAME:LOCAL}
spring.application.name=BulletinBoard
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database=mysql
spring.data.web.pageable.default-page-size=10

spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://<db 주소>:3306/<db 이름>?characterEncoding=UTF-8&serverTimezone=Asia/Seoul
spring.datasource.username=<user 이름>
spring.datasource.password=<패스워드>

spring.jackson.serialization.FAIL_ON_EMPTY_BEANS=false
logging.level.root=DEBUG
logging.file.path=/logs/instargram/api/${spring.application.instance-id}
server.servlet.context-path=/
server.servlet.encoding.enabled=true
server.servlet.encoding.charset=UTF-8
server.servlet.encoding.force=true
server.error.whitelabel.enabled=false
spring.mvc.throw-exception-if-no-handler-found=true

jwt.secret=<JWT Secret Key>
jwt.refreshSecret=<JWT Refresh Secret Key>
jwt.expirationDateInMinute=60000000
jwt.refreshTokenExpirationDateInMinute=90000000
 ```
5. docker-compose.yml 파일 작성
 ```
 sudo vi docker-compose.yml
 ```
### docker-compose.yml 파일
```
version: '3'
services:
  app:
    build:
      context: .
      dockerfile: Dockerfile
    restart: always
    ports:
      - "8080:8080"
    container_name: BulletinBoard
    environment:
      - SPRING_DATASOURCE_URL=jdbc:mysql://<db 주소>:3306/<db 이름>?characterEncoding=UTF-8&serverTimezone=Asia/Seoul
      - SPRING_DATASOURCE_USERNAME=<user 이름>
      - SPRING_DATASOURCE_PASSWORD=<패스워드>
    networks:
      - springboot-mysql-net

networks:
  springboot-mysql-net:
    driver: bridge

```
6. Docker compose를 이용하여 애플리케이션을 빌드하고 실행
    ```
    docker-compose up -d --bulid
     ```
7. 애플리케이션을 종료하려면 다음 명령어를 사용
  ```
docker-compose down
  ```

## 구현 방법 및 이유에 대한 간략한 설명
###  회원가입 엔드포인트
- 이메일 유효성 검사
@Email 어노테이션을 이용하여 이메일 형식을 체크합니다. 이는 사용자가 회원가입을 할 때 바르게 formatted된 이메일을 제공해야 함을 보장합니다.
- 비밀번호 유효성 검사
@Size 어노테이션을 이용하여 비밀번호를 검증합니다. 이를 통해 비밀번호가 8글자 이상이 되도록 강제합니다.
- 비밀번호 암호화
passwordEncoder를 이용하여 사용자의 비밀번호를 암호화하여 저장합니다.
###  로그인 엔드포인트
- Spring Security를 활용하여 이메일, 비밀번호를 검증 
- 사용자의 이메일과 비밀번호를 검증하고, 성공시 해당 사용자에게 권한을 부여합니다. 그리고 JWT(Jason Web Token)에 사용자 ID, 이메일, 권한을 포함시키고, 이 토큰을 리스폰 헤더에 넣어 전송합니다.(refresh Token, access Token) 
### 새로운 게시글 생성하는 엔드포인트
게시글 작성시 content, title을 받아 컨트롤러에서 게시글을 만들어 DB에 저장
ROLE_USER,ROLE_ADMIN이 아닐 경우 작성하지 못하도록 예외 처리 
### 게시글 목록 조회하는 엔드포인트
게시글 페이지네이션 처리
로그인 하지않아도 조회 가능
### 특정 게시글 조회하는 엔드포인트
PathVariable로 게시글 번호를 받아 게시글번호의 게시글을 리턴
로그인 하지 않아도 조회 가능 
### 특정 게시글 수정하는 엔드포인트
PathVariable로 게시글 번호, Body로 수정내용을 받아 게시글 수정처리
getMyUserWithAuthorities() 함수 이용해서 JWT 검증하여 작성자만 수정 가능
작성자가 아닌 경우 예외 처리 
### 특정 게시글을 삭제하는 엔드포인트
PathVariable로 게시글 번호를 받아 게시글을 삭제처리
getMyUserWithAuthorities() 함수 이용해서 JWT 검증하여 작성자만 수정 가능
작성자가 아닌 경우 예외 처리 
### docker compose를 이용하여 애플리케이션 배포
 도커 이미지로 생성 ->  DB, 서버이미지를 도커 컴포즈로 구성하여 배포
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
- 가입 성공일 경우, 
```javascript
HTTP 200 OK
```
- 이미 가입된 이메일인 경우
```
{
    "code": "400",
    "description": "이미 가입되어 있는 이메일입니다."
}
```
- 이메일 형식이 올바르지 않을 경우 
```
{
    "code": "400",
    "description": "올바른 이메일을 입력해주세요"
}
```
- 비밀번호가 8자 이하인 경우
```
{
    "code": "400",
    "description": "비밀번호는 8자 이상이어야 합니다."
}
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
- 비밀번호 / 이메일이 틀린 경우
  ```
{
    "code": "502",
    "description": "Bad credentials"
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

```
json
{
    "id": 1,
    "userId": 1,
    "title": "test",
    "content": "content",
    "createdAt": "2023-08-02T14:49:53",
    "updatedAt": "2023-08-02T14:55:05"
}
```
- postId가 없는 id일경우(존재하지않는 post인 경우)
```
{
    "code": "404",
    "description": "게시글을 찾을 수 없습니다."
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
- 다른사람이 해당 게시글을 수정할려고 요청 경우 
```
{
    "code": "502",
    "description": "Access is denied"
}```
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
- 다른사람이 해당 게시글을 삭제할려고 요청한 경우 
```
{
    "code": "502",
    "description": "Access is denied"
}```
