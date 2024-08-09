package com.example.demo2;

import org.hibernate.Session;
import org.mindrot.jbcrypt.BCrypt;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import utility.HibernateUtil;

public class HibernateTrail {
    public static void main(String[] args) throws ConstraintViolationException {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try{
            tx = session.beginTransaction();
            tx.commit();
        }
        catch (Exception e){
            assert tx != null;
            if(tx.isActive()) tx.rollback();
            System.out.println("Error: "+e.getMessage());
        }
        finally{
            System.out.println("finally");
        }
        session.close();

    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashed) {
        // Verify the password using bcrypt
        return BCrypt.checkpw(password, hashed);
    }
}
