public interface IEncryptDecrypt {


    public byte[] encryption(byte[] keys , byte[] message);

    public byte[] decryption(byte[] keys , byte[] cypher);


}
