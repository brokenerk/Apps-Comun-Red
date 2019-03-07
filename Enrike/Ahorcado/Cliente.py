#!python3
import socket #Libreria sockets
import traceback #Libreria excepciones

try:
	#Host y puerto del servidor
	servidor = ("127.0.0.1", 1234)
	nuevoJuego = True

	while nuevoJuego:
		# -------------------- LOGICA DEL SOCKET --------------------------
		# Crear socket UDP - datagrama
		cl = socket.socket(socket.AF_INET, socket.SOCK_DGRAM) 

		print("")
		print("Cliente de datagramas iniciado. Ahorcado... Conectado al servidor.")

		print("")
		dificultad = input("Seleccione la dificultad. Facil = 0. Media = 1. Dificil = 2: ")

		#Convertir de string a byte[]
		b = dificultad.encode()
		#Enviar dificultad al servidor
		enviado = cl.sendto(b, servidor)

		print("")
		print("Esperando palabra del servidor...")

		#Recibir palabra en byte[] de tam max = 65535 bytes
		bpal = cl.recvfrom(65535) #Recibimos un buffer con la palabra y los datos del servidor (host, pto)
		#Decodificamos de bytes[] a string
		palabra = bpal[0].decode()
		#Cerramos el socket cliente
		cl.close()

		#Obtenermos longitud de caracteres
		longitud = len(palabra)

		#Imprimimos la palabra y los datos del servidor
		print("Palabra recibida: " + str(longitud) + " caracteres desde " + str(bpal[1]))

		# -------------------- LOGICA DEL AHORCADO --------------------------
		intentos = 5
		listaPalabraAdiv = []
		listaPalabraMost = []
		letra = ""

		#Separamos la palabra en letras
		listaPalabraAdiv = list(palabra)

		for item in listaPalabraAdiv:
			listaPalabraMost.append("_")

		while True:
			#Mostramos pantalla a adivinar
			print("")
			print(" ".join(listaPalabraMost))
			letra = input("Ingrese una letra: ")

			error = False

			#Comprobamos la letra ingresada
			if letra not in listaPalabraAdiv:
				#Ha fallado
				error = True
				intentos = intentos - 1
				print("Letra incorrecta. Intentos disponibles: " + str(intentos))
			else:
				#Letra adivinada
				for i, letraPalabra in enumerate(listaPalabraAdiv):
					if letraPalabra == letra:
						listaPalabraMost[i] = letraPalabra

			#Comprobamos los intentos restantes o si ya adivino completamente la palabra
			if intentos <= 0:
				print("")
				print("Has perdido, la palabra a adivinar era: " + palabra)
				break
			elif listaPalabraAdiv == listaPalabraMost:
				print("")
				print("Has ganado, la palabra adivinada es: " + palabra)
				break

		#Para iniciar un nuevo juego
		print("")
		jugar = input("Jugar de nuevo? S/N: ")

		if(jugar == "N" or jugar == "n"):
			nuevoJuego = False

except Exception as e:
    print(traceback.format_exc())
