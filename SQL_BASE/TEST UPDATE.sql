SET @in_success = FALSE;

CALL updateData(
    'newUser1',
    'Carlos User',
    'USAS',
    1.75,
    2000,
    2000,
    2000,
    2000,
    2000,
    'Ganar Peso',
    'Masculino',
    'Pasado de peso',
    'Bastante Activo',
    @in_success
);

SELECT @in_success;