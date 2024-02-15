package meeting_room;

import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Encoder;

public class SeedEncoding {

	private static byte pbUserKey[] = "0123456789abcdef".getBytes(); // 16
	private static byte pbCipher[] = new byte[50];
	
	//seed ��ȣȭ
	public static byte[] encrypt(String str){
		byte[] userBytes = str.getBytes();
		byte pbData[] = new byte[16];
			
		for(int i=0; i<userBytes.length; i++) {
			if (i < userBytes.length) 
				pbData[i] = userBytes[i];
			else
				pbData[i] = 0x00;
		}
		
		//��ȣȭ �Լ� ȣ��
		pbCipher = KISA_SEED_ECB.SEED_ECB_Encrypt(pbUserKey, pbData,  0, pbData.length);
		
		Encoder encoder = Base64.getEncoder(); 
		byte[] encArray = encoder.encode(Arrays.copyOf(pbCipher, 16)); // ���ڵ��� ����Ʈ �迭�� ���̸� 16���� ����
		 
		return encArray;
	}
}