USE Foro;

DELETE FROM Usuario;
DELETE FROM Comentario;
DELETE FROM Publicacion;

ALTER TABLE Usuario AUTO_INCREMENT = 1;
ALTER TABLE Publicacion AUTO_INCREMENT = 1;
ALTER TABLE Comentario AUTO_INCREMENT = 1;

INSERT INTO Usuario(Nickname, Password, Avatar) VALUES("brokenerk", "prueba123", "./avatars/1.png");
INSERT INTO Usuario(Nickname, Password, Avatar) VALUES("goku777", "prueba123", "./avatars/2.png");
INSERT INTO Usuario(Nickname, Password, Avatar) VALUES("operalta", "prueba123", "./avatars/3.png");

INSERT INTO Publicacion(Nombre, Fecha) VALUES("Perritos", "2019-03-14 10:00:00");
INSERT INTO Publicacion(Nombre, Fecha) VALUES("Amor", "2019-03-01 11:00:00");
INSERT INTO Publicacion(Nombre, Fecha) VALUES("Carros", "2019-04-22 12:00:00");
INSERT INTO Publicacion(Nombre, Fecha) VALUES("Gatitos", "2019-03-14 12:00:00");

INSERT INTO Comentario(Fecha, Texto, Imagen, IdUsuario, IdPublicacion) VALUES("2019-03-14 10:00:00", "Prueba de texto", "./fotos/1.png", 1, 1);
INSERT INTO Comentario(Fecha, Texto, Imagen, IdUsuario, IdPublicacion) VALUES("2019-04-01 12:00:00", "Otra prueba de texto", null, 2, 1);
INSERT INTO Comentario(Fecha, Texto, Imagen, IdUsuario, IdPublicacion) VALUES("2019-03-01 13:00:00", "Lorem ipsum", null, 3, 2);
INSERT INTO Comentario(Fecha, Texto, Imagen, IdUsuario, IdPublicacion) VALUES("2019-04-22 14:00:00", "Aaaaaaaa", null, 1, 3);