# 우리말 퀴즈
## 목표
MVVM패턴 및 코틀린기반의 안드로이드를 공부해보기 위한 앱입니다.
추가로 안드로이드 최신기술인 AAC(Android Architecture Component), Hilt를 직접 적용해보았습니다.

### 사용 데이터
서울 열린 데이터광장(https://data.seoul.go.kr/dataList/OA-2202/S/1/datasetView.do)

### 사용 기술
 WorkManager, Android(Kotlin), Hilt, Node.js(express), dataBinding, coroutine, Gson, MVVM

## 앱설명
우리말 맞춤법 및 올바른 표현에대한 퀴즈 앱으로 총 100문제로 구성되어있으며 1문제를 풀 때 연속해서 3번 틀릴경우에
일정 시간동안 문제를 풀 수 없습니다. 그리고 그 시간이 지나거나 문제를 맞출 시에는 틀린 횟수가 0번으로 초기화 됩니다.
랭킹메뉴에서는 각 유저의 랭킹을 확인할 수 있습니다.

## 기술활용
1. 문제를 3번 연속 틀렸을 시 시간 제한을 거는 과정에서 앱 종료 후 재실행 했을 때 예약된 백그라운드 작업을 처리하기 위해
WorkManager를 사용했고 반복적으로 현재 시간과 제한이 풀리는 시간을 비교하여 문제풀이를 제한할 것인지 허용할 것인지 처리했습니다.

2. 처음 적용해보는 MVVM디자인 패턴과 coroutine, dataBinding, hilt 기술을 사용해봄으로써 
자바 기반의 개발경험과 비교해보았을 때 coroutine을 활용한 비동기처리의 간편함을 느꼈습니다. 
그리고 LiveData와 dataBinding을 결합하여 xml파일에서 화면이 자동으로 업데이트 되도록 처리해주었고,
의존성주입 기술인 hilt를 활용하여 원하는 객체를 재생성 하는 것 없이 간편하게 사용할 수 있었지만 아직
공부가 더 필요한 기술이라고 느꼈습니다.



