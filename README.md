##  패키지 구조

```
com.example._teamcommerce
│
├─ Application.java                      # 메인. 시작점
│
├─ common                                # 여러 도메인이 공통으로 쓰는 것들
│  ├─ config
│  │  ├─ JpaAuditingConfig.java          # 생성/수정 시간 자동 기록 설정
│  │  ├─ PasswordEncoder.java            # 비밀번호 암호화 (BCrypt)
│  │  └─ WebConfig.java                  # 인터셉터 등록 등 웹 설정
│  ├─ entity
│  │  └─ BaseTimeEntity.java             # createdAt / modifiedAt 공통 필드
│  ├─ exception
│  │  ├─ ErrorCode.java                  # 에러 종류 정의 (상태코드 + 코드 + 메시지)
│  │  ├─ BusinessException.java          # 비즈니스 로직에서 던지는 예외
│  │  ├─ ErrorResponse.java              # 에러 응답 JSON 형식
│  │  └─ GlobalExceptionHandler.java     # 전역 예외 처리
│  ├─ session
│  │  ├─ SessionConst.java               # 세션 관련 상수
│  │  └─ LoginCheckInterceptor.java      # 로그인 여부 검사 인터셉터
│  └─ dto
│     └─ PageResponse.java               # 목록 조회 공통 페이징 응답
│
├─ admin                                 # "관리자" 도메인
│  ├─ controller
│  │  ├─ AdminSignupController.java      # 회원가입
│  │  ├─ AdminAuthController.java        # 로그인 / 로그아웃
│  │  ├─ AdminController.java            # 목록 / 상세 / 수정 / 역할 / 상태 / 삭제 / 승인 / 거부
│  │  └─ AdminProfileController.java     # 내 프로필 조회 / 수정 / 비밀번호 변경
│  ├─ service
│  │  ├─ AdminSignupService.java
│  │  ├─ AdminAuthService.java
│  │  └─ AdminService.java
│  ├─ repository
│  │  └─ AdminRepository.java
│  ├─ entity
│  │  └─ Admin.java
│  ├─ type
│  │  ├─ AdminRole.java                  # SUPER_ADMIN / OPERATION_ADMIN / CS_ADMIN
│  │  └─ AdminStatus.java                # PENDING / ACTIVE / INACTIVE / SUSPENDED / REJECTED
│  └─ dto
│     ├─ request                         # 요청 DTO (유효성 검증 포함)
│     ├─ response                        # 응답 DTO
│     └─ session
│        └─ AdminSession.java            # 세션에 저장하는 로그인 정보
│
├─ customer                              # "고객" 도메인
│  ├─ controller
│  ├─ service
│  ├─ repository
│  ├─ entity
│  ├─ type
│  └─ dto (request / response)
│
├─ product                               # "상품" 도메인
│  ├─ controller
│  ├─ service
│  ├─ repository
│  ├─ entity
│  ├─ type
│  └─ dto (request / response)
│
└─ order                                 # "주문" 도메인
   ├─ controller
   ├─ service
   ├─ repository
   ├─ entity
   ├─ type
   └─ dto (request / response)
```

## 패키지 구조 설계 이유
이렇게 패키지 구조를 설계한 이유는 크게 2가지.
```
첫째, 역할이 겹치지 않게 (도메인별, 계층별, 파일별로 책임 분리 → 코드 이해가 쉬움)하고, 
둘째, 6명이 동시에 작업해도 충돌이 안 나게 (폴더/파일 단위로 작업 영역 분리 + 공통 코드는 먼저 확정 후 시작)하기 위해서
```
입니다.

### 1. 도메인 기준으로 나눴습니다 (admin / customer / product / order)

- 계층(controller, service...)이 아니라 **도메인(업무 대상)** 기준으로 최상위를 나눴습니다.
- 담당자가 자기 도메인 폴더 하나만 보면 관련 코드를 전부 볼 수 있습니다.
- 팀원별 작업 영역이 폴더 단위로 분리되어 **Git 충돌이 크게 줄어듭니다.**

### 2. 도메인 안에서는 3 Layer Architecture로 나눴습니다

- `controller` → 요청을 받는 입구 (HTTP 처리만 담당)
- `service` → 실제 비즈니스 로직 (중복 검증, 암호화, 상태 처리 등)
- `repository` → DB 접근 담당
- 각 계층이 자기 역할만 하기 때문에 문제가 생겼을 때 **어디를 봐야 할지 명확**합니다.

### 3. Admin 기능을 Controller/Service 파일 단위로 쪼갰습니다

- `AdminSignupController` / `AdminAuthController` / `AdminController` / `AdminProfileController`
- 기능 분담(admin-auth / admin-approval / admin-manage)과 파일이 1:1에 가깝게 대응되어,
  **각자 다른 파일에서 작업하므로 같은 파일을 동시에 수정할 일이 거의 없습니다.**

### 4. 공통 코드는 common 패키지로 분리했습니다

- 시간 자동 기록(BaseTimeEntity), 예외 처리(exception), 암호화(PasswordEncoder),
  세션(session), 페이징 응답(PageResponse)은 모든 도메인이 함께 씁니다.
- 도메인마다 복사해서 쓰지 않도록 한 곳에 모았습니다.
- 단, 공통 코드는 여러 명이 동시에 수정하면 충돌이 잦기 때문에,
  **담당자를 정해 develop에 먼저 병합한 후 각자 기능 개발을 시작합니다.** (협업 규칙 참고)

### 5. DTO를 request / response로 분리했습니다

- 들어오는 데이터(request)와 나가는 데이터(response)의 역할이 다르기 때문입니다.
    - request → 유효성 검증(@NotBlank, @Pattern 등)이 붙습니다.
    - response → 노출할 필드만 담습니다. (예: 비밀번호는 응답에서 제외)
- Entity를 API 응답에 직접 사용하지 않아 **민감 정보 노출을 원천 차단**합니다.

### 6. 상태값은 enum(type 패키지)으로 관리합니다

- `AdminRole`, `AdminStatus`처럼 정해진 값만 허용되는 데이터는 enum으로 분리했습니다.
- 문자열 오타로 인한 버그를 컴파일 시점에 막을 수 있고,
  DB에는 `EnumType.STRING`으로 저장해 데이터를 사람이 읽을 수 있게 했습니다.