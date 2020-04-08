import java.lang.reflect.Array;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class AesStar3Breaker implements IBreaker {

    /**
     * this function random two keys k1,k2 and calculate k3
     * return bytes array - contains all 3 keys - each key is 128 byte - output is 384 byte
     * @param message
     * @param cypher
     * @return
     */
    @Override
    public byte[] breaker(byte[] message, byte[] cypher) {
        byte[] key = new byte[48];
        /***************************************************************/
        /*
        calculate k3
         */
        byte[] k3 = new byte[16];
        for(int i=0 ; i<k3.length ; i++){
            k3[i] = cypher[i];
        }
        /***************************************************************/
        /*
        print k3 result
         */
        for(int i=0 ; i<k3.length ; i++){
            key[i+32] = k3[i];
            System.out.println(k3[i]);
        }
        System.out.println("");
        /***************************************************************/
        /*
       random k1,k2
        */
        SecureRandom random = new SecureRandom();
        byte[] k1 = new byte[16];
        byte[] k2 = new byte[16];
        do{
            random.nextBytes(k1);
        }while (Arrays.equals(k1,k3));
        do{
            random.nextBytes(k2);
        }
        while (Arrays.equals(k1,k2) || Arrays.equals(k2,k3));
        /***************************************************************/
        /*
        print k1 , k2 result
         */
        for(int i=0 ; i< k1.length ;i++){
            key[i] = k1[i];
            System.out.print("k1 = " + k1[i]);
        }
        System.out.println("");
        for(int i=0 ; i< k2.length ;i++){
            key[i+16] = k2[i];
            System.out.print("k2 = " + k2[i]);
        }
        /***************************************************************/
        /*
        braking!
         */
        AesStar3 braker = new AesStar3();
        byte[] k3_new = braker.encryption(key , message);
        for(int i=0 ; i<k3.length ; i++){
            key[i+32] = k3_new[i];
            System.out.println(k3_new[i]);
        }
        return key;
    }
}
