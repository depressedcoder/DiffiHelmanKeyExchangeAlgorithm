import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Bob {

	public static void main(String[] args) throws IOException{
		try { 
            int port = 8088; 
  
            // Server Key 
            int bobPrivateKey = 4; 
  
            double primeNumber, primitiveRoot, alicePublicKey, bobPublicKey, secretKey; 
  
            // Established the Connection with Alice
            ServerSocket serverSocket = new ServerSocket(port); 
            System.out.println("Waiting for Alice on port " + serverSocket.getLocalPort() + "..."); 
            Socket server = serverSocket.accept(); 
            System.out.println("Just connected to " + server.getRemoteSocketAddress()); 
  
            // Accepts the data from Alice 
            DataInputStream in = new DataInputStream(server.getInputStream()); 
  
            primeNumber = Integer.parseInt(in.readUTF()); //getting prime number from Alice
            System.out.println("Prime Number From Alice = " + primeNumber); 
  
            primitiveRoot = Integer.parseInt(in.readUTF()); //getting primitive root form Alice
            System.out.println("primitive Root From Alice = " + primitiveRoot); 
  
            alicePublicKey = Double.parseDouble(in.readUTF()); // getting Alice public key 
            
            System.out.println("Bob Private Key = " + bobPrivateKey); //Bob private Key
            
            bobPublicKey = ((Math.pow(primitiveRoot, bobPrivateKey)) % primeNumber); // Bob's public key Calculation
            
            System.out.println("Bob Public Key = " + bobPrivateKey); //Bob private Key
            
            System.out.println("Alice Public Key = " + alicePublicKey);
            // Sending Bob's Public key to Alice 
            OutputStream outToAlice = server.getOutputStream(); 
            DataOutputStream out = new DataOutputStream(outToAlice); 
  
            out.writeUTF(Double.toString(bobPublicKey)); // sending Bob's public key
  
            secretKey = ((Math.pow(alicePublicKey, bobPrivateKey)) % primeNumber); // calculation of Secret Key 
  
            System.out.println("Secret Key to perform Symmetric Encryption = "
                               + secretKey); 
            server.close(); 
        } 
  
        catch (SocketTimeoutException s) { 
            System.out.println("Socket timed out!"); 
        } 
        catch (IOException e) { 
        } 
	}

}
