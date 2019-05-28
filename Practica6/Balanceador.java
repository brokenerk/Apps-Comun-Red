import java.nio.channels.*;
import java.nio.*;
import java.net.*;
import java.util.Iterator;
import java.io.*;
import java.util.*;

public class Balanceador implements Runnable{
	//Datos red
	private int port = 6543;
	private String host = "127.0.0.1";

	private int TIMEOUT = 35000; //35 segundos
	private int tam = 1024;//1Kb

	private static int nPorts = 4;
	private static int[] ports = new int[nPorts];
	private int pos = 0;
	
	private Map<SocketChannel, Socket> pending = new HashMap<>();
	private ServerSocketChannel s; //Servidor
	private Selector sel;

	public Balanceador() {
		try {
			s = ServerSocketChannel.open();
			s.configureBlocking(false); //No bloqueante
			s.setOption(StandardSocketOptions.SO_REUSEADDR, true);
			s.socket().bind(new InetSocketAddress(port));
			sel = Selector.open();
			s.register(sel, SelectionKey.OP_ACCEPT);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		System.out.println("Balanceador de cargas iniciado...");
		while(!Thread.currentThread().isInterrupted()) {
			try {
				sel.select(TIMEOUT); //Timeout para matar proceso si tarda demasiado
				Iterator<SelectionKey> it = sel.selectedKeys().iterator();

				while(it.hasNext()) {
					SelectionKey key = (SelectionKey) it.next();
					it.remove();

					if(!key.isValid())
						continue;

					if(key.isAcceptable()) {
						//Entrante
						ServerSocketChannel socketChannel = (ServerSocketChannel) key.channel();
						SocketChannel canal = socketChannel.accept();
						canal.configureBlocking(false); // No bloqueante
						canal.register(sel, SelectionKey.OP_READ);
						System.out.println("Cliente conectado desde " + canal.socket().getInetAddress() + ":" + canal.socket().getPort());
						continue;
					}
					else if(key.isReadable()) {
						//Lectura
						SocketChannel canal = (SocketChannel) key.channel();
						ByteBuffer buffer = ByteBuffer.allocate(tam);

						Socket cl = new Socket();
						cl.connect(new InetSocketAddress(host, ports[pos]), TIMEOUT);
						cl.setSoTimeout(TIMEOUT);
						pos = (pos + 1) % nPorts;
						DataOutputStream dos = new DataOutputStream(cl.getOutputStream());

						int n = 0;

						while((n = canal.read(buffer)) > 0) {
							buffer.flip();
							//Peticion HTTP del cliente
							System.out.print(new String(buffer.array(), 0, n));
							dos.write(buffer.array(), 0, n);
							buffer.clear();
						}

						pending.put(canal, cl);
						key.interestOps(SelectionKey.OP_WRITE);
						continue;
					}
					else if(key.isWritable()) {
						//Escritura
						SocketChannel canal = (SocketChannel) key.channel();
						System.out.println("Enviando datos al cliente " + canal.socket().getInetAddress() + ":" + canal.socket().getPort() + "...");
						Socket cl = pending.get(canal);
						DataInputStream dis = new DataInputStream(cl.getInputStream());

						try {
							int n = 0;
							byte[] b = new byte[tam];
							
							while((n = dis.read(b, 0, tam)) > 0)
								canal.write(ByteBuffer.wrap(b, 0, n));

						} catch(Exception e) {
							System.out.println("Se ha excedido el tiempo de envio limite");
							key.interestOps(SelectionKey.OP_READ);
							continue;
						}

						key.interestOps(SelectionKey.OP_READ);
						System.out.println("Fin envio de datos al cliente " + canal.socket().getInetAddress() + ":" + canal.socket().getPort());
						continue;
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		for(int i = 0; i < nPorts; i++) {
			System.out.print("Puerto #" + (i + 1) + ": ");
			ports[i] = sc.nextInt();
		}
		Thread t = new Thread(new Balanceador());
		t.start();
	}
}