package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.Service;
import org.hibernate.service.ServiceRegistry;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        //Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        //Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    //private static final String PERSISTENCE_UNIT_NAME = "projeto2_desktop";
    //private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    public static void main(String[] args) {
        System.out.println("Hello world!");
        User u1 = new User();
        u1.setNome("Tiago");
        System.out.println(u1.getNome());
        //new login().setVisible(true); para javaSwing

        /*Configuration cfg = new Configuration(); //.configure().addAnnotatedClass(User.class);
        //cfg.addAnnotatedClass(User.class);
        cfg.configure();
        System.out.println(cfg);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
        //Metadata metadata = new MetadataSources(standardServiceRegistry).getMetadataBuilder().build();
        SessionFactory factory = cfg.buildSessionFactory(builder.build());
        System.out.println("Configuration Done");
        cfg.buildSessionFactory();
        System.out.println("Session Factory Done");

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        User user1 = new User(1,"Tiago", "tiago@gmail.com", "tiago");
        session.save(user1);
        transaction.commit();
        session.close();*/
        EntityManager em = JPAUtil.getEntityManager();
        var user = new User();

        launch();
    }
}