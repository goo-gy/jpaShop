package jpabook.jpashop.team;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Team;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class TeamRepositoryTest {

    @Test
    public void main() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpashop");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Team team = new Team();
            team.setName("look us");
            em.persist(team);

            Member member = new Member();
            member.setName("lucas");
            member.setTeam(team);
            em.persist(member);

            em.flush(); // commit 전에 db로 보내도록
            em.clear(); // cache 된게 아니라 DB에서 다시 가져오도록
            Member findMember = em.find(Member.class, member.getId());
            Team findTeam = findMember.getTeam();
            List<Member> members = findTeam.getMembers();
            for(Member teamMember : members) {
                System.out.println("======" + teamMember.getName());
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
}
