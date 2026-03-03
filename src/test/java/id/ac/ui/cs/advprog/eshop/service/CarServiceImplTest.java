package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepositoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private CarRepositoryInterface carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setCarId("car-001");
        car.setCarName("Toyota Avanza");
        car.setCarColor("Silver");
        car.setCarQuantity(10);
    }

    @Test
    void testCreate() {
        when(carRepository.create(car)).thenReturn(car);

        Car result = carService.create(car);

        assertNotNull(result);
        assertEquals("car-001", result.getCarId());
        verify(carRepository, times(1)).create(car);
    }

    @Test
    void testFindAll() {
        List<Car> carList = new ArrayList<>();
        carList.add(car);
        Iterator<Car> iterator = carList.iterator();

        when(carRepository.findAll()).thenReturn(iterator);

        List<Car> result = carService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Toyota Avanza", result.get(0).getCarName());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(carRepository.findById("car-001")).thenReturn(car);

        Car result = carService.findById("car-001");

        assertNotNull(result);
        assertEquals("car-001", result.getCarId());
        verify(carRepository, times(1)).findById("car-001");
    }

    @Test
    void testUpdate() {
        Car updatedCar = new Car();
        updatedCar.setCarId("car-001");
        updatedCar.setCarName("Toyota Avanza Veloz");
        updatedCar.setCarColor("Black");
        updatedCar.setCarQuantity(15);

        carService.update("car-001", updatedCar);

        verify(carRepository, times(1)).update("car-001", updatedCar);
    }

    @Test
    void testDelete() {
        carService.delete("car-001");

        verify(carRepository, times(1)).delete("car-001");
    }
}
