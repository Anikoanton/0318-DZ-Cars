import java.util.concurrent.CountDownLatch;

/**
 * Created by Антон on 03.03.2018.
 */
public class Concur {

    int CARS_C;
   public CountDownLatch cd_start;
    public CountDownLatch cd_finish;


    public Concur(int CARS_C) {
        this.CARS_C = CARS_C;
        this.cd_start = new CountDownLatch(CARS_C);
        this.cd_finish = new CountDownLatch(CARS_C);
    }

   // счетчик для старта

}
