/**
 * Created by Gennadiy on 18.01.2015.
 */
public class PA2 {

    public static void main(String[] args) {

        String key = "140b41b22a29beb4061bda66b6747e14";
        String cipher = "4ca00ff4c898d61e1edbf1800618fb2828a226d160dad07883d04e008a7897ee2e4b7465d5290d0c0e6c6822236e1daafb94ffe0c5da05d9476be028ad7c1d81";

        new AES().decryptCBC(key, cipher);

    }
}
