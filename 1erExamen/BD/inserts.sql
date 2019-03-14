USE Foro;

DELETE FROM Usuario;
DELETE FROM Comentario;
DELETE FROM Publicacion;

ALTER TABLE Usuario AUTO_INCREMENT = 0;
ALTER TABLE Publicacion AUTO_INCREMENT = 0;
ALTER TABLE Comentario AUTO_INCREMENT = 0;

INSERT INTO Usuario(Nickname, Password, Foto) VALUES("brokenerk", "prueba123", "./avatars/1.png");
INSERT INTO Usuario(Nickname, Password, Foto) VALUES("goku777", "prueba123", "./avatars/2.png");
INSERT INTO Usuario(Nickname, Password, Foto) VALUES("operalta", "prueba123", "./avatars/3.png");

INSERT INTO Publicacion(Nombre, Fecha) VALUES("Perritos", "2019-03-14");
INSERT INTO Publicacion(Nombre, Fecha) VALUES("Amor", "2019-03-01");
INSERT INTO Publicacion(Nombre, Fecha) VALUES("Carros", "2019-04-22");

INSERT INTO Comentario(Fecha, Texto, Imagen, IdUsuario, IdPublicacion) VALUES("2019-03-14", "Prueba de texto", null, 1, 1);
INSERT INTO Comentario(Fecha, Texto, Imagen, IdUsuario, IdPublicacion) VALUES("2019-04-01", "Otra prueba de texto", null, 2, 1);
INSERT INTO Comentario(Fecha, Texto, Imagen, IdUsuario, IdPublicacion) VALUES("2019-03-01", "Lorem ipsum", null, 3, 2);
INSERT INTO Comentario(Fecha, Texto, Imagen, IdUsuario, IdPublicacion) VALUES("2019-04-22", "Aaaaaaaa", null, 1, 3);