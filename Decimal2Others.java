import java.util.ArrayList;

public class Practise {
    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        for(int i=1; i<18; i++){
            arr.add(i);
        }
        int i =0;
        while(i<17){
            System.out.println(arr.get(i) + "  " + decimalTOBin(arr.get(i)) + "  "
                                + decimalToOctal(arr.get(i)) + "  " +
                                            decimalToHexadecimal(arr.get(i)));
            ++i;
        }
    }

    public static String decimalTOBin(int val){
        String bin;
        bin = Integer.toBinaryString(val);
        return bin;
    }

    public static String decimalToOctal(int val){
        String oct;
        oct = Integer.toOctalString(val);
        return oct;
    }

    public static String decimalToHexadecimal(int val){
        String hex;
        hex = Integer.toHexString(val);
        return hex;
    }
}
