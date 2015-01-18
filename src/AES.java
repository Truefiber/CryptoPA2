import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by Gennadiy on 18.01.2015.
 */
public class AES {

    public String decryptCBC(String keyString, String cipherString) {

        List<String> listOfBlocks = getListFromString(cipherString, 32);

        try {
            Cipher cipherBlock = Cipher.getInstance("AES/ECB/NoPadding");
            Key key = new SecretKeySpec(getBytesFromString(keyString), "AES");
            cipherBlock.init(Cipher.DECRYPT_MODE, key);

            boolean isLastBlock = false;
            byte[] previousCipherBlock = getBytesFromString(listOfBlocks.get(0));
            for (int i = 1; i < listOfBlocks.size(); i++) {
                byte[] decodedBlock = cipherBlock.doFinal(getBytesFromString(listOfBlocks.get(i)));

                if (i == (listOfBlocks.size() - 1)) {
                    isLastBlock = true;
                }
                getMessage(decodedBlock, previousCipherBlock, isLastBlock);
                previousCipherBlock = getBytesFromString(listOfBlocks.get(i));

            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }


        return null;
    }


    private void getMessage(byte[] decodedBlock, byte[] prevBlock, boolean isLast) {
        int bound = decodedBlock.length;
        if (isLast && ((decodedBlock[decodedBlock.length-1] & 0xFF) < 17)) {

            bound = decodedBlock.length - (decodedBlock[decodedBlock.length-1] & 0xFF);
        }

            for (int i = 0; i < bound; i++) {

                System.out.print(Character.toChars(decodedBlock[i] ^ prevBlock[i]));
            }


    }

    private List<String> getListFromString(String incomeString, int splitFactor) {
        return Lists.newArrayList(Splitter.fixedLength(splitFactor).split(incomeString));
    }



    private byte[] getBytesFromString(String blockString) {
        byte[] resultByteArray = new byte[blockString.length() / 2];
        List<String> byteList = getListFromString(blockString,2);
        for (int i = 0; i < byteList.size(); i++) {
            resultByteArray[i] = (byte)Integer.parseInt(byteList.get(i), 16);
        }

        return resultByteArray;
    }


}
