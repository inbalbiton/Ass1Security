
public class AesStar1 implements IEncryptDecrypt{

    /**
     * this function encrypt the message with the key
     * first we will call shiftColumns(message)
     * after we will call addRoundKey(outputResult,key)
     * and return the cypher output
     * @param key
     * @param message
     * @return
     */
    @Override
    public byte[] encryption(byte[] key, byte[] message) {
        byte[] shiftedColumn = shiftColumnEncrypt(message);
        byte[] afterRoundKey = xor2arrays(shiftedColumn,key);
        return afterRoundKey;
    }

    /**
     * shiftColumnDecrypt
     * first col - moves 0 steps down
     * second col - moves 1 steps down
     * third col - moves 2 steps down
     * fourth col - moves 3 steps down
     * @param key
     * @param cypher
     * @return
     */
    @Override
    public byte[] decryption(byte[] key, byte[] cypher) {
        int numOfBlocks = cypher.length / 16; // 16 Bytes in 1 Block
        byte[] result = new byte[cypher.length];
        //Do Xor With key
        byte[] tmp = xor2arrays(cypher , key);
        int index = 0;
        for (int i = 0 ; i < numOfBlocks ; i++){
            byte[][] matrix = insertToMatrix(tmp , index);
            for(int j = 0 ; j < 4 ; j++){
                for(int k = 0 ; k < j ; k++){
                    byte b = matrix[3][j];
                    matrix[3][j] = matrix[2][j];
                    matrix[2][j] = matrix[1][j];
                    matrix[1][j] = matrix[0][j];
                    matrix[0][j] = b;
                }
            }

            for(int col = 0 ; col < 4 ; col++){
                for(int row = 0 ; row < 4 ; row++){
                    result[index] = matrix[row][col];
                    index++;
                }
            }
            index ++;
        }
        return result;
    }

    /**
     * this function insert the message into a matrix array
     * in the same organized like we learn in class
     * @param insert byte[16]
     * @param index
     * @return matrix [4][4]
     */
    private byte[][] insertToMatrix(byte[] insert , int index){
        byte[][] matrix = new byte[4][4];
        for(int j = 0 ; j < 4 ; j++){
            for(int i = 0 ; i < 4 ; i++){
                matrix[i][j] = insert[index];
                index++;
            }
        }
        return matrix;
    }

    /**
     *
     * @param matrix
     * @return
     */
    private byte[] readFromMatrixIntoBytesArray(byte[][] matrix) {
        byte[] resultShipted = new byte[matrix.length*4];
        int index = 0;
        for(int j = 0 ; j < 4 ; j++){
            for(int i = 0 ; i < 4 ; i++){
                resultShipted[index] = matrix[i][j];
                index++;
            }
        }
        return resultShipted;
    }


    private byte[] xor2arrays(byte[] b1 , byte[] b2){
        byte[] result = new byte[16];
        for(int i = 0 ; i < 16 ; i++){
            String var1 = String.format("%8s", Integer.toBinaryString(b1[i] & 0xFF)).replace(' ', '0');
            String var2 = String.format("%8s", Integer.toBinaryString(b2[i] & 0xFF)).replace(' ', '0');
            String res = "";
            for(int j = 0 ; j < var1.length() ; j++){
                char c1 = var1.charAt(j);
                char c2 = var2.charAt(j);
                if( (c1 == '0' && c2 == '0') || ( c1 == '1' && c2 == '1')){
                    res += '0';
                }
                else{
                    res += '1';
                }
            }
            byte b = (byte)(int)Integer.valueOf(res, 2);
            result[i] = b;
        }
        return result;
    }

    /**
     * first col - moves 0 steps up
     * second col - moves 1 steps up
     * third col - moves 2 steps up
     * fourth col - moves 3 steps up
     * @param message
     * @return
     */
    private byte[] shiftColumnEncrypt(byte[] message) {

        byte[][] matrix = insertToMatrix(message,0);
        for(int j=0; j<matrix[0].length; j++){
            for(int i=0; i<j; i++){
                byte tmpValueForSwitching = matrix[0][j];
                matrix[0][j] = matrix[1][j];
                matrix[1][j] = matrix[2][j];
                matrix[2][j] = matrix[3][j];
                matrix[3][j] = tmpValueForSwitching;
            }
        }
        return readFromMatrixIntoBytesArray(matrix);
    }

}
