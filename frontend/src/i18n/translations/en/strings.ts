import {StringCollection} from '../../types';

let enCollection: StringCollection = {
    header: {
        explore: 'Explore',
        logout: 'Log out',
        profile: 'My profile',
        login: 'Log in',
        signup: 'Register',
        myRestaurant: 'My restaurant'
    },
    verification: {
        success: 'Last step and we are done...',
        message: 'A verification email was sent to your email address, please check your inbox and follow the instructions.',
        goBack: 'Back home'
    },
    pageTitles: {
        login: 'Log in',
        register: 'Register',
        explore: 'Explore',
        registerChoice: 'What are you?',
        home: 'Home',
        profile: 'Profile'
    },
    register: {
        title: 'Register',
        subtitle: 'Register your restaurant by completing the following information',
        userSubtitle: 'Please complete the following form to register',
        username: 'Username',
        email: 'Email',
        phone: 'Phone',
        name: "Restaurant's name",
        address: 'Address',
        neighborhood: 'Neighborhood',
        password: 'Password',
        cuisine: 'Cuisine type',
        price: 'Average price',
        reservation: 'Do you want to accept booking?',
        capacity: 'What booking capacity do you have?',
        capacityBook: 'Booking capacity',
        usernamePlaceholder: 'Enter your username',
        emailPlaceholder: 'Enter your email',
        phonePlaceholder: 'Enter phone',
        namePlaceholder: "Enter restaurant's name",
        addressPlaceholder: 'Enter address',
        neighborhoodPlaceholder: 'Enter neighborhood',
        passwordPlaceholder: 'Enter your password',
        new: 'Lastest news',
        about: 'About the restaurant',
        registerButton: 'Register',
        save: 'Save',
        invalidToken: 'Invalid token',
        validToken: 'Your account has been confirmed. Welcome to Spoon!',
        errors: {
            email: {
                required: 'You must enter your email',
                minLength: 'Mail must have between {minLength} and {maxLength} characters',
                maxLength: 'Mail must have between {minLength} and {maxLength} characters',
                validate: 'Mail is already in use'
            },
            password: {
                required: 'You must enter your password',
                validate: 'Passwords must match',
                minLength: 'Password must have between {minLength} and {maxLength} characters',
                maxLength: 'Password must have between {minLength} and {maxLength} characters',
            },
            username: {
                required: 'The username is required',
                pattern: 'The username can only have letters and numbers',
                minLength: 'The username must have between {minLength} and {maxLength} characters',
                maxLength: 'The username must have between {minLength} and {maxLength} characters',
                validate: 'Username is already in use'

            },
            name: {
                required: 'This field can not be blank',
                pattern: 'You must enter a valid name, only letters and numbers are allowed',
                minLength: 'The name must have between {minLength} and {maxLength} characters',
                maxLength: 'The name must have between {minLength} and {maxLength} characters',
                validate: 'You must enter a valid name, only letters and numbers are allowed'

            },
            phone: {
                required: 'The phone number is required',
                minLength: 'The phone number must have 10 digits (example: 1122334455)',
                maxLength: 'The phone number must have 10 digits (example: 1122334455)',

            },
            address: {
                required: 'The restaurant address is required',
                pattern: 'You must enter a valid address of the type "Street 1111"',
            },
            neighborhood: {
                required: 'You must enter a valid neighborhood',
                validate: 'You must enter a valid neighborhood',
            },
            price: {
                validate: 'The value must be greater than or equal to 1'
            },
            capacity: {
                validate: 'The value must be greater than or equal to 1'
            },
            cuisine: {
                validate: 'The cuisine type is required'
            },
            registerFail: 'Email or password is incorrect'
        }
    },
    login: {
        title: 'Log in',
        email: 'Email',
        emailPlaceholder: 'Enter your email',
        password: 'Password',
        passwordPlaceholder: 'Enter your password',
        rememberMe: 'Remember me',
        loginButton: 'LogIn',
        signupMessage: 'Do not have an account yet?',
        signupButton: 'Create an account.',
        errors: {
            email: {
                required: 'You must enter your email',
                minLength: 'Mail must have between {minLength} and {maxLength} characters',
                maxLength: 'Mail must have between {minLength} and {maxLength} characters',
                validate: 'Mail is already in use'
            },
            password: {
                required: 'You must enter your password',
                validate: 'Passwords must match',
                minLength: 'Password must have between {minLength} and {maxLength} characters',
                maxLength: 'Password must have between {minLength} and {maxLength} characters',
            },
            loginFail: 'Email or password is incorrect',
            userDisabled: 'Email not verified, please check your inbox'
        }
    },
    noData: {
        restaurantNotFoundTitle: 'No restaurants match your search',
        noReviews: 'There are no reviews yet',
        noBookings: 'There are no bookings yet',
        noFavorites: 'There are no favorites yet'
    },
    filter: {
        title: 'Filters',
        name: 'Name',
        search: 'Search',
        cuisine: 'Cuisine type',
        orderBy: 'Order by',
        neighborhood: 'Neighborhood',
        minPrice: 'Min. price',
        maxPrice: 'Max. price',
        button: 'Search',
        everyNeighborhood: 'Every neighborhood',
        everyCuisine: 'Every cuisine type'
    },
    filterInfo: {
        query: 'Name',
        filtering: 'Filtering by cuisine:',
        location: 'in',
        search: 'Showing results for:',
        clear: 'Clear'
    },
    restaurant: {
        ownerCardTitle: 'Owner',
        descriptionTitle: 'Description',
        priceText: 'Average price',
        edit: 'Edit',
        booking: 'Book a table',
        menu: 'Menu',
        reviews: 'Reviews',
        review: 'Review',
        profile: 'Profile',
        picture: 'Picture',
        myPicture: 'Pictures',
        bookings: 'Bookings',
        addReview: 'Add review',
        addAnswer: 'Add answer',
        favorites: 'Favorites',
        uploadImage: 'Upload your pictures',
        removeImages: 'Remove all pictures',
        updateImage: 'Update',
        removeImage: 'Remove',
        messageAlert: 'Your profile has been successfully modified! '
    },
    menu: {
        starter: 'Starter',
        main: 'Main',
        dessert: 'Dessert',
        dishName: 'Dish name',
        description: 'Description',
        price: 'Price',
        addDish: 'Add dish',
        delete: 'Delete',
        error: {
            nameDish: {
                required: 'You must enter the dish name',
                minLength: 'Must have between {minLength} and {maxLength} characters',
                maxLength: 'Must have between {minLength} and {maxLength} characters',
            },
            price: {
                required: 'The value must be greater than or equal to 1'},
            description: {
                required: 'You must enter the description',
                minLength: 'Description must have between {minLength} and {maxLength} characters',
                maxLength: 'Description must have between {minLength} and {maxLength} characters',
            },
        },
    },
    landing: {
        home: 'Home',
        hello: 'Hey {0}!',
        search: 'Search',
        title: 'Discover and book the best restaurant',
        noRest: 'Whoops! There are no restaurants available right now',
        bestRated: 'Best rated restaurants',
    },
    errors: {
        pageNotFoundTitle: 'Whoops! It seems that this table is not set.',
        pageNotFoundSubtitle: 'Return to home screen',
    },
    booking: {
        title: 'Book a table',
        date:'Choose your date',
        time:'Choose your time',
        date2: 'Date',
        time2: 'Time',
        dinners:'Number of dinners',
        book: 'Book now',
        user: 'User',
        restaurant: 'Restaurant',
        status: 'Status',
        diners: 'Diners',
        address: 'Address',
        cancel: 'Cancel',
        confirm: 'Confirm',
        ok: 'Ok',
        confirmed: 'Confirmed',
        waiting: 'Waiting',
        cancelBooking: 'Cancel booking',
        question: 'Are you sure you want to cancel your booking?',
        messageConfirm: '\n' +
            'Your booking has been added! In your profile you can see when the restaurant confirms it',
        error: {
            date: {
                required: 'You must enter the date'},
            time: {
                required: 'You must enter the time'},
            dinners: {
                required: 'You must enter the amount of dinners'},
        },

    },
    reviews: {
        title: 'Add a review',
        rating:'Rating',
        message: 'Review',
        review: 'Add review',
        addanswer: 'Add answer',
        answer: 'Answer',
        error: {
            message: {
                required: 'You must enter a review for the restaurant',
                minLength: 'The review must have between {minLength} and {maxLength} characters',
                maxLength: 'The review must have between {minLength} and {maxLength} characters'
            },
            rating: {
                required: 'You must select a rating'},
        },


    },
};

export {enCollection};
