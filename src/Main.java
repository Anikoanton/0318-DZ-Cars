import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;


/*Организуем гонки:
        Все участники должны стартовать одновременно, несмотря на то что на подготовку у каждого их них уходит разное время
        В туннель не может заехать одновременно больше половины участников (условность)
        Попробуйте всё это синхронизировать.
        Только после того как все завершат гонку нужно выдать объявление об окончании
        Можете корректировать классы (в т.ч. конструктор машин) и добавлять объекты классов из пакета util.concurrent*/
public class Main {
    public static final int CARS_COUNT = 4;


   public static void main(String[] args) {

       System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
       //семафор для тунеля чтобы проезажало только половина машин
       Semaphore sm = new Semaphore(CARS_COUNT/2);
       // счетчик для старта
       // final CountDownLatch cdl_start = new CountDownLatch(CARS_COUNT);
       // передаем симофор в тунель
        Race race = new Race(new Road(60), new Tunnel(sm), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
       // создаем объек CyclicBarrier для синхронизации сарта
       CyclicBarrier cb = new CyclicBarrier(CARS_COUNT);
       Concur Conc = new Concur(CARS_COUNT);

        for (int i = 0; i < cars.length; i++) {
            // передаем в через конструкотор объект CiclicBarried для синхронизации потоков и объект с CountDownLatch, для счетчиков
            cars[i] = new Car(race, (20 + (int) (Math.random() * 10)), cb, Conc);
        }

        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }

      // ожидание для начала гонки по счетчику готовности
       try {
           Conc.cd_start.await();
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
       System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");

       try {
           Conc.cd_finish.await();
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
       // ожидание завершения гонки по счетчику готовности

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}




