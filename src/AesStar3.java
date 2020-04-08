public class AesStar3 implements IEncryptDecrypt{

    /**
     * this function use AesStar1 to encrypt message with 3 keys
     * return cypher bytes
     * @param key
     * @param message
     * @return
     */
    @Override
    public byte[] encryption(byte[] key , byte[] message) {
        AesStar1 aes1 = new AesStar1();
        byte[][] keys = new byte[3][key.length/3];
        int indexInkeyArray = 0;
        for(int i=0; i<=2; i++){
            for(int j=0; j<key.length/3; j++){
                keys[i][j] = key[indexInkeyArray];
                indexInkeyArray++;
            }
        }
        byte[] c1 = aes1.encryption(keys[0],message);
        byte[] c2 = aes1.encryption(keys[1],c1);
        return aes1.encryption(keys[2],c2);

    }


    @Override
    public byte[] decryption(byte[] key , byte[] cypher) {
        AesStar1 aes1 = new AesStar1();
        byte[] result = new byte[cypher.length];
        byte[][] keys = new byte[3][16];
        int index = key.length-1;
        for(int i = 2 ; i >= 0  ;i--){
            for(int j = 15 ; j >= 0 ; j--){
                keys[i][j] = key[index];
                index--;
            }
            result = aes1.decryption(keys[i] , cypher);
            cypher = result;
        }

        return result;
    }
}
