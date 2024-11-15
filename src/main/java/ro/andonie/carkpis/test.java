package ro.andonie.carkpis;

public class test {
    public static void main(String[] args) {
        int j = 1;
        int i = 2 * (j = 3);
        System.out.println(i);
        System.out.println(j);
    }

}