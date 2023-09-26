package edu.trinity;

public class Fibonacci {

    public static int calculate(int i) {
        if (i == 0){
            return 0;
        }
        else if (i <= 2){
            return 1;
        }
        else{
            return (calculate(i-2) + calculate(i-1));
        }
    }
}
