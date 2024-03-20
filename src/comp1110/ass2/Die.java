package comp1110.ass2;

import java.util.Random;
/**
 * This class represents a die used in a game. It provides a method to roll the die and obtain a result.
 *
 * @Author Yichi Zhang(u7748799)
 */
public class Die {

    public int rollSpecialDie() {
        Random random = new Random();//这是一个不均匀分布，所以采用一一对应的映射
        int result = random.nextInt(6) + 1;//generate
        if (result == 1) {
            return 1;
        } else if (result == 2 || result == 3) {
            return 2;
        } else if (result == 4 || result == 5) {
            return 3;
        } else {
            return 4;
        }
    }
}
