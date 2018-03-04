import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }
    private Race race;

    CyclicBarrier cb1;
    private int speed;
    private String name;
    CountDownLatch cd_st;
    Concur concur;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed, CyclicBarrier cb, Concur conc) {
        //this.cd_st=cd_start;
        this.concur=conc;
        this.cb1=cb;
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов" + " со  скоростью " + this.speed);
            // обратный отчет для начала старта
            concur.cd_start.countDown();

            // начать гонку только тогда когда все будут готовы

            //cd_st.countDown();

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            this.cb1.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < race.getStages().size(); i++) {

                race.getStages().get(i).go(this);
        }
        concur.cd_finish.countDown();
    }


   // public abstract void go(Car c);
}