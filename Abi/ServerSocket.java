// 29 de Enero 2019
// Socket bloqueante, SERVIDOR

try {
	ServerSocket s = new ServerSocket();
	int pto = 9999;
	InetSocketAddress pto_l = new InetSocketAddress(pto);
	s.bind(pto_l);

	// Diferente constructor
	// ServerSocket s = new ServerSocket(9999);

	// Para que no corra luego luego si muere
	s.setReuseAddress(true); 
}