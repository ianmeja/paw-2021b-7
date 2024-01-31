import { LocalizedStringsMethods } from 'react-localization';

export interface ErrorMessageInterface {
    max?: string;
    min?: string;
    maxLength?: string;
    minLength?: string;
    required?: string;
    pattern?: string;
    onBlur?: string;
    disabled?: string;
    value?: string;
    onChange?: undefined;
    validate?: string | undefined;
    valueAsNumber?: undefined;
    valueAsDate?: undefined;
    setValueAs?: undefined;
    shouldUnregister?: undefined;
    deps?: undefined;
}

interface HeaderStrings {
    logout: string;
    profile: string;
    explore: string;
    login: string;
    signup: string;
    myRestaurant: string;
}

interface VerificationStrings {
    success: string;
    message: string;
    goBack:string;
}

interface PageTitlesString {
    login: string;
    register: string;
    explore: string;
    registerChoice: string;
    home: string;
    profile: string;
}

interface RegisterStrings {
    title: string;
    subtitle: string;
    userSubtitle: string;
    email: string;
    username: string;
    phone: string;
    name: string;
    address: string;
    neighborhood: string;
    cuisine: string;
    price: string;
    reservation: string;
    capacity: string;
    capacityBook: string;
    usernamePlaceholder: string;
    emailPlaceholder: string;
    password: string;
    passwordPlaceholder: string;
    phonePlaceholder: string;
    registerButton: string;
    save: string;
    namePlaceholder: string;
    addressPlaceholder: string;
    neighborhoodPlaceholder: string;
    about: string;
    new: string;
    invalidToken: string;
    validToken: string;
    errors: {
        email: ErrorMessageInterface;
        password: ErrorMessageInterface;
        username: ErrorMessageInterface;
        name: ErrorMessageInterface;
        phone: ErrorMessageInterface;
        address: ErrorMessageInterface;
        price: ErrorMessageInterface;
        capacity: ErrorMessageInterface;
        cuisine: ErrorMessageInterface;
        neighborhood: ErrorMessageInterface;
        registerFail: string;

    };
}

interface LogInStrings {
    title: string;
    email: string;
    emailPlaceholder: string;
    password: string;
    passwordPlaceholder: string;
    rememberMe: string;
    loginButton: string;
    signupMessage: string;
    signupButton: string;
    errors: {
        email: ErrorMessageInterface;
        password: ErrorMessageInterface;
        loginFail: string;
        userDisabled: string;
    };
}

interface FilterStrings {
    title: string;
    name: string;
    search: string;
    cuisine: string;
    orderBy: string;
    neighborhood: string;
    minPrice: string;
    maxPrice: string;
    button: string;
    everyNeighborhood: string;
    everyCuisine: string;
}

interface FilterInfoStrings {
    query: string;
    location: string;
    search: string;
    clear: string;
    filtering: string;
}

interface RestaurantStrings {
    ownerCardTitle: string;
    descriptionTitle: string;
    priceText: string;
    edit: string;
    booking: string,
    menu: string;
    reviews: string;
    review: string;
    profile: string;
    picture: string;
    myPicture: string;
    bookings: string;
    addReview: string;
    addAnswer: string;
    favorites: string;
    uploadImage: string;
    removeImages: string;
    updateImage: string;
    removeImage: string;
    messageAlert: string;
}

interface MenuStrings{
    starter: string;
    main: string;
    dessert: string;
    dishName: string;
    description: string;
    price: string;
    addDish: string;
    delete: string;
    error:{
        nameDish:ErrorMessageInterface;
        price:ErrorMessageInterface;
        description:ErrorMessageInterface;
    }
}

interface LandingStrings {
    home: string;
    hello: string;
    search: string;
    title: string;
    noRest: string;
    bestRated: string;
}

interface BookingsStrings {
    user: string;
    restaurant: string;
    status: string;
    diners: string;
    address: string;
    cancel: string;
    title: string;
    time: string;
    date: string;
    time2: string;
    date2: string;
    dinners: string;
    book: string;
    confirm: string;
    confirmed: string;
    ok: string;
    waiting: string;
    question: string;
    cancelBooking: string;
    messageConfirm: string;
    error:{
        date:ErrorMessageInterface;
        time:ErrorMessageInterface;
        dinners:ErrorMessageInterface;
    }
}

interface ReviewsStrings {
    title: string;
    rating: string;
    message: string;
    review: string;
    addanswer: string,
    answer: string;
    error:{
        message:ErrorMessageInterface;
        rating:ErrorMessageInterface;
    }
}

export interface NoDataString {
    restaurantNotFoundTitle: string;
    noReviews: string;
    noBookings: string;
    noFavorites: string;
}

export interface ErrorsString {
    pageNotFoundTitle: string;
    pageNotFoundSubtitle: string;
}

export interface StringCollection {
    header: HeaderStrings;
    pageTitles: PageTitlesString;
    register: RegisterStrings;
    login: LogInStrings;
    noData: NoDataString;
    filter: FilterStrings;
    filterInfo: FilterInfoStrings;
    restaurant: RestaurantStrings;
    landing: LandingStrings;
    menu: MenuStrings;
    errors: ErrorsString;
    booking:BookingsStrings;
    reviews:ReviewsStrings;
    verification: VerificationStrings;
}

export interface IStrings extends LocalizedStringsMethods {
    collection: StringCollection;
}