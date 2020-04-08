import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {


    public static void main(String[] args) {


        String command = args[0];
        switch (command){
            case "-b":{

                String messagePath = args[2];
                String cypherPath = args[4];
                String outputPath = args[6];

                AesStar3Breaker aes = new AesStar3Breaker();
                byte[] breakResult = aes.breaker(getbythes(messagePath),getbythes(cypherPath));
                writebythes(breakResult,outputPath);

            }break;

            case "-e":{
                String keysPath = args[2];
                String input = args[4];
                String output = args[6];

                AesStar3 myAes = new AesStar3();
                byte[] keys = getbythes(keysPath);
                byte[] message = getbythes(input);
                byte[] cypher = myAes.encryption(keys,message);
                writebythes(cypher,output);

                /*
                test
                 */
                System.out.println(testEncryption(message , cypher , keys));

            }break;
            case "-d":{
                String keysPath = args[3];
                String input = args[5];
                String output = args[7];

                AesStar3 myAes = new AesStar3();
                byte[] keys = getbythes(keysPath);
                byte[] cypher = getbythes(input);
                byte[] message = myAes.decryption(keys , cypher);
                writebythes(message , output);

                System.out.println(testDecryption(cypher , message , keys));

            }
        }
    }

    /**
     * this function is for testing
     * @param result
     * @return
     */
    private static boolean testEncryption(byte[] message , byte[] result , byte[] key) {
        AesStar3 tester = new AesStar3();
        byte[] decResult = tester.decryption(key , result);
        return Arrays.equals(message , decResult);
    }

    private static boolean testDecryption(byte[] cypher, byte[] result , byte[] key) {
        AesStar3 tester = new AesStar3();
        byte[] encResult = tester.encryption(key , result);
        return Arrays.equals(cypher , encResult);
    }

    /**
     * this function read a byte[] from a absolute path
     * @param pathToRead
     * @return
     */
    public static byte[] getbythes(String pathToRead){
        try {
            byte[] result = Files.readAllBytes(Paths.get(pathToRead));
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * this function write a byte[] to a absolute path
     * @param toWrite
     * @param outputPath
     */
    public static void writebythes(byte[] toWrite , String outputPath){
        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            fos.write(toWrite);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
