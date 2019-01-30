
try {
	socket cl = new socket();
	String host = "127.0.0.1";
	int pto = 1234;
	InetSocketAddress dst = new InetSocketAddress(host, pto);
	cl.connect(dst);

	// Se puede evitar con:
	// Socket cl = new Socket(InetAddress.getByName(host), pto);
	// Socket cl = new Socket(host, pto);
}