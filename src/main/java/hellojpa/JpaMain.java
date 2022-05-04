package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory enf = Persistence.createEntityManagerFactory("hello");
        // pom.xml의 name 값이 매개변수로 들어감

        EntityManager entityManager = enf.createEntityManager();
        // code
        entityManager.close();

        enf.close();
    }
}