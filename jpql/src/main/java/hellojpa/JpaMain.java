package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {



        //엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성

        EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

        tx.begin(); //트랜잭션 시작

        try {
            for(int i = 0; i < 100; i++) {
                Member member = new Member();
                member.setUsername("member" +i);
                member.setAge(i);
                em.persist(member);
            }


            em.flush();
            em.clear();

            //페이징 쿼리
            List<Member> result = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(0) //0번째부터
                    .setMaxResults(10) //10개 가져옴
                    .getResultList();
            System.out.println("resultList = " + result.size());
            for(Member member1 : result){
                System.out.println("member1 = " + member1);
            }

            tx.commit();

        } catch (Exception e) {

            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }

}
