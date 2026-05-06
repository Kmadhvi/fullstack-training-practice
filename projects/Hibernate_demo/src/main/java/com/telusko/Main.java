package com.telusko;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;



//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main() {
      Alien a1 =new Alien();
        a1.setAid(104);
        a1.setAname("Avni");
        a1.setTech("DBMS");

        SessionFactory factory = new Configuration()
                    .addAnnotatedClass(com.telusko.Alien.class)
                    .configure()
                    .buildSessionFactory();
        Session session = factory.openSession();

        Transaction tx = session.beginTransaction();

        //Alien a1 = session.find(Alien.class,103);
        //Alien a1 = session.byId(Alien.class).getrefernce(101);
       // Alien a1 = session.get(Alien.class, 101);
        //System.out.println(a1);
        //Alien a1 =session.find(Alien.class, 104);
      // session.merge(a1);
        //session.remove(a1);
        session.persist(a1);
         tx.commit();
        session.close();
        factory.close();
    }
}
