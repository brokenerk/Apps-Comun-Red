import socket

#Host y puerto
host = "127.0.0.1"
pto = 1234

# Crear socket UDP
cl = socket.socket(socket.AF_INET, socket.SOCK_DGRAM) 

print "Cliente de datagramas iniciado. Ahorcado... Conectado al servidor."
print "Seleccione la dificultad. Facil: 0. Media: 1. Dificil: 2"
dificultad = raw_input()

#Enviar dificultad
enviado = cl.sendto(dificultad, (host, pto))

print "Esperando palabra del servidor.."

#Recibir palabra
palabra, s = cl.recvfrom(pto)
longitud = len(palabra)

print "Palabra recibida " + str(longitud) + " caracteres"

for i in range(0, longitud):
	print "_",

cl.close()