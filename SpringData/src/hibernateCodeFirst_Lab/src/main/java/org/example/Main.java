package org.example;

import entities.Bike;
import entities.Car;
import entities.Vehicle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory =
                Persistence.createEntityManagerFactory("hibernate");
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();

        Vehicle car = new Car("Ford", "Diesel", 5);
        Vehicle bike = new Bike();

        em.persist(car);
        em.persist(bike);

        em.getTransaction().commit();
        em.close();
    }
}