# architecture-projects

# architecture-ee
Mybatis +Spring 사용이 일반화된 지금 대부분의 실 프로젝트를 자세히 보면 ORM 이 목적이 아닌 단순하게 쿼리와 자바 로직을 분리하는 정도로 사용하는 것으로 보인다. 
참고로 Hibernate는 여러 장점이 있으나 투자한 시간 대비 성능적으로 너무 실망스러웠다.

또한 국내의 특성상 변경가능성 역시 높기 때문에 실시간 쿼리 적용 환경이 필요하다.

이런 이유에서 Mybatis와 Hibernate API 및 코드를 참고하여 XML기반의 전통적인 JDBC 프로그래밍을 손쉬게 구현하고자 한다. 

