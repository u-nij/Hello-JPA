package hellojpa;

import jdk.swing.interop.SwingInterOpUtils;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class JpaMain {

    public JpaMain() {
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

/*
        // ===================== JPA =====================

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // code
        try {
//            Member member = new Member();
//
//            // 생성
//            member.setId(1L);
//            member.setName("HelloA");

            Member findMember = em.find(Member.class, 1L);

            // 조회
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.name = " + findMember.getName());

            // 수정
            findMember.setName("HelloJPA");

            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(5)
                    .setMaxResults(8)
                    .getResultList();

            for (Member member : result) {
                System.out.println("member = " + member.getName());
            }

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
*/

/*
        // =========== 영속성 매핑 (Persistence Mapping) ===========

        EntityManager emPM = emf.createEntityManager();
        EntityTransaction txPM = emPM.getTransaction();
        txPM.begin();

        try {
            // 비영속
            Member member = new Member(101L, "HelloJPA");

            // 영속
            System.out.println("===== BEFORE =====");
            emPM.persist(member);
            System.out.println("===== AFTER =====");

            Member findMember = emPM.find(Member.class, 101L);
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.Name = " + findMember.getName());

            Member a = emPM.find(Member.class, 101L);
            Member b = emPM.find(Member.class, 101L);
            System.out.println(a == b); // 동일성 비교

            // 변경 감지
            Member member1 = emPM.find(Member.class, 150L);
            member1.setName("AAAAA");
            emPM.persist(member1);

            System.out.println("========================");

            Member member2 = emPM.find(Member.class, 151L);
            member2.setName("BBBBB");

            Member member200 = new Member(200L, "member200");
            emPM.persist(member200);
            emPM.flush();
            System.out.println("========================");

            // 준영속
            Member find200 = emPM.find(Member.class, 200L);
            find200.setName("AAAAA");
            emPM.detach(find200); // AAAAA로 변경되지 않음

            txPM.commit();
        } catch (Exception e) {
            txPM.rollback();
        } finally {
            emPM.close();
        }
*/

/*
        // =========== 엔티티 매핑 (Entity Mapping) ===========

        EntityManager emEM = emf.createEntityManager();
        EntityTransaction txEM = emEM.getTransaction();
        txEM.begin();

        try {
            Member member1 = new Member();
            member1.setUsername("A");
            Member member2 = new Member();
            member2.setUsername("B");
            Member member3 = new Member();
            member3.setUsername("C");

            System.out.println("============================");

                                    // DB MEMBER_SEQ : -49
            emEM.persist(member1);    // DB MEMBER_SEQ : 1->51(2번 호출)  |  APP 1
            emEM.persist(member2);    // APP 2 (DB 부르지 않고, MEM에서 호출)
            emEM.persist(member3);    // APP 3 (DB 부르지 않고, MEM에서 호출)

            System.out.println("member.id = " + member1.getId());
            System.out.println("member.id = " + member2.getId());
            System.out.println("member.id = " + member3.getId());

            System.out.println("============================");

            txEM.commit();
        } catch (Exception e) {
            txEM.rollback();
        } finally {
            emEM.close();
        }
*/

/*
        // =========== 연관관계 매핑 기초 ===========

        EntityManager emRM = emf.createEntityManager();
        EntityTransaction txRM = emRM.getTransaction();
        txRM.begin();

        try {
            Team team = new Team();
            team.setName("TeamA");
            emRM.persist(team); // 순수한 team 객체 상태

            Member2 member = new Member2();
            member.setUsername("member1");
//            member.setTeamId(team.getId());  // 1) 연관관계 없을 때
//            member.changeTeam(team); // 2) 단방향 연관관계 설정, 참조 저장 // 3) 양방향
            emRM.persist(member);

//            team.getMembers().add(member); // team 객체에 member 객체 추가 -> Member2.java에 추가
            // members 리스트에 들어간 것이 없음

            // * Team에 Member 넣는 방법 (Member2의 changeTeam() 주석 처리해야 함)
            team.addMember(member);

//            em.flush();
//            em.clear();

            // 깨끗해진 상태

            Team findTeam = emRM.find(Team.class, team.getId()); // DB에서 새로 데이터를 가져옴
            List<Member2> members = findTeam.getMembers();

            // 리스트에 세팅한 것이 없음에도 값이 출력됨

            // 조회
//            Member2 findMember = emRM.find(Member2.class, member.getId());

            // Team findTeam = emRM.find(Team.class, team.getId()); // 1) 연관관계 없음
            // Team findTeam = findMember.getTeam();   // 2)
            // List<Member2> members = findMember.getTeam().getMembers(); // 3)E

            // System.out.println("findTeam = " + findTeam.getName()); // 1, 2)
            for (Member2 m : members) { // 3)
                System.out.println("m = " + m.getUsername());
            }

            txRM.commit();
        } catch (Exception e) {
            txRM.rollback();
        } finally {
            emRM.close();
        }
*/

/*
        // =========== 고급 매핑 ===========

        EntityManager emInheritance = emf.createEntityManager();
        EntityTransaction txInheritance = emInheritance.getTransaction();
        txInheritance.begin();

        try {
            Movie movie = new Movie();
            movie.setDirector("감독");
            movie.setActor("배우");
            movie.setName("바람과 함께 사라지다");
            movie.setPrice(10000);

            emInheritance.persist(movie);

            emInheritance.flush();
            emInheritance.clear();

            Movie findMovie = emInheritance.find(Movie.class, movie.getId());
            System.out.println("findMovie = " + findMovie);

//            Item findItem = em.find(Item.class, movie.getId());
//            System.out.println("findItem = " + findItem);

            // @MappedSuperclass
            Member_mapping member = new Member_mapping();
            member.setCreatedBy("kim");
            member.setCreateDate(LocalDateTime.now());

            emInheritance.persist(member);

            emInheritance.flush();
            emInheritance.clear();

            txInheritance.commit();
        } catch (Exception e) {
            txInheritance.rollback();
            e.printStackTrace();
        } finally {
            emInheritance.close();
        }
*/

/*
        // =========== 프록시(Proxy) ===========

        EntityManager emProxy = emf.createEntityManager();
        EntityTransaction txProxy = emProxy.getTransaction();
        txProxy.begin();

        try {
            Member_Proxy member = new Member_Proxy();
            member.setUsername("hello");
            emProxy.persist(member);

            Member_Proxy member2 = new Member_Proxy();
            member2.setUsername("hello2");
            emProxy.persist(member2);

            emProxy.flush();
            emProxy.clear();

//            Member_Proxy findMember = em.find(Member_Proxy.class, member.getId());
            Member_Proxy findMember = emProxy.getReference(Member_Proxy.class, member.getId());
            System.out.println("findMember.username = " + findMember.getUsername());

            // 클래스 확인
            System.out.println("findMember.getClass() = " + findMember.getClass());
            System.out.println("findMember.getClass().getName() = " + findMember.getClass().getName());

            // find 호출 후, gerReference 호출
            Member_Proxy member1 = new Member_Proxy();
            member1.setUsername("hello");
            emProxy.persist(member1);

            emProxy.flush();
            emProxy.clear();

            Member_Proxy member1_ = emProxy.find(Member_Proxy.class, member1.getId());
            System.out.println("member = " + member1_.getClass());

            Member_Proxy reference = emProxy.getReference(Member_Proxy.class, member1.getId());
            System.out.println("reference = " + reference.getClass()); // 실제 값

            // JPA에서 == 비교시 한 영속성 컨텍스트에서 가져왔거나, pk가 같으면 항상 true 반환
            System.out.println("a == a : " + (member1_ == reference));

            emProxy.flush();
            emProxy.clear();

            // gerReference 호출 후, find 호출

            Member_Proxy member1__ = new Member_Proxy();
            member1__.setUsername("hello");
            emProxy.persist(member1__);

            emProxy.flush();
            emProxy.clear();

            Member_Proxy refMember = emProxy.getReference(Member_Proxy.class, member1__.getId());
            System.out.println("refMember = " + refMember.getClass()); // 프록시

            Member_Proxy fMember = emProxy.find(Member_Proxy.class, member1__.getId());
            System.out.println("fMember = " + fMember.getClass()); // 멤버

            System.out.println("refMember == fMember : " + (member1_ == fMember));

            emProxy.detach(refMember);
            refMember.getUsername();
            // tx.commit(); 전까지 주석처리 후 사용

            emProxy.flush();
            emProxy.clear();

            // 타입 체크
            Member_Proxy m1 = emProxy.find(Member_Proxy.class, member.getId());
            Member_Proxy m2_find = emProxy.find(Member_Proxy.class, member2.getId());
            System.out.println("m1 == m2_find : " + (m1.getClass() == m2_find.getClass())); // true

            emProxy.flush();
            emProxy.clear();

            Member_Proxy m1_ = emProxy.find(Member_Proxy.class, member.getId());
            Member_Proxy m2_getReference = emProxy.getReference(Member_Proxy.class, member2.getId());
            System.out.println("m1 == m2_getReference : " + (m1_.getClass() == m2_getReference.getClass())); // false

            // 초기화 체크
            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(m2_getReference));
            Hibernate.initialize(m2_getReference); // 강제 초기화
            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(m2_getReference));

            txProxy.commit();
        } catch (Exception e) {
            txProxy.rollback();
            e.printStackTrace();
        } finally {
            emProxy.close();
        }
*/

/*
        // =========== 즉시 로딩과 지연 로딩 ===========

        EntityManager emLoading = emf.createEntityManager();
        EntityTransaction txLoading = emLoading.getTransaction();
        txLoading.begin();

        try {
            Team team = new Team();
            team.setName("teamA");
            emLoading.persist(team);

            Member_Proxy member1 = new Member_Proxy();
            member1.setUsername("member1");
            member1.setTeam(team);
            emLoading.persist(member1);

            emLoading.flush();
            emLoading.clear();

//            Member_Proxy m = em.find(Member_Proxy.class, member1.getId());
//
//            System.out.println("m = " + m.getTeam().getClass());
//
//            System.out.println("============");
//            m.getTeam().getName();
//            System.out.println("============");

            List<Member_Proxy> members = emLoading.createQuery("select m from Member m", Member_Proxy.class)
                    .getResultList();
            // SQL: select * from Member
            // SQL: select * from Team where TEAM_ID = XXX

            txLoading.commit();
        } catch (Exception e) {
            txLoading.rollback();
            e.printStackTrace();
        } finally {
            emLoading.close();
        }
*/

/*
        // =========== 영속성 전이(CASCADE)와 고아 객체 ==========

        EntityManager emCasOrp = emf.createEntityManager();
        EntityTransaction txCasOrp = emCasOrp.getTransaction();
        txCasOrp.begin();

        try {
            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            emCasOrp.persist(parent); // cascade = CascadeType.ALL 옵션 있을 때

            emCasOrp.flush();
            emCasOrp.clear();

            Parent findParent = emCasOrp.find(Parent.class, parent.getId());
            findParent.getChildList().remove(0); // orphanRemoval = true 옵션 있을 때

            txCasOrp.commit();
        } catch (Exception e) {
            txCasOrp.rollback();
            e.printStackTrace();
        } finally {
            emCasOrp.close();
        }
*/

        // =========== 값 타입 ===========

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
//            Address address = new Address("city", "street", "10000");
//            Member_Type member = new Member_Type();
//            member.setUsername("member1");
//            member.setHomeAddress(address);
//            em.persist(member);

            /*
            Address copyAddress = new Address(address.getCity(), address.getCity(), address.getZipcode());

            Member_Type member2 = new Member_Type();
            member2.setUsername("member2");
            member2.setHomeAddress(copyAddress);
            em.persist(member2);

            member.getHomeAddress().setCity("newCity");
             */

            // 불변 객체 Address
            // 새로 만들어서 통째로 갈아 끼워야 함
//            Address newAddress = new Address("NewCity", address.getCity(), address.getZipcode());
//            member.setHomeAddress(newAddress);

            Member_Type member = new Member_Type();
            member.setUsername("member");
            member.setHomeAddress(new Address("homeCity", "street", "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new AddressEntity("old1", "street", "10000"));
            member.getAddressHistory().add(new AddressEntity("old2", "street", "10000"));

            em.persist(member);

            em.flush();
            em.clear();


            System.out.println("================ 조회 ================");
            Member_Type findMember = em.find(Member_Type.class, member.getId()); // SELECT문

            // 조회
//            List<Address> addressHistory = findMember.getAddressHistory();
//            for (Address address : addressHistory) { // iter
//                System.out.println("address = " + address.getCity());
//            }

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for (String favoriteFood : favoriteFoods) {
                System.out.println("favoriteFood = " + favoriteFood);
            }

            // 수정
            // homeCity -> newCity
//            findMember.getHomeAddress().setCity("newCity"); // 하면 안됨
            Address a = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipcode()));

            // 치킨 -> 한식
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

//            findMember.getAddressHistory()
//                    .remove(new Address("old1", "street", "10000"));
//                    // equals()와 hashCode()가 제대로 구현되어야 하는 이유
//            findMember.getAddressHistory()
//                    .add(new Address("newCity", "street", "10000"));


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        
        emf.close();
    }
}