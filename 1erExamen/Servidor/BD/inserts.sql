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
INSERT INTO Usuario(Nickname, Password, Avatar) VALUES("searlese", "prueba123", "./avatars/4.PNG");
INSERT INTO Usuario(Nickname, Password, Avatar) VALUES("abiisnn", "prueba123", "./avatars/5.png");


INSERT INTO Publicacion(Nombre, Fecha) VALUES("Imagenes de Perritos", "2019-03-14 10:00:00");
INSERT INTO Publicacion(Nombre, Fecha) VALUES("Discusiones sobre el Amor", "2019-03-01 11:00:00");
INSERT INTO Publicacion(Nombre, Fecha) VALUES("Venta y Compra de Carros", "2019-02-22 12:00:00");
INSERT INTO Publicacion(Nombre, Fecha) VALUES("Gatitos haciendo cosas", "2019-03-14 12:00:00");
INSERT INTO Publicacion(Nombre, Fecha) VALUES("Oso panda", "2019-03-18 12:50:00");

INSERT INTO Comentario(Fecha, Texto, Imagen, IdUsuario, IdPublicacion) VALUES("2019-03-14 10:00:00", "Prueba de texto", "./fotos/1.png", 1, 1);
INSERT INTO Comentario(Fecha, Texto, Imagen, IdUsuario, IdPublicacion) VALUES("2019-03-16 12:00:00", "Otra prueba de texto", null, 2, 1);
INSERT INTO Comentario(Fecha, Texto, Imagen, IdUsuario, IdPublicacion) VALUES("2019-03-01 13:00:00", "El amor es una magia. Una simple fantasía. Es como un sueño. Y al fin lo encontré", null, 3, 2);
INSERT INTO Comentario(Fecha, Texto, Imagen, IdUsuario, IdPublicacion) VALUES("2019-02-22 14:00:00", "Se venda $$ Ford Fiesta Ikon 2004 45 mil a tratar. Interesados comentar este post.", "./fotos/4.png", 1, 3);
INSERT INTO Comentario(Fecha, Texto, Imagen, IdUsuario, IdPublicacion) VALUES("2019-03-15 19:57:00", "Doggo wow", "./fotos/5.png", 3, 1);
INSERT INTO Comentario(Fecha, Texto, Imagen, IdUsuario, IdPublicacion) VALUES("2019-03-15 13:00:00", "Perritos en la nave del amor", "./fotos/6.png", 1, 1);
INSERT INTO Comentario(Fecha, Texto, Imagen, IdUsuario, IdPublicacion) VALUES("2019-03-14 18:40:10", "Este post es para publicar imagenes de gatitos haciendo cosas jaja", "./fotos/7.png", 5, 4);
INSERT INTO Comentario(Fecha, Texto, Imagen, IdUsuario, IdPublicacion) VALUES("2019-03-14 18:40:10", "¿Los osos panda estan en peligro de extincion?", "./fotos/8.png", 4, 5);