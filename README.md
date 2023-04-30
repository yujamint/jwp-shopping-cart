# jwp-shopping-cart

## 상품 목록 페이지 연동
- index.html 파일을 이용하여 상품 목록이 노출되는 페이지를 완성합니다. 
- `/` url로 접근할 경우 상품 목록 페이지를 조회할 수 있어야 합니다.
- 상품이 가지고 있는 정보는 다음과 같습니다. 필요한 경우 상품 정보의 종류를 추가할 수 있습니다.(ex. 상품 설명, 상품 카테고리)
  - 상품 ID
  - 상품 이름
  - 상품 이미지
  - 상품 가격

## 상품 관리 CRUD API 작성
- 상품 생성, 상품 목록 조회, 상품 수정, 상품 삭제 API를 작성합니다. 
- API 스펙은 정해진것이 없으므로 API 설계를 직접 진행 한 후 기능을 구현 합니다.

### API 설계
- 상품 생성: 이름,이미지,가격 필요
- 상품 목록 조회: 전체 조회
- 상품 수정: 모든 정보 수정 가능
- 상품 삭제: 단건 삭제

## 관리자 도구 페이지 연동
- admin.html 파일과 상품 관리 CRUD API를 이용하여 상품 관리 페이지를 완성합니다.
- `/admin` url로 접근할 경우 관리자 도구 페이지를 조회할 수 있어야 합니다
- 상품 추가, 수정, 삭제 기능이 동작하게 만듭니다.

## 요구사항 외 추가 적용 요소
- [x] 예외처리
- [ ] 테스트 작성
  - [x] 컨트롤러
    - [x] 조회: 상태 코드, 응답 Body 검증
    - [x] 뷰 반환: 상태 코드, 모델 attribute 검증
    - [x] 삽입: 상태 코드, 실패 케이스 검증
    - [x] 수정: 상태 코드, 실패 케이스 검증
    - [x] 삭제: 상태 코드, 실패 케이스 검증
  - [x] Dao
    - [x] 삽입: 삽입이 정상적으로 이루어졌는지
    - [x] 조회: 삽입한 데이터가 잘 들어갔는지
    - [x] 수정: 수정 전후 데이터 비교
    - [x] 삭제: 실제로 데이터가 삭제됐는지
- [ ] Spring Validation


### 궁금한 내용
- 가지고 있는 데이터는 같지만, 사용되는 계층이 다른 Dto 처리 방법
- DTO <-> Entity 변환 방법 (toEntity(), Mapper 클래스, ...)
- 현재 단계에서는 서비스가 가지는 역할이 Dao 호출뿐인 것 같다. 이러한 경우에는 서비스 계층을 생략해도 좋을까?
- 처음에 생각한 API 설계는 CRUD를 다루는 것이라고 생각해 따로 CartApiController로 분리해주었는데 View 반환은 Api가 아닌가 하는 의문점
- 현재 컨트롤러 테스트는 상태 코드와 응답값만 검증하고 있고, DB에 실제로 변경사항(삽입,수정,삭제)이 반영되는지는 검증하지 않고 있다. 실제 변경사항 검증은 Dao 테스트에 맡기고, 컨트롤러는 이정도의 테스트만 진행해도 괜찮을지?
- 실제로 없는 id의 행을 update 할 때, 변경사항이 전혀 없고 예외도 발생하지 않는 것을 확인함. 클라이언트는 수정이 되었을 거라고 예상할텐데 이를 정상적인 흐름으로 봐도 될지? 또는 예외처리를 통해 잘못된 요청이 들어오고 있다고 알려야될지?


## 2단계

### 기능 구현 전 리팩토링
- [x] 삽입/수정/삭제가 반환값을 가지도록 하고, 해당 반환값이 컨트롤러까지 전달되도록 수정
- [x] 단건 조회 API 생성
- [x] 변경된 반환값에 따른 테스트 실행
- [x] DTO - Entity Mapper 클래스 생성
- [x] 불필요한 @Autowired 생략
- [ ] @Valid를 활용한 유효성 검증
- [x] API URL 설계 다시 하기

### 궁금한 내용
- [ ] 상품 추가 요청에 대한 컨트롤러의 응답으로 엔티티를 반환하도록 했다. 이때 상태 코드는 CREATED로 설정하며, location 헤더에 단건 조회 API URL을 담았다. 프론트 쪽에서는 이 응답을 보고 단건 조회 API를 담았다는 것을 바로 알기 힘들 것 같다.
  - 왜냐하면, 어떤 HTTP 요청을 보내야 되는지에 대한 정보 없이, URI만 있기 때문이다. URI를 이렇게 담아서 보내는 것이 맞는지, 올바르게 사용한 것인지 궁금하다.
- [ ] 상품 추가의 결과로 엔티티를 반환하고 있다. 하지만 업데이트와 삭제의 결과로는 변경된 row 수를 반환하고 있다. 통일성이 없는 것일까? 아니면, 프론트엔드에서 필요로 하는 정보를 주는 게 중요한 것이지, 통일성은 크게 중요하지 않을까? 
