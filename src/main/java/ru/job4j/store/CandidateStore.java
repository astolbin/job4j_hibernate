package ru.job4j.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.model.Candidate;

import java.util.List;

public class CandidateStore {
    private static final Logger LOG = LoggerFactory.getLogger(CandidateStore.class.getName());
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf =
            new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public void add(Candidate candidate) {
        try (Session session = sf.openSession();) {
            session.beginTransaction();
            session.save(candidate);
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Exception", e);
        }
    }

    public List<Candidate> getAll() {
        List<Candidate> candidates = null;
        try (Session session = sf.openSession();) {
            session.beginTransaction();
            candidates = session.createQuery("from Candidate").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Exception", e);
        }

        return candidates;
    }

    public Candidate getById(int id) {
        Candidate candidate = null;
        try (Session session = sf.openSession();) {
            session.beginTransaction();
            Query query = session.createQuery("from Candidate c where c.id = :id");
            query.setParameter("id", id);
            candidate = (Candidate) query.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Exception", e);
        }

        return candidate;
    }

    public List<Candidate> getByName(String name) {
        List<Candidate> candidates = null;
        try (Session session = sf.openSession();) {
            session.beginTransaction();
            Query query = session.createQuery("from Candidate c where c.name = :name");
            query.setParameter("name", name);
            candidates = (List<Candidate>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Exception", e);
        }

        return candidates;
    }

    public void update(int id, Candidate candidate) {
        try (Session session = sf.openSession();) {
            session.beginTransaction();
            Query query = session.createQuery(
                    "update Candidate c set c.name = :name, c.experience = :experience, "
                    + "c.salary = :salary where c.id = :id"
            );
            query.setParameter("name", candidate.getName());
            query.setParameter("experience", candidate.getExperience());
            query.setParameter("salary", candidate.getSalary());
            query.setParameter("id", id);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Exception", e);
        }
    }

    public void delete(int id) {
        try (Session session = sf.openSession();) {
            session.beginTransaction();
            Query query = session.createQuery("delete from Candidate c where c.id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Exception", e);
        }
    }
}
