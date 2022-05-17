package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {

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
//            Member2 findMember = em.find(Member2.class, member.getId());

            // Team findTeam = em.find(Team.class, team.getId()); // 1) 연관관계 없음
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


        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Movie movie = new Movie();
            movie.setDirector("감독");
            movie.setActor("배우");
            movie.setName("바람과 함께 사라지다");
            movie.setPrice(10000);

            em.persist(movie);

            em.flush();
            em.clear();

            Movie findMovie = em.find(Movie.class, movie.getId());
            System.out.println("findMovie = " + findMovie);

//            Item findItem = em.find(Item.class, movie.getId());
//            System.out.println("findItem = " + findItem);

            // @MappedSuperclass
            Member_mapping member = new Member_mapping();
            member.setCreatedBy("kim");
            member.setCreateDate(LocalDateTime.now());

            em.persist(member);

            em.flush();
            em.clear();

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