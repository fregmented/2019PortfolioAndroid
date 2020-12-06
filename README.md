# Hanwool_Lotto
포트폴리오에 제출된 lotto앱은 기술 시연용으로 개발한 앱으로, 기존 로또 번호를 조회하고 새로운 로또 번호를 만들어 기 회차의 로또 번호와 비교하는 앱으로 대략 3일의 시간동안 구현한 것입니다.

로또 번호를 조회하기 위해 okhttp3와 retrofit을 이용하였고, RxJAVA를 사용하려 했으나 RESTful한 API가 아닌 관계로 커스텀 이벤트버스를 구현하여 RxJAVA를 활용하였습니다.

또한 GSON을 이용한 DTO를 구현하여 native Kotlin data class를 이용할 수 있도록 하였습니다.

로또번호를 저장하기 위해 Realm 데이터베이스를 이용하였습니다.

UI면에서는 DataBinding과 RecyclerView를 이용하여 코드의 간략화를 꾀하였고, 본인이 이해한 MVVM 아키텍처를 최대한 구현하기 위해 노력하였습니다.

UX적인 면에서는 Navigation 라이브러리를 사용하여 자연스러운 화면 이동을 꽤하면서 Fragment 관리의 어려움을 최소하하였습니다.

시연 실행파일은 github의 [release페이지](https://github.com/fregmented/Lotto/releases/tag/v1.0_apk)에서 받을 수 있습니다.
