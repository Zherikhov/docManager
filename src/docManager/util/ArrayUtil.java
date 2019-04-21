package docManager.util;

/**
 * Метод принимает массив int, и выдает сумму значений
 */
public class ArrayUtil {
    int temp;
    public int sumArray (int [] array){
        for (int i = 0; i < array.length; i++) {
            temp = temp + array[i];
        }
        return temp;
    }
}
