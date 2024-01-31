import { Route, Routes, useLocation } from 'react-router-dom';
import Explore from './Explore';
import Login from './Login';
import Error from '../components/Error';
import Restaurant from "./Restaurant";
import Register from "./Register";
import UserRegister from "./UserRegister";
import RestaurantRegister from "./RestaurantRegister";
import Landing from "./Landing";
import RestaurantProfile from "./RestauranteProfile";
import UserProfile from "./UserProfile";
import Verification from "./Verification";
import Verify from "./Verify";

export default function RouteMapper() {
    return (
        <div>
            <Routes>

                <Route path='*' element={<Error error={404} />} />

                <Route path='/login' element={<Login />} />
                <Route path='/register' element={<Register />} />
                <Route path='/user-register' element={<UserRegister />} />
                <Route path='/rest-register' element={<RestaurantRegister />} />
                <Route path='/rest-profile' element={<RestaurantProfile />} />
                <Route path='/profile' element={<UserProfile />} />
                <Route path='/explore' element={<Explore />} />
                <Route path='/' element={<Landing />} />
                <Route path='/verification' element={<Verification />} />
                <Route path={`/restaurants/:id`} element={<Restaurant />} />
                <Route path={`/verify`} element={<Verify />} />
            </Routes>
        </div>
    );
}