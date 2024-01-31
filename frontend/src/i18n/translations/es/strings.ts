import {StringCollection} from '../../types';

let esCollection: StringCollection = {
    header: {
        explore: 'Explorar',
        logout: 'Cerrar sesión',
        profile: 'Mi perfil',
        login: 'Iniciar sesión',
        signup: 'Registrarse',
        myRestaurant: 'Mi restaurante'
    },
    verification: {
        success: 'Un último pasito y estamos...',
        message: 'Un correo de verificación fue enviado a su dirección de mail, por favor verifique su casilla de entrada y siga las instrucciones.',
        goBack: 'Volver a inicio'
    },
    pageTitles: {
        login: 'Iniciar sesión',
        register: 'Registrarse',
        explore: 'Explorar',
        registerChoice: '¿Qué eres?',
        home: 'Inicio',
        profile: 'Perfil'
    },
    register: {
        title: 'Register',
        subtitle: 'Registre su restaurante completando los siguientes datos',
        userSubtitle: 'Por favor complete el siguiente formulario para registrarse.',
        username: 'Usuario',
        email: 'Correo electrónico',
        phone: 'Télefono',
        name: 'Nombre del restaurante',
        address: 'Dirección',
        neighborhood: 'Barrio',
        password: 'Contraseña',
        cuisine: 'Tipo de cocina',
        price: 'Precio promedio',
        reservation: '¿Quieres aceptar reservas?',
        capacity: '¿QUe capacidad de reserva tienes?',
        capacityBook: 'Capacidad',
        usernamePlaceholder: 'Ingresa tu usuario',
        emailPlaceholder: 'Ingresa tu email',
        passwordPlaceholder: 'Ingresa tu contraseña',
        neighborhoodPlaceholder: 'Ingrese barrio',
        phonePlaceholder: 'Ingrese télefono',
        namePlaceholder: 'Ingrese nombre del restaurante',
        addressPlaceholder: 'Ingrese dirección',
        new: 'Ultima novedad',
        about: 'Sobre el restaurante',
        registerButton: 'Registrarse',
        save: 'Guardar',
        invalidToken: 'Token invalido',
        validToken: 'Se ha confirmado su cuenta ¡Bienvenido a Spoon!',
        errors: {
            email: {
                required: 'Es obligatorio ingresar un email',
                minLength: 'El mail debe tener entre {minLength} y {maxLength} caracteres',
                maxLength: 'El mail debe tener entre {minLength} y {maxLength} caracteres',
                validate: 'El mail ingresado ya se encuentra en uso'
            },
            password: {
                required: 'Es obligatorio ingresar una contraseña',
                minLength: 'La contraseña debe tener entre {minLength} y {maxLength} caracteres',
                maxLength: 'La contraseña debe tener entre {minLength} y {maxLength} caracteres',
                validate: 'Las contraseñas deben ser iguales'
            },
            username: {
                required: 'El nombre usuario es obligatorio',
                pattern: 'El nombre usuario solo puede tener letras y numeros',
                minLength: 'El nombre usuario debe tener entre {minLength} y {maxLength} caracteres',
                maxLength: 'El nombre usuario debe tener entre {minLength} y {maxLength} caracteres',
                validate: 'El nombre usuario ingresado ya se encuentra en uso'
            },
            name: {
                required: 'Este campo no puede estar vacío',
                pattern: 'Debe ingresar un nombre válido, solo se permiten letras y números',
                minLength: 'El nombre debe tener entre {minLength} y {maxLength} caracteres',
                maxLength: 'El nombre debe tener entre {minLength} y {maxLength} caracteres',
                validate: 'Debe ingresar un nombre válido, solo se permiten letras y numeros'
            },
            phone: {
                required: 'Debe tener exactamente 10 dígitos (ejemplo: 1122334455)',
                minLength: 'El número de teléfono debe tener 10 dígitos (ejemplo: 1122334455)',
                maxLength: 'El número de teléfono debe tener 10 dígitos (ejemplo: 1122334455)',
            },
            address: {
                required: 'Es obligatorio ingresar una dirección',
                pattern: 'Debe ingresar una dirección válida del tipo "Calle 1111"',
            },
            neighborhood: {
                required: 'Debe ingresar un barrio válido',
                validate: 'Debe ingresar un barrio válido',
            },
            price: {
                validate: 'El valor debe ser superior o igual a 1'
            },
            capacity: {
                validate: 'El valor debe ser superior o igual a 1'
            },
            cuisine: {
                validate: 'El tipo de cocina del restaurante es obligatorio'
            },

            registerFail: 'El usuario o la contraseña ingresada son incorrectos'
        }
    },
    login: {
        title: 'Iniciar Sesión',
        email: 'Email*',
        emailPlaceholder: 'Ingresa tu email',
        password: 'Contraseña*',
        passwordPlaceholder: 'Ingresa tu contraseña',
        rememberMe: 'Recordar mi sesión',
        loginButton: 'Iniciar Sesión',
        signupMessage: '¿No tienes una cuenta aún?',
        signupButton: 'Crea una cuenta.',
        errors: {
            email: {
                required: 'Es obligatorio ingresar un email',
                minLength: 'El mail debe tener entre {minLength} y {maxLength} caracteres',
                maxLength: 'El mail debe tener entre {minLength} y {maxLength} caracteres',
                validate: 'El mail ingresado ya se encuentra en uso'
            },
            password: {
                required: 'Es obligatorio ingresar una contraseña',
                minLength: 'La contraseña debe tener entre {minLength} y {maxLength} caracteres',
                maxLength: 'La contraseña debe tener entre {minLength} y {maxLength} caracteres',
                validate: 'Las contraseñas deben ser iguales'
            },
            loginFail: 'El usuario o la contraseña ingresada son incorrectos',
            userDisabled: 'Email no verificado, por favor chequee su casilla de corre'
        }
    },
    noData: {
        restaurantNotFoundTitle: 'No hay restaurantes que coincidan con tu búsqueda',
        noReviews: 'Todavía no hay reseñas',
        noBookings: 'Todavía no hay reservas',
        noFavorites: 'Todavía no hay favoritos'
    },
    filter: {
        title: 'Filtros',
        name: 'Nombre',
        search: 'Buscar',
        cuisine: 'Tipos de cocinas',
        orderBy: 'Ordenar por',
        neighborhood: 'Barrio',
        minPrice: 'Precio mínimo',
        maxPrice: 'Precio máximo',
        button: 'Buscar',
        everyNeighborhood: 'Todos los barrios',
        everyCuisine: 'Todas los tipos de cocinas'
    },
    filterInfo: {
        query: 'Nombre',
        filtering: 'Filtrando por cocina:',
        location: 'en',
        search: 'Mostrando resultados para:',
        clear: 'Limpiar'
    },
    restaurant: {
        ownerCardTitle: 'Dueño',
        descriptionTitle: 'Descripcion',
        priceText: 'Precio promedio',
        edit: 'Editar',
        booking: 'Reservar',
        menu: 'Menú',
        reviews: 'Reseñas',
        review: 'Reseña',
        profile: 'Perfil',
        picture: 'Imagen',
        myPicture: 'Imágenes',
        bookings: 'Reservas',
        addReview: 'Añadir reseña',
        addAnswer: 'Añadir respuesta',
        favorites: 'Favoritos',
        uploadImage: 'Subir imágenes',
        removeImages: 'Eliminar todas las imágenes',
        updateImage: 'Actualizar',
        removeImage: 'Eliminar',
        messageAlert: '¡Se ha modificado su perfil con exito! '
    },
    menu: {
        starter: 'Entrada',
        main: 'Principal',
        dessert: 'Postre',
        dishName: 'Nombre del plato',
        description: 'Descripción',
        price: 'Precio',
        addDish: 'Añadir plato',
        delete: 'Eliminar',
        error: {
            nameDish: {
                required: 'Debe ingresar un nombre',
                minLength: 'Debe ser entre {minLength} y {maxLength} caracteres',
                maxLength: 'Debe ser entre {minLength} y {maxLength} caracteres',
            },
            price: {
                required: 'El valor debe ser superior o igual a 1'},
            description: {
                required: 'You must enter the name',
                minLength: 'La description del plato debe ser entre {minLength} y {maxLength} caracteres',
                maxLength: 'La description del plato debe ser entre {minLength} y {maxLength} caracteres',
            },
        },
    },
    landing: {
        home: 'Inicio',
        hello: '¡Hola {0}!',
        search: 'Buscar',
        title: 'Descubrí y reservá el mejor restaurante',
        noRest: '¡Ups! En este momento no hay restaurantes',
        bestRated: 'Restaurantes mejores valorados',
    },
    errors: {
        pageNotFoundTitle: '¡Ups! Parece que esta mesa no esta puesta',
        pageNotFoundSubtitle: 'Volver a la página principal'
    },
    booking: {
        title: 'Reservar mesa',
        date:'Elegí tu fecha',
        time:'Elegí tu hora',
        date2: 'Fecha',
        time2: 'Horario',
        dinners:'Cantidad de comensales',
        book: 'Reserva ahora',
        user: 'Usuario',
        restaurant: 'Restaurante',
        status: 'Estado',
        diners: 'Comensales',
        address: 'Dirección',
        cancel: 'Cancelar',
        confirm: 'Confirmar',
        ok: 'Ok',
        confirmed: 'Confirmado',
        waiting: 'En espera',
        cancelBooking: 'Cancelar reserva',
        question: '¿Seguro que desea cancelar la reserva?',
        messageConfirm: 'Se añadio su reserva! En su perfil puede ver cuando el restaurante la confirme',
        error: {
            date: {
                required: 'Es obligatorio ingresar la fecha'
            },
            time: {
                required: 'Es obligatorio ingresar la hora'
            },
            dinners: {
                required: 'Es obligatorio la cantidad de comensales'},
        },

    },
    reviews: {
        title: 'Añade reseña',
        rating:'Rating',
        message: 'Reseña',
        review: 'Añadir reseña',
        answer: 'Respuesta',
        addanswer: 'Añadir respuesta',
        error: {
            message: {
                required: 'Es obligatorio agregar una reseña',
                minLength: 'La reseña debe ser entre {minLength} y {maxLength} caracteres',
                maxLength: 'La reseña debe ser entre {minLength} y {maxLength} caracteres'
            },
            rating: {
                required: 'Es obligatorio dejar rating'},
        },

    },
};

export {esCollection};
