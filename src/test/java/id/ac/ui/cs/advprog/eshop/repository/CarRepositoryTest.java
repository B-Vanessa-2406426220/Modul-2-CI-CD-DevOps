package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {

    CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    void testCreateAndFind() {
        Car car = new Car();
        car.setCarId("car-001");
        car.setCarName("Toyota Avanza");
        car.setCarColor("Silver");
        car.setCarQuantity(10);
        carRepository.create(car);

        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
        Car savedCar = carIterator.next();
        assertEquals("car-001", savedCar.getCarId());
        assertEquals("Toyota Avanza", savedCar.getCarName());
        assertEquals("Silver", savedCar.getCarColor());
        assertEquals(10, savedCar.getCarQuantity());
    }

    @Test
    void testCreateWithoutId() {
        Car car = new Car();
        car.setCarName("Honda Jazz");
        car.setCarColor("Red");
        car.setCarQuantity(5);

        carRepository.create(car);

        assertNotNull(car.getCarId());
        assertFalse(car.getCarId().isEmpty());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Car> carIterator = carRepository.findAll();
        assertFalse(carIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneCar() {
        Car car1 = new Car();
        car1.setCarId("car-001");
        car1.setCarName("Toyota Avanza");
        car1.setCarColor("Silver");
        car1.setCarQuantity(10);
        carRepository.create(car1);

        Car car2 = new Car();
        car2.setCarId("car-002");
        car2.setCarName("Honda Jazz");
        car2.setCarColor("Red");
        car2.setCarQuantity(5);
        carRepository.create(car2);

        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
        Car savedCar = carIterator.next();
        assertEquals("car-001", savedCar.getCarId());
        savedCar = carIterator.next();
        assertEquals("car-002", savedCar.getCarId());
        assertFalse(carIterator.hasNext());
    }

    @Test
    void testFindByIdFound() {
        Car car = new Car();
        car.setCarId("car-001");
        car.setCarName("Toyota Avanza");
        car.setCarColor("Silver");
        car.setCarQuantity(10);
        carRepository.create(car);

        Car found = carRepository.findById("car-001");
        assertNotNull(found);
        assertEquals("car-001", found.getCarId());
    }

    @Test
    void testFindByIdNotFound() {
        Car result = carRepository.findById("non-existent");
        assertNull(result);
    }

    @Test
    void testUpdateFound() {
        Car car = new Car();
        car.setCarId("car-001");
        car.setCarName("Toyota Avanza");
        car.setCarColor("Silver");
        car.setCarQuantity(10);
        carRepository.create(car);

        Car updatedCar = new Car();
        updatedCar.setCarId("car-001");
        updatedCar.setCarName("Toyota Avanza Veloz");
        updatedCar.setCarColor("Black");
        updatedCar.setCarQuantity(15);

        Car result = carRepository.update("car-001", updatedCar);

        assertNotNull(result);
        assertEquals("Toyota Avanza Veloz", result.getCarName());
        assertEquals("Black", result.getCarColor());
        assertEquals(15, result.getCarQuantity());
    }

    @Test
    void testUpdateNotFound() {
        Car updatedCar = new Car();
        updatedCar.setCarId("car-999");
        updatedCar.setCarName("Ghost Car");
        updatedCar.setCarColor("Invisible");
        updatedCar.setCarQuantity(0);

        Car result = carRepository.update("car-999", updatedCar);
        assertNull(result);
    }

    @Test
    void testDeleteFound() {
        Car car = new Car();
        car.setCarId("car-001");
        car.setCarName("Toyota Avanza");
        car.setCarColor("Silver");
        car.setCarQuantity(10);
        carRepository.create(car);

        carRepository.delete("car-001");

        Iterator<Car> carIterator = carRepository.findAll();
        assertFalse(carIterator.hasNext());
    }

    @Test
    void testDeleteNotFound() {
        carRepository.delete("non-existent");

        Iterator<Car> carIterator = carRepository.findAll();
        assertFalse(carIterator.hasNext());
    }
}
