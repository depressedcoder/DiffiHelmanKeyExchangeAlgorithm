import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Alice {

	public static void main(String[] args) {
		try { 
            int port = 8088; 
  
            int primeNumber = 11; // prime number
            int primitiveRoot = 2; // primitive root of prime number
            int alicePrivateKey = 8; // alice private key
            double secretKey, bobPublicKey; 
  
            // Established the connection 
            System.out.println("Connecting to Bob on port " + port); 
            Socket client = new Socket("localhost", port); 
            System.out.println("Just connected to " + client.getRemoteSocketAddress()); 
  
            // Sends the data to Bob 
            OutputStream outToBob = client.getOutputStream(); 
            DataOutputStream out = new DataOutputStream(outToBob); 
   
            out.writeUTF(Integer.toString(primeNumber)); // Sending prime number to Bob 
  
            out.writeUTF(Integer.toString(primitiveRoot)); // Sending primitive root to Bob 
  
            double publicKeyAlice = ((Math.pow(primitiveRoot, alicePrivateKey)) % primeNumber); // calculation of alice  public key 
           
            out.writeUTF(Double.toString(publicKeyAlice)); // Sending Alice public key
            
            System.out.println("Prime Number = " + primeNumber); 
            System.out.println("primitive Root = " + primitiveRoot); 
            System.out.println("Alice Private Key = " + alicePrivateKey); 
            System.out.println("Alice Public Key = " + publicKeyAlice); 
  
            // Accepts the data from Bob
            DataInputStream in = new DataInputStream(client.getInputStream()); 
  
            bobPublicKey = Double.parseDouble(in.readUTF()); //BOB public key
            System.out.println("Bob Public Key = " + bobPublicKey); 
  
            secretKey = ((Math.pow(bobPublicKey, alicePrivateKey)) % primeNumber); // calculation of the secret key
  
            System.out.println("Secret Key to perform Symmetric Encryption = "
                               + secretKey); 
            client.close(); 
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        }
	}

}
