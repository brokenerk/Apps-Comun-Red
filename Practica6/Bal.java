import java.nio.channels.*;
import java.nio.*;
import java.net.*;
import java.util.Iterator;
import java.io.*;
import java.util.*;

public class Bal implements Runnable{

	private final static int port = 1234;
	private final static int TIMEOUT = 10000;
	private final static int bufferSize = 4096;

	private static String endServer = "127.0.0.1";
	private static int[] endPorts = new int[5];
	private static int pos = 0;
	private static int nPorts = 0;

	private Map<SocketChannel, Socket> pending = new HashMap<>();

	private ServerSocketChannel server;
	private Selector sel;

	public Bal(){
		try{
			server = ServerSocketChannel.open();
			server.configureBlocking(false);
			server.setOption(StandardSocketOptions.SO_REUSEADDR, true);
			server.socket().bind(new InetSocketAddress(port));
			sel = Selector.open();
			server.register(sel, SelectionKey.OP_ACCEPT);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void run(){
		System.out.println("Proxy server started...");
		while(!Thread.currentThread().isInterrupted()){
			try{
				sel.select(TIMEOUT);
				Iterator<SelectionKey> it = sel.selectedKeys().iterator();
				while(it.hasNext()){
					SelectionKey key = (SelectionKey)it.next();
					it.remove();
					if(!key.isValid()){
						continue;
					}
					if(key.isAcceptable()){
						ServerSocketChannel serverChannel = (ServerSocketChannel)key.channel();
						SocketChannel channel = serverChannel.accept();
						channel.configureBlocking(false);
						channel.register(sel, SelectionKey.OP_READ);
						System.out.println("Incoming connection from " + channel.socket().getInetAddress() + ":" + channel.socket().getPort());
					}
					else if(key.isReadable()){
						SocketChannel channel = (SocketChannel)key.channel();
						ByteBuffer readBuffer = ByteBuffer.allocate(bufferSize);

						Socket client = new Socket();
						client.connect(new InetSocketAddress(endServer, endPorts[pos]), TIMEOUT);
						client.setSoTimeout(TIMEOUT);
						pos = (pos + 1) % nPorts;
						DataOutputStream outputClient = new DataOutputStream(client.getOutputStream());

						int read = 0;
						while((read = channel.read(readBuffer)) > 0){
							readBuffer.flip();
							System.out.print(new String(readBuffer.array(), 0, read));
							outputClient.write(readBuffer.array(), 0, read);
							readBuffer.clear();
						}

						pending.put(channel, client);
						key.interestOps(SelectionKey.OP_WRITE);
					}
					else if(key.isWritable()){
						SocketChannel channel = (SocketChannel)key.channel();
						System.out.println("Sending data to " + channel.socket().getInetAddress() + ":" + channel.socket().getPort() + "...");
						Socket client = pending.get(channel);
						DataInputStream clientInput = new DataInputStream(client.getInputStream());

						try{
							int read = 0;
							byte buffer[] = new byte[bufferSize];
							while((read = clientInput.read(buffer, 0, bufferSize)) > 0){
								channel.write(ByteBuffer.wrap(buffer, 0, read));
							}
						}catch(Exception e){
							System.out.println("Timeout");
							key.interestOps(SelectionKey.OP_READ);
							continue;
						}

						key.interestOps(SelectionKey.OP_READ);
						System.out.println("Finished sending data to " + channel.socket().getInetAddress() + ":" + channel.socket().getPort());
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
		}
	}

	public static void main(String [] args){
		Scanner sc = new Scanner(System.in);
		System.out.print("Number of available ports: ");
		nPorts = sc.nextInt();
		for(int i = 0; i < nPorts; ++i){
			System.out.print("Port #" + (i + 1) + ": ");
			endPorts[i] = sc.nextInt();
		}
		Thread t = new Thread(new Bal());
		t.start();
	}
}